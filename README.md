# UrlSpace 🔗

UrlSpace is a **production-grade URL Shortener platform** built using  
**Java (Spring Boot), React, Redis, and WebSockets**.

It demonstrates real-world backend and full-stack engineering concepts such as:

- JWT authentication & RBAC
- Redis caching and rate limiting
- Idempotent API design
- Async background processing
- Real-time alerting with WebSockets
- Analytics dashboards

---

## 🚀 Features

### 🔐 Authentication & Security

- JWT-based authentication
- Role-based access (USER / ADMIN)
- Protected APIs
- Global exception handling
- Input validation

### 🔗 URL Shortening

- Random short code generation
- **DB-enforced uniqueness**
- **Idempotent URL creation**
- Safe retry logic for collisions
- Ownership-based URL access

### ⚡ Performance & Scalability

- Redis caching with TTL
- Redis-based rate limiting
- Async analytics logging
- Cache eviction on delete

### 📊 Analytics

- Click tracking per URL
- Clicks per day aggregation
- React dashboard with charts

### 🚨 Alerting System

- Traffic spike detection (Redis counters)
- Persistent alert storage
- Real-time admin alerts via WebSocket
- Historical alert viewing

### 🛡 Admin Capabilities

- View users and URLs
- Delete abusive URLs
- Audit logging for admin actions
- Live alert monitoring dashboard

---

## 🏗 Tech Stack

### Backend

- Java 17
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- Redis
- WebSockets (STOMP)
- PostgreSQL

### Frontend

- React (Vite)
- Axios
- React Router
- Chart.js
- SockJS + STOMP

---

## 🧱 Architecture Overview

- React frontend communicates with backend via REST APIs (JWT secured)
- Redis used for:
  - URL redirect caching
  - Rate limiting
  - Traffic spike detection
- Analytics logged asynchronously
- Alerts pushed to admins using WebSockets
- PostgreSQL is the source of truth

---

## ▶️ Running Locally

### Prerequisites

- Java 17+
- Node.js 18+
- Docker (for Redis)

### Start Redis

```bash
docker run -p 6379:6379 redis
```

### Backend

cd backend
mvnw spring-boot:run

### Frontend

cd frontend
npm install
npm run dev

---

### API Highlights

- POST /auth/register
- POST /auth/login
- POST /url/shorten
- GET /{shortCode}
- GET /url/my
- GET /analytics/\*
- GET /admin/alerts (ADMIN only)

---

### Key Engineering Concepts Demonstrated

- Database-level correctness guarantees
- Handling race conditions & retries
- Event-driven design
- Async background processing
- Cache consistency
- Real-time systems using WebSockets

---

### Future Improvements

- Docker Compose
- Email/SMS integration
- OAuth2 / SSO
- Distributed ID generation
- Advanced analytics filters
