# Online Book Store API Documentation


## Description:


Our project aims to develop a comprehensive Online Book Store application, implemented in a phased approach. The system will involve various domain models to facilitate a seamless experience for both users and administrators. 

The project will be executed in a modular manner, with each module covering specific functionalities. The GitHub repository will serve as a central hub for collaboration and version control. 

The project involves two main user roles: "Shopper" (regular user) and "Manager" (admin). Shoppers engage in activities related to exploring and purchasing books, while Managers focus on maintaining the inventory, organizing book sections, and managing order receipts. This structured approach ensures a user-friendly experience for customers and efficient management for administrators.
## Technology Stack:
<details>
  <summary><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZqRFNAis0vxGXeQDFA2thujnilvYO8eqTKDX5QgJ5APGtLTNQu0-d6rTkb8oSWOdyRyY&usqp=CAU" width="30"/> Java</summary>

`In this project, we used Java 17 as the main programming language.`
</details>

<details>
  <summary><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwsq-7f5BWyog4cdeT1sQaYLVzhJ0o37Up8TjHvVU08WUgfyyMMRMHTVwJ5XReSjyhZa0&usqp=CAU" width="30"/> Spring Boot v.3.1.5</summary>

`A powerful framework for building Java-based applications.`
</details>

<details>
  <summary><img src="https://www.baeldung.com/wp-content/uploads/2021/02/lsd-module-icon-1.png" width="30"/> Spring Data JPA</summary>

`Simplifies data access and persistence with JPA (Java Persistence API).`
</details>

<details>
  <summary><img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" width="30"/> Spring Security v.6.1.5</summary>

`Enables robust and secure authentication and authorization mechanisms.`
</details>

<details>
  <summary><img src="https://oddblogger.com/wp-content/uploads/2021/03/swagger-logo-2.png" width="30"/> Swager</summary>

`Provides API documentation.`
</details>

<details>
  <summary><img src="https://www.freepnglogos.com/uploads/logo-mysql-png/logo-mysql-mysql-logo-png-images-are-download-crazypng-21.png" width="30"/> MySQL v.8.0.33 </summary>

` Utilization of a relational database to store information about books, users, orders, etc.`

</details>

<details>
  <summary><img src="https://cdn-icons-png.flaticon.com/512/919/919853.png" width="30"/> Docker v.3.8</summary>

`Used for containerization of the application and database.`
</details>

<details>
  <summary><img src="https://user-images.githubusercontent.com/1204509/79262490-b2012a80-7e91-11ea-82fa-e791f8b4d177.jpg" width="30"/> Lombok</summary>

`Reduces boilerplate code with annotations.`
</details>

<details>
  <summary><img src="https://1.bp.blogspot.com/-C5lGqSQuCic/WX39mN-OhdI/AAAAAAAAALU/qUZQdUPTvmInwGSKAYfcZ-QA_PXxhXCXwCLcBGAs/s1600/mapstruct.png" width="30"/> Mapstruct</summary>

`Simplifies object mapping between DTOs and entities.`
</details>

<details>
  <summary><img src="https://www.liquibase.org/wp-content/themes/liquibase/assets/img/cta-icon.svg" width="30" height="30"/> Liquibase v.4.20.0 </summary>

`Ensures the application database is updated along with the application code.`
</details> 

### Authentication Controller:

| HTTP Method | Endpoint                 | Role | Function                                      |
|-------------|--------------------------|------|-----------------------------------------------|
| POST        | /api/auth/registration   | ALL  | Allows a new user to register                 |
| POST        | /api/auth/login          | ALL  | Authenticates a user and returns JWT token    |

### Book Controller:

| HTTP Method | Endpoint                | Role  | Function                                       |
|-------------|-------------------------|-------|------------------------------------------------|
| GET         | /api/book               | USER  | Enable users to see the list of books          |
| GET         | /api/book/{id}          | USER  | Enable users to get available books by id      |
| POST        | /api/books              | ADMIN | Enables admin to create a new book             |
| PUT         | /api/books/{id}         | ADMIN | Enables admin to update book                  |
| DELETE      | /api/books/delete/{id}  | ADMIN | Enables admin to delete certain book by id    |

### Category Controller:

| HTTP Method | Endpoint                  | Role  | Function                                             |
|-------------|---------------------------|-------|------------------------------------------------------|
| GET         | /api/categories           | USER  | Enables users to get all categories                 |
| GET         | /api/categories/{id}      | USER  | Enables users to get info on a category             |
| POST        | /api/categories           | ADMIN | Enables admin to add a new category to book         |
| PUT         | /api/categories/{id}      | ADMIN | Enables admin to update category on existing book   |
| DELETE      | /api/categories/{id}      | ADMIN | Enables admin to delete a category by id            |

### Order Controller:

| HTTP Method | Endpoint                        | Role  | Function                                               |
|-------------|---------------------------------|-------|--------------------------------------------------------|
| GET         | /api/orders                     | USER  | Enables users to get all their orders                   |
| GET         | /api/orders/{orderId}/items      | USER  | Enables users to get all their items by appropriate order |
| GET         | /api/orders/{orderId}/items/{itemId} | USER  | Enables users to get a specific item within an order  |
| POST        | /api/orders                     | USER  | Enables users to place an order                         |
| PATCH       | /api/orders/{id}                | ADMIN | Enables admin to update order with its status           |

### ShoppingCart Controller:

| HTTP Method | Endpoint                              | Role  | Function                                                  |
|-------------|---------------------------------------|-------|-----------------------------------------------------------|
| GET         | /api/cart                             | USER  | Enables users to store all the items in the shopping cart  |
| POST        | /api/cart                             | USER  | Enables users to add book to the shopping cart              |
| GET         | /api/cart/cart-items/{cartItemId}     | ADMIN | Enables admin to update the quantity of available books    |
| DELETE      | /api/cart/cart-items/{cartItemId}     | USER  | Enables users to delete book from cart                     |


### User/Manager functionality:
#### User Actions:

Join and sign in to the store.

Browse and search for books.

Explore bookshelf sections and view books within each section.

Utilize the shopping basket to add, view, and remove books.

Purchase books in the basket.

View past receipts and details of each receipt.
#### Manager Actions:

Manage books by adding, modifying, or removing them from the store.

Organize bookshelf sections, including the addition, modification, or removal of sections.

Monitor and update the status of receipts, such as marking them as "Shipped" or "Delivered".

## How to run the application:

#### 1.Clone the repository from GitHub: GitHub repositry
#### 2.Create a .env file with necessary environment variables (check .env.sample)
#### 3.Run mvn clean package int terminal
#### 4.Install Docker: Docker Install
#### 5.Run docker-compose build , and docker-compose up to start the Docker containers
#### 6.The application should be running locally at http://localhost:8082
#### 7.Test the application using swagger http://localhost:8088/swagger-ui/index.html
