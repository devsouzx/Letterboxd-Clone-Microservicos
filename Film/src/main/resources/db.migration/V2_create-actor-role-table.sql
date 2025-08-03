CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE actor_role (
    identifier UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    actor_name TEXT,
    character_name TEXT,
    movie_id UUID,

    CONSTRAINT fk_actor_movie FOREIGN KEY (movie_id) REFERENCES movies(identifier) ON DELETE CASCADE
);
