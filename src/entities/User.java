package entities;

import java.sql.Timestamp;

public class User {
    private int id;
    private String username;
    private String password;
    private String nama;
    private String role;
    private Timestamp createdAt;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(int id, String username, String password, String nama, String role, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
