package edu.psu.se411.lab09_inventory.strategy;
import java.util.Objects;
import edu.psu.se411.lab09_inventory.model.*;
public final class SearchById implements SearchStrategy<Item> {
 private final String value;
 public SearchById(String value) { this.value=Objects.requireNonNull(value); }
 @Override public boolean matches(Item item) { return item.getId().equals(value); }
}
