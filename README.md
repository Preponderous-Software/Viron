# Viron

**Viron** is the **spatial simulation core** of a modular service ecosystem — a reusable backend module for building and managing **2D grid-based environments**.  
It provides a structured way to represent space, manage grids, track locations, place entities, and handle movement — while remaining agnostic to the rules, logic, and gameplay systems that run on top.

---

## 🌍 Purpose

Viron serves as the **spatial foundation** in a larger system.  
It answers questions like:
- *What* environments exist?
- *What* is the size and structure of an environment’s grid?
- *Where* is a given entity located?
- *What* is occupying a given location?
- *How* can entities move between locations and environments?

Viron does **not** decide *why* an entity moves, what it does there, or how time progresses — those are handled externally.

---

## 🧠 Design Principles

- **Spatial First** – Optimized for representing and manipulating grid-based space  
- **Decoupled** – Works independently of game rules, AI logic, or rendering systems  
- **Modular** – Easily integrates with other services or applications  
- **Reusable** – Applicable across genres and simulation types  
- **Predictable** – Deterministic spatial modeling for consistent behavior  

---

## ✨ Features

- **Environment Management** – Create and organize named environments  
- **Grid Retrieval** – Query the structure and dimensions of an environment’s grid  
- **Location Queries** – Retrieve occupants of specific coordinates or all locations in an environment  
- **Entity Placement & Movement** – Place, move, and remove entities  
- **Multi-Environment Support** – Entities can move between separate environments  
- **Simulation Agnostic** – Works for games, AI research, visualization tools, and more  

---

## 🎮 Example Use Cases

- Ecosystem simulation  
- Idle creature evolution  
- Board games like Chess, Checkers, and Tic Tac Toe  
- Arcade-style games like Snake, Pong, Breakout  
- Turn-based tactics  
- Conway’s Game of Life  
- Pathfinding visualizations  
- City simulations

---

## 🚀 Installation

Java (Maven):

<dependency>
  <groupId>com.preponderous</groupId>
  <artifactId>viron</artifactId>
  <version>1.0.0</version>
</dependency>

---

## 📖 Getting Started

// Create a 10×10 forest environment  
Environment env = new Environment("Forest", 10, 10);

// Add a fox entity at coordinate (5, 5)  
Entity fox = new Entity("Fox");  
env.placeEntityAt(fox, new Coordinate(5, 5));

// Move the fox north  
env.moveEntity(fox, Direction.NORTH);

---

## 🧪 Testing

mvn test

---

## 📦 Deployment

# Start  
docker compose up --build -d

# Stop  
docker compose down

Accessible at: http://localhost:8080

---

## 📄 License

Viron is licensed under the MIT License.  
See the [LICENSE](LICENSE) file for full terms.

Copyright © 2022–2025 Daniel McCoy Stephenson. All rights reserved.

---

## 🤝 Open Source Commitment

Viron will remain open source under an OSI-approved license.  
Its role as a **core spatial service** makes it a foundation for current and future projects, and broad adoption is encouraged.
