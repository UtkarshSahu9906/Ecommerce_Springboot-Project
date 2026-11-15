# E-Commerce Backend – Spring Boot

A REST API for an online store, built using Spring Boot, Spring Security, JPA, and MySQL.

This project demonstrates backend development concepts including REST API design, layered architecture, JWT authentication, role-based authorization, database relationships, and e-commerce business logic.

**Project scope:** This is a learning and portfolio project focused on the core functionality of an e-commerce application: user authentication, product management, shopping carts, and order processing.

## Features

- User registration and login with JWT authentication.
- Password encryption using BCrypt.
- Role-based authorization with `USER` and `ADMIN` roles.
- Admin-only product and category management.
- Product listing with pagination and sorting.
- Product search by name.
- Shopping cart management: add, update, remove, and clear items.
- Server-side cart total calculation.
- Order placement with stock validation.
- Automatic stock reduction after successful order placement.
- Order history and individual order details.
- Order cancellation with stock restoration.
- Transaction management using `@Transactional`.
- Global exception handling and request validation.
- Swagger UI documentation.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 27 | Programming language |
| Spring Boot 4.1.2 | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database operations |
| Hibernate | ORM |
| Spring Security | Authentication and authorization |
| JWT | Token-based authentication |
| BCrypt | Password hashing |
| MySQL | Relational database |
| Maven | Dependency management |
| Lombok | Boilerplate reduction |
| Springdoc OpenAPI | Swagger API documentation |

## Project Architecture

The application follows a layered architecture:

```text
Controller → Service → Repository → Database
```

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains business logic.
- **Repository:** Handles database operations.
- **Entity:** Represents database tables.
- **DTO:** Transfers request and response data.
- **Security:** Handles JWT authentication and authorization.
- **Exception:** Handles application exceptions.

The main Java package is:

```text
com.utkarsh.ecommerce
```

## Database Design

The project contains the following main entities:

- User
- Category
- Product
- Cart
- CartItem
- Order
- OrderItem

### Entity Relationships

- One category can have multiple products.
- One user has one cart.
- One cart can contain multiple cart items.
- One user can place multiple orders.
- One order can contain multiple order items.
- Order items preserve the product price at the time of purchase.

## API Endpoints

All endpoints require JWT authentication except registration and login.

### Authentication

| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/users/register` | Public |
| POST | `/api/users/login` | Public |

### Categories

| Method | Endpoint | Access |
|---|---|---|
| GET | `/api/categories` | Authenticated |
| POST | `/api/categories` | ADMIN |
| PUT | `/api/categories/{id}` | ADMIN |
| DELETE | `/api/categories/{id}` | ADMIN |

### Products

| Method | Endpoint | Access |
|---|---|---|
| GET | `/api/products` | Authenticated |
| GET | `/api/products/{id}` | Authenticated |
| POST | `/api/products` | ADMIN |
| PUT | `/api/products/{id}` | ADMIN |
| DELETE | `/api/products/{id}` | ADMIN |
| GET | `/api/products/sort` | Authenticated |

The product module also supports pagination and searching by product name.

### Cart

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/cart/add` | Add a product to cart |
| GET | `/api/cart` | View the current user's cart |
| PUT | `/api/cart/items/{productId}` | Update cart item quantity |
| DELETE | `/api/cart/items/{productId}` | Remove an item |
| DELETE | `/api/cart/clear` | Clear the cart |

All cart endpoints require authentication.

### Orders

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/orders` | Place an order |
| GET | `/api/orders` | View order history |
| GET | `/api/orders/{orderId}` | View a specific order |
| PUT | `/api/orders/{orderId}/cancel` | Cancel an eligible order |

Users can access their own orders. Order cancellation is permitted only when the order status is `PLACED`.

## Authentication Flow

1. Register a new account using `/api/users/register`.
2. Log in using `/api/users/login`.
3. Copy the JWT token returned by the login API.
4. Include the token in the Authorization header when accessing protected endpoints.

Example:

```http
Authorization: Bearer your_jwt_token
```

### Role-Based Authorization

The application supports two roles:

- `USER`: Can manage their own cart and orders.
- `ADMIN`: Can manage products and categories.

Newly registered users receive the `USER` role by default.

## Running the Project Locally

### Prerequisites

- Java 27
- Maven
- MySQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/UtkarshSahu9906/Ecommerce_Springboot-Project.git

cd Ecommerce_Springboot-Project
```

### 2. Create the database

```sql
CREATE DATABASE ecomerse_db;
```

### 3. Configure environment variables

Configure the following environment variables in your IDE run configuration or terminal:

| Variable | Description |
|---|---|
| `DB_URL` | MySQL database connection URL |
| `DB_USERNAME` | MySQL username |
| `DB_PASSWORD` | MySQL password |
| `JWT_SECRET` | Secret key used for JWT signing |

Example database URL:

```text
jdbc:mysql://localhost:3306/ecomerse_db
```

Use your own database credentials and a securely generated JWT secret. Never commit passwords or secret keys to GitHub.

### 4. Run the application

```bash
mvn spring-boot:run
```

The application starts at:

```text
http://localhost:8080
```

## Swagger API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

You can use Swagger UI to:

- Explore available API endpoints.
- View request and response structures.
- Test API requests.
- Authorize protected endpoints using your JWT token.

## Running Tests

Execute the following command:

```bash
mvn test
```

## Future Improvements

Potential improvements for future versions:

- Automated unit and integration tests.
- Inventory concurrency protection.
- Improved API sorting validation.
- Product image upload.
- Address management.
- Payment gateway integration.
- Email notifications.
- Docker and cloud deployment.

## What This Project Demonstrates

- REST API development using Spring Boot.
- Layered application architecture.
- JWT-based authentication.
- Role-based authorization.
- Database relationships using JPA and Hibernate.
- DTO-based request and response handling.
- Transactional business operations.
- Input validation and exception handling.
- API documentation using Swagger.

## Contact

**Utkarsh Sahu**

- GitHub: https://github.com/UtkarshSahu9906
- LinkedIn: https://in.linkedin.com/in/utkarshsahu9906
- Email: utkarshsahu9906@gmail.com

I am interested in backend development opportunities and continuously improving my skills in Java and Spring Boot.
