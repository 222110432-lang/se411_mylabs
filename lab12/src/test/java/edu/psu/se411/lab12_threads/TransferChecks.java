package edu.psu.se411.lab12_threads;
import java.util.concurrent.*;
import edu.psu.se411.lab12_threads.model.*;
import edu.psu.se411.lab12_threads.exceptions.*;
public class TransferChecks {
 public static void main(String[] args) throws Exception {
  CryptoAccount a=new CryptoAccount("a",1000.0), b=new CryptoAccount("b",1000.0);
  new Transaction(a,b,25.0).execute();
  check(a.getBalanceCents()==97500 && b.getBalanceCents()==102500,"successful transfer");
  try { new Transaction(a,b,2000.0).execute(); throw new AssertionError("Expected insufficient funds"); } catch(InsufficientBalanceException expected) {}
  check(a.getBalanceCents()==97500 && b.getBalanceCents()==102500,"failed transfer must not mutate");
  for(double amount:new double[]{-1,0,Double.NaN,Double.POSITIVE_INFINITY}) {
   try { new Transaction(a,b,amount); throw new AssertionError("Accepted invalid amount"); } catch(IllegalArgumentException expected) {}
  }
  try { new Transaction(a,a,1.0); throw new AssertionError("Accepted self-transfer"); } catch(IllegalArgumentException expected) {}
  ExecutorService pool=Executors.newFixedThreadPool(4);
  Future<?>[] futures=new Future<?>[4];
  for(int i=0;i<4;i++) {
   final boolean reverse=i%2==0;
   futures[i]=pool.submit(()->{
    for(int j=0;j<2000;j++) try { new Transaction(reverse?a:b,reverse?b:a,0.01).execute(); }
    catch(InsufficientBalanceException e) { throw new AssertionError(e); }
   });
  }
  pool.shutdown();
  for(Future<?> future:futures) future.get(30,TimeUnit.SECONDS);
  check(pool.awaitTermination(30,TimeUnit.SECONDS),"termination");
  check(a.getBalanceCents()==97500 && b.getBalanceCents()==102500,"opposing transfers/conservation");
  System.out.println("PASS: transfer validation, insufficient funds, 8000 opposing transfers, exact conservation");
 }
 static void check(boolean value,String message) { if(!value) throw new AssertionError(message); }
}
