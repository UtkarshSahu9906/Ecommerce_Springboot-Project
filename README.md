# E-Commerce Backend (Spring Boot)

A REST API for a small online store, built with Spring Boot. I made this project to show the backend skills I would bring to a development team: clean layered code, secure authentication, solid database design and reliable business logic.

**A note on scope:** this is a compact project, not a production-scale platform. I kept it focused on the core flow of an online store (catalog, cart, orders) and put my effort into doing those parts properly rather than adding features for the sake of it.

## What it does

- Register and log in with JWT authentication, with passwords hashed using BCrypt
- Role-based access: `USER` and `ADMIN`. Only admins can manage products, categories and order statuses
- Product browsing with pagination, sorting, search by name, and filters for category and price range
- Per-user cart: add, update and remove items, with the total calculated on the server
- Order placement from the cart. Stock is checked and reduced, the cart is cleared, and everything runs in one transaction, so a failure never leaves half-saved data
- Order history, order cancellation (stock is restored), and admin order status updates: `PLACED → CONFIRMED → SHIPPED → DELIVERED`

## Tech stack

Java 17, Spring Boot 3, Spring Web, Spring Data JPA (Hibernate), Spring Security with JWT, MySQL, Maven, Lombok, springdoc-openapi (Swagger), JUnit 5 and Mockito.

## Skills this project demonstrates

| Area | What's in the project |
|---|---|
| REST API design | Resource-based endpoints, proper HTTP methods and status codes |
| Security | JWT auth with a custom filter, BCrypt hashing, role-based authorization. The user is always read from the token, never from the request |
| Database design | Normalised schema with one-to-one, one-to-many and many-to-one relationships. Order items store the price at purchase time |
| Business logic | Stock validation, cart totals, order lifecycle and cancellation rules, all in the service layer |
| Data integrity | `@Transactional` on multi-table operations such as placing and cancelling an order |
| Clean code | Controller → Service → Repository layering, DTOs instead of exposing entities, input validation |
| Error handling | Custom exceptions and a global handler that returns a consistent JSON error format |
| Testing | Unit tests with JUnit and Mockito, controller tests with MockMvc |
| Documentation | Swagger UI, Postman collection, and this README |

## How it's organised

```
com.yourname.ecommerce
├── config        security and swagger setup
├── controller    REST endpoints
├── service       business logic (interfaces + impl)
├── repository    JPA repositories
├── entity        database tables
├── dto           request / response objects
├── exception     custom exceptions and global handler
├── security      JWT filter, token utility, user details
└── util          helpers
```

Controllers only handle HTTP, services hold the business rules, and repositories handle the database. Each layer talks only to the one below it.

## Database

Main tables: `User`, `Category`, `Product`, `Cart`, `CartItem`, `Order`, `OrderItem`.

- A category has many products
- A user has one cart, and a cart has many cart items
- A user has many orders, and an order has many order items

## API endpoints

**Auth**
- `POST /api/auth/register` (public)
- `POST /api/auth/login` (public)

**Categories**
- `GET /api/categories` (public)
- `POST`, `PUT /{id}`, `DELETE /{id}` (admin)

**Products**
- `GET /api/products` and `GET /api/products/{id}` (public)
- `POST`, `PUT /{id}`, `DELETE /{id}` (admin)

**Cart** (logged-in user)
- `GET /api/cart`
- `POST /api/cart/items`
- `PUT /api/cart/items/{itemId}`
- `DELETE /api/cart/items/{itemId}`

**Orders**
- `POST /api/orders` place an order (user)
- `GET /api/orders` my orders (user)
- `GET /api/orders/{id}` (user or admin)
- `PUT /api/orders/{id}/cancel` (user)
- `PUT /api/orders/{id}/status` (admin)

Example search:

```
GET /api/products?keyword=phone&categoryId=2&minPrice=5000&maxPrice=30000&page=0&size=10&sort=price,asc
```

Errors come back in a consistent format:

```json
{
  "timestamp": "2026-11-13T01:30:00",
  "status": 404,
  "message": "Product not found with id: 99"
}
```

## Running it locally

You'll need JDK 17+, Maven and MySQL.

1. Clone the repo
   ```bash
   git clone https://github.com/<your-username>/<your-repo-name>.git
   cd <your-repo-name>
   ```
2. Create a database
   ```sql
   CREATE DATABASE ecommerce_db;
   ```
3. Set these environment variables (no secrets are stored in the repo):

   | Variable | Example |
   |---|---|
   | `DB_URL` | `jdbc:mysql://localhost:3306/ecommerce_db` |
   | `DB_USERNAME` | `root` |
   | `DB_PASSWORD` | your password |
   | `JWT_SECRET` | any long random string |

4. Start the app
   ```bash
   mvn spring-boot:run
   ```

The app runs on `http://localhost:8080`, and Swagger UI is at `http://localhost:8080/swagger-ui/index.html`.

To call protected endpoints, log in, copy the token, and send it as `Authorization: Bearer <token>`. A Postman collection is available in the `postman` folder.

## Tests

```bash
mvn test
```

## What I can bring to a team

- I write readable, layered code that other developers can pick up and extend
- I think about security and data integrity from the start, not as an afterthought
- I handle edge cases like stock checks, failed transactions and bad input
- I document my work so others can run and test it quickly
- I'm comfortable taking a feature from database design to a tested API

## Possible next steps

Product image upload, address management, a payment flow, email notifications, Redis caching, and Docker with cloud deployment.

## Contact

**Your Name**
GitHub: [@UtkarshSahu9906](https://github.com/UtkarshSahu9906) · LinkedIn: [your-linkedin](https://in.linkedin.com/in/utkarshsahu9906) · utkarshsahu9906@gmail.com

I'm open to backend developer roles and would be glad to talk about this project.
