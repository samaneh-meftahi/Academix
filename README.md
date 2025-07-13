# Academix - Academic Management System

**Academix** is a robust Spring Boot application designed to efficiently manage academic entities such as students, professors, and courses. Built on a layered architecture, it leverages JPA for seamless database interactions and Spring Security for secure authentication and authorization.

---

## 🚀 Features

- **Comprehensive Academic Management**  
  Perform create, read, update, and delete (CRUD) operations on Students, Professors, and Courses.

- **RESTful APIs**  
  Well-structured endpoints for smooth integration with frontend applications.

- **Spring Security Integration**  
  Role-based authentication and authorization to protect resources.

- **Custom Exception Handling**  
  Enhanced error management with meaningful custom exceptions.

- **Layered Architecture**  
  Clear separation of concerns with DTO, Service, Repository, and API layers.

---

## 🛠️ Technologies Used

- Spring Boot  
- Spring Security  
- JPA & Hibernate  
- H2 Database (in-memory for development and testing)  
- Maven  

---

## 📦 Installation & Setup

1. **Clone the repository:**

   ```
   git clone https://github.com/samaneh-meftahi/Academix.git
   cd Academix
   ```

2. **Configure the database:**  
   The project uses an in-memory H2 database by default.  
   To switch databases, update `application.properties` accordingly.

3. **Build and run the application:**

   ```
   mvn spring-boot:run
   ```

4. **Access the API:**  
   Base URL: `http://localhost:8081`  
   Example endpoints:  
   - `GET /students`  
   - `POST /professors`

5. **Access H2 Console:**  
   URL: `http://localhost:8080/h2-console`  
   JDBC URL: `jdbc:h2:mem:testdb`  
   Username: `sa`  
   Password: *(leave empty)*

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repository and submit pull requests.

---

## License

This project is licensed under the MIT License.
