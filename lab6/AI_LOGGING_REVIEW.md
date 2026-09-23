# AI Logging Strategy Review

The logging strategy was reviewed and the following improvements were applied:

1. Use the required levels consistently:
   - `INFO` for application lifecycle events.
   - `DEBUG` for wallet creation and normal deposit/withdraw operations.
   - `WARN` when an `InsufficientFundsException` object is created.
   - `ERROR` when an exception reaches the main calling program.
2. Use parameterized SLF4J messages (`{}`) instead of string concatenation for logged values.
3. Pass the caught exception object to `logger.error(...)` so the log contains the exception and stack trace.
4. Keep operational events in the logger rather than using `System.out.println`.
5. Use a file appender so the execution history is retained in `logs/App/log4j/log.out`.

These changes preserve the event/level mapping required by Lab 06 while making the logging more useful for debugging and maintenance.
