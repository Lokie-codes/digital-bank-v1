# 🚀 Quick Start Guide

Get the Digital Bank application running in just a few minutes!

## Prerequisites Check

Before starting, make sure you have:

- [ ] Docker installed (`docker --version`)
- [ ] Docker Compose installed (`docker-compose --version`)
- [ ] Node.js 18+ installed (for frontend development)
- [ ] At least 4GB of free RAM
- [ ] Ports 5173, 8080, and 8761 available

## Installation (3 steps)

### Step 1: Clone and Setup

```bash
# Clone the repository
git clone https://github.com/Lokie-codes/digital-bank-v1.git
cd digital-bank-v1

# Run automated setup
chmod +x setup.sh
./setup.sh
```

### Step 2: Start Backend Services

```bash
cd backend
docker-compose up --build
```

**Note:** First startup will take 5-10 minutes to download and build images. Subsequent starts are much faster.

### Step 3: Start Frontend (in a new terminal)

```bash
cd frontend
npm install
npm run dev
```

## Access the Application

Once all services are running (wait ~60 seconds after starting):

- 🌐 **Frontend**: http://localhost:5173
- 🔌 **API Gateway**: http://localhost:8080
- 📡 **Service Registry**: http://localhost:8761

## What You'll See

### Home Page
![Home Page](screenshots/01-home-page.png)

The landing page with features overview and call-to-action buttons.

### Register
![Register Page](screenshots/03-register-page.png)

Create a new account with:
- First Name & Last Name
- Username (must be unique)
- Email
- Phone Number
- Password

### Login
![Login Page](screenshots/02-login-page.png)

Sign in with your username and password.

## Quick Test

1. Click "Create Account" on the home page
2. Fill in the registration form
3. Click "Create Account" button
4. If backend is running, you'll be redirected to login
5. Sign in with your credentials
6. Explore the dashboard!

## Troubleshooting

### Frontend won't start?
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
npm run dev
```

### Backend services won't start?
```bash
cd backend
docker-compose down -v
docker network create microservices
docker-compose up --build
```

### Services not registering?
- Wait 60 seconds after starting
- Check http://localhost:8761 for registered services
- Check logs: `docker-compose logs -f <service-name>`

### Port already in use?
```bash
# Find and kill the process using port 8080
lsof -i :8080
kill -9 <PID>
```

## Health Check

Run the health check script to verify all services:

```bash
./health-check.sh
```

## Next Steps

- 📖 Read the [full setup guide](SETUP.md) for advanced configuration
- 🔧 Check [optimization tips](SETUP.md#optimizations) for production deployment
- 🐛 See [troubleshooting guide](SETUP.md#troubleshooting) for detailed solutions

## Common Commands

```bash
# Start backend
cd backend && docker-compose up

# Start frontend
cd frontend && npm run dev

# Stop backend
cd backend && docker-compose down

# View logs
cd backend && docker-compose logs -f

# Restart a service
cd backend && docker-compose restart <service-name>

# Check service health
./health-check.sh
```

## Default Ports

| Service | Port |
|---------|------|
| Frontend (Dev) | 5173 |
| Frontend (Prod) | 3000 |
| API Gateway | 8080 |
| Service Registry | 8761 |
| Config Server | 8888 |
| PostgreSQL DBs | 5432-5435 |

## Need Help?

1. Check the logs: `cd backend && docker-compose logs -f`
2. Run health check: `./health-check.sh`
3. See [SETUP.md](SETUP.md#troubleshooting) for detailed troubleshooting
4. Create an issue on GitHub with error details

---

**⏱️ Total setup time**: ~10 minutes (first time), ~2 minutes (subsequent starts)

**💡 Tip**: Keep the backend terminal open to monitor service logs!
