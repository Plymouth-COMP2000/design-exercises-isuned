
package com.example.hqrestaurant;

public class User {
    public String username;
    public String email;
    public String password;

    // optional extras
    public String role; // "guest" or "staff"

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
