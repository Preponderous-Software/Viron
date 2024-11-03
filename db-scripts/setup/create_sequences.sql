-- Copyright (c) 2024 Preponderous Software
-- MIT License

-- create viron schema if it does not exist
CREATE SCHEMA IF NOT EXISTS viron;

-- create sequences for entity ids, environment ids, grid ids and location ids
CREATE SEQUENCE IF NOT EXISTS viron.entity_id_seq;
CREATE SEQUENCE IF NOT EXISTS viron.environment_id_seq;
CREATE SEQUENCE IF NOT EXISTS viron.grid_id_seq;
CREATE SEQUENCE IF NOT EXISTS viron.location_id_seq;