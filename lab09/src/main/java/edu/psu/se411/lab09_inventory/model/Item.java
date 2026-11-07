package edu.psu.se411.lab09_inventory.model;
import java.util.Objects;
public abstract class Item {
 private final String id;
 private final String name;
 protected Item(String id, String name) {
  if (Objects.requireNonNull(id).isBlank() || Objects.requireNonNull(name).isBlank()) throw new IllegalArgumentException("Id and name are required");
  this.id=id; this.name=name;
 }
 public String getId() { return id; }
 public String getName() { return name; }
}
