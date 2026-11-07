# Observations
Measured on the user's Windows PC with OpenJDK 25, compiling for Java 17. Each row in benchmark.csv contains one million transactions. Fixed pool: 4 workers. Both pools: at most 128 submitted tasks in flight. One run per combination; other applications were running.

| Mode | Accounts | Fixed pool (ms) | Cached pool (ms) |
|---|---:|---:|---:|
| synchronized | 1000 | 408 | 1791 |
| synchronized | 10 | 374 | 1945 |
| ordered locks | 1000 | 584 | 1797 |
| ordered locks | 10 | 592 | 2013 |

All eight runs preserved the exact starting total and ended without negative balances. Some transfers with 10 accounts were rejected for insufficient funds; rejected counts differ because scheduling changes which transfers arrive first.

The fixed pool was faster in these runs. Cached pools created more workers (14–66), adding scheduling and coordination overhead. Ordered locks were not faster than the simple global synchronized baseline here: these transfers do very little work and extra lock operations have a cost. This does not show that synchronized is universally faster. A larger workload, different contention or repeated warmed-up measurements may produce different results.

Locking sender first and receiver second without ordering can deadlock for opposing transfers. Ordering both locks by immutable account id removes that circular wait.
