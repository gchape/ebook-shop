# Ebook-Shop

## Overview
EbookShop is a web-based application for browsing, searching, and managing eBooks. It is built using Spring Boot, Thymeleaf, Servlets, and SCSS for styling. The application allows users to search for books via the Google Books API and manage their selections using a cart feature.

## Features
- [X] **Book Listing**: Display books fetched from Google Books API. 
- [ ] **Search**: Search for books by title, author, or ISBN.
- [X] **User Authentication**: Signup and login functionality.
- [ ] **Cart Management**: Add and remove books from the cart.
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
│   ├── services/      # Service classes (DAO, API integration)
│   ├── servlets/      # Servlet controllers
│   ├── utils/         # Utility classes (common functions, helpers, etc.)
│-- src/main/resources/
│   ├── templates/     # Thymeleaf templates
│   ├── static/        # Static assets (CSS, JS, images)
│   ├── sql/           # SQL dump file and other scripts
│-- pom.xml            # Maven dependencies
```
## Diagram
<img src="https://github.com/user-attachments/assets/37f6a063-4f97-48ef-94dd-a89e84a2b9e7" width="600" height="800"/>

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

