-- Copyright (c) 2024 Preponderous Software
-- MIT License

-- Migration for issue #200: one placement per entity.
--
-- Databases created before this change key viron.entity_location on (entity_id, location_id),
-- which permits the same entity to occupy two locations at once. Two concurrent placements of an
-- unplaced entity at different locations both pass the controller's read-then-write guard and
-- both insert, so the invariant the rest of the service assumes is not actually enforced.
-- Keying on entity_id alone makes the database reject the second insert, which is what lets the
-- losing request be reported as a 409 instead of a 500.
--
-- db-scripts/setup/create_tables.sql already creates new databases this way; this script brings
-- an existing one into line. It is not applied automatically (the setup scripts run only when
-- Postgres initialises an empty volume), so run it once against each existing database:
--
--     psql -U "$DATABASE_DB_USERNAME" -d "$DATABASE_DB_NAME" \
--         -f db-scripts/migrations/2026-08-12_entity_location_one_placement_per_entity.sql
--
-- Nothing is deleted here. If any entity is already placed twice, the migration aborts and
-- reports the count, leaving it to an operator to decide which placement is the real one.

BEGIN;

DO $$
DECLARE
    duplicate_count INT;
BEGIN
    SELECT count(*) INTO duplicate_count
    FROM (
        SELECT entity_id
        FROM viron.entity_location
        GROUP BY entity_id
        HAVING count(*) > 1
    ) duplicates;

    IF duplicate_count > 0 THEN
        RAISE EXCEPTION
            'viron.entity_location places % entity/entities at more than one location; resolve them before applying this migration. To list them: SELECT entity_id, location_id FROM viron.entity_location WHERE entity_id IN (SELECT entity_id FROM viron.entity_location GROUP BY entity_id HAVING count(*) > 1) ORDER BY entity_id, location_id;',
            duplicate_count;
    END IF;
END $$;

ALTER TABLE viron.entity_location DROP CONSTRAINT entity_location_pkey;
ALTER TABLE viron.entity_location ADD PRIMARY KEY (entity_id);

COMMIT;
