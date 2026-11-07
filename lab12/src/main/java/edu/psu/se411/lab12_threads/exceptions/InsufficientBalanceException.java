package edu.psu.se411.lab12_threads.exceptions;
public class InsufficientBalanceException extends Exception {
 public InsufficientBalanceException(String id) { super("Insufficient balance in account "+id); }
}
