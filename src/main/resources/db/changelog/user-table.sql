--liquibase formatted sql
--changeset sovietspy2:create-user-table
CREATE TABLE bloguser
(
    id serial,
    name VARCHAR (255),
    email VARCHAR(255),
    CONSTRAINT bloguser_pk PRIMARY KEY (id)
);
--rollback DROP TABLE
--rollback "user"
