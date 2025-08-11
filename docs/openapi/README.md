# Viron OpenAPI Specification

This directory contains the **OpenAPI 3.0.3** specification for the Viron spatial simulation API.  
The specification defines endpoints, request/response formats, and DTO schemas for all core domain objects.

---

## 📂 Files

- **viron-api.json** – The complete OpenAPI definition for the Viron API, covering:
  - **Environments** – Create, read, update, and delete environments.
  - **Grids** – Retrieve grids and their relationships to environments and entities.
  - **Locations** – Manage locations, including entity placement and removal.
  - **Entities** – Access and manage entities in the simulation.
  - **Debug Utilities** – Endpoints for generating sample data and test scenarios.

---

## 🧩 Structure

The API is organized around **domain-specific controllers**:

- **EnvironmentController** – Handles environment-level operations.
- **GridController** – Handles grid-related retrieval and relationships.
- **LocationController** – Manages spatial locations and entity placement.
- **DebugController** – Provides testing and demonstration endpoints.

Each path in the spec reflects a **clear mapping to a domain object**, ensuring maintainability and discoverability.

---

## 🗂 DTOs (Data Transfer Objects)

The specification uses DTOs for all request and response bodies.  
This ensures:
- **Consistency** in API contracts.
- **Clarity** for consumers of the API.
- **Separation** of internal models from public API.

Key DTOs include:
- `EnvironmentDTO`
- `CreateEnvironmentRequest`
- `UpdateEnvironmentNameRequest`
- `GridDTO`
- `LocationDTO`
- `EntityDTO`

---

## 🚀 Usage

1. **View the spec**
   - Use any OpenAPI-compatible viewer such as [Swagger Editor](https://editor.swagger.io/) or [Insomnia](https://insomnia.rest/).

2. **Test the API**
   - Start the Viron application locally.
   - Use the endpoints defined in `viron-api.json` to interact with the service.

3. **Generate clients or servers**
   - Use OpenAPI code generation tools (e.g., `openapi-generator-cli`) to scaffold API clients or server stubs.

---

## 📄 Related Documentation

Refer to [`../docs/MVP.md`](../docs/MVP.md) for the **minimum viable product (MVP)** checklist, which aligns with this specification and guides the initial implementation.

---
