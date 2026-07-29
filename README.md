# Zach Simple LMS

A simple Spring Boot REST API for the library management system.

## Swagger UI

Start the application, then open:

<http://localhost:8080/swagger-ui/index.html>

The OpenAPI specification is available at:

<http://localhost:8080/v3/api-docs>

## H2 Database

The application uses a file-backed H2 database. Open the H2 Console at:

<http://localhost:8080/h2-console>

Use these connection details:

```text
JDBC URL: jdbc:h2:file:./data/zachsimplelms
User Name: sa
Password:
```

The database file is created at `data/zachsimplelms.mv.db`, relative to the
directory from which the application is started.
