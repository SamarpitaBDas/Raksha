package com.example.raksha;

public class Report {
    private String accused;
    private String description;
    private String location;
    private String timestamp;
    private String username;

    public Report() {
        // Default constructor required for Firebase
    }

    public Report(String accused, String description, String location, String timestamp, String username) {
        this.accused = accused;
        this.description = description;
        this.location = location;
        this.timestamp = timestamp;
        this.username = username;
    }

    public String getAccused() {
        return accused;
    }

    public void setAccused(String accused) {
        this.accused = accused;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
