# Smart Education Analytics System

An AI-powered education management backend built with Spring Boot for schools and colleges. Tracks student performance, attendance, and quiz scores — and generates personalized AI insights, study plans, and tests using Groq (Llama 3.3).

---

## Live Demo

https://smart-education-production.up.railway.app


---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Design](#database-design)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Security](#security)
- [AI Integration](#ai-integration)
- [Author](#author)

---

## Features

| Feature | Description |
|---|---|
| JWT Authentication | Secure stateless login with JSON Web Tokens |
| Role-Based Access Control | Separate access for ADMIN, TEACHER, STUDENT, and PARENT |
| Score Tracking | Record and retrieve exam scores across subjects |
| Attendance Management | Mark and monitor student attendance with percentage calculation |
| AI Insights | AI-generated personalized study insights based on student performance |
| Personalized Test Generation | Auto-generated MCQ tests tailored to student weak areas |
| Parent Portal | Parents can view their child's performance and AI-generated summaries |
| Swagger UI | Interactive API documentation at `/swagger-ui/index.html` |

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Programming Language |
| Spring Boot | 3.4.4 | Backend Framework |
| Spring Security | 6.x | Authentication and Authorization |
| Spring Data JPA | 3.4.4 | Database ORM |
| Hibernate | 6.6.x | JPA Implementation |
| MySQL | 8.0 | Relational Database |
| JWT (jjwt) | 0.11.5 | Token-Based Authentication |
| Groq API (Llama 3.3) | — | AI Integration |
| SpringDoc OpenAPI | 2.8.6 | Swagger API Documentation |
| Lombok | 1.18.x | Boilerplate Code Reduction |
| Maven | 3.x | Build Tool |

---

## Project Structure

```
src/main/java/com/smartedu/
├── controller/          REST API endpoints
├── service/             Business logic interfaces
│   └── impl/            Service implementations
├── repository/          Database access layer
├── entity/              JPA entities (mapped to DB tables)
├── dto/
│   ├── request/         Input DTOs for API requests
│   └── response/        Output DTOs for API responses
├── security/            JWT filter and UserDetailsService
├── config/              Security and Swagger configuration
└── exception/           Global exception handling
```

---

## Database Design

The system uses 11 tables organized across 4 layers:

```
Auth Layer        users
People Layer      students, teachers, parents
Academic Layer    subjects, scores, attendance, quiz_scores, teacher_subjects
AI Layer          ai_insights, personalized_tests, test_responses
```

Entity relationships:

```
users ──────────────── students
  |                       |
  ├── teachers            ├── scores
  |                       ├── attendance
  └── parents ────────────├── quiz_scores
                          ├── ai_insights
subjects ───────────────  └── personalized_tests
    |                              |
    └── teacher_subjects      test_responses
```

---

## Getting Started

### Prerequisites

- Java 21
- MySQL 8.0
- Maven 3.x

### Installation

**1. Clone the repository**
```bash
git clone https://github.com/taniy8/smart-education.git
cd smart-education
```

**2. Create the database**
```sql
CREATE DATABASE smart_edu_db;
```

**3. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_edu_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.ai.openai.api-key=your_groq_api_key

server.port=8080
```

> Get a free Groq API key at https://console.groq.com

**4. Run the application**
```bash
mvn spring-boot:run
```

**5. Access Swagger UI**
```
http://localhost:8080/swagger-ui/index.html
```

Hibernate will automatically create all database tables on first run.

---

## API Documentation

### Auth (Public)
```
POST /api/auth/register          Register a new user
POST /api/auth/login             Login and receive JWT token
```

### Students (ADMIN, TEACHER)
```
GET    /api/students                          Get all students
GET    /api/students/{id}                     Get student by ID
GET    /api/students/roll/{rollNumber}        Get by roll number
GET    /api/students/class/{className}        Get by class
POST   /api/students                          Add new student
PUT    /api/students/{id}                     Update student
DELETE /api/students/{id}                     Delete student
```

### Scores (ADMIN, TEACHER)
```
GET  /api/scores/student/{id}                       Get all scores for student
GET  /api/scores/weak?studentId=1                   Get weak scores
GET  /api/scores/student/{id}/subject/{id}/average  Get subject average
POST /api/scores                                    Add score
PUT  /api/scores/{id}                               Update score
```

### Attendance (ADMIN, TEACHER)
```
GET  /api/attendance/student/{id}             Get attendance records
GET  /api/attendance/student/{id}/percentage  Get attendance percentage
POST /api/attendance                          Mark attendance
```

### AI Insights (ADMIN, STUDENT)
```
POST /api/insights/generate/{studentId}       Generate AI insight for student
GET  /api/insights/student/{id}/latest        Get latest insight
```

### Personalized Tests (ADMIN, STUDENT)
```
GET  /api/tests/generate?studentId=1&subjectId=1&difficulty=MEDIUM    Generate test
GET  /api/tests/{id}                                                   Get test by ID
```

---

## Security

- JWT tokens expire after 24 hours
- Passwords hashed with BCrypt
- All endpoints protected by role-based access control

| Role | Access |
|---|---|
| ADMIN | Full system access |
| TEACHER | Students, Scores, Attendance, Subjects |
| STUDENT | Own insights and personalized tests |
| PARENT | Child's performance summary |

---

## AI Integration

The system uses **Groq API with Llama 3.3-70b** for two AI features:

**Student Insights**
- Analyzes a student's exam scores across subjects
- Identifies weak areas (below 60%) and strong areas (above 75%)
- Generates a personalized weekly study plan
- Produces a parent-friendly performance summary

**Personalized Test Generation**
- Generates 5 MCQ questions per test
- Supports three difficulty levels: EASY, MEDIUM, HARD
- Questions are subject-specific and stored as JSON

---

## Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m "Add: your feature"`
4. Push and open a Pull Request

Please follow the existing code structure and keep one feature per PR.

---

## Author

**Taniya Saxena** — [@taniy8](https://github.com/taniy8)


---

## License

This project is licensed under the MIT License.
