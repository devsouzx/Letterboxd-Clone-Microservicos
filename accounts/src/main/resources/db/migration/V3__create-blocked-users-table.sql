CREATE TABLE blocked_users (
  blocker_identifier UUID NOT NULL,
  blocked_identifier UUID NOT NULL,
  PRIMARY KEY (blocker_identifier, blocked_identifier),
  FOREIGN KEY (blocker_identifier) REFERENCES users (identifier) ON DELETE CASCADE,
  FOREIGN KEY (blocked_identifier) REFERENCES users (identifier) ON DELETE CASCADE
);