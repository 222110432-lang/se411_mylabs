# Lab 11 — Threads and double spending
Run `mvn clean test exec:java` with Java 17+.
Ten accounts start at SAR 1,000 each. Each mode runs 10,000 transactions using 10,000 distinct platform threads in batches of 100. All threads are joined before totals are calculated.

The unsafe mode deliberately yields between reading and writing a balance, making lost updates easier to observe. The safe mode uses one static synchronized method for the whole transfer. Synchronizing an individual Transaction instance would not protect accounts shared by other Transaction instances.

Money is stored as integer halalas to distinguish race conditions from floating-point errors. The safe total must remain exactly SAR 10,000 and no balance may become negative. Unsafe results vary; a correct total in one run does not prove thread safety.
logs/app.log contains lifecycle events and results, using Java's built-in logger.
