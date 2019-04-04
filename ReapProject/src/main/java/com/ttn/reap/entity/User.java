package com.ttn.reap.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotNull
    @Email
    @NotEmpty
    @Column(unique = true)
    String email;
    String firstName;
    String lastName;
    @Transient
    String name;
    int availPoints;
    int redeemedPoints;
    String password;
    int points = 0;
    String token;

    @Embedded
    @Column(name = "user_role")
    Role role;

    boolean isAdmin = false;

    boolean isActive = false;
    @OneToOne
    Attachment attachment;

    //COSTRUCTOR
    public User(String email, String firstName, String lastName, String password, int points, Role role, boolean isAdmin, boolean isActive, Attachment attachment) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.points = points;
        this.role = role;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
        this.attachment = attachment;
    }

    public User() {
    }

    //GETTER SETTERS
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailPoints() {
        return availPoints;
    }

    public void setAvailPoints(int availPoints) {
        this.availPoints = availPoints;
    }

    public int getRedeemedPoints() {
        return redeemedPoints;
    }

    public void setRedeemedPoints(int redeemedPoints) {
        this.redeemedPoints = redeemedPoints;
    }
    //ToString

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", points=" + points +
                ", role=" + role +
                ", isAdmin=" + isAdmin +
                ", isActive=" + isActive +
                ", attachment=" + attachment +
                '}';
    }
}
