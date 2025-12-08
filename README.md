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

## 📌 API Documentation
- [Swagger UI](http://localhost:8081/swagger-ui/index.html) *(Run the Spring Boot server locally to access)*

---

## 🔍 API Overview

### 1️⃣ Book APIs
Endpoints for managing books, including CRUD, search, and like/unlike features.

[![Book APIs](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/BookApis.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/BookApis.png?raw=true)

---

### 2️⃣ User APIs
Endpoints for user registration, login, and fetching user data.

[![User APIs](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/UserApis.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/UserApis.png?raw=true)

---

### 3️⃣ Schemas
Data models and request/response schemas used in the API.
[![Schemas](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/schemas.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/schemas.png?raw=true)

---

## Project Overview


## 1️⃣ Landing Page
Main landing page for the BookHub application.  

[![Landing Page](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/landing.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/landing.png?raw=true)

---

## 2️⃣ Login Page
User login page for authentication.  

[![Login Page](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/login.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/login.png?raw=true)

---

## 3️⃣ Register Page
User registration page.  

[![Register Page](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/register.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/register.png?raw=true)

---

## 4️⃣ Admin Dashboard
Overview of the admin dashboard showing statistics and management features.  

[![Admin Dashboard](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/adminDashboard.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/adminDashboard.png?raw=true)

---

## 5️⃣ Add Book Form
Form used for adding new books in the system.  

[![Add Book Form](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/addForm.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/addForm.png?raw=true)

---

## 6️⃣ Edit Book Form
Form used to edit existing book details.  

[![Edit Book Form](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/editForm.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/editForm.png?raw=true)

---

## 7️⃣ All Books
List of all books available in the system.  

[![All Books](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/allBooks.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/allBooks.png?raw=true)

---

## 8️⃣ Top Liked Books
Displays top liked books in the system.  

[![Top Liked Books](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/topLiked.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/topLiked.png?raw=true)

---

## 9️⃣ Profile Page
Displays user profile information and settings.  

[![Profile Page](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/profile.png?raw=true)](https://github.com/RahmaIsmaill/BookHub/blob/develop/imgs/profile.png?raw=true)

---
