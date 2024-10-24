package com.beaconfire.springsecurityauth.dto;

import javax.persistence.*;
import java.time.LocalDateTime;

public class UserDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private boolean active;
    private LocalDateTime dateJoined;
    private String type;
    private boolean emailVerified;
    private String profileImageURL;

    // Constructor
    public UserDTO(Long userId, String firstName, String lastName, String email, boolean active, LocalDateTime dateJoined, String type, boolean emailVerified, String profileImageURL) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.active = active;
        this.dateJoined = dateJoined;
        this.type = type;
        this.emailVerified = emailVerified;
        this.profileImageURL = profileImageURL;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getDateJoined() { return dateJoined; }
    public void setDateJoined(LocalDateTime dateJoined) { this.dateJoined = dateJoined; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
    public String getProfileImageURL() { return profileImageURL; }
    public void setProfileImageURL(String profileImageURL) { this.profileImageURL = profileImageURL; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }



}