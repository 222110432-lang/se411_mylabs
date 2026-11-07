package edu.psu.se411.lab12_threads;
import java.io.IOException;
import java.nio.file.*;
import java.util.logging.*;
public final class AppLog {
 private AppLog() {}
 public static Logger open() {
  Logger logger = Logger.getLogger("lab12_threads");
  logger.setUseParentHandlers(false);
  try {
   Files.createDirectories(Path.of("logs"));
   FileHandler handler = new FileHandler("logs/app.log", true);
   handler.setFormatter(new SimpleFormatter());
   logger.addHandler(handler);
  } catch (IOException e) { throw new IllegalStateException("Cannot open application log", e); }
  return logger;
 }
 public static void close(Logger logger) { for (Handler h : logger.getHandlers()) { h.close(); logger.removeHandler(h); } }
}
