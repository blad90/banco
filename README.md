# Banco App

Technologies: **Spring Boot** + **Angular** + **MySQL** + **Docker**

## Architecture

```
banco/
├── backend/          # Spring Boot
├── frontend/         # Angular
├── BaseDatos.sql     # MySQL schema + seed data
└── docker-compose.yml
```

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET/POST/PUT/DELETE | `/api/clientes` | CRUD Clientes |
| GET/POST/PUT/DELETE | `/api/cuentas` | CRUD Cuentas |
| GET/POST/DELETE | `/api/movimientos` | CRUD Movimientos |
| GET | `/api/reportes?clienteId=&fechaInicio=&fechaFin=` | Estado de Cuenta (JSON) |
| GET | `/api/reportes/pdf?clienteId=&fechaInicio=&fechaFin=` | Estado de Cuenta (PDF) |
| GET | `/api/reportes/base64?...` | Estado de Cuenta |

## Business Rules
- Amount not available: returned when balance is 0 or insufficient for a debit
- Daily limit exceeded: returned when daily withdrawal exceeds $1,000
- Credits are stored as positive values, debits as negative values
- Each transaction updates the running balance

## Run with Docker

```bash
docker-compose up --build
```

- Frontend: http://localhost:4200
- Backend: http://localhost:8081
- MySQL: localhost:3306, mysql:3307 (Docker)

## Run locally

### Backend
```bash
cd backend
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm i 
ng serve
```

### Tests
```bash
# Backend
cd backend && mvn test

# Frontend
cd frontend && npm test
```
