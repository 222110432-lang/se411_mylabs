package edu.psu.se411.lab09_inventory;
import java.util.logging.*;
import edu.psu.se411.lab09_inventory.model.*;
import edu.psu.se411.lab09_inventory.strategy.*;
public class App {
 public static void displayInventory(Inventory<?> inventory) { inventory.getAll().forEach(System.out::println); }
 public static void main(String[] args) {
  Logger logger=AppLog.open();
  logger.info("Application is starting...");
  try {
   Inventory<Item> inventory=new Inventory<>();
   Book book=new Book("B1","Concurrency in Java","Brian Goetz");
   inventory.add(book);
   inventory.add(new Book("B2","Effective Java","Joshua Bloch"));
   inventory.add(new ElectronicDevice("E1","ThinkPad","Laptop"));
   inventory.add(new ElectronicDevice("E2","Galaxy","Phone"));
   displayInventory(inventory);
   System.out.println("By id: "+inventory.findItems(new SearchById("B1")));
   System.out.println("Book by name: "+inventory.findItems(new SearchByName("Concurrency in Java")));
   System.out.println("Device by name: "+inventory.findItems(new SearchByName("ThinkPad")));
   System.out.println("By author: "+inventory.findItems(new SearchByAuthor("Brian Goetz")));
   System.out.println("By category: "+inventory.findItems(new SearchByCategory("Phone")));
   System.out.println("Lambda: "+inventory.findItems(item -> item.getName().startsWith("E")));
   inventory.remove(book);
   System.out.println("After removing B1:"); displayInventory(inventory);
  } catch(RuntimeException e) { logger.log(Level.SEVERE,"Inventory operation failed",e); throw e; }
  finally { logger.info("Application is closing..."); AppLog.close(logger); }
 }
}
