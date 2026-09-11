package edu.psu.se411;

import edu.psu.se411.exceptions.InsufficientFundsException;
import edu.psu.se411.exceptions.InvalidAgeException;

public class App {

    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Invalid age: user must be at least 18 years old.");
        }

        System.out.println("Age valid message.");
    }

    public static void main(String[] args) {
        System.out.println("Exercise 1: Custom InvalidAgeException");

        try {
            validateAge(20);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }

        try {
            validateAge(16);
        } catch (InvalidAgeException e) {
            System.out.println("Caught InvalidAgeException: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Exercise 2: Online Wallet");

        Wallet wallet = new Wallet(500.0);
        BankAccount bankAccount = new BankAccount(1000.0);

        try {
            wallet.withdrawToBank(200.0, bankAccount);
            System.out.println("Withdrawal successful.");
            System.out.println("Wallet balance: " + wallet.getBalance());
            System.out.println("Bank balance: " + bankAccount.getBalance());
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: " + e.getMessage());
        }

        try {
            wallet.withdrawToBank(400.0, bankAccount);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: " + e.getMessage());
        }
    }
}
