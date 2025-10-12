#!/bin/bash

# Health Check Script for Digital Bank Services
# This script checks the health of all running services

echo "🏥 Digital Bank Health Check"
echo "============================"
echo ""

# Colors
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Function to check service health
check_service() {
    local service_name=$1
    local url=$2
    local expected_status=${3:-200}
    
    echo -n "Checking $service_name... "
    
    if command -v curl &> /dev/null; then
        response=$(curl -s -o /dev/null -w "%{http_code}" "$url" 2>/dev/null)
        
        if [ "$response" == "$expected_status" ] || [ "$response" == "200" ]; then
            echo -e "${GREEN}✓ Healthy${NC}"
            return 0
        else
            echo -e "${RED}✗ Unhealthy (HTTP $response)${NC}"
            return 1
        fi
    else
        echo -e "${YELLOW}⚠ curl not found, skipping${NC}"
        return 2
    fi
}

# Check Docker
if ! command -v docker &> /dev/null; then
    echo -e "${RED}✗ Docker is not installed${NC}"
    exit 1
fi

# Check if services are running
echo "Checking Docker containers..."
if ! docker ps | grep -q "digital-bank\|microservices"; then
    echo -e "${YELLOW}⚠ No Digital Bank containers are running${NC}"
    echo "Start services with: cd backend && docker-compose up"
    exit 1
fi

running_containers=$(docker ps --format "table {{.Names}}\t{{.Status}}" | grep -E "service-registry|config-server|user-service|api-gateway")
if [ -n "$running_containers" ]; then
    echo -e "${GREEN}✓ Found running containers:${NC}"
    echo "$running_containers"
else
    echo -e "${RED}✗ No Digital Bank services found${NC}"
    exit 1
fi

echo ""
echo "Checking service endpoints..."

# Check Service Registry (Eureka)
check_service "Service Registry (Eureka)" "http://localhost:8761"

# Wait a moment for services to register
sleep 2

# Check Config Server
check_service "Config Server" "http://localhost:8888/actuator/health" 200

# Check API Gateway
check_service "API Gateway" "http://localhost:8080/actuator/health" 200

# Check Frontend
check_service "Frontend (Dev)" "http://localhost:5173" 200 || \
check_service "Frontend (Prod)" "http://localhost:3000" 200

echo ""
echo "Service Registry Status:"
if command -v curl &> /dev/null; then
    registered_services=$(curl -s http://localhost:8761/eureka/apps | grep -o '<name>[^<]*</name>' | sed 's/<name>//g;s/<\/name>//g' | sort -u)
    if [ -n "$registered_services" ]; then
        echo -e "${GREEN}Registered services:${NC}"
        echo "$registered_services" | while read service; do
            echo "  - $service"
        done
    else
        echo -e "${YELLOW}⚠ No services registered yet (this is normal on first startup)${NC}"
        echo "  Services may take 30-60 seconds to register"
    fi
fi

echo ""
echo "Database Containers:"
db_containers=$(docker ps --format "table {{.Names}}\t{{.Status}}" | grep "db-")
if [ -n "$db_containers" ]; then
    echo -e "${GREEN}✓ Database containers:${NC}"
    echo "$db_containers"
else
    echo -e "${RED}✗ No database containers found${NC}"
fi

echo ""
echo "Resource Usage:"
docker stats --no-stream --format "table {{.Name}}\t{{.CPUPerc}}\t{{.MemUsage}}" | head -n 10

echo ""
echo "🏥 Health check complete!"
