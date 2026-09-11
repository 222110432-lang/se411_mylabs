package edu.psu.se411;

import edu.psu.se411.exceptions.InsufficientFundsException;

public class Wallet {
    private double balance;

    public Wallet(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial wallet balance cannot be negative.");
        }
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void withdrawToBank(double amount, BankAccount bankAccount)
            throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Wallet balance: " + balance +
                    ", requested withdrawal: " + amount);
        }

        balance -= amount;
        bankAccount.deposit(amount);
    }
}
