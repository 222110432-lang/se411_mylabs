package edu.psu.se411.lab12_threads;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.*;
import edu.psu.se411.lab12_threads.model.*;
import edu.psu.se411.lab12_threads.exceptions.*;
public final class Benchmark {
 public static void run(String pool,String[] args) throws Exception {
  int count=args.length>0?Integer.parseInt(args[0]):1_000_000;
  String mode=args.length>1?args[1]:"locks";
  if(count<1 || !(mode.equals("locks")||mode.equals("synchronized"))) throw new IllegalArgumentException("Usage: [transaction-count] [locks|synchronized]");
  Logger logger=AppLog.open(); logger.info("Application is starting...");
  try {
   System.out.println("pool,mode,accounts,transactions,initial_sar,final_sar,rejected,peak_workers,elapsed_ms");
   for(int accountCount:new int[]{1000,10}) execute(pool,mode,accountCount,count,logger);
  } finally { logger.info("Application is closing..."); AppLog.close(logger); }
 }
 private static void execute(String pool,String mode,int n,int count,Logger logger) throws Exception {
  CryptoAccount[] accounts=new CryptoAccount[n];
  for(int i=0;i<n;i++) accounts[i]=new CryptoAccount(String.format("acc_%04d",i),1000.0);
  ThreadPoolExecutor executor=(ThreadPoolExecutor)(pool.equals("cached")?Executors.newCachedThreadPool():Executors.newFixedThreadPool(4));
  // Both pools use the same submission bound to avoid queuing a million tasks.
  Semaphore pending=new Semaphore(128);
  AtomicInteger rejected=new AtomicInteger(),errors=new AtomicInteger();
  Random random=new Random(411);
  long start=System.nanoTime();
  try {
   for(int i=0;i<count;i++) {
    int a=random.nextInt(n),b=(a+1+random.nextInt(n-1))%n;
    Transaction transaction=new Transaction("t"+i,accounts[a],accounts[b],(1+random.nextInt(1000))/100.0);
    pending.acquire();
    executor.execute(()->{
     try { if(mode.equals("locks")) transaction.execute(); else Transaction.executeSynchronized(transaction); }
     catch(InsufficientBalanceException e) { rejected.incrementAndGet(); }
     catch(RuntimeException e) { logger.log(Level.SEVERE,"Transfer failed",e); errors.incrementAndGet(); }
     finally { pending.release(); }
    });
   }
  } finally { executor.shutdown(); }
  if(!executor.awaitTermination(5,TimeUnit.MINUTES)) { executor.shutdownNow(); throw new IllegalStateException("Pool did not finish"); }
  long elapsed=(System.nanoTime()-start)/1_000_000;
  long total=Arrays.stream(accounts).mapToLong(CryptoAccount::getBalanceCents).sum();
  String result=String.format(Locale.ROOT,"%s,%s,%d,%d,%.2f,%.2f,%d,%d,%d",pool,mode,n,count,n*1000.0,total/100.0,rejected.get(),executor.getLargestPoolSize(),elapsed);
  System.out.println(result); logger.info(result);
  if(errors.get()!=0 || total!=n*100_000L || Arrays.stream(accounts).anyMatch(a->a.getBalanceCents()<0)) throw new AssertionError("Balance invariant or worker failure");
 }
}
