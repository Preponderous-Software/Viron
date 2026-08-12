-- Copyright (c) 2024 Preponderous Software
-- MIT License

-- create viron schema if it does not exist
CREATE SCHEMA IF NOT EXISTS viron;

-- entity table (entity_id, name, creation_date)
CREATE TABLE IF NOT EXISTS viron.entity (
    entity_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

-- location table (location_id, x, y)
CREATE TABLE IF NOT EXISTS viron.location (
    location_id INT PRIMARY KEY,
    x INT NOT NULL,
    y INT NOT NULL
);

-- grid table (grid_id, columns, rows, name)
CREATE TABLE IF NOT EXISTS viron.grid (
    grid_id INT PRIMARY KEY,
    rows INT NOT NULL,
    columns INT NOT NULL,
    name VARCHAR(255)
);

-- environment table (environment_id, name, creation_date)
CREATE TABLE IF NOT EXISTS viron.environment (
    environment_id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

-- entity_location table (entity_id, location_id)
-- An entity occupies at most one location, so entity_id alone is the key: a location may hold
-- many entities, but a second placement of the same entity is rejected by the database rather
-- than only by the read-then-write guard in LocationController.addEntityToLocation, which two
-- concurrent requests can both pass.
CREATE TABLE IF NOT EXISTS viron.entity_location (
    entity_id INT NOT NULL,
    location_id INT NOT NULL,
    PRIMARY KEY (entity_id),
    FOREIGN KEY (entity_id) REFERENCES viron.entity(entity_id),
    FOREIGN KEY (location_id) REFERENCES viron.location(location_id)
);

-- location_grid table (grid_id, location_id)
CREATE TABLE IF NOT EXISTS viron.location_grid (
    grid_id INT NOT NULL,
    location_id INT NOT NULL,
    PRIMARY KEY (grid_id, location_id),
    FOREIGN KEY (grid_id) REFERENCES viron.grid(grid_id),
    FOREIGN KEY (location_id) REFERENCES viron.location(location_id)
);

-- grid_environment table (environment_id, grid_id)
CREATE TABLE IF NOT EXISTS viron.grid_environment (
    environment_id INT NOT NULL,
    grid_id INT NOT NULL,
    PRIMARY KEY (environment_id, grid_id),
    FOREIGN KEY (environment_id) REFERENCES viron.environment(environment_id),
    FOREIGN KEY (grid_id) REFERENCES viron.grid(grid_id)
);