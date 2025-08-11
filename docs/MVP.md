# Viron MVP – Implementation Checklist

The **Minimum Viable Product (MVP)** for Viron establishes the core spatial simulation capabilities necessary to manage **environments**, **grids**, **locations**, and **entities** via a REST API.

This document aligns with the [`../openapi/viron-api.json`](../openapi/viron-api.json) specification and serves as the implementation guide.

---

## 🎯 Purpose

Deliver a working, tested API that supports:
- Creation and retrieval of simulation environments.
- Hierarchical spatial structures (environments → grids → locations).
- Entity management and placement within locations.
- Debug utilities for rapid testing and demonstration.

---

## 📌 Core Features

### 1. **Environment Management**
- [ ] `GET /api/v1/environments` – Retrieve all environments.
- [ ] `GET /api/v1/environments/{id}` – Retrieve a specific environment by ID.
- [ ] `GET /api/v1/environments/name/{name}` – Retrieve a specific environment by name.
- [ ] `GET /api/v1/environments/entity/{entityId}` – Get the environment containing a specific entity.
- [ ] `POST /api/v1/environments/{name}/{numGrids}/{gridSize}` – Create an environment.
- [ ] `DELETE /api/v1/environments/{id}` – Delete an environment and all related entities, locations, and grids.
- [ ] `PATCH /api/v1/environments/{id}/name/{name}` – Update environment name.

---

### 2. **Grid Management**
- [ ] `GET /api/v1/grids` – Retrieve all grids.
- [ ] `GET /api/v1/grids/{id}` – Retrieve a grid by ID.
- [ ] `GET /api/v1/grids/environment/{environmentId}` – Retrieve all grids in an environment.
- [ ] `GET /api/v1/grids/entity/{entityId}` – Retrieve the grid containing a specific entity.

---

### 3. **Location Management**
- [ ] `GET /api/v1/locations` – Retrieve all locations.
- [ ] `GET /api/v1/locations/{id}` – Retrieve a location by ID.
- [ ] `GET /api/v1/locations/environment/{environmentId}` – Retrieve locations in an environment.
- [ ] `GET /api/v1/locations/grid/{gridId}` – Retrieve locations in a grid.
- [ ] `GET /api/v1/locations/entity/{entityId}` – Retrieve the location of a specific entity.
- [ ] `PUT /api/v1/locations/{locationId}/entity/{entityId}` – Add an entity to a location.
- [ ] `DELETE /api/v1/locations/{locationId}/entity/{entityId}` – Remove an entity from a location.
- [ ] `DELETE /api/v1/locations/entity/{entityId}` – Remove an entity from its current location.

---

### 4. **Entity Management**
- [ ] `GET /api/v1/entities` – Retrieve all entities.
- [ ] `GET /api/v1/entities/{id}` – Retrieve a specific entity by ID.
- [ ] `POST /api/v1/entities` – Create a new entity.
- [ ] `DELETE /api/v1/entities/{id}` – Delete an entity.

---

### 5. **Debug Utilities**
- [ ] `POST /api/v1/debug/create-sample-data` – Create an environment with grids, locations, and sample entities for testing.
- [ ] `POST /api/v1/debug/create-world-and-place-entity/{environmentName}` – Create a world and place a random entity.

---

## 🧩 DTO Requirements

- [ ] **EnvironmentDTO** – Public representation of an environment.
- [ ] **CreateEnvironmentRequest** – Request body for creating environments (name, number of grids, grid size).
- [ ] **UpdateEnvironmentNameRequest** – Request body for updating environment names.
- [ ] **GridDTO** – Public representation of a grid.
- [ ] **LocationDTO** – Public representation of a location.
- [ ] **EntityDTO** – Public representation of an entity.

---

## ✅ Completion Criteria

- All endpoints in [`../openapi/viron-api.json`](../openapi/viron-api.json) implemented and tested.
- DTOs returned in responses, avoiding direct exposure of internal entity models.
- Unit and integration tests covering all controllers and repositories.
- Endpoints verified via Postman or Swagger UI.
- API returns proper HTTP status codes and error messages.
- Debug endpoints operational for development testing.

---
