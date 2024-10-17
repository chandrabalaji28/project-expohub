//package org.example;
//
//public class Booking {
//    private String eventName;
//    private String venueName;
//    private String customization;
//    private String foodPreference;
//    private String decorationPreference;
//
//
//    // Constructor
//    public Booking(String eventName, String venueName, String customization, String foodPreference, String decorationPreference) {
//        this.eventName = eventName;
//        this.venueName = venueName;
//        this.customization = customization;
//        this.foodPreference = foodPreference;
//        this.decorationPreference = decorationPreference;
//    }
//
//    public String getEventName() {
//        return eventName;
//    }
//
//    public void setEventName(String eventName) {
//        this.eventName = eventName;
//    }
//
//    public String getVenueName() {
//        return venueName;
//    }
//
//    public void setVenueName(String venueName) {
//        this.venueName = venueName;
//    }
//
//    public String getCustomization() {
//        return customization;
//    }
//
//    public void setCustomization(String customization) {
//        this.customization = customization;
//    }
//
//    public String getFoodPreference() {
//        return foodPreference;
//    }
//
//    public void setFoodPreference(String foodPreference) {
//        this.foodPreference = foodPreference;
//    }
//
//    public String getDecorationPreference() {
//        return decorationPreference;
//    }
//
//    public void setDecorationPreference(String decorationPreference) {
//        this.decorationPreference = decorationPreference;
//    }
//
//    // Getters and Setters...
//}
package org.example;

public class Booking {
    private String eventName;
    private String venueName;
    private String customization;
    private String foodPreference;
    private String decorationPreference;
    private String date; // Added date attribute

    // Constructor
    public Booking(String eventName, String venueName, String customization, String foodPreference, String decorationPreference, String date) {
        this.eventName = eventName;
        this.venueName = venueName;
        this.customization = customization;
        this.foodPreference = foodPreference;
        this.decorationPreference = decorationPreference;
        this.date = date; // Initialize date
    }

    // Getters and Setters
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getCustomization() {
        return customization;
    }

    public void setCustomization(String customization) {
        this.customization = customization;
    }

    public String getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(String foodPreference) {
        this.foodPreference = foodPreference;
    }

    public String getDecorationPreference() {
        return decorationPreference;
    }

    public void setDecorationPreference(String decorationPreference) {
        this.decorationPreference = decorationPreference;
    }

    public String getDate() { // Getter for date
        return date;
    }

    public void setDate(String date) { // Setter for date
        this.date = date;
    }
}
