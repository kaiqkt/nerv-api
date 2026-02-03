CREATE TABLE projects
(
    id          VARCHAR(26)  PRIMARY KEY,
    name        VARCHAR(25)  NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP,
    CONSTRAINT projects_name_unique UNIQUE (name)
);

CREATE TABLE servers
(
    id          VARCHAR(26)  PRIMARY KEY,
    project_id  VARCHAR(26)  NOT NULL,
    name        VARCHAR(25)  NOT NULL,
    url         VARCHAR(255),
    created_at  TIMESTAMP    NOT NULL,
    CONSTRAINT fk_servers_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);
