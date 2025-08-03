CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE movies (
    identifier UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title TEXT,
    release_year TEXT,
    synopsis TEXT,
    tagline TEXT,
    runtime INTEGER,
    country TEXT,
    primary_language TEXT,
    trailer_url TEXT,
    poster_url TEXT,
    backdrop_url TEXT,
    tmdbid INTEGER UNIQUE NOT NULL,

    spoken_languages TEXT[],
    alternative_titles TEXT[],
    studios TEXT[],
    genres TEXT[],
    themes TEXT[],

    last_updated TIMESTAMP,
    created TIMESTAMP
);
