# Viron OpenAPI Specification

This directory contains the **OpenAPI 3.0.3** specification for the Viron spatial simulation API.  
The specification defines endpoints, request/response formats, and DTO schemas for all core domain objects.

---

## 📂 Files

- **viron-api.json** – The complete OpenAPI definition for the Viron API, covering:
  - **Environments** – Create, read, update (including renaming), and delete environments.
  - **Grids** – Retrieve grids and their relationships to environments and entities.
  - **Locations** – Manage locations, including entity placement and removal.
  - **Entities** – Access, create, and delete entities in the simulation.
  - **Debug Utilities** – Endpoints for generating sample data and test scenarios.

---

## 🧩 Structure

The API is organized around **domain-specific controllers**:

- **EnvironmentController** – Handles environment-level operations, including creation, retrieval, renaming, and deletion.
- **GridController** – Handles grid-related retrieval and relationships.
- **LocationController** – Manages spatial locations and entity placement/removal.
- **EntityController** – Manages entity creation, retrieval, and deletion.
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

- [`../docs/MVP.md`](../docs/MVP.md) – Minimum Viable Product checklist, aligned with this specification.
- [`../PLANNING.md`](../PLANNING.md) – Milestone and issue breakdown for implementing the MVP.
- [`../REBUILD_PLAN.md`](../REBUILD_PLAN.md) – Step-by-step plan for rebuilding the Viron codebase in alignment with this spec.

---

## ℹ️ Notes

- Pagination & sorting are **optional for MVP** and may not be present in the current `viron-api.json`.
- This specification is the **source of truth** for all endpoints and data contracts. All other documentation is derived from it.
