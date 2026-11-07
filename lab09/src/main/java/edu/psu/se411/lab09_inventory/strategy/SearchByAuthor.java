package edu.psu.se411.lab09_inventory.strategy;
import java.util.Objects;
import edu.psu.se411.lab09_inventory.model.*;
public final class SearchByAuthor implements SearchStrategy<Item> {
 private final String value;
 public SearchByAuthor(String value) { this.value=Objects.requireNonNull(value); }
 @Override public boolean matches(Item item) { return item instanceof Book book && book.getAuthor().equalsIgnoreCase(value); }
}
