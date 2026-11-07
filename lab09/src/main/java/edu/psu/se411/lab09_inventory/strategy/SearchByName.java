package edu.psu.se411.lab09_inventory.strategy;
import java.util.Objects;
import edu.psu.se411.lab09_inventory.model.*;
public final class SearchByName implements SearchStrategy<Item> {
 private final String value;
 public SearchByName(String value) { this.value=Objects.requireNonNull(value); }
 @Override public boolean matches(Item item) { return item.getName().equalsIgnoreCase(value); }
}
