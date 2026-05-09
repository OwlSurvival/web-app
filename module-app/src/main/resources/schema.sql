
CREATE TABLE IF NOT EXISTS cats_data (
      id UUID PRIMARY KEY,
      name VARCHAR(30),
      date_time TIMESTAMP,
      type_eat VARCHAR(30),
      weight_cat DOUBLE,
      weight_eat INT,
      mood_cat INT
);