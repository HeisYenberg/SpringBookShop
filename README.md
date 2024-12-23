# Spring Book Shop: Book Shop Management System

Spring Book Shop is a comprehensive bookshop management system developed using Spring Boot, Spring Security, Thymeleaf, Spring Data JPA, and Docker. It provides a robust platform for managing books and cart functionalities for users, with specialized admin roles for book management.

## Features

- User Authentication and Session Management: Secure user login and session 
management using Spring Security.
- Cart Management: Users can add books to their cart, view cart contents, and 
  proceed to checkout.
- Book Management: Admin roles have the ability to add, edit, and remove 
  books from the inventory.
- Database Management: Utilizes Spring Data JPA for database interactions, 
  with support for PostgreSQL.

## Prerequisites

- Java 8 or later.
- Maven for building the project.
- PostgreSQL database for data storage. 
- Docker and Docker Compose (for containerized setup).


## Setting Up the Application with Docker

- Clone this repository to your local machine.
- Navigate to the project root directory.
- Run the application with Docker Compose: This setup uses Docker Compose to manage both the Spring Boot application and PostgreSQL database.
- Start the containers:
- Run the following command to start the Spring Boot application and PostgreSQL database in Docker containers:

```bash
docker-compose up
```

## Running the Application Manually (Without Docker)

If you prefer to run the application without Docker:

- Install PostgreSQL and create a new database called spring_book.
- Run the data.sql script to set up the database schema and initial data.
- Set up your local database connection in application.properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/spring_book
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

- Use Maven to build and run the application:

```bash
mvn clean package spring-boot:run
```