# ZoomCart Orders and Cart Service

This service is responsible for managing customer shopping carts and processing orders within the ZoomCart application.

## Purpose

The Orders and Cart Service handles all functionalities related to the customer's shopping experience, from adding items to their cart to successfully placing an order and viewing their order history.

## Key Features

*   **Shopping Cart Management:**
    *   Add items to the cart.
    *   Remove items from the cart.
    *   Update item quantities in the cart.
    *   View cart contents.
    *   Clear cart.
*   **Order Processing:**
    *   Place orders from the items in the cart.
    *   Validate order information.
    *   Process payments (integration with a payment gateway would typically be here).
*   **Order History:**
    *   Allow users to view their past orders and order details.
    *   Track order status (e.g., pending, shipped, delivered).

## Technologies Used

*   **Spring Boot:** An open-source Java-based framework used for creating microservices.
*   **Java:** The primary programming language for the service.
*   **MongoDB:** A NoSQL document database used for storing cart and order information, providing flexibility and scalability.
*   **Spring Data MongoDB:** Part of the Spring Data family, provides integration with MongoDB.
*   **Spring Web:** Part of the Spring Framework, used for building web applications, including RESTful APIs.
