# Smart Education Analytics

An AI-powered academic analytics platform for schools and colleges that analyzes student performance data and generates personalized insights, study plans, and recommendations using Spring AI and GPT.

---

## Features

- **Student Performance Tracking** — Marks, attendance, and quiz scores across all subjects
- **AI-Powered Insights** — GPT analyzes student data and identifies weak/strong areas
- **Personalized Test Generation** — Auto-generates MCQs based on student's weak topics
- **Smart Study Plans** — Daily/weekly revision plans tailored to each student
- **Parent Dashboard** — Simplified performance summaries for parents
- **Teacher Dashboard** — Class-level heatmaps and low-performing student alerts
- **Role-Based Access** — Separate access for Admin, Teacher, Student, and Parent

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 4.0 |
| Database ORM | Spring Data JPA + Hibernate |
| Database | MySQL 8 |
| AI Integration | Spring AI + OpenAI GPT |
| Security | Spring Security + JWT |
| API Docs | Swagger UI (SpringDoc OpenAPI) |
| Build Tool | Maven |

---

## Database Design

The system uses **11 tables** organized into 4 layers:

```
Auth Layer        → users
People Layer      → students, teachers, parents
Academic Layer    → subjects, scores, attendance, quiz_scores, teacher_subjects
AI Layer          → ai_insights, personalized_tests, test_responses
```

---

## Setup Instructions

### Prerequisites

- Java 21
- Maven
- MySQL 8
- IntelliJ IDEA (recommended)

### 1. Clone the repository
```bash
git clone https://github.com/taniy8/smart-education.git
cd smart-education
```

### 2. Create the database
```sql
CREATE DATABASE smart_edu_db;
```

### 3. Configure application.properties
Create `src/main/resources/application.properties` and add:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_edu_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

spring.ai.openai.api-key=your_openai_api_key

server.port=8080
```

### 4. Run the project
```bash
mvn spring-boot:run
```

Or click the **Run** button in IntelliJ.

### 5. Access Swagger UI
```
http://localhost:8080/swagger-ui.html
```

Hibernate will automatically create all database tables on first run.

---

## Project Structure

```
src/main/java/com/smartedu/
├── entity/          → JPA Entity classes (DB tables)
├── repository/      → Database operations
├── service/         → Business logic
├── controller/      → REST API endpoints
└── security/        → JWT authentication
```

---

## User Roles

| Role | Access |
|---|---|
| ADMIN | Full system access |
| TEACHER | Class performance, mark entry, attendance |
| STUDENT | Own scores, AI insights, personalized tests |
| PARENT | Child's performance summary and AI tips |

---

## Project Status

Currently in development.

- [x] Project setup
- [x] Database design
- [x] Entity classes
- [ ] Repository layer
- [ ] Service layer
- [ ] REST APIs
- [ ] JWT Authentication
- [ ] AI Integration
- [ ] Frontend Dashboard

---

## Contributing

Contributions are welcome.

### How to Contribute

**1. Fork the repository**

Click the "Fork" button on the top right of this page.

**2. Clone your fork**
```bash
git clone https://github.com/your-username/smart-education.git
cd smart-education
```

**3. Create a new branch**
```bash
git checkout -b feature/your-feature-name
```

**4. Make your changes and commit**
```bash
git add .
git commit -m "Add: your feature description"
```

**5. Push and create a Pull Request**
```bash
git push origin feature/your-feature-name
```
Then go to GitHub → click **"Compare & Pull Request"** → describe your changes → submit.

---

### Contribution Guidelines

- Follow existing code structure and naming conventions
- Write clean, readable code with comments where necessary
- One feature per Pull Request — don't combine multiple changes
- Test your changes before submitting
- Update README if you add a new feature

---

### Areas You Can Contribute To

| Area | Description |
|---|---|
| Bug Fixes | Fix any issues you find |
| New Features | Add features from the roadmap |
| Documentation | Improve README or add code comments |
| Tests | Write unit or integration tests |
| Frontend | Build the React/Angular dashboard |

---

**Found a bug?** Go to the Issues tab → New Issue → describe the bug and steps to reproduce.

**Have an idea?** Go to the Issues tab → New Issue → label it `enhancement` and describe your idea.

---

## Authors

**Taniya** — [@taniy8](https://github.com/taniy8)

**Harishkrishnakumar** — [@Harishkrishnakumarr](https://github.com/Harishkrishnakumarr/Smart__Education)

---

*This project is built as a full-stack AI-integrated backend system demonstrating skills in Spring Boot, JPA/Hibernate, MySQL, REST API design, and AI integration.*
