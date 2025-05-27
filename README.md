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

## Project Structure

- `environments/` – Defines environments and grids  
- `entities/` – Defines basic entity behavior  
- `coordinates/` – Spatial utilities  
- `utils/` – Support classes  

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

## License

MIT License  
© 2022–2025 Daniel McCoy Stephenson  
See the [LICENSE](LICENSE) file for details.

---

## Contact & Attribution

This project is developed and maintained by **Preponderous**.  
For questions or contributions, visit:  
[https://github.com/Preponderous-Software/Viron](https://github.com/Preponderous-Software/Viron)