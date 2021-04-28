--liquibase formatted sql
--changeset stevedonie:create-test-table
CREATE TABLE testTable(
    columnName1 VARCHAR (355)
);
--rollback DROP TABLE
--rollback testTable
