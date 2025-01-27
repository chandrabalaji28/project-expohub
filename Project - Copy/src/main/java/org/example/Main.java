package org.example;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = Connections.getConnection();
        if (conn != null) {
            UserDAO userDAO = new UserDAO(conn);
            Scanner scanner = new Scanner(System.in);
            int choice;

            // Main loop to keep showing the menu after registration or login
            while (true) {
                System.out.println("Welcome! Choose an option:");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        // Registration process
                        registerUser(userDAO, scanner);
                        break;

                    case 2:
                        // Login process
                        loginUser(userDAO, scanner);
                        break;

                    case 3:
                        // Exit the application
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please choose again.");
                        break;
                }
            }
        } else {
            System.out.println("Connection failed!");
        }
    }

    private static void registerUser(UserDAO userDAO, Scanner scanner) {
        System.out.println("Registration:");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        boolean created = userDAO.createUser(username, email, password, phone);
        if (created) {
            System.out.println("User created successfully.");
        } else {
            System.out.println("Failed to create user.");
        }
    }

    private static void loginUser(UserDAO userDAO, Scanner scanner) {
        System.out.println("Login:");
        System.out.print("Enter email: ");
        String loginEmail = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();
        User user = userDAO.validateUser(loginEmail, loginPassword);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getUsername());
            handleUserMenu(userDAO, user, scanner);
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    private static void handleUserMenu(UserDAO userDAO, User user, Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Book an event");
            System.out.println("2. View booked events");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    handleEventCreation(userDAO, user, scanner);
                    break;
                case 2:
                    viewBookedEvents(userDAO, user);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return; // Return to the main menu
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
    }

    private static void handleEventCreation(UserDAO userDAO, User user, Scanner scanner) {
        // Fetch and display event types from the database
        System.out.println("Select an event type:");
        List<String> eventTypes = userDAO.getEventTypes();
        for (int i = 0; i < eventTypes.size(); i++) {
            System.out.println((i + 1) + ". " + eventTypes.get(i));
        }
        int eventChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String selectedEventType = eventTypes.get(eventChoice - 1);
        System.out.println("You selected: " + selectedEventType);

        // Store the event in 'events' table and get event_id
        int eventId = userDAO.createEvent(selectedEventType);
        if (eventId > 0) {
            // Fetch and display venues for the selected event
            System.out.println("Available venues:");
            List<Venue> venues = userDAO.getVenuesByEvent(eventChoice);
            for (int i = 0; i < venues.size(); i++) {
                System.out.println((i + 1) + ". " + venues.get(i).getVenueName() + " at " + venues.get(i).getLocation() + " (Capacity: " + venues.get(i).getCapacity() + ")");
            }
            int venueChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Venue selectedVenue = venues.get(venueChoice - 1);
            System.out.println("You selected venue: " + selectedVenue.getVenueName());

            // Customization options
            System.out.println("Enter customization details for the event:");
            String customization = scanner.nextLine();

            // Food preference
            System.out.println("Enter food preference (e.g., Vegetarian, Non-Vegetarian, Vegan):");
            String foodPreference = scanner.nextLine();

            // Decoration preference
            System.out.println("Enter decoration preference (e.g., Floral, Modern, Classic):");
            String decorationPreference = scanner.nextLine();

            // Assume 'user' is the currently logged-in user with a getUsername() method
            boolean bookingCreated = userDAO.createBooking(eventId, user.getUserId(), selectedVenue.getVenueId(), customization, foodPreference, decorationPreference, user.getUsername());
            if (bookingCreated) {
                System.out.println("Booking successfully!");
            } else {
                System.out.println("Failed to create booking.");
            }

        } else {
            System.out.println("Failed to create event.");
        }
    }

    private static void viewBookedEvents(UserDAO userDAO, User user) {
        List<Booking> bookings = userDAO.getBookedEvents(user.getUserId());
        System.out.println("Your booked events:");
        for (Booking booking : bookings) {
            System.out.println("Event: " + booking.getEventName() +
                    ", Venue: " + booking.getVenueName() +
                    ", Customization: " + booking.getCustomization() +
                    ", Food Preference: " + booking.getFoodPreference() +
                    ", Decoration Preference: " + booking.getDecorationPreference() +
                    ", Date: " + booking.getDate()); // Include date here
        }
    }


}
