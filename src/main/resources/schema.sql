USE weather_broker;

CREATE TABLE IF NOT EXISTS weather_condition (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	version INTEGER ,
  city VARCHAR(255),
  date_condition DATETIME,
  temp INTEGER,
  description VARCHAR(255)
);