(In progress ...)

# Digital Bank Microservices

Welcome to the Digital Bank microservices project! This project is designed to create a scalable digital banking application using a microservices architecture. It consists of various services, including user management, accounts, loans, cards, transactions, and an agent name generator.

## Project Structure

### Services

1. **User Service**
   - Manages user profiles and authentication.
   - Provides endpoints to create, update, and retrieve user information.

2. **Accounts Service**
   - Handles user accounts.
   - Manages account creation, balance inquiries, and transactions.

3. **Loan Service**
   - Manages loan applications and approvals.
   - Provides functionality to calculate EMI and manage loan statuses.

4. **Cards Service**
   - Manages debit and credit cards.
   - Allows users to request, activate, and manage their cards.

5. **Transactions Service**
   - Handles financial transactions between accounts.
   - Supports transaction history and balance updates.

### Supporting Components

1. **Spring Boot Service Registry**
   - A service registry for discovering and managing microservices.
   - Uses Eureka for service registration and discovery.

2. **Config Server**
   - Centralized configuration management for all microservices.
   - Stores and provides configuration properties for the services.

3. **Agent Name Generator (FastAPI)**
   - A FastAPI service that generates unique names for users.
   - Utilizes a JSON file with adjectives and nouns to create distinctive agent names.

## Getting Started

### Prerequisites

- Docker
- Docker Compose

### Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-repository/digital-bank.git
   cd digital-bank
   ```

2. **Build and Start Services**

   Use Docker Compose to build and start all services.

   ```bash
   docker-compose up --build
   ```

(Updation of ports)
3. **Access Services**
    - **API Gateway:** `http://localhost:8080`
   - **User Service:** `http://localhost:8081`
   - **Accounts Service:** `http://localhost:8082`
   - **Loan Service:** `http://localhost:8083`
   - **Cards Service:** `http://localhost:8084`
   - **Transactions Service:** `http://localhost:8085`
   - **Agent Name Generator:** `http://localhost:8086`

### Configuration

- Configuration files are stored in the Config Server.
- Modify the configurations as needed in the `config` directory.

### API Documentation

- Each microservice provides its own API documentation. You can access Swagger UI for each service at:
(Need to add swagger dependencies)
  - **User Service:** `http://localhost:8081/swagger-ui.html`
  - **Accounts Service:** `http://localhost:8082/swagger-ui.html`
  - **Loan Service:** `http://localhost:8083/swagger-ui.html`
  - **Cards Service:** `http://localhost:8084/swagger-ui.html`
  - **Transactions Service:** `http://localhost:8085/swagger-ui.html`
  - **Agent Name Generator:** `http://localhost:8086/docs`

## Contributing

1. Fork the repository.
2. Create a new branch for your feature or fix.
3. Commit your changes.
4. Push to the branch.
5. Create a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
