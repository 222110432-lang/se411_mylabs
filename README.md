# SE411 lab solutions
This is a repository for my solutions to SE411 labs.

| Lab | Topic | Folder |
|---|---|---|
| 2 | Generics | [lab02](lab02) |
| 3 | JUnit | [Lab03](Lab03) |
| 4 | Maven / JavaFX | [Lab04](Lab04) |
| 5 | Exceptions | [lab5](lab5) |
| 6 | Logging | [lab6](lab6) |
| 7 | Polymorphism | [lab7](lab7) |
| 8 | Observer pattern | [Lab8](Lab8) |
| 9 | Inventory / strategy pattern | [lab09](lab09) |
| 11 | Threads / double spending | [lab11](lab11) |
| 12 | Thread pools / ordered locks | [lab12](lab12) |
| 13 | Spring product API | [lab13](lab13) |
| 14 | Spring course CRUD | [lab14](lab14) |
| 15 | Spring CRUD / MySQL | [lab15](lab15) |

Each new lab is an independent Maven project; import the desired lab folder into Eclipse as an Existing Maven Project. See its README for commands and examples. Lab 15 requires a MySQL connection supplied through environment variables.

## Verification
The Verify SE411 labs workflow builds and tests Labs 9 and 11–15. Its MySQL job tests CRUD and persistence across application restart with an isolated MySQL service. Local measured results for thread experiments are under lab11/results and lab12/results.

## Chapter 01: Git
The first chapter is an introduction to git.
