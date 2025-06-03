package model;

import java.sql.Date;

public class User {
    public String id;
    public String username;
    public String password;
    public String role;
    public String name;
    public String lastName;
    public String country;
    public String email;
    public Date timeZone; 
    
    public User(String id, String username, String password, String role, String name, String lastName, String country, String email, Date timeZone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.lastName = lastName;
        this.country = country;
        this.email = email;
        this.timeZone = timeZone;
    }

    public User(String id, String username, String password, String role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.role = role;
   }

    public String getId() {
        return id;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getNombre() { return name; }
    public String getEmail() { return email; }
    
    public void setNombre(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "User{id='" + id + "', username='" + username + "', role='" + role + "'}";
    }
}
