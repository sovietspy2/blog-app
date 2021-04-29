--liquibase formatted sql
--changeset sovietspy2:fileupload-alter-table
ALTER TABLE fileupload ALTER COLUMN content TYPE text;
-- rollback ALTER TABLE fileupload ALTER COLUMN content TYPE bytea;
