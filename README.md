# Viron

**Viron** is a flexible simulation framework for building and managing 2D virtual environments. It provides tools to create, traverse, and manipulate grid-based spaces where entities can exist, move, and interact. Viron is designed to be used as a **core simulation engine** for games, AI training environments, educational apps, and more.

---

## Design Philosophy

Viron is built to be a lightweight, adaptable core for managing 2D environments. Its focus is on:

- **Simplicity** – Easy to understand and integrate  
- **Modularity** – Keeps simulation logic separate from game or application logic  
- **Flexibility** – Supports many types of simulations, from games to research  
- **Structure** – Uses grid-based environments for predictable spatial modeling  
- **Reusability** – Designed to be a foundation for many different projects  

---

## Features

- **Modular Architecture** – Easy to plug into Java and Python projects  
- **2D Spatial Modeling** – Manage environments, grid layouts, and entity positioning  
- **Entity Movement** – Track movement across grid cells and between environments  
- **Simulation Agnostic** – Suitable for ecological sims, city builders, idle games, and more  
- **Multi-language Support** – Java and Python implementations with a shared design  

---

## Example Use Cases

### 1. Ecosystem Simulator
Simulates food chain dynamics in a virtual world. Entities such as foxes, rabbits, and grass interact in Viron-managed environments.

### 2. Idle Evolution Game
Evolves creatures over time in changing biomes, using Viron to track grid-based locations and terrain types.

### 3. Educational Tools
Interactive classroom tools where students can simulate ecological systems or environmental changes.

### 4. AI Training Environments
Use Viron to define spatial challenges or resource collection problems in reinforcement learning projects.

### 5. Tic Tac Toe
Use Viron to model a 3×3 environment where each player places an "X" or "O" on the grid. Track turns, detect win conditions, and enforce valid moves.

### 6. Chess
Represent the 8×8 board as a Viron environment. Each piece is an entity with movement logic handled externally. Viron tracks positions, captures, and board state.

### 7. Checkers
Use a grid-based environment to model diagonal movement and jumping. Viron can track entity state changes (like promotion) and valid move paths.

### 8. Block Breaker (Breakout)
Model the paddle, ball, and blocks within a 2D grid. Viron tracks positions, collisions, and block removal as entities are hit.

### 9. Pong
Represent the playing field, paddles, and ball in a 2D space. Viron manages movement updates, collisions, and scorekeeping.

### 10. Snake
Use Viron to track the snake's body segments and apple placement. Manage movement direction, growth, and self-collision logic.

### 11. Turn-Based Tactics Game
Simulate a battlefield grid with units as entities. Viron manages movement, attack range visibility, and terrain occupancy.

### 12. Conway’s Game of Life
Each cell is alive or dead and changes state based on neighbors. Viron can model the entire board and apply the update rules in steps.

### 13. Pathfinding Visualizations
Use Viron to define a navigable grid with start and goal positions. Track algorithm progress (A*, Dijkstra, etc.) in real time through environment updates.

### 14. City Simulation
Simulate a city with roads, zones, and agents (e.g., people, vehicles). Viron provides structure for tracking locations and interactions between entities.

---

## Installation

### Java (Maven)

```xml
<dependency>
  <groupId>com.preponderous</groupId>
  <artifactId>viron</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Python

Coming soon as a PyPI package.

---

## Getting Started

Initialize a 10×10 environment and add an entity:

```java
Environment env = new Environment("Forest", 10, 10);
Entity fox = new Entity("Fox");
env.placeEntityAt(fox, new Coordinate(5, 5));
```

Move the entity:

```java
env.moveEntity(fox, Direction.NORTH);
```

---

## Testing

**Java:**

```bash
mvn test
```

**Python:**

```bash
pytest
```

---

## Deployment
### Starting the application
To start the application, run the following command:

```bash
docker compose up --build -d
```

The application will be available at `http://localhost:8080`.

### Stopping the application
To stop the application, run the following command:

```bash
docker compose down
```

## 📄 License

Viron is licensed under the [MIT License](LICENSE).

Copyright © 2022–2025 Daniel McCoy Stephenson. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
and associated documentation files (the “Software”), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies 
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

---

### Why MIT?
Viron is a foundational library intended for broad adoption across the simulation and game development community. The **MIT License** was chosen for its simplicity and permissiveness, making it easy for individuals, research institutions, and commercial entities alike to integrate Viron without licensing friction. This aligns with the goal of establishing Viron as a widely used standard in 2D environment management.

### Open Source Commitment
There are **no plans to move away from open source** for Viron. The project will remain freely available under an OSI-approved license, with community contributions encouraged and welcomed.

---

## Contact & Attribution

This project is developed and maintained by **Preponderous**.  
For questions or contributions, visit:  
[https://github.com/Preponderous-Software/Viron](https://github.com/Preponderous-Software/Viron)
