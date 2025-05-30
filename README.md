
# Digital Bank

Welcome to the Digital Bank project! This application is designed to provide a scalable digital banking platform with core features such as user management, accounts, loans, cards, transactions, and an agent name generator.

## Project Structure

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

## Getting Started

### Prerequisites

* Docker
* Docker Compose

### Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-repository/digital-bank.git
   cd digital-bank
   ```

2. **Build and Start the Application**

   Use Docker Compose to build and run the application.

   ```bash
   docker-compose up --build
   ```

3. **Environment Variables**

   Create a `.env` file and include the following variables:

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
   ```

4. **Access the Application**

   Access the main application via:

   * **Main App URL:** `http://localhost:8080`

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
