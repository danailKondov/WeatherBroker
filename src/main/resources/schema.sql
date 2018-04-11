USE weather_broker;

CREATE TABLE IF NOT EXISTS weather_condition (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	version INTEGER ,
  city VARCHAR(255),
  date_condition DATETIME,
  temp INTEGER,
  description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS weather_forecast (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  version INTEGER,
  date_forecast DATETIME,
  city VARCHAR(255),
  temp_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS forecast_for_day (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  version INTEGER,
  date_for_forecast DATE,
  day_of_week VARCHAR(255),
  high_temp INTEGER,
  low_temp INTEGER,
  description VARCHAR(255),
  forecast_id INTEGER
);

ALTER TABLE forecast_for_day ADD FOREIGN KEY (forecast_id) REFERENCES weather_forecast(id);