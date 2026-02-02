CREATE TABLE projects
(
    id          VARCHAR(26)  PRIMARY KEY,
    name        VARCHAR(25)  NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP
);

ALTER TABLE projects ADD CONSTRAINT projects_name_unique UNIQUE (name);