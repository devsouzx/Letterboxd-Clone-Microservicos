CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE crew_members (
    identifier UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT,
    role TEXT,
    movie_id UUID,

    CONSTRAINT fk_crew_movie FOREIGN KEY (movie_id) REFERENCES movies(identifier) ON DELETE CASCADE
);
