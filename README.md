# SpringBookShop: Book Shop Management System

SpringBookShop is a comprehensive bookshop management system developed using Spring Boot, Spring Security, Thymeleaf, Spring Data JPA, and Docker. It provides a robust platform for managing books and cart functionalities for users, with specialized admin roles for book management.

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


## Database Setup

1. Install PostgreSQL on your local machine or server.
2. Create a new database for your application, run `schema.sql` script.
3. Fill the database with data by running `data.sql` script.
4. Configure the database connection in your `application.properties` file 
   with the following settings:

```spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update