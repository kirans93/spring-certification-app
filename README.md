# 🎓 Certificate Generation System

A Spring Boot based backend project for generating internship/training completion certificates.  
The system integrates with a relational database, renders certificate templates using Thymeleaf,  
and exports them as downloadable PDFs.

---

## 🚀 Features

- Spring Boot backend with REST APIs  
- Relational database integration using Spring Data JPA  
- Entity relationships: **Student**, **Trainer**, **Training**, **Certificate**  
- Certificate generation with unique Certificate IDs (UUID)  
- Dynamic certificate rendering using Thymeleaf templates  
- HTML → PDF conversion using Flying Saucer  
- Integration and end-to-end testing for reliability  

---

## 🗂️ Project Structure

```
src/main/java/com/kiran/certification/certification
 ├── controller/        # REST Controllers
 ├── model/             # Entities (Student, Trainer, Training, Certificate)
 ├── repo/              # JPA Repositories
 ├── service/           # Interfaces
 ├── serviceimpl/       # Business logic implementations
 └── templates/         # Thymeleaf templates (certificate.html)
```

---

## ⚙️ Tech Stack

- **Language:** Java 17+  
- **Framework:** Spring Boot 3.x  
- **Database:** MySQL / PostgreSQL / H2 (Dev)  
- **ORM:** Spring Data JPA / Hibernate  
- **Template Engine:** Thymeleaf  
- **PDF Generator:** Flying Saucer / OpenHTMLtoPDF  
- **Build Tool:** Maven  

---

## 📌 API Endpoints

- **POST** `/trainings/{id}/certificates` → Generate certificates for a training  
- **GET** `/certificates/{id}` → Get certificate details  
- **GET** `/certificates/{id}/download` → Download certificate PDF  
- **GET** `/students/{id}/certificates` → Get all certificates of a student  

---

## ▶️ Setup & Run

1. **Clone repository**
   ```bash
   git clone git@github.com:kirans93/spring-certification-app.git
   cd spring-certification-app
   ```

2. **Configure database**  
   In `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/certification_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build & Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access APIs**  
   Base URL: `http://localhost:8080`

---

## 🧪 Testing

- Integration testing for API endpoints  
- End-to-end testing for certificate generation and PDF download  
- Error handling and exception management  

---

## 📅 Progress Updates

### 29 Aug 2025 – Progress Update  
As a developer, I have:  
- Extended the business logic implementation to support core backend operations.  

Currently, I am:  
- Adding error handling and exception management to improve system stability.  

Next, I will:  
- Prepare the backend for certification functionality and perform integration testing.  

### 30 Aug 2025 – Progress Update  
As a developer, I have:  
- Completed the backend for the certification module using Spring Boot and JPA.  
- Integrated PDF generation with Thymeleaf templates to produce dynamic certificates.  
- Finalized REST APIs for certificate generation, download, and verification.  
- Conducted integration testing and resolved alignment/formatting issues.  
- Performed end-to-end testing of the certificate generation and download flow.  

---

## 📄 License

------------------------------------------------------------------------------------
