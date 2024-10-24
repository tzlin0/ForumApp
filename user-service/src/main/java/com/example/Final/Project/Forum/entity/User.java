package com.example.Final.Project.Forum.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Long userId;

    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "ProfileImageUrl", length = 255)
    private String profileImageUrl;

    @Column(name = "RegistrationDate", updatable = false)
    private LocalDateTime registrationDate;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Type", nullable = false, length = 50)
    private String Type;

    @Column(name = "EmailVerified")
    private Boolean emailVerified;

    @Column(name = "Code")
    private String code;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }

    public Long getUserId() {return userId;}

    public void setUserId(Long userId) {this.userId = userId;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getProfileImageUrl() {return profileImageUrl;}

    public void setProfileImageUrl(String profileImageUrl) {this.profileImageUrl = profileImageUrl;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public void setRegistrationDate() {this.registrationDate = LocalDateTime.now();}

    public void setNewtRegistrationDate(LocalDateTime time) {this.registrationDate = time;}

    public LocalDateTime getRegistrationDate() {return registrationDate;}

    public Boolean getActive() {return active;}

    public void setActive(Boolean active) {this.active = active;}

    public String getType() {return Type;}

    public void setType(String type) {this.Type = type;}

    public Boolean getEmailVerified() {return emailVerified;}

    public void setEmailVerified(Boolean emailVerified) {this.emailVerified = emailVerified;}

    public void setCode(String code) {this.code = code;}

    public String getCode() {return code;}





}

