# Twitter-like Forum App

## Project Description
This project is a **Twitter-like forum application** built using **Spring Boot**. The app provides features similar to a social media platform where users can create accounts, authenticate, post messages, follow other users, and interact through a forum-like interface. The application leverages microservice architecture and is built with **Spring Boot**, **Maven**, **MySQL**, and **RabbitMQ** for inter-service communication. It also incorporates **Eureka Service Discovery** for better service registration and discovery.

### Key Features:
- **User Registration and Authentication**: Sign up and authenticate users securely.
- **Forum-like Posting System**: Users can post messages, comment on posts, and like/dislike content.
- **Follow Users**: Follow other users and view their posts.
- **Inter-Service Communication**: Uses RabbitMQ for message-driven communication between services.
- **Service Discovery**: Uses Eureka for discovering and managing microservices.
- **Scalable Architecture**: Microservice architecture for easy scaling and maintenance.

## Project Architecture
The project is divided into the following core services:
1. **User Service**: Manages user information, profiles, following functionality, and user-related data.
2. **Authentication Service**: Handles user authentication and authorization using JWT tokens for secure login and access control.
3. **Eureka Service**: A service registry to register and discover microservices.
4. **Message Service**: Handles the creation and management of forum posts and messages.
5. **RabbitMQ**: Facilitates communication between services using message queues for asynchronous processing.

## Technologies Used
- **Spring Boot**: Backend framework for microservices and business logic.
- **Maven**: Dependency and build management.
- **MySQL**: Relational database for storing user and message data.
- **RabbitMQ**: Message broker for inter-service communication.
- **Eureka**: Service registry for service discovery and load balancing.
- **JWT (JSON Web Tokens)**: Authentication mechanism for secure user sessions.
- **Spring Security**: Security framework to protect endpoints and manage authentication/authorization.

## Prerequisites
- Java 17+
- Maven 3+
- MySQL 8+
- RabbitMQ
- Eureka Server

## Getting Started
### Step 1: Clone the Repository
```bash
git clone https://github.com/your-repository/twitter-forum-app.git
cd twitter-forum-app
