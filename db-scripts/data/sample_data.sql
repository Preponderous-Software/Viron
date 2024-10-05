-- Copyright (c) 2024 Preponderous Software
-- MIT License

-- create environment, grid and location data
INSERT INTO viron.environment (environment_id, name, creation_date) VALUES (1, 'Test Environment', '2024-01-01 00:00:00');
INSERT INTO viron.grid (grid_id, columns, rows) VALUES (1, 4, 4);
INSERT INTO viron.location (location_id, x, y) VALUES (1, 0, 0);
INSERT INTO viron.location (location_id, x, y) VALUES (2, 1, 0);
INSERT INTO viron.location (location_id, x, y) VALUES (3, 2, 0);
INSERT INTO viron.location (location_id, x, y) VALUES (4, 3, 0);
INSERT INTO viron.location (location_id, x, y) VALUES (5, 0, 1);
INSERT INTO viron.location (location_id, x, y) VALUES (6, 1, 1);
INSERT INTO viron.location (location_id, x, y) VALUES (7, 2, 1);
INSERT INTO viron.location (location_id, x, y) VALUES (8, 3, 1);
INSERT INTO viron.location (location_id, x, y) VALUES (9, 0, 2);
INSERT INTO viron.location (location_id, x, y) VALUES (10, 1, 2);
INSERT INTO viron.location (location_id, x, y) VALUES (11, 2, 2);
INSERT INTO viron.location (location_id, x, y) VALUES (12, 3, 2);
INSERT INTO viron.location (location_id, x, y) VALUES (13, 0, 3);
INSERT INTO viron.location (location_id, x, y) VALUES (14, 1, 3);
INSERT INTO viron.location (location_id, x, y) VALUES (15, 2, 3);
INSERT INTO viron.location (location_id, x, y) VALUES (16, 3, 3);

-- create entities
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (1, 'Fred the Cat', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (2, 'Bob the Dog', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (3, 'Alice the Cat', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (4, 'Eve the Dog', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (5, 'Mallory the Cat', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (6, 'Oscar the Dog', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (7, 'Charlie the Cat', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (8, 'Toby the Dog', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (9, 'Luna the Cat', '2024-01-01 00:00:00');
INSERT INTO viron.entity (entity_id, name, creation_date) VALUES (10, 'Max the Dog', '2024-01-01 00:00:00');

-- associate locations with grid
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 1);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 2);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 3);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 4);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 5);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 6);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 7);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 8);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 9);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 10);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 11);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 12);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 13);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 14);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 15);
INSERT INTO viron.location_grid (grid_id, location_id) VALUES (1, 16);

-- associate grid with environment
INSERT INTO viron.grid_environment (grid_id, environment_id) VALUES (1, 1);

-- associate entities with locations
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (1, 1);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (2, 2);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (3, 3);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (4, 4);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (5, 5);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (6, 6);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (7, 7);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (8, 8);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (9, 9);
INSERT INTO viron.entity_location (entity_id, location_id) VALUES (10, 10);