# 🏦 Java Bank Management System

A console-based Java application for managing bank accounts with full CRUD operations, transaction history, and loan features, backed by a MySQL database.

---

## 📌 Features

- **Account Management**
  - Open new account
  - View all accounts (Only Admin can access with admin username and password)
  - Activate / Deactivate accounts
  - Close account (status-based, not physical deletion)
  
- **Bank Operations**
  - Deposit money
  - Withdraw money
  - Transfer funds between accounts
  - Apply for a loan (with balance and limit checks)
  - Check balance (only for active accounts)

- **Transaction History**
  - View full transaction history per account (deposit, withdrawal, loan, transfer)

- **Validations**
  - Valid account ID checks
  - Active/inactive status checks before operations
  - Minimum balance check for loans
  - Maximum loan cap per account

---

## 🛠️ Technologies Used

- Java (JDK 8+)
- JDBC (Java Database Connectivity)
- MySQL (Database)
- Maven (optional, for project management)

---

## 🧾 Database Structure

### `accounts` Table:
| Column     | Type     | Description                      |
|------------|----------|----------------------------------|
| id         | INT      | Primary Key                      |
| name       | VARCHAR  | Account holder's name            |
| balance    | DOUBLE   | Current account balance          |
| loan       | DOUBLE   | Loan amount taken                |
| status     | VARCHAR  | `Active` or `Inactive`           |

### `transactions` Table:
| Column       | Type     | Description                    |
|--------------|----------|--------------------------------|
| id           | INT      | Primary Key                    |
| account_id   | INT      | Foreign Key to accounts table  |
| type         | VARCHAR  | DEPOSIT / WITHDRAW / LOAN / TRANSFER |
| amount       | DOUBLE   | Transaction amount             |
| description  | VARCHAR  | Extra details about transaction|
| timestamp    | DATETIME | Auto timestamp of action       |

---

## 🚀 How to Run

1. **Clone the repo:**
   ```bash
   git clone https://github.com/your-username/bank-management-system-java.git
   cd bank-management-system-java
