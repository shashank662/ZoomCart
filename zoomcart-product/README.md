# ZoomCart Product Service

This service is responsible for managing and providing product information to customers in the ZoomCart application.

## Purpose

The Product Service serves as the central repository for all product-related data that is displayed to customers. It allows users to browse, search, and view detailed information about products available on the ZoomCart platform.

## Key Features

*   **Retrieve Product Details:** Provides detailed information for a specific product, including name, description, price, images, merchant information, and customer reviews.
*   **List Products:** Offers paginated lists of products, potentially filterable and sortable by various criteria (e.g., category, price, popularity).
*   **Product Search:** Enables customers to search for products based on keywords, categories, or other attributes. This often involves integration with a dedicated search engine for performance and relevance.
*   **Product Categories:** Manages product categories to help users navigate and discover products.
*   **View Product Reviews and Ratings:** Aggregates and displays customer reviews and ratings for products.

## Technologies Used

*   **Spring Boot:** An open-source Java-based framework used for creating microservices.
*   **Java:** The primary programming language for the service.
*   **Elasticsearch:** A distributed, RESTful search and analytics engine used for efficient product search and filtering. Product data is often indexed in Elasticsearch for fast and complex queries.
*   **PostgreSQL (or other primary datastore):** While Elasticsearch is used for search, a primary database (like PostgreSQL, MySQL, or a NoSQL DB) might be used as the source of truth for product data, which is then synchronized to Elasticsearch.
*   **Spring Data Elasticsearch:** Part of the Spring Data family, provides integration with Elasticsearch.
*   **Spring Web:** Part of the Spring Framework, used for building web applications, including RESTful APIs.
