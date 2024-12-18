package frameworks.ui;

import entities.ChatMessage;
import entities.ChatRoom;
import frameworks.DatabaseConnector;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ChatPanel extends JPanel {
    private final DatabaseConnector databaseConnector;
    private final int userId;
    private final String userRole;
    private final ChatRoom currentRoom;
    private final JTextArea chatArea;
    private final JTextField messageField;
    private Timer refreshTimer;

    public ChatPanel(DatabaseConnector databaseConnector, int userId,
                     String userRole, ChatRoom room) {
        this.databaseConnector = databaseConnector;
        this.userId = userId;
        this.userRole = userRole;
        this.currentRoom = room;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Chat Area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Message Input
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Start refresh timer
        startMessageRefresh();
        loadMessages();
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            try {
                databaseConnector.sendMessage(currentRoom.getId(), userId, message);
                messageField.setText("");
                loadMessages();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to send message");
            }
        }
    }

    private void loadMessages() {
        try {
            java.util.List<ChatMessage> messages = databaseConnector.getChatMessages(currentRoom.getId(), 50);
            chatArea.setText("");

            for (int i = messages.size() - 1; i >= 0; i--) {
                ChatMessage msg = messages.get(i);
                chatArea.append(String.format("[%s] %s: %s\n",
                        new SimpleDateFormat("HH:mm").format(msg.getCreatedAt()),
                        msg.getSenderUsername(),
                        msg.getMessage()
                ));
            }

            databaseConnector.markMessagesAsRead(currentRoom.getId(), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void startMessageRefresh() {
        refreshTimer = new Timer(3000, e -> loadMessages());
        refreshTimer.start();
    }

    public void cleanup() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
    }

}
