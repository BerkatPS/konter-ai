package entities;

import java.sql.Timestamp;

public class ChatMessage {
    private int id;
    private int chatRoomId;
    private int senderId;
    private String senderUsername;
    private String senderRole;
    private String message;
    private Timestamp createdAt;
    private boolean isRead;

    // Default Constructor
    public ChatMessage() {
    }

    // Basic Constructor
    public ChatMessage(int chatRoomId, int senderId, String message) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.message = message;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.isRead = false;
    }

    // Full Constructor
    public ChatMessage(int id, int chatRoomId, int senderId, String senderUsername,
                       String senderRole, String message, Timestamp createdAt) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.senderRole = senderRole;
        this.message = message;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getSenderRole() {
        return senderRole;
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

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
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
    public boolean isFromTeknisi() {
        return "teknisi".equals(senderRole);
    }

    public boolean isFromUser() {
        return "user".equals(senderRole);
    }

    public String getFormattedTime() {
        if (createdAt == null) return "";
        return new java.text.SimpleDateFormat("HH:mm").format(createdAt);
    }

    public boolean isRecent() {
        if (createdAt == null) return false;
        long diffMinutes = (System.currentTimeMillis() - createdAt.getTime()) / (60 * 1000);
        return diffMinutes < 5; // Consider messages in last 5 minutes as recent
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", chatRoomId=" + chatRoomId +
                ", senderId=" + senderId +
                ", senderUsername='" + senderUsername + '\'' +
                ", senderRole='" + senderRole + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", isRead=" + isRead +
                '}';
    }
}
