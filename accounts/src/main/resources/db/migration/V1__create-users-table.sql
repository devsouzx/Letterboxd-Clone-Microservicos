CREATE TABLE users (
  identifier UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  first_name VARCHAR(255) NULL,
  last_name VARCHAR(255) NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(30) NOT NULL UNIQUE,
  biography TEXT NULL,
  location VARCHAR(100) NULL,
  site VARCHAR(255) NULL,
  avatar TEXT NULL,
  pronoun VARCHAR(50) NULL,
  custom_posters TINYINT(1) NOT NULL DEFAULT 0,
  adult_content TINYINT(1) NOT NULL DEFAULT 0,
  include_profile_in_members TINYINT(1) NOT NULL DEFAULT 1,
  registration_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  confirmed_email TINYINT(1) NOT NULL DEFAULT 0,
  confirmation_code VARCHAR(100),
  first_access TINYINT(1) NOT NULL DEFAULT 1,
  verified TINYINT(1) NOT NULL DEFAULT 0,
);

ALTER TABLE users ADD COLUMN role VARCHAR(15) NOT NULL;