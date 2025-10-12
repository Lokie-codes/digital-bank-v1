# Digital Bank Setup Guide

This guide will help you set up and run the Digital Bank application on your local machine.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Detailed Setup](#detailed-setup)
- [Running the Application](#running-the-application)
- [Troubleshooting](#troubleshooting)
- [Optimizations](#optimizations)

## Prerequisites

Before you begin, ensure you have the following installed:

- **Docker** (version 20.10 or higher)
- **Docker Compose** (version 2.0 or higher)
- **Node.js** (version 18 or higher) - for frontend development
- **npm** or **yarn** - for frontend package management
- **Git** - for cloning the repository

### Verify Installation

```bash
docker --version
docker-compose --version
node --version
npm --version
```

## Quick Start

The fastest way to get started is using our automated setup script:

```bash
# Clone the repository
git clone https://github.com/Lokie-codes/digital-bank-v1.git
cd digital-bank-v1

# Run the setup script
chmod +x setup.sh
./setup.sh

# Start the backend services
cd backend
docker-compose up --build

# In a new terminal, start the frontend
cd frontend
npm install
npm run dev
```

Visit `http://localhost:5173` to see the application!

## Detailed Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/Lokie-codes/digital-bank-v1.git
cd digital-bank-v1
```

### Step 2: Create Docker Network

The application uses a custom Docker network for microservices communication:

```bash
docker network create microservices
```

### Step 3: Configure Environment Variables

#### Backend Configuration

Create a `.env` file in the `backend` directory:

```bash
cd backend
cat > .env << 'EOF'
# Database Configuration
POSTGRES_DB=postgres
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

# Service Configuration
SERVICE_PORT=8080
DB_PORT=5432
PROFILE=prod

# Security
JWT_SECRET_KEY=your-string-jwt-secret-key-here-change-in-production
SPRING_PROFILES_ACTIVE=prod

# Frontend
FRONTEND_PORT=3000
API_BASE_URL=http://localhost:8080
EOF
```

**⚠️ Important:** Change the `JWT_SECRET_KEY` to a secure random string for production!

#### Frontend Configuration

Create a `.env` file in the `frontend` directory:

```bash
cd ../frontend
cat > .env << 'EOF'
VITE_API_BASE_URL=http://localhost:8080
EOF
```

### Step 4: Install Frontend Dependencies

```bash
cd frontend
npm install
```

## Running the Application

### Option 1: Using Docker Compose (Recommended for Production)

Start all services including backend and frontend:

```bash
cd backend
docker-compose up --build
```

This will start:
- Service Registry (Eureka) - Port 8761
- Config Server - Port 8888
- API Gateway - Port 8080
- User Service
- Account Service
- Loan Service
- Transaction Service
- Agent Name Service
- Frontend - Port 3000
- PostgreSQL databases

### Option 2: Backend with Docker, Frontend Locally (Recommended for Development)

Start the backend services:

```bash
cd backend
docker-compose up --build
```

In a new terminal, start the frontend development server:

```bash
cd frontend
npm run dev
```

The frontend will be available at `http://localhost:5173` with hot-reload enabled.

### Accessing the Application

- **Frontend:** http://localhost:5173 (development) or http://localhost:3000 (production)
- **API Gateway:** http://localhost:8080
- **Service Registry:** http://localhost:8761

## Troubleshooting

### Common Issues and Solutions

#### 1. Docker Network Error

**Error:** `network microservices declared as external, but could not be found`

**Solution:**
```bash
docker network create microservices
```

#### 2. Port Already in Use

**Error:** `Bind for 0.0.0.0:8080 failed: port is already allocated`

**Solution:**
```bash
# Find the process using the port
lsof -i :8080  # On Mac/Linux
netstat -ano | findstr :8080  # On Windows

# Kill the process or change the port in docker-compose.yml
```

#### 3. Frontend Can't Connect to Backend

**Solution:**
- Ensure the backend is running: `docker-compose ps`
- Check the API URL in `frontend/.env`
- Verify the API Gateway is accessible: `curl http://localhost:8080`

#### 4. Database Connection Issues

**Solution:**
```bash
# Check if databases are running
docker ps | grep postgres

# Check logs
docker-compose logs db-user-service
docker-compose logs db-accounts-service
```

#### 5. Services Not Registering with Eureka

**Solution:**
- Wait 30-60 seconds for services to register
- Check Service Registry: http://localhost:8761
- Check service logs: `docker-compose logs <service-name>`

#### 6. Build Failures

**Solution:**
```bash
# Clean Docker cache
docker-compose down -v
docker system prune -a

# Rebuild
docker-compose up --build --force-recreate
```

### Checking Service Health

```bash
# View all running containers
docker-compose ps

# View logs for a specific service
docker-compose logs -f user-service

# View logs for all services
docker-compose logs -f

# Check Service Registry
curl http://localhost:8761/eureka/apps
```

## Optimizations

### Production Optimizations

#### 1. Frontend Build Optimization

```bash
cd frontend

# Build for production
npm run build

# The optimized files will be in the dist/ folder
```

#### 2. Backend JVM Optimization

Add JVM options in `docker-compose.yml`:

```yaml
environment:
  JAVA_OPTS: >-
    -Xms512m
    -Xmx1024m
    -XX:+UseG1GC
    -XX:MaxGCPauseMillis=200
```

#### 3. Database Connection Pooling

Configure in `application.yml`:

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
```

#### 4. Enable Caching

Add Redis for caching:

```yaml
redis:
  image: redis:alpine
  ports:
    - "6379:6379"
```

### Development Optimizations

#### 1. Use Docker BuildKit

```bash
export DOCKER_BUILDKIT=1
docker-compose up --build
```

#### 2. Frontend Hot Reload

Already configured in `vite.config.js`. Ensure you're using `npm run dev`.

#### 3. Selective Service Startup

Start only the services you need:

```bash
docker-compose up service-registry config-server user-service api-gateway
```

### Performance Monitoring

#### 1. View Resource Usage

```bash
docker stats
```

#### 2. Check Application Metrics

Access Actuator endpoints (if configured):
- http://localhost:8080/actuator/health
- http://localhost:8080/actuator/metrics

## Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev/)
- [Vite Documentation](https://vitejs.dev/)

## Support

If you encounter any issues not covered here:

1. Check the logs: `docker-compose logs`
2. Search for similar issues in the repository
3. Create a new issue with:
   - Error message
   - Steps to reproduce
   - Environment details (OS, Docker version, etc.)
