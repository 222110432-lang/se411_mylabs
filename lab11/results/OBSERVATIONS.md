# Observed result
10 accounts, SAR 1,000 each; 10,000 transaction threads per mode.

- Unsafe final total: SAR 10,208.54 (incorrect; expected SAR 10,000).
- Synchronized final total: SAR 10,000.00 (correct).
- Elapsed: 727 ms unsafe; 741 ms synchronized.
- No insufficient-funds rejections in this run.

The unsafe implementation deliberately yields between balance reads and writes to expose lost updates. Each mode creates fresh accounts and reuses the same seeded transaction sequence. Timings and unsafe totals vary between runs. See comparison.csv for raw output.
