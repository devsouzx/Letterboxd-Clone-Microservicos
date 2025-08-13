CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS movies (
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
    slug TEXT UNIQUE NOT NULL,

    spoken_languages TEXT[],
    alternative_titles TEXT[],
    studios TEXT[],
    genres TEXT[],
    themes TEXT[],

    updated_at TIMESTAMP,
    created_at TIMESTAMP
);
