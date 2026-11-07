# Lab 12 — Thread pools and ordered locks
Java 17+ and Maven. `mvn clean test`.

Fixed pool (4 workers): `mvn exec:java`
Cached pool: `mvn exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.CachedPoolApp`

Each program runs 1,000,000 transactions with 1,000 accounts, then with 10 accounts. Each account starts with SAR 1,000. A fixed random seed supplies the same transaction sequence for both pool types.
For the synchronized baseline add `-Dexec.args="1000000 synchronized"`. The default mode is locks.
For a quick smoke run use `-Dexec.args="10000 locks"`.

## Lock ordering
Each account owns a ReentrantLock. Transactions always acquire the lower account id first, then the higher, and release both in finally blocks. Without a common order, A-to-B could hold A while B-to-A holds B, causing deadlock. Distinct account objects must have distinct ids. Both balances are protected for the entire transfer.

## Comparing performance
Both pools use the same 128-task in-flight bound to keep resource use manageable; the cached pool itself remains newCachedThreadPool. This is a bounded-submission experiment, not a claim about unlimited cached pools. More accounts can allow more transfers on disjoint locks; 10 accounts cause more contention. Actual timings depend on scheduling, CPU load, JIT warm-up and logging. See results/benchmark.csv for measured runs. These are single runs, not statistically controlled benchmarks.
Java's built-in file logger writes lifecycle events and results to logs/app.log.
