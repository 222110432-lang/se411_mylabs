package edu.psu.se411.lab11_threads;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.*;
import edu.psu.se411.lab11_threads.model.*;
import edu.psu.se411.lab11_threads.exceptions.*;
public class App {
 public static void main(String[] args) throws InterruptedException {
  Logger logger=AppLog.open(); logger.info("Application is starting...");
  try {
   System.out.println("mode,accounts,transactions,initial_sar,final_sar,rejected,elapsed_ms");
   run(false,logger); run(true,logger);
  } finally { logger.info("Application is closing..."); AppLog.close(logger); }
 }
 static void run(boolean safe,Logger logger) throws InterruptedException {
  CryptoAccount[] accounts=new CryptoAccount[10];
  for(int i=0;i<10;i++) accounts[i]=new CryptoAccount("acc_"+i,1000.0);
  Random random=new Random(411);
  AtomicInteger rejected=new AtomicInteger(),errors=new AtomicInteger();
  long start=System.nanoTime();
  // 10,000 distinct platform threads, launched in 100-thread batches.
  for(int batch=0;batch<100;batch++) {
   CountDownLatch gate=new CountDownLatch(1);
   List<Thread> threads=new ArrayList<>();
   for(int j=0;j<100;j++) {
    int a=random.nextInt(10),b=(a+1+random.nextInt(9))%10;
    Transaction transaction=new Transaction(accounts[a],accounts[b],(1+random.nextInt(1000))/100.0);
    Thread thread=new Thread(()->{
     try {
      gate.await();
      if(safe) transaction.execute(); else transaction.executeUnprotected();
     } catch(InsufficientBalanceException e) { rejected.incrementAndGet(); }
       catch(InterruptedException e) { Thread.currentThread().interrupt(); errors.incrementAndGet(); }
       catch(RuntimeException e) { logger.log(Level.SEVERE,"Transfer failed",e); errors.incrementAndGet(); }
    });
    threads.add(thread); thread.start();
   }
   gate.countDown();
   for(Thread thread:threads) thread.join();
  }
  long total=Arrays.stream(accounts).mapToLong(CryptoAccount::getBalanceCents).sum();
  long elapsed=(System.nanoTime()-start)/1_000_000;
  String result=String.format(Locale.ROOT,"%s,10,10000,10000.00,%.2f,%d,%d",safe?"synchronized":"unsafe",total/100.0,rejected.get(),elapsed);
  System.out.println(result); logger.info(result);
  if(errors.get()!=0) throw new AssertionError("Unexpected worker failure");
  if(safe && (total!=1_000_000 || Arrays.stream(accounts).anyMatch(a->a.getBalanceCents()<0))) throw new AssertionError("Balance invariant failed");
 }
}
