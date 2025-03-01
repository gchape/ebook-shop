# Ebook-Shop

## Overview
EbookShop is a web-based application for browsing, searching, and managing eBooks. It is built using Spring Boot, Thymeleaf, Servlets, and SCSS for styling. The application allows users to search for books via the Google Books API and manage their selections using a cart feature.

## Features
- [X] **Book Listing**: Display books fetched from Google Books API.
- [X] **Payment Mock**: Unlimited payment.
- [X] **User Authentication**: Signup and login functionality.
- [X] **Cart Management**: Add and remove books from the cart.
- [X] **Server-side Rendering**: Uses Thymeleaf for dynamic HTML rendering.

## Technologies Used
- **Spring Boot** (Service layer and dependency management)
- **Jakarta Servlets** (Request handling)
- **Thymeleaf** (View rendering)
- **Google Books API** (Fetching book data)
- **SCSS** (Styling and UI enhancements)
- **JPA/Hibernate** (Data persistence)

## Pictures
### Home
<img src="https://github.com/user-attachments/assets/e44d1f54-9344-404a-82de-27ac412dd070" width="600" />

### Authentication (BCrypt + HTML Escaping)
<img src="https://github.com/user-attachments/assets/b9f415c6-8cbb-47f3-aa74-393c8aa1c964" width="300" />
<img src="https://github.com/user-attachments/assets/c3f8a035-d563-4c52-bbab-7b0e913b7766" width="300" />

## Project Structure
```
EbookShop/
│-- src/main/java/io/github/gchape/ebookshop/
│   ├── entities/      # Entity classes (Book, User, etc.)
│   ├── services/      # Service classes (Business logic)
│   ├── servlets/      # Servlet controllers
│   ├── repositories/
│   ├── utils/         # Utility classes (common functions, helpers, etc.)
│-- src/main/resources/
│   ├── templates/     # Thymeleaf templates
│   ├── static/        # Static assets (CSS, JS, images)
│   ├── sql/           # SQL dump file and other scripts
│-- pom.xml            # Maven dependencies
```
## Diagram
![public (1)](https://github.com/user-attachments/assets/2eedcd53-bf68-4adf-b4ae-37b0e2f24c36)

## Setup and Installation
### Prerequisites
- Java 17+
- Maven
- PostgreSQL
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
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
   spring.datasource.username=your_user
   spring.datasource.password=your_password
   ```
   Import schema
   ```
   psql -U your_user -d your_database -f sql_dump_file
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the application in your browser at:
   ```
   http://localhost:8080/
   ```

## Contribution
Feel free to fork the repository and submit pull requests for improvements.

## License
This project is licensed under the GNU General Public License.

