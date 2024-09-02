# Product Pricing API

## Overview

This repository contains a Spring Boot application designed to manage and query product pricing based on various criteria. The application provides RESTful endpoints for retrieving prices and includes comprehensive tests to ensure functionality and reliability.

## Table of Contents

- [Project Description](#project-description)
- [Technologies Used](#technologies-used)
- [Architecture and Design Patterns](#architecture-and-design-patterns)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Integration Tests](#integration-tests)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Project Description

The application offers a RESTful API to query for product prices based on product ID, brand ID, and a specific date. It includes:

- **Price Management**: Queries to retrieve applicable prices for products.
- **Testing**: Unit and integration tests to ensure the quality and correctness of the application.

## Technologies Used

- **Spring Boot**: For building the RESTful web service.
- **Hibernate**: For ORM and database interaction.
- **JUnit**: For writing unit and integration tests.
- **Mockito**: For mocking dependencies in tests.
- **H2 Database**: For an in-memory database used in tests.

## Architecture and Design Patterns

### Architecture

The application follows a layered architecture:

- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains business logic and interacts with the repository layer.
- **Repository Layer**: Manages database operations using Spring Data JPA.

### Design Patterns

- **Repository Pattern**: Encapsulates database access.
- **Service Pattern**: Separates business logic from the web layer.
- **DTO Pattern**: Transfers data between layers without exposing internal entities.

## Setup and Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/whatever/whatever.git

2. **H2 database**

    Download the H2 Database:

    https://www.h2database.com/html/download.html

    Access the H2 console to interact with the database. If you want the tests to work, you should insert this into your database:

    ```
    INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES
    (1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
    (1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
    (1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
    (1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR');
    ```

2. **Install Dependencies**

    Ensure you have Java 11+ and Maven installed. Run:


   ```bash
   mvn clean install
    ```
## Running the Application

1. **Start the Application**
    Run the application using Maven:

   ```
   mvn spring-boot:run
    ```
2. **Access the Application**

    The application will be available at http://localhost:8080. You can use tools like Postman or cURL to interact with the API.

    
## Running Tests

### Unit Tests

Run unit tests using Maven:

    mvn test


### Integration Tests

Integration tests can be run with the following command:

    mvn verify

These tests include both unit and integration tests to ensure comprehensive coverage of the application.

## Integration Tests

Integration tests are designed to test the application as a whole, ensuring that all components work together as expected. They are located in the src/test/java directory and use @SpringBootTest for running the full application context.

Key integration tests include:

- **Controller Integration Tests**: Verify the correctness of REST endpoints.
- **Service Integration Tests**: Ensure business logic is functioning correctly with real data.

## API Endpoints

### Get Price

**Endpoint**: GET /api/prices

Parameters:

- **date (required)**: The date and time to check the price for, formatted as ISO 8601 date-time.
- **productId (required)**: The ID of the product.
- **brandId (required)**: The ID of the brand.

## License

This project is licensed under the Inditex License. See the [LICENSE](https://www.license.com/inditex) file for details.


For further information or assistance, feel free to open an issue or contact the project maintainers.

