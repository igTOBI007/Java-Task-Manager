**Java Task Manager**

A simple layered console app written in plain Java. Built to practise OOP and clean backend design.

### Features
- Add, list, filter, update and delete tasks
- Tasks are saved to a file so they stick around between runs
- Basic input validation with custom exceptions
- JUnit 5 tests covering the service layer

### Architecture
- **model** – `Task`, `Priority`, `Status`
- **repository** – `TaskRepository` interface + in-memory and file-based implementations
- **service** – business rules, validation, and not-found handling
- **ui** – console menu

The service only depends on the `TaskRepository` interface, so you can switch storage by changing a single line in `Main`.

### How to run
1. Open the project in IntelliJ IDEA (JDK 17 or newer)
2. Run `Main`

### Tests
Just run everything in the `test` folder from IntelliJ.
