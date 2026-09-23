package edu.spu.se411.lab06_logging;

import edu.spu.se411.lab06_logging.exceptions.InsufficientFundsException;
import edu.spu.se411.lab06_logging.model.WalletAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Application is starting...");

        WalletAccount account = new WalletAccount(1000);

        try {
            account.withdraw(1500);
        } catch (InsufficientFundsException | IllegalArgumentException e) {
            logger.error("Exception thrown while withdrawing money.", e);
        }

        try {
            account.deposit(-100);
        } catch (IllegalArgumentException e) {
            logger.error("Exception thrown while depositing money.", e);
        }

        // Successful operations are included so DEBUG transaction messages can also be tested.
        try {
            account.deposit(250);
            account.withdraw(100);
        } catch (InsufficientFundsException | IllegalArgumentException e) {
            logger.error("Unexpected wallet operation failure.", e);
        }

        logger.info("Application is ending...");
    }
}
