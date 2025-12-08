## 📚 BookHub  (Spring Boot Online Bookstore)

[![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white)](https://hibernate.org/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=white)](INSERT_YOUR_SWAGGER_LINK_HERE)

A robust and fully documented RESTful API designed for managing a digital book library. It features a role-based access control system (USER and ADMIN) and provides advanced interactive features like liking, searching, and filtering books.
---
---
## 🚀 Features

### User Features
- Register and login with email & password  
- Fetch user profile by ID or email  
- Role-based distinction (USER / ADMIN)

### Book Features
- Add, update, delete books (Admins only)  
- Upload cover images for books  
- Search books by title or category  
- View top liked books globally or by category  
- Pagination support for all book listings  

### Like System
- Users can like/unlike books  
- Prevent duplicate likes  
- Real-time likes count update  

### Admin Features
- Admin-only CRUD for books  
- View all books added by a specific admin  

---

## 📌 Technologies Used

| Technology | Purpose |
|------------|---------|
| **Java 17** | Core backend language |
| **Spring Boot** | REST controllers, routing, and backend logic |
| **Hibernate / JPA** | ORM and entity mapping |
| **MySQL** | Relational database for users, books, and likes |
| **Swagger** | API documentation and testing |
| **Lombok** | Reduce boilerplate (getters, setters, constructors) |
| **Maven** | Dependency and build management |
| **Tomcat** | Embedded server |
| **HTML / CSS / JS** | Frontend UI for users |

---

## 📑 API Endpoints

### User APIs
| Method | Endpoint                  | Description                       |
|--------|---------------------------|-----------------------------------|
| POST   | /userApi/v1/registration  | Register a new user               |
| POST   | /userApi/v1/login         | Login with email and password     |
| GET    | /userApi/v1/user          | Get user by ID                    |
| GET    | /userApi/v1/user/email    | Get user by email                 |

### Book APIs
| Method | Endpoint                         | Description                                      |
|--------|----------------------------------|-------------------------------------------------|
| GET    | /bookApi/v1/books                | Get all books (paginated)                       |
| GET    | /bookApi/v1/book/{id}            | Get book by ID                                  |
| GET    | /bookApi/v1/books/search/title   | Search books by title                            |
| GET    | /bookApi/v1/books/search/category| Search books by category                        |
| GET    | /bookApi/v1/books/top-liked      | Top liked books (all categories)               |
| GET    | /bookApi/v1/books/top-liked/category | Top liked books by category                 |
| POST   | /bookApi/v1/book                 | Add a new book (Admin only)                     |
| PUT    | /bookApi/v1/book/{id}            | Update a book (Admin only)                      |
| DELETE | /bookApi/v1/book/{id}            | Delete a book (Admin only)                      |
| GET    | /bookApi/v1/books/admin/{adminID}| Get books added by a specific admin            |
| POST   | /bookApi/v1/books/like           | Like a book                                     |
| DELETE | /bookApi/v1/books/like           | Unlike a book                                  |


---
## Database Schema

```mermaid
erDiagram
    USER {
        long id PK "Primary Key"
        string username
        string email
        string password
        string role
    }

    BOOK {
        long id PK "Primary Key"
        string title
        string description
        string author
        double price
        string coverImage
        string category
        int likesCount
        long addedBy FK "References User(id)"
    }

    LIKE {
        long id PK "Primary Key"
        long userId FK "References User(id)"
        long bookId FK "References Book(id)"
    }

    USER ||--o{ BOOK : "adds"
    USER ||--o{ LIKE : "likes"
    BOOK ||--o{ LIKE : "has"
```
---

