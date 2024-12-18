package entities;

import java.sql.Timestamp;

public class ChatRoomStatus {
    private int id;
    private int chatRoomId;
    private String status;
    private Timestamp updatedAt;

    // Default Constructor
    public ChatRoomStatus() {
    }

    // Basic Constructor
    public ChatRoomStatus(int chatRoomId, String status) {
        this.chatRoomId = chatRoomId;
        this.status = status;
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    // Full Constructor
    public ChatRoomStatus(int id, int chatRoomId, String status, Timestamp updatedAt) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Utility Methods
    public boolean isOpen() {
        return "open".equals(status);
    }

    public boolean isPending() {
        return "pending".equals(status);
    }

    public boolean isClosed() {
        return "closed".equals(status);
    }

    public long getMinutesSinceLastUpdate() {
        if (updatedAt == null) return 0;
        return (System.currentTimeMillis() - updatedAt.getTime()) / (60 * 1000);
    }

    @Override
    public String toString() {
        return "ChatRoomStatus{" +
                "id=" + id +
                ", chatRoomId=" + chatRoomId +
                ", status='" + status + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}