# Project Overview

This is a RESTful microservice for waste management, built with Spring Boot. The project aims to track waste collection, send alerts, and provide information on correct disposal methods. It uses Java 17, Maven for dependency management, and Spring Boot for the application framework. The application is containerized using Docker and is integrated with **AWS RDS (PostgreSQL)** for data persistence.

## Key Technologies

*   **Backend:** Java 17, Spring Boot 3.5.7
*   **Data Persistence:** Spring Data JPA (Hibernate) with **PostgreSQL**
*   **Infrastructure:** AWS RDS (Database as a Service)
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

The project is fully containerized. For testing and evaluation, a local PostgreSQL container is automatically provisioned via Docker Compose to ensure a ready-to-use environment without requiring external AWS credentials.

## Prerequisites

*   Docker installed and running.
*   Docker Compose (usually included with Docker Desktop).

## Running the Application (Local Environment)

1.  Open a terminal in the project's root directory.
2.  Run the following command to build the application and start both the API and the local PostgreSQL database:

    ```bash
    docker-compose up --build -d
    ```

3.  The application will be available at `http://localhost:8080/api`.

## Running with AWS RDS

To connect the local container to an AWS RDS instance, provide the database environment variables:

```bash
DB_URL=jdbc:postgresql://your-rds-endpoint:5432/gestao_residuos \
DB_USER=your_user \
DB_PASS=your_password \
docker-compose up --build -d
```

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
