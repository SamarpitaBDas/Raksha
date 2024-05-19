package com.example.raksha;

public class HelperClass {

    String name, email, username, password,role,phone_number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void role(String role) {this.role = role;}

    public String getphone_number() {
        return phone_number;
    }

    public void setphone_number(String password) {
        this.phone_number = phone_number;
    }


    public HelperClass(String name, String email, String username, String password, String role, String phone_number) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone_number = phone_number;
    }

    public HelperClass() {
    }
}
