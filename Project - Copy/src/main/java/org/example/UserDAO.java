package org.example;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    // Constructor accepting Connection
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // Create User
    public boolean createUser(String username, String email, String password, String phone) {
        String sql = "INSERT INTO Users (username, email, password, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); // Use hashed passwords in a real app
            stmt.setString(4, phone);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read User
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        User user = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Update User
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET username = ?, email = ?, phone = ? WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setInt(4, user.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete User
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Validate User Login (Simple Example)
    public User validateUser(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        User user = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password); // Use hashed passwords in real app
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("phone"), rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    //for events
    public List<String> getEventTypes() {
        List<String> eventTypes = new ArrayList<>();
        eventTypes.add("Birthday");
        eventTypes.add("Wedding");
        eventTypes.add("Family Party");
        eventTypes.add("Meeting");
        return eventTypes;
    }

    public List<Venue> getVenuesByEvent(int eventChoice) {
        List<Venue> venues = new ArrayList<>();
        String query = "SELECT * FROM venues";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Venue venue = new Venue(rs.getInt("venue_id"), rs.getString("venue_name"), rs.getString("location"), rs.getInt("capacity"));
                venues.add(venue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venues;
    }

    // Method to store event name and event id in 'events' table
    // Method to store event name in the 'events' table and return event ID
    public int createEvent(String eventType) {
        String query = "INSERT INTO events (event_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, eventType);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                // Retrieve generated event_id
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated event ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicate failure
    }


    // Method to store booking details in the 'booking' table
    public boolean createBooking(int eventId, int userId, int venueId, String customization, String foodPreference, String decorationPreference, String username) {
        // Updated method to include username as a parameter
        String query = "INSERT INTO booking (event_id, user_id, venue_id, customization, food_preference, decoration_preference, username) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            stmt.setInt(2, userId);
            stmt.setInt(3, venueId);
            stmt.setString(4, customization);
            stmt.setString(5, foodPreference);
            stmt.setString(6, decorationPreference);
            stmt.setString(7, username);  // Now username is passed as a parameter
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false on error
        }
    }

    public List<Booking> getBookedEvents(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT e.event_name, v.venue_name, b.customization, b.food_preference, b.decoration_preference, b.booking_date " +
                "FROM booking b " +
                "JOIN events e ON b.event_id = e.event_id " + // Assuming there's a join with events to get event_name
                "JOIN venues v ON b.venue_id = v.venue_id " +
                "WHERE b.user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String eventName = rs.getString("event_name");
                String venueName = rs.getString("venue_name");
                String customization = rs.getString("customization");
                String foodPreference = rs.getString("food_preference");
                String decorationPreference = rs.getString("decoration_preference");
                String date = rs.getString("booking_date"); // Assuming the date column is named booking_date
                bookings.add(new Booking(eventName, venueName, customization, foodPreference, decorationPreference, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }





}
