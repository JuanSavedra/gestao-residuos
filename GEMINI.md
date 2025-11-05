# Project Overview

This is a RESTful microservice for waste management, built with Spring Boot. The project aims to track waste collection, send alerts, and provide information on correct disposal methods. It uses Java 17, Maven for dependency management, and Spring Boot for the application framework. The application is containerized using Docker and uses an Oracle database for data persistence.

## Key Technologies

*   **Backend:** Java 17, Spring Boot 3
*   **Data Persistence:** Spring Data JPA (Hibernate) with Oracle Database
*   **Security:** Spring Security
*   **Build:** Maven
*   **Containerization:** Docker, Docker Compose

## Architecture

The application follows a layered architecture:

*   **Controller:** Handles HTTP requests and responses.
*   **Service:** Contains the business logic.
*   **Repository:** Manages data access using Spring Data JPA.
*   **Entity:** Defines the data model.
*   **DTO:** Data Transfer Objects are used for validation and to decouple the API from the database schema.

# Building and Running

The project is fully containerized and can be run using Docker Compose.

## Prerequisites

*   Docker installed and running.
*   Docker Compose (usually included with Docker Desktop).

## Running the Application

1.  Open a terminal in the project's root directory.
2.  Run the following command to build the application and start the services:

    ```bash
    docker-compose up --build -d
    ```

3.  The application will be available at `http://localhost:8080/api`.

## Stopping the Application

To stop the running services, use the following command:

```bash
docker-compose down
```

# Development Conventions

*   **Lombok:** The project uses Lombok to reduce boilerplate code.
*   **Layered Architecture:** The code is organized into controller, service, and repository layers.
*   **DTOs:** DTOs are used for request and response objects to ensure a clean API and allow for validation.
*   **Exception Handling:** A global exception handler (`@RestControllerAdvice`) is used to manage different types of errors.
*   **Data Initialization:** The application uses a `DataInitializer` class to populate the database with initial data on startup.
