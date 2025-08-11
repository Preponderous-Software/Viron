# 🛠 Viron MVP

The **Minimum Viable Product** for Viron establishes the foundation of its role as the **spatial simulation core** in a modular service ecosystem.  
This MVP focuses on core 2D grid management, entity tracking, and movement — leaving simulation logic and higher-order systems to external modules.

---

## 🎯 Goals

- Provide a lightweight, modular service for managing 2D spatial environments.
- Keep logic **simulation-agnostic** — no game rules, AI behavior, or time progression.
- Deliver a **stable API** for creating, traversing, and manipulating environments.

---

## 📌 Scope

The MVP covers:

- Creating named 2D environments of arbitrary size.
- Placing and removing entities at specific coordinates.
- Moving entities within and between environments.
- Querying entity positions and cell contents.
- Basic validation (bounds checking, occupancy checks).

---

## ✅ MVP Checklist

### Environment Management
- [ ] Define `Environment` class with:
  - [ ] Name
  - [ ] Width
  - [ ] Height
  - [ ] Grid data structure
- [ ] Method to create an environment
- [ ] Method to retrieve environment dimensions and name

### Entity Management
- [ ] Define `Entity` class with:
  - [ ] Unique identifier
  - [ ] Name
- [ ] Place entity at specific coordinates
- [ ] Remove entity from environment
- [ ] Query entity’s current location

### Movement
- [ ] Define `Direction` enum (NORTH, SOUTH, EAST, WEST)
- [ ] Move entity in a direction (with bounds checking)
- [ ] Support moving entities between environments
- [ ] Prevent invalid moves (e.g., outside grid)

### Query & Utility Methods
- [ ] Get all entities in a cell
- [ ] Get all entities in an environment
- [ ] Check if a cell is occupied
- [ ] Validate coordinates before operations

### Testing
- [ ] Unit tests for environment creation
- [ ] Unit tests for entity placement
- [ ] Unit tests for entity movement
- [ ] Unit tests for query methods

### Deployment
- [ ] Dockerfile for running the service
- [ ] Docker Compose support for local deployment
- [ ] Accessible at `http://localhost:8080`

---

## 🚫 Out of Scope for MVP

- Pathfinding algorithms
- AI/agent behavior
- Time progression
- Rendering or UI
- Terrain types or biome logic

---

## 📍 Definition of Done

- All checklist items implemented and tested.
- Code compiles and runs without errors.
- Service can be started locally via Docker.
- README is updated with usage instructions.
- API is stable enough for integration with other services.