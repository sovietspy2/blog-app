--liquibase formatted sql
--changeset sovietspy2:create-blog-records
INSERT INTO blog (name) VALUES ('Tech Blog');
--rollback DELETE FROM blog WHERE NAME = 'Tech Blog';
INSERT INTO blog (name) VALUES ('Food Blog');
--rollback DELETE FROM blog WHERE NAME = 'Food Blog';
INSERT INTO blog (name) VALUES ('Travel Blog');
--rollback DELETE FROM blog WHERE NAME = 'Travel Blog';
