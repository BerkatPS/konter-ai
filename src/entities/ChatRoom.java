// ChatRoom.java
package entities;

import java.sql.Timestamp;

public class ChatRoom {
    private int id;
    private int userId;
    private int teknisiId;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default Constructor


    // Parameterized Constructor
    public ChatRoom(int id, int userId, int teknisiId, String status, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.teknisiId = teknisiId;
        this.status = status;
        this.createdAt = createdAt;
    }



    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getTeknisiId() {
        return teknisiId;
    }


    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTeknisiId(int teknisiId) {
        this.teknisiId = teknisiId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Utility Methods
    public boolean isActive() {
        return "open".equals(status) || "pending".equals(status);
    }

    public boolean isPending() {
        return "pending".equals(status);
    }

    public boolean isClosed() {
        return "closed".equals(status);
    }

    public long getDurationInMinutes() {
        if (createdAt == null) return 0;
        Timestamp end = updatedAt != null ? updatedAt : new Timestamp(System.currentTimeMillis());
        return (end.getTime() - createdAt.getTime()) / (60 * 1000);
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", userId=" + userId +
                ", teknisiId=" + teknisiId +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
