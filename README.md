|Name| ID |
| -------- | -------- |
| Eyerusalem Moges |  UGR/8447/15 | 
| Bitanya Aleme| UGR/3502/15 | 
| Nuhamin Emnebered | UGR/5628/15 |
|Feven Hailu | UGR/5607/15
|Tsion Getaneh | UGR/5106/15 |

#  Coffee Shop App

##  Project Overview
The **Coffee Shop App** is a mobile-first application designed to simplify coffee ordering, menu management, and user authentication. This document outlines the core features, technologies, and setup instructions to guide the development process from inception.

---

## Objectives
- Develop a **mobile application** for ordering coffee.
- Implement **authentication & authorization** for customers and admins.
- Enable **menu management** and **order processing** via a locally hosted backend.
- Ensure a seamless **user experience** with an intuitive UI.
- Provide **comprehensive testing** for reliability.

---

##  Key Features
###  Authentication & Authorization
- **User Registration & Login** using JWT authentication.
- **Role-Based Access Control (RBAC)** (Customers & Admins).
- **Secure Password Storage** with hashing and salting.
- **Delete Account**: Allow users to delete their account permanently.

###  Menu Management (Admin Feature)
- **Create**: Add new coffee items.
- **Read**: View available coffee items.
- **Update**: Modify coffee item details (name, price, description, availability).
- **Delete**: Remove items from the menu.

###  Order Management (User Feature)
- **Create**: Place new coffee orders.
- **Read**: View order history and status.
- **Update**: Modify orders before confirmation.
- **Delete**: Cancel orders before preparation starts.

###  Dashboard & Analytics
- Track **total sales**, **most ordered items**, and **order trends**.
- Display **order statuses** (Pending, In Progress, Completed).

###  Notifications
- Notify users when their order is **ready for pickup**.
- Alert admins of **new orders** in real time.


###  Offline Mode
- Enable menu browsing without an internet connection.

---

##  Tech Stack
### **Frontend**
- **Kotlin Jetpack Compose**

### **Backend**
- **Node.js (Express)** 
- **SQLite/PostgreSQL**
- **JWT Authentication**

### **Testing**
- **JUnit / Mockito (Kotlin)**
- **Postman for API Testing**

---

##  Development Setup
### ** 1️ Clone the Repository**
```sh
 git clone https://github.com/yourusername/coffee-shop-app.git
 cd coffee-shop-app
```

### ** 2️ Backend Setup**
- Install dependencies and start the backend server:
```sh
 cd backend
 npm install  # or pip install -r requirements.txt
 npm start  
```

### ** 3️ Frontend Setup**
- Install dependencies and run the mobile application:
```sh
 cd frontend
 Android Studio 
 ./gradlew build
```

---

## 4 API Endpoints (Planned)
### **Authentication**
| Method | Endpoint            | Description       |
|--------|---------------------|-------------------|
| POST   | /api/auth/register  | Register a user  |
| POST   | /api/auth/login     | Login a user     |
| DELETE | /api/auth/delete    | Delete user account |

### **Menu Management**
| Method | Endpoint            | Description              |
|--------|---------------------|--------------------------|
| GET    | /api/menu           | Get all coffee items    |
| POST   | /api/menu           | Add a new coffee item   |
| PUT    | /api/menu/:id       | Update a coffee item    |
| DELETE | /api/menu/:id       | Delete a coffee item    |

### **Order Management**
| Method | Endpoint            | Description              |
|--------|---------------------|--------------------------|
| GET    | /api/orders         | Get all orders          |
| POST   | /api/orders         | Place a new order       |
| PUT    | /api/orders/:id     | Update an order         |
| DELETE | /api/orders/:id     | Cancel an order         |

---

## 📌 License
This project is licensed under the **MIT License**.

---




