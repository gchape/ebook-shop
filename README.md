# Ebook-Shop

## Overview
EbookShop is a web-based application for browsing, searching, and managing eBooks. It is built using Spring Boot, Thymeleaf, Servlets, and SCSS for styling. The application allows users to search for books via the Google Books API and manage their selections using a cart feature.

## Features
- [X] **Book Listing**: Display books fetched from Google Books API. 
- [X] **Payment Mock**
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

