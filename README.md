# Zach Simple LMS

Zach Simple Library Management System (LMS) is a Java 17 Spring Boot REST API for registering borrowers and
books, managing physical book copies, and borrowing or returning available
copies.

## Requirements

- Java 17
- Maven (or IntelliJ IDEA with its bundled Maven)

## How to Run: Use the provided runnable JAR
A ready-made JAR file is included in the ZIP file along with the code.

If it is not required to build a new JAR, skip to the "Run from the desktop or another folder" section.

## Build a runnable JAR

In IntelliJ IDEA, open the Maven tool window and run **Lifecycle > package**.

Alternatively, from a terminal with Maven installed, run:

```powershell
mvn clean package
```

The runnable JAR is created in the `target` folder. For the current project
version, its name is:

```text
target/testzach2026-0.0.1-SNAPSHOT.jar
```

## Run from the desktop or another folder

1. Create a folder anywhere convenient, such as `Desktop\ZachSimpleLMS`.
2. Copy the runnable JAR from `target` into that folder.
3. Open PowerShell in the folder and run:

   ```powershell
   java -jar .\testzach2026-0.0.1-SNAPSHOT.jar
   ```

4. Leave that PowerShell window open while using the API. The application
   starts on port `8080`.

The embedded H2 database is created in a `data` subfolder next to the JAR, so
the application remains self-contained and retains its data between restarts.

## Swagger UI

After starting the application, open:

<http://localhost:8080/swagger-ui/index.html>

Swagger provides interactive request fields for every endpoint. The OpenAPI
specification is also available at:

<http://localhost:8080/v3/api-docs>

## H2 Database Console

Open the H2 Console at:

<http://localhost:8080/h2-console>

Use these connection details:

```text
JDBC URL: jdbc:h2:file:./data/zachsimplelms
User Name: sa
Password:
```

The database file is `data/zachsimplelms.mv.db`, relative to the folder from
which the JAR is started. For example, after running the JAR from the desktop,
the database will be stored in that desktop folder's `data` directory.

## Main API actions

| Action | Method and path |
|---|---|
| Register borrower | `PUT /borrowers/register` |
| List borrowers | `GET /borrowers/getAllBorrowers` |
| Register book/copy | `PUT /books/registerNewBook` |
| List catalogue books | `GET /books/getAllBooks` |
| List physical copies | `GET /books/getAllBookCopies` |
| Borrow a book | `PUT /borrowBook/borrow?bookId={bookId}&borrowerId={borrowerId}` |
| Return a book | `PUT /borrowBook/return?bookId={bookId}&borrowerId={borrowerId}` |

Example borrower request body:

```json
{
  "name": "Ada Lovelace",
  "email": "ada@example.com"
}
```

Example book request body:

```json
{
  "isbn": "9780132350884",
  "title": "Clean Code",
  "author": "Robert C. Martin"
}
```

## Why H2

H2 is used as a file-backed embedded database. It requires no separate
database server, has no installation or credentials to distribute for this
demo, and is included as a runtime dependency in the packaged Spring Boot JAR.
This makes the API easy to demonstrate: run one JAR and H2 creates the local
database file automatically.

## Assumptions

1. Borrowers may share the same name, but each borrower email address is
   unique.
2. A `Book` is a catalogue record identified by its Book ID and ISBN. Borrowing
   and returning use the Book ID and Borrower ID.
3. A `BookCopy` represents one physical copy of a catalogue `Book`. Registering
   an existing ISBN with the same title and author creates another `BookCopy`.
4. When borrowing, the system selects the oldest `AVAILABLE` `BookCopy` for the
   requested Book ID. API users do not need to supply a BookCopy ID.
5. A borrower may have at most one `CHECKED_OUT` BookCopy for the same catalogue
   Book at a time. Other borrowers may borrow other available copies.
6. ISBN values are stored as text so leading zeroes and ISBN-10/ISBN-13
   formatting can be preserved.

The same assumptions are also available in [assumptions.txt](assumptions.txt).

## Run tests

Run **Lifecycle > test** from IntelliJ's Maven tool window, or run:

```powershell
mvn test
```

The project already includes `spring-boot-starter-test`, which provides JUnit 5
and Mockito for the unit tests.
