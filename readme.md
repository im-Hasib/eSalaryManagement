# 🧾 Employee Salary Management System

A full-stack web application built using **Spring Boot** and **Angular** to manage employee salary payments with proper accounting and validation features.

---

## 🚀 Project Objective

To develop a salary management system that calculates and processes employee payments based on grade levels and predefined salary rules.

---

## 🧱 Tech Stack

### Backend:

- Java 11 / 17 / 21
- Spring Boot 3+
- Spring Data JPA
- Hibernate
- Maven
- PostgreSQL / MSSQL

### Frontend:

- Angular (TypeScript)

---

## 📚 Project Overview

### 👤 Employee Grades:

| Grade | Number of Employees |
| ----- | ------------------- |
| 1     | 1 (highest)         |
| 2     | 1                   |
| 3     | 2                   |
| 4     | 2                   |
| 5     | 2                   |
| 6     | 2 (lowest)          |

---

### 🧾 Employee Details:

- 4-digit Unique Employee ID
- Name
- Grade/Rank
- Address
- Mobile Number
- Bank Account

---

### 🏦 Bank Account Details:

- Account Type: `Savings` / `Current`
- Account Name
- Account Number
- Current Balance
- Bank Name & Branch

---

### 💰 Salary Calculation:

- **Input:** Basic salary of the lowest grade
- **House Rent:** 20% of Basic
- **Medical Allowance:** 15% of Basic
- **Higher Grade Salary Formula:**
