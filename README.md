# 🏥 Hospital Management System (Microservices)

A scalable microservices-based backend system for hospital management.

## 🚀 Overview

This is a **Microservices-based Hospital Management System** built using modern backend technologies.

It handles:

* Patient Management
* Doctor Management
* Appointment Booking
* Billing & Payments
* Notifications
* Reporting & Analytics

---

## 🏗️ Architecture

This project follows a **Microservices Architecture** with:

* API Gateway
* Service Registry (Eureka)
* Multiple Independent Services
* Kafka for Event-Driven Communication

---

## 🧰 Tech Stack

* **Backend:** Spring Boot, Spring Cloud
* **Security:** JWT Authentication
* **Database:** MySQL / H2
* **Messaging:** Apache Kafka
* **Containerization:** Docker
* **Build Tool:** Maven

---

## 📦 Microservices List

* 🔹 api-gateway
* 🔹 user-service
* 🔹 patient-service
* 🔹 doctor-service
* 🔹 appointment-service
* 🔹 billing-service
* 🔹 notification-service
* 🔹 reporting-service
* 🔹 hospital-service
* 🔹 service-registry

---

## 🔄 System Flow

1. Client request goes to API Gateway
2. Gateway routes request to respective service
3. Services communicate via REST & Kafka
4. Events (like AppointmentBooked) are published
5. Other services consume events and process

---

## ⚙️ Setup & Run

### 🔹 Clone the project

git clone https://github.com/satish21-01/hms-project-satish-.git

### 🔹 Run services using Docker

docker-compose up --build

---

## 🔐 Authentication

* JWT-based authentication implemented
* Secure APIs with Spring Security

---

## 📊 Features

* ✅ Microservices Architecture
* ✅ API Gateway Routing
* ✅ Service Discovery (Eureka)
* ✅ Kafka Event Streaming
* ✅ Dockerized Services
* ✅ Secure APIs (JWT)

---

## 📸 Screenshots (Optional)

*Add screenshots here*

---

## 👨‍💻 Author

Satish Dhankar

---

## ⭐ Give a Star
## 🏗️ Architecture Diagram

```mermaid
flowchart TD

Client[Client / Postman / UI]

Gateway[API Gateway]

Eureka[Service Registry (Eureka)]

Kafka[(Kafka Broker)]

User[User Service]
Patient[Patient Service]
Doctor[Doctor Service]
Appointment[Appointment Service]
Billing[Billing Service]
Notification[Notification Service]
Reporting[Reporting Service]
Hospital[Hospital Service]

DB[(Database)]

Client --> Gateway

Gateway --> User
Gateway --> Patient
Gateway --> Doctor
Gateway --> Appointment
Gateway --> Billing
Gateway --> Hospital

User --> Eureka
Patient --> Eureka
Doctor --> Eureka
Appointment --> Eureka
Billing --> Eureka
Notification --> Eureka
Reporting --> Eureka
Hospital --> Eureka

Appointment --> Kafka
Kafka --> Notification
Kafka --> Reporting
Kafka --> Billing

User --> DB
Patient --> DB
Doctor --> DB
Appointment --> DB
Billing --> DB
Hospital --> DB

If you like this project, please give it a ⭐ on GitHub!
