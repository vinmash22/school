ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (age >= 16);
ALTER TABLE student ADD CONSTRAINT name_unique TEXT UNIQUE NOT NULL (name);
ALTER TABLE faculty ADD CONSTRAINT name_color_unique UNIQUE (name, color);
ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;