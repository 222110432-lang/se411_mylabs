# Lab 09 — Inventory and strategy pattern
A generic inventory stores books and electronic devices. SearchById, SearchByName, SearchByAuthor and SearchByCategory implement SearchStrategy. A lambda supplies another strategy. Inventory<?> supports displaying any item type.

## Run
Java 17+ and Maven: `mvn clean test exec:java`.
In Eclipse, import this folder as an Existing Maven Project and run the same goals.
The app demonstrates adding, searching, displaying and removing items. File logs are written to logs/app.log using Java's built-in logger.

## Design
Item holds the common id/name fields. Book and ElectronicDevice add their own fields. SearchStrategy<? super T> lets a strategy for Item also search an Inventory<Book>. Author/category strategies safely skip unrelated item types in a mixed inventory. getAll returns an immutable snapshot.
