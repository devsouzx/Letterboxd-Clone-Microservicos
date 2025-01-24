CREATE TABLE users_follows (
  follower_identifier UUID NOT NULL,
  followed_identifier UUID NOT NULL,
  PRIMARY KEY (follower_identifier, followed_identifier),
  FOREIGN KEY (follower_identifier) REFERENCES users (identifier) ON DELETE CASCADE,
  FOREIGN KEY (followed_identifier) REFERENCES users (identifier) ON DELETE CASCADE
);