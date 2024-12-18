package entities;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private int userId;
    private String message;
    private Timestamp createdAt;
    private boolean isRead;

    // Default Constructor
    public Notification() {
    }

    // Basic Constructor
    public Notification(int userId, String message) {
        this.userId = userId;
        this.message = message;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.isRead = false;
    }

    // Full Constructor
    public Notification(int id, int userId, String message, Timestamp createdAt, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    // Utility Methods
    public String getFormattedTime() {
        if (createdAt == null) return "";
        return new java.text.SimpleDateFormat("HH:mm").format(createdAt);
    }

    public boolean isRecent() {
        if (createdAt == null) return false;
        long diffMinutes = (System.currentTimeMillis() - createdAt.getTime()) / (60 * 1000);
        return diffMinutes < 30; // Consider notifications in last 30 minutes as recent
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", isRead=" + isRead +
                '}';
    }
}