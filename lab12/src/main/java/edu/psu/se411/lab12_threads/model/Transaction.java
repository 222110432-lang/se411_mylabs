package edu.psu.se411.lab12_threads.model;
import java.util.Objects;
import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;
public final class Transaction {
 private final String id;
 private final CryptoAccount sender,receiver;
 private final Double amount;
 public Transaction(CryptoAccount sender,CryptoAccount receiver,Double amount) { this("transaction",sender,receiver,amount); }
 public Transaction(String id,CryptoAccount sender,CryptoAccount receiver,Double amount) {
  this.id=Objects.requireNonNull(id); this.sender=Objects.requireNonNull(sender); this.receiver=Objects.requireNonNull(receiver);
  if(sender==receiver || sender.getId().equals(receiver.getId())) throw new IllegalArgumentException("Two distinct account ids are required");
  if(CryptoAccount.toCents(amount)<=0) throw new IllegalArgumentException("Amount must be at least 0.01");
  this.amount=amount;
 }
 public void execute() throws InsufficientBalanceException {
 CryptoAccount first=sender.getId().compareTo(receiver.getId())<0?sender:receiver;
  CryptoAccount second=first==sender?receiver:sender;
  first.getLock().lock();
  try {
   second.getLock().lock();
   try { executeUnprotected(); } finally { second.getLock().unlock(); }
  } finally { first.getLock().unlock(); }
 }
 private void executeUnprotected() throws InsufficientBalanceException {
  // Check destination overflow before removing any money.
  Math.addExact(receiver.getBalanceCents(),CryptoAccount.toCents(amount));
  sender.retrieveFromBalance(amount);
  receiver.addToBalance(amount);
 }
 public static synchronized void executeSynchronized(Transaction transaction) throws InsufficientBalanceException {
  transaction.executeUnprotected();
 }
 public String getId() { return id; }
 public CryptoAccount getSender() { return sender; }
 public CryptoAccount getReceiver() { return receiver; }
 public Double getAmount() { return amount; }
}
