package edu.psu.se411.lab09_inventory;
import edu.psu.se411.lab09_inventory.model.*;
import edu.psu.se411.lab09_inventory.strategy.*;
public class InventoryChecks {
 public static void main(String[] args) {
  Inventory<Item> inventory=new Inventory<>();
  Book book=new Book("B1","Concurrency in Java","Brian Goetz");
  inventory.add(book); inventory.add(new ElectronicDevice("E1","ThinkPad","Laptop"));
  check(inventory.findItems(new SearchByAuthor("Brian Goetz")).size()==1,"author across mixed inventory");
  check(inventory.findItems(new SearchByCategory("Laptop")).size()==1,"category across mixed inventory");
  check(inventory.findItems(new SearchByName("THINKPAD")).size()==1,"name");
  check(inventory.findItems(new SearchById("B1")).get(0)==book,"id");
  check(inventory.findItems(new SearchById("missing")).isEmpty(),"no match");
  check(inventory.findItems(i->i instanceof Book).size()==1,"lambda");
  Inventory<Book> books=new Inventory<>(); books.add(book);
  check(books.findItems(new SearchByName("Concurrency in Java")).size()==1,"contravariant strategy");
  try { inventory.getAll().clear(); throw new AssertionError("Mutable inventory view"); } catch(UnsupportedOperationException expected) {}
  check(inventory.remove(book)&&!inventory.remove(book)&&inventory.getAll().size()==1,"remove");
  System.out.println("PASS: 9 inventory checks");
 }
 static void check(boolean ok,String message) { if(!ok) throw new AssertionError(message); }
}
