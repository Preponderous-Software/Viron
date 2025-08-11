# Viron

Viron is a **2D spatial simulation management service** that provides APIs for creating and managing **environments**, **grids**, **locations**, and **entities**.  
It serves as a reusable backend component for simulation-based games, AI experiments, and virtual world applications.

---

## 🎯 Purpose

Viron abstracts away low-level spatial data management so client applications can focus on simulation logic, rendering, and game mechanics.

Core responsibilities:
- Managing hierarchical spatial structures (environments → grids → locations).
- Tracking entity placement and movement.
- Providing clean, testable REST APIs.
- Offering debug tools for rapid development and testing.

---

## 📦 Features (MVP Scope)

The MVP implements the endpoints defined in `openapi/viron-api.json` and documented in `docs/MVP.md`.

**Environment Management**
- Create, retrieve, update, and delete environments.
- Query environments by ID, name, or contained entity.

**Grid Management**
- Retrieve grids by ID or environment.
- Find the grid containing a specific entity.

**Location Management**
- Retrieve locations by ID, grid, or environment.
- Manage entity placement in locations.

**Entity Management**
- Create, retrieve, and delete entities.

**Debug Utilities**
- Generate sample environments, grids, locations, and entities.
- Quickly create a world and place an entity for testing.

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 3
- Lombok
- PostgreSQL (persistence layer)
- Maven (build tool)
- Docker + Docker Compose (deployment)
- Swagger/OpenAPI (API documentation)

---

## 📂 Project Structure

viron/  
 ├── src/main/java/preponderous/viron/  
 │    ├── controllers/       # REST controllers (Environment, Grid, Location, Entity, Debug)  
 │    ├── dto/               # Data Transfer Objects (DTOs) for API input/output  
 │    ├── models/            # Internal domain models  
 │    ├── repositories/      # Data access layer  
 │    ├── services/          # Business logic  
 │    └── factories/         # Creation logic for environments and entities  
 ├── src/test/java/...       # Unit and integration tests  
 ├── docs/  
 │    └── MVP.md             # Implementation checklist for MVP  
 ├── openapi/  
 │    └── viron-api.json     # API specification  
 ├── pom.xml                 # Maven configuration  
 └── README.md               # This file  

---

## 🚀 Getting Started

### Prerequisites
- Java 21
- Maven 3.9+
- Docker & Docker Compose

### Installation
mvn clean install

### Running Locally
docker-compose up --build  
API will be available at: http://localhost:8080

---

## 📜 API Documentation

Once running, you can view the interactive API docs:  
http://localhost:8080/swagger-ui.html  
or refer to the `openapi/viron-api.json` file.

---

## 🧪 Testing

Run all unit and integration tests:  
mvn test

---

## 📄 License

This project is licensed under the MIT License.  

Copyright © 2022-2025 Daniel McCoy Stephenson. All rights reserved.

---

## 📬 Contact

For inquiries, feature requests, or contributions, please open an issue or reach out via the official GitHub repository:  
https://github.com/Preponderous-Software/Viron
