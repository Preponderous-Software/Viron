# 🛠 Viron MVP

The **Minimum Viable Product** for Viron establishes the foundation of its role as the **spatial simulation core** in a modular service ecosystem.  
This MVP focuses on core 2D grid management, location-based entity tracking, and movement — leaving simulation logic and higher-order systems to external modules.

---

## 🎯 Goals

- Provide a lightweight, modular service for managing 2D spatial environments.
- Keep logic **simulation-agnostic** — no game rules, AI behavior, or time progression.
- Deliver a **stable API** for creating, traversing, and manipulating environments, grids, locations, and entities.

---

## 📌 Scope

The MVP covers:

- Creating named 2D environments of arbitrary size.
- Retrieving grid metadata for an environment.
- Listing and retrieving locations within an environment.
- Placing and removing entities at specific locations.
- Moving entities within and between environments.
- Querying entity positions and location occupants.
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
- [ ] API endpoint to fetch grid metadata for an environment

### Location Management
- [ ] API endpoint to list all locations in an environment with their occupants
- [ ] API endpoint to retrieve a specific location’s occupants

### Entity Management
- [ ] Define `Entity` class with:
  - [ ] Unique identifier
  - [ ] Name
- [ ] Create entity
- [ ] Place entity at specific coordinates
- [ ] Remove entity from environment
- [ ] Query entity’s current location

### Movement
- [ ] Define `Direction` enum (NORTH, SOUTH, EAST, WEST)
- [ ] Move entity in a direction (with bounds checking)
- [ ] Support moving entities between environments
- [ ] Prevent invalid moves (e.g., outside grid)

### Query & Utility Methods
- [ ] Get all entities in a location
- [ ] Get all entities in an environment
- [ ] Check if a location is occupied
- [ ] Validate coordinates before operations

### Testing
- [ ] Unit tests for environment creation
- [ ] Unit tests for grid retrieval
- [ ] Unit tests for location queries
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
- Terminology in code, documentation, and API is consistent (environment, grid, location, entity).
