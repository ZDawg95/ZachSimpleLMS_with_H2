Zach Simple LMS Quick Start

This project is a Java 17 Spring Boot REST API.

How to run the application:

1. Make sure Java 17 is installed.
2. Open a terminal in the folder that contains the jar file.
3. Run:

   java -jar testzach2026-0.0.1-SNAPSHOT.jar

4. Leave the terminal window open while using the app.

Swagger UI:

Open this in your browser after the app starts:

http://localhost:8080/swagger-ui/index.html

H2 Database Console:

Open this in your browser after the app starts:

http://localhost:8080/h2-console

Use these login details:

JDBC URL: jdbc:h2:file:./data/zachsimplelms
User Name: sa
Password:

Notes:

- The database is file-based and is stored in a local data folder next to the
  folder where the jar is run.
- The application starts on port 8080.
- If you need the full documentation, see README.md.
