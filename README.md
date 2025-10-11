
# Digital Bank

![Digital bank logo](images/Digital-Bank-logo.png)

Welcome to the Digital Bank project! This application is designed to provide a scalable digital banking platform with core features such as user management, accounts, loans, cards, transactions, and an agent name generator.

## Project Structure

The project consists of:
- **Backend**: Microservices-based architecture using Spring Boot
- **Frontend**: Modern React application with Tailwind CSS

<!-- Image of overall backend design -->

<!-- ![Digital Bank](images/digital-bank-backend-architecture.jpg) -->

### Features

1. **User Management**

   * Manage user profiles and authentication.
   * Endpoints to create, update, and retrieve user information.

2. **Accounts**

   * Handle account creation and balance inquiries.
   * Support for transactions between user accounts.

3. **Loans**

   * Submit and manage loan applications.
   * EMI calculation and loan status tracking.

4. **Cards**

   * Request and manage debit and credit cards.
   * Activate and monitor card usage.

5. **Transactions**

   * Perform and record financial transactions.
   * View transaction history and balances.

6. **Agent Name Generator**

   * A FastAPI-based feature to generate unique user names.
   * Combines adjectives and nouns from a JSON file.

7. **Frontend**

   * Modern React-based web application
   * User authentication and registration
   * Account management interface
   * Loan application and tracking
   * Transaction history and creation
   * Responsive design with Tailwind CSS

## Getting Started

### Prerequisites

* Docker
* Docker Compose
* Node.js 18+ (for local development)
* npm or yarn (for local development)

### Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-repository/digital-bank.git
   cd digital-bank
   ```

2. **Build and Start the Application**

   Use Docker Compose to build and run the entire application (backend + frontend).

   ```bash
   cd backend
   docker-compose up --build
   ```

3. **Environment Variables**

   Create a `.env` file in the backend directory and include the following variables:

   ```bash
   # .env file
   POSTGRES_DB=postgres
   POSTGRES_USER=postgres
   POSTGRES_PASSWORD=postgres

   SERVICE_PORT=8080
   DB_PORT=5432
   PROFILE=prod

   JWT_SECRET_KEY=your-string-jwt-secret-key-here
   SPRING_PROFILES_ACTIVE=prod
   
   # Frontend
   FRONTEND_PORT=3000
   API_BASE_URL=http://localhost:8080
   ```

4. **Access the Application**

   Access the application via:

   * **Frontend:** `http://localhost:3000`
   * **Backend API Gateway:** `http://localhost:8080`

### Local Development (Frontend Only)

To run the frontend in development mode:

1. **Navigate to the frontend directory**

   ```bash
   cd frontend
   ```

2. **Install dependencies**

   ```bash
   npm install
   ```

3. **Create environment file**

   Copy `.env.example` to `.env` and configure the API URL:

   ```bash
   cp .env.example .env
   ```

4. **Start the development server**

   ```bash
   npm run dev
   ```

   The frontend will be available at `http://localhost:5173`

5. **Build for production**

   ```bash
   npm run build
   ```

### Configuration

* Configuration settings are managed in a centralized location.
* You can update application settings in the `config` directory.

## Contributing

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit and push your changes.
4. Open a pull request with a detailed description.

## License

This project is licensed under the [MIT License](LICENSE).
