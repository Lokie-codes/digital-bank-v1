# Digital Bank Makefile
# Convenient commands for common tasks

.PHONY: help setup install start stop clean logs health build test

# Default target
help:
	@echo "Digital Bank - Available Commands"
	@echo "=================================="
	@echo ""
	@echo "Setup & Installation:"
	@echo "  make setup      - Run automated setup script"
	@echo "  make install    - Install frontend dependencies"
	@echo ""
	@echo "Running the Application:"
	@echo "  make start      - Start all backend services"
	@echo "  make start-dev  - Start backend + frontend in dev mode"
	@echo "  make start-prod - Start with production optimizations"
	@echo "  make stop       - Stop all services"
	@echo ""
	@echo "Development:"
	@echo "  make frontend   - Start frontend dev server only"
	@echo "  make backend    - Start backend services only"
	@echo "  make build      - Build frontend for production"
	@echo ""
	@echo "Monitoring:"
	@echo "  make logs       - Show logs from all services"
	@echo "  make health     - Run health check on services"
	@echo "  make status     - Show running containers"
	@echo ""
	@echo "Maintenance:"
	@echo "  make clean      - Stop services and clean up volumes"
	@echo "  make restart    - Restart all services"
	@echo "  make rebuild    - Clean and rebuild everything"

# Setup
setup:
	@echo "Running setup script..."
	@chmod +x setup.sh
	@./setup.sh

install:
	@echo "Installing frontend dependencies..."
	@cd frontend && npm install

# Starting services
start:
	@echo "Starting backend services..."
	@cd backend && docker-compose up

start-dev:
	@echo "Starting services in development mode..."
	@echo "Backend will start in Docker, frontend will need to be started separately"
	@echo "Run 'make frontend' in a new terminal"
	@cd backend && docker-compose up

start-prod:
	@echo "Starting services with production optimizations..."
	@cd backend && docker-compose -f docker-compose.yml -f docker-compose.prod.yml up

backend:
	@echo "Starting backend services only..."
	@cd backend && docker-compose up

frontend:
	@echo "Starting frontend development server..."
	@cd frontend && npm run dev

# Stopping services
stop:
	@echo "Stopping all services..."
	@cd backend && docker-compose down

# Monitoring
logs:
	@cd backend && docker-compose logs -f

health:
	@echo "Running health check..."
	@chmod +x health-check.sh
	@./health-check.sh

status:
	@echo "Current running containers:"
	@docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

# Building
build:
	@echo "Building frontend for production..."
	@cd frontend && npm run build

# Maintenance
clean:
	@echo "Cleaning up..."
	@cd backend && docker-compose down -v
	@echo "Removing node_modules..."
	@rm -rf frontend/node_modules
	@echo "Removing build artifacts..."
	@rm -rf frontend/dist

restart:
	@echo "Restarting services..."
	@cd backend && docker-compose restart

rebuild:
	@echo "Rebuilding everything..."
	@$(MAKE) clean
	@$(MAKE) setup
	@$(MAKE) install
	@echo "Rebuilding and starting services..."
	@cd backend && docker-compose up --build

# Testing
test:
	@echo "Running tests..."
	@cd frontend && npm run test

# Quick commands
up: start
down: stop
ps: status
