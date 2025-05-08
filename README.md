| Name              | ID          |
| ----------------- | ----------- |
| Eyerusalem Moges  | UGR/8447/15 |
| Bitanya Aleme     | UGR/3502/15 |
| Nuhamin Emnebered | UGR/5628/15 |
| Feven Hailu       | UGR/5607/15 |
| Tsion Getaneh     | UGR/5106/15 |

# Coffee Shop App

## Project Overview

The **Coffee Shop App** System is a local application designed to help coffee shop owners and managers efficiently handle daily operations. It provides features for inventory tracking, employee shift management, and other essential tasks to streamline business processes. The system is built with a REST API that runs locally, ensuring data security and control. No cloud hosting or Firebase/Firestore is used for authentication or storage.

---

## Features

### 1. User Authentication & Authorization

- Role-based authentication (**Admin, Manager, Employee**).
- Secure password hashing using **bcrypt**.
- Login/logout functionality using **JWT tokens**.

### 2. Inventory Management (CRUD)

- Add, update, delete, and track stock items (e.g., coffee beans, milk, cups).
- Low-stock alerts to prevent shortages.

### 3. Employee Shift Management (CRUD)

- Assign employees to specific shifts (morning/evening).
- View, update, and delete shift schedules.

### 4. Sales & Transactions Tracking

- Record daily sales data and transaction history.
- Generate reports on revenue and popular items.

### 5. Local Database & REST API (No Hosting Required)

- Backend API built using **Node.js + Express/NestJS**.
- **Database runs locally** .
- RESTful API with endpoints for inventory, employees, and sales.

### 6. Testing (Unit, Integration, & UI Tests)

- **Unit tests** for backend logic (Jest).
- **Integration tests** for API endpoints (Supertest).
- **End-to-end tests** .
