CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE release_info (
    identifier UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    date DATE,
    country TEXT,
    type TEXT,
    location TEXT,
    classification TEXT,
    movie_id UUID,

    CONSTRAINT fk_release_movie FOREIGN KEY (movie_id) REFERENCES movies(identifier) ON DELETE CASCADE
);
