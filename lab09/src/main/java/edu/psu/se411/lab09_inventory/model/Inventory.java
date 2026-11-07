package edu.psu.se411.lab09_inventory.model;
import java.util.*;
import edu.psu.se411.lab09_inventory.strategy.SearchStrategy;
public final class Inventory<T> {
 private final List<T> items = new ArrayList<>();
 public void add(T item) { items.add(Objects.requireNonNull(item)); }
 public boolean remove(T item) { return items.remove(item); }
 public List<T> getAll() { return List.copyOf(items); }
 public List<T> findItems(SearchStrategy<? super T> strategy) {
  Objects.requireNonNull(strategy);
  List<T> matches=new ArrayList<>();
  for (T item:items) if(strategy.matches(item)) matches.add(item);
  return List.copyOf(matches);
 }
}
