Academix - Academic Management System

Academix is a Spring Boot-based application designed for managing academic entities such as students, professors, and courses. It follows a layered architecture with JPA for database interactions and Spring Security for authentication and authorization.

🚀 Features

Student, Professor, and Course Management: Create, read, update, and delete operations for academic entities.

RESTful APIs: Well-structured endpoints for seamless frontend integration.

Spring Security: Role-based authentication and authorization.

Exception Handling: Custom exceptions for improved error management.

Layered Architecture: Organized into DTO, Service, Repository, and API layers.

🛠️ Technologies Used

Spring Boot

Spring Security

JPA & Hibernate

H2 Database (In-memory database for development and testing)

Maven

📦 Installation & Setup

Clone the repository:

git clone https://github.com/yourusername/Academix.git
cd Academix

Configure the database:

The project is pre-configured to use an in-memory H2 Database.

If needed, update application.properties to switch to another database.

Build and run the application:

mvn spring-boot:run

Access the API:

Base URL: http://localhost:8081

Sample Endpoints:

GET /students

POST /professors

Access H2 Console:

URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: (leave empty by default)

Contributors

If you'd like to contribute, feel free to fork the repository and submit a pull request.