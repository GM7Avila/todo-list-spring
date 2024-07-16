# ✔️ Todo List - Spring REST API

- This is a backend study project, applying the concepts of a **REST API** using the **_Spring Framework_**.

## Technologies Used
- Java 17
- Apache Maven >= 3.8.6
- Spring Framework
  - Spring Boot Starter Web
  - Spring Boot Stater Data JPA
  - Spring Boot Starter Security + JWT
  - Lombok
- MySQL Server
- Docker
- BootstrapJS

---
## Initializing the Application

To run the application, you need to have Docker installed on your machine.

- Run in the terminal: `docker-compose up --build`;
- The MySQL database runs on port `3307:3306` in Docker, while the backend environment (Spring) uses port `8080:8080`.

---
## API Endpoints

### Auth

- **POST** - `/login` - Authentication/Authorization (JWT Token)

### User

- **POST** - `/user` - Create a user
- **GET** - `/user/{id}` - Get user data by id
- **PUT** - `/user/{id}` - Update user by id
- **DELETE** - `/user/{id}` - Delete user by id

### Tasks

- **POST** - `/task` - Create a task for the authenticated user
- **GET** (Find) - `task/{id}` - Get task by id
- **GET** (FindByUser) - `task/user` - Get tasks of the authenticated user
- **PUT** - `task/{id}` - Update task by id
- **DELETE** - `task/{id}` - Delete task by id

