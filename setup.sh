#!/bin/bash

# Digital Bank Setup Script
# This script sets up the Digital Bank application with all required dependencies

set -e

echo "🏦 Digital Bank Setup Script"
echo "=============================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored messages
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_info() {
    echo -e "${YELLOW}ℹ $1${NC}"
}

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    print_error "Docker is not installed. Please install Docker first."
    exit 1
fi
print_success "Docker is installed"

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null; then
    print_error "Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi
print_success "Docker Compose is installed"

# Create Docker network if it doesn't exist
if ! docker network ls | grep -q microservices; then
    print_info "Creating 'microservices' Docker network..."
    docker network create microservices
    print_success "Docker network 'microservices' created"
else
    print_success "Docker network 'microservices' already exists"
fi

# Create .env file in backend if it doesn't exist
if [ ! -f backend/.env ]; then
    print_info "Creating backend/.env file..."
    cat > backend/.env << 'EOF'
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
    print_success "Backend .env file created"
else
    print_success "Backend .env file already exists"
fi

# Create .env file in frontend if it doesn't exist
if [ ! -f frontend/.env ]; then
    print_info "Creating frontend/.env file..."
    cat > frontend/.env << 'EOF'
VITE_API_BASE_URL=http://localhost:8080
EOF
    print_success "Frontend .env file created"
else
    print_success "Frontend .env file already exists"
fi

echo ""
print_success "Setup completed successfully!"
echo ""
print_info "Next steps:"
echo "  1. To start the backend services:"
echo "     cd backend && docker-compose up --build"
echo ""
echo "  2. To start the frontend (in a new terminal):"
echo "     cd frontend && npm install && npm run dev"
echo ""
echo "  3. Access the application:"
echo "     - Frontend: http://localhost:5173"
echo "     - Backend API: http://localhost:8080"
echo ""
print_info "Note: Make sure to change the JWT_SECRET_KEY in backend/.env for production!"
