# Online Book Store API Documentation


## Description:

The "Online Book Store" application is a web service that allows users to perform various operations related to a catalog of books and orders. The application implements secure authentication using JWT tokens.


## Technology Stack:
<details>
  <summary><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZqRFNAis0vxGXeQDFA2thujnilvYO8eqTKDX5QgJ5APGtLTNQu0-d6rTkb8oSWOdyRyY&usqp=CAU" width="30"/> Java</summary>

`In this project, we used Java as the main programming language.`
</details>

<details>
  <summary><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwsq-7f5BWyog4cdeT1sQaYLVzhJ0o37Up8TjHvVU08WUgfyyMMRMHTVwJ5XReSjyhZa0&usqp=CAU" width="30"/> Spring Boot</summary>

`A powerful framework for building Java-based applications.`
</details>

<details>
  <summary><img src="https://www.baeldung.com/wp-content/uploads/2021/02/lsd-module-icon-1.png" width="30"/> Spring Data JPA</summary>

`Simplifies data access and persistence with JPA (Java Persistence API).`
</details>

<details>
  <summary><img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" width="30"/> Spring Security</summary>

`Enables robust and secure authentication and authorization mechanisms.`
</details>

<details>
  <summary><img src="https://oddblogger.com/wp-content/uploads/2021/03/swagger-logo-2.png" width="30"/> Swager</summary>

`Provides API documentation.`
</details>

<details>
  <summary><img src="https://www.freepnglogos.com/uploads/logo-mysql-png/logo-mysql-mysql-logo-png-images-are-download-crazypng-21.png" width="30"/> MySQL </summary>

` Utilization of a relational database to store information about books, users, orders, etc.`

</details>

<details>
  <summary><img src="https://cdn-icons-png.flaticon.com/512/919/919853.png" width="30"/> Docker</summary>

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
  <summary><img src="https://www.liquibase.org/wp-content/themes/liquibase/assets/img/cta-icon.svg" width="30" height="30"/> Liquibase </summary>

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
