CREATE TABLE cars
(
    id    SERIAL PRIMARY KEY,
    brand TEXT,
    model TEXT,
    price NUMERICN
);

CREATE TABLE persons
(
    id             SERIAL PRIMARY KEY,
    name           TEXT,
    age            SMALLINT,
    driver_license BOOLEAN,
    car_id         INTEGER REFERENCES cars (id)
);



