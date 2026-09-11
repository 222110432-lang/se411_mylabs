# SE411 Lab 05 - Exception Handling

This Maven project implements both exercises from Lab 05.

## Exercise 1
- Custom `InvalidAgeException` in a dedicated exceptions package.
- `validateAge(int age)` throws the custom exception when age is below 18.
- Prints `Age valid message.` for an age of 18 or higher.

## Exercise 2
- `Wallet` supports withdrawing money to a `BankAccount`.
- Custom `InsufficientFundsException` is thrown when the withdrawal exceeds the wallet balance.

## Maven run sequence

```text
mvn clean
mvn package
mvn exec:java
```

The `exec-maven-plugin` main class is `edu.psu.se411.App`, matching the lab handout.
