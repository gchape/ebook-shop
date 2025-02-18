# Ebook-Shop

## Overview
EbookShop is a web-based application for browsing, searching, and managing eBooks. It is built using Spring Boot, Thymeleaf, Servlets, and SCSS for styling. The application allows users to search for books via the Google Books API and manage their selections using a cart feature.

## Features
- **Book Listing**: Display books fetched from Google Books API.
- **Search**: Search for books by title, author, or ISBN.
- **User Authentication**: Signup and login functionality.
- **Cart Management**: Add and remove books from the cart.
- **Server-side Rendering**: Uses Thymeleaf for dynamic HTML rendering.

## Technologies Used
- **Spring Boot** (Service layer and dependency management)
- **Jakarta Servlets** (Request handling)
- **Thymeleaf** (View rendering)
- **Google Books API** (Fetching book data)
- **SCSS** (Styling and UI enhancements)
- **JPA/Hibernate** (Data persistence)

## Project Structure
```
EbookShop/
│-- src/main/java/io/github/gchape/ebookshop/
│   ├── entities/      # Entity classes (Book, User, etc.)
│   ├── services/      # Service classes (DAO, API integration)
│   ├── servlets/      # Servlet controllers
│-- src/main/resources/
│   ├── templates/     # Thymeleaf templates
│   ├── static/        # Static assets (CSS, JS, images)
│-- pom.xml            # Maven dependencies
```
## ER model
![unnamed0](https://github.com/user-attachments/assets/04a9c833-05bc-4f12-adba-f6bb054aa5c8)


## Setup and Installation
### Prerequisites
- Java 17+
- Maven
- PostgreSQL (or other configured database)
- Spring Web
- Spring Data JPA
- Thymeleaf

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/gchape/ebookshop.git
   cd ebookshop
   ```
2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ebookshop
   spring.datasource.username=your_user
   spring.datasource.password=your_password
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the application in your browser at:
   ```
   http://localhost:8080/
   ```

## API Endpoints
- `GET /` - Home page displaying books
- `GET /search?query=title` - Search books by title
- `POST /signup` - Register a new user
- `POST /signin` - Authenticate user
- `GET /cart` - View cart
- `POST /cart/add/{id}` - Add book to cart
- `POST /cart/remove/{id}` - Remove book from cart

## Styling with SCSS
SCSS files are located under `src/main/resources/static/css/`. To compile them, use:
```sh
sass --watch src/main/resources/static/css:src/main/resources/static/css
```

## Contribution
Feel free to fork the repository and submit pull requests for improvements.

## License
This project is licensed under the MIT License.

