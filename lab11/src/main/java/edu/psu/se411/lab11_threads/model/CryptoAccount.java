package edu.psu.se411.lab11_threads.model;
import java.math.*;
import java.util.Objects;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;
public final class CryptoAccount {
 private final String id;
 private volatile long cents;
 
 public CryptoAccount(String id, Double balance) {
  this.id=Objects.requireNonNull(id);
  if(id.isBlank()) throw new IllegalArgumentException("Id is required");
  cents=toCents(balance);
 }
 public static long toCents(Double amount) {
  if(amount==null || !Double.isFinite(amount) || amount<0) throw new IllegalArgumentException("Amount must be finite and nonnegative");
  return BigDecimal.valueOf(amount).movePointRight(2).setScale(0,RoundingMode.HALF_UP).longValueExact();
 }
 // The complete transfer, not just these individual updates, must be protected.
 public void addToBalance(Double amount) {
  long next=Math.addExact(cents,toCents(amount));
  Thread.yield(); // Widen the race window in the intentionally unsafe demonstration.
  cents=next;
 }
 public void retrieveFromBalance(Double amount) throws InsufficientBalanceException {
  long value=toCents(amount);
  if(value>cents) throw new InsufficientBalanceException(id);
  long next=cents-value;
  Thread.yield();
  cents=next;
 }
 public String getId() { return id; }
 public Double getBalance() { return cents/100.0; }
 public long getBalanceCents() { return cents; }
}
