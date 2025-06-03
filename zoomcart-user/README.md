# ZoomCart User Service

This service is responsible for managing user accounts, authentication, and authorization within the ZoomCart application.

## Purpose

The User Service is the central authority for all user-related operations. It handles user registration, login, profile management, and ensures secure access to the application's resources.

## Key Features

*   **User Registration:** Allows new users to create an account by providing necessary information (e.g., name, email, password).
*   **User Login:** Authenticates users based on their credentials (e.g., email and password).
*   **Profile Management:** Enables users to view and update their profile information (e.g., name, shipping addresses, payment methods).
*   **Password Management:** Allows users to change their password and recover a forgotten password.
*   **Authentication:** Verifies the identity of users trying to access the system. This often involves issuing tokens (e.g., JWT - JSON Web Tokens).
*   **Authorization:** Manages user roles and permissions, ensuring that users can only access the features and data they are authorized for.
*   **Address Management:** Allows users to save and manage multiple shipping addresses.
*   **User Deactivation/Deletion:** Provides functionality for users to deactivate or request deletion of their accounts.

## Technologies Used

*   **Spring Boot:** An open-source Java-based framework used for creating microservices.
*   **Java:** The primary programming language for the service.
*   **Spring Security:** A powerful and highly customizable authentication and access-control framework. It's likely used to implement:
    *   **OAuth2:** An authorization framework that enables third-party applications to access user resources without exposing credentials. Could be used for social logins or for securing inter-service communication.
    *   **JWT (JSON Web Tokens):** For creating stateless authentication tokens.
*   **PostgreSQL (or other relational database):** Used for persistently storing user credentials and profile information.
*   **Spring Data JPA:** Part of the Spring Data family, makes it easy to implement JPA based repositories for database interaction.
*   **Spring Web:** Part of the Spring Framework, used for building RESTful APIs for user management and authentication.
*   **Email Service Integration:** For sending verification emails, password reset links, etc.
