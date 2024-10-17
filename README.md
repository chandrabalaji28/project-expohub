# project-expohub

Sql query 

1.venues
CREATE TABLE venues (
    venue_id INT PRIMARY KEY AUTO_INCREMENT,
    venue_name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL
);

INSERT INTO venues (venue_name, location, capacity) VALUES
('Fun Zone', 'City Center', 100),
('Garden Hall', 'Downtown', 150),
('Royal Banquet', 'Uptown', 200),
('Ocean View Hall', 'Seaside', 250),
('Kids Paradise', 'North Park', 80),
('Community Hall', 'Central Park', 120),
('Conference Room', 'Business District', 50),
('Corporate Suite', 'City Center', 75);

2.events

CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL
    -- other columns if needed
);

INSERT INTO events (event_name, event_id) VALUES
('Birthday', 1),
('Wedding', 2),
('Family Party', 3),
('Meeting', 4);


3.booking

CREATE TABLE booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    user_id INT,
    venue_id INT,
    customization VARCHAR(255),
    food_preference VARCHAR(255),
    decoration_preference VARCHAR(255),
    username VARCHAR(100),  -- Add this column for username
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (venue_id) REFERENCES venues(venue_id)
);
ALTER TABLE booking ADD COLUMN booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
