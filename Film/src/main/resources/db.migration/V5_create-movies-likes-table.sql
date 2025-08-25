CREATE TABLE movie_likes (
    identifier UUID DEFAULT gen_random_uuid() NOT NULL,
    movie_identifier UUID DEFAULT gen_random_uuid() NOT NULL,
    user_identifier UUID DEFAULT gen_random_uuid() NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (identifier, movie_identifier, user_identifier),
    FOREIGN KEY (movie_identifier) REFERENCES movies (identifier) ON DELETE CASCADE,
    FOREIGN KEY (user_identifier) REFERENCES users (identifier) ON DELETE CASCADE
)