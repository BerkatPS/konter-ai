package frameworks.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import entities.ChatRoom;
import frameworks.DatabaseConnector;
import frameworks.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChatWindow extends JFrame {
    private static final Color PRIMARY_DARK = new Color(42, 52, 94);
    private static final Color BACKGROUND = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;

    private final DatabaseConnector databaseConnector;
    private final String username;
    private final String userRole;
    private final UIComponents uiComponents;
    private ChatPanel activeChatPanel;

    public ChatWindow(DatabaseConnector databaseConnector, String username, String userRole) {
        this.databaseConnector = databaseConnector;
        this.username = username;
        this.userRole = userRole;
        this.uiComponents = new UIComponents();

        setupWindow();
        loadChatRooms();
    }

    private void setupWindow() {
        setTitle("Live Chat");
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Left Panel - Chat Rooms List
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setBackground(CARD_BG);

        JLabel titleLabel = new JLabel("Chat Rooms");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JPanel roomsPanel = new JPanel();
        roomsPanel.setLayout(new BoxLayout(roomsPanel, BoxLayout.Y_AXIS));
        roomsPanel.setBackground(CARD_BG);

        JScrollPane scrollPane = new JScrollPane(roomsPanel);
        scrollPane.setBorder(null);

        leftPanel.add(titleLabel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        // Right Panel - Chat Area
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(CARD_BG);

        JLabel welcomeLabel = new JLabel("Select a chat to start messaging");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rightPanel.add(welcomeLabel, BorderLayout.CENTER);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void loadChatRooms() {
        try {
            SessionManager.startSession(databaseConnector.getUserIdByUsername(username), username, userRole);

            int userId = databaseConnector.getUserIdByUsername(username);
            java.util.List<ChatRoom> rooms = databaseConnector.getActiveChatRooms(userId, userRole);

            JPanel roomsPanel = (JPanel) ((JScrollPane) ((JPanel) getContentPane()
                    .getComponent(0)).getComponent(1)).getViewport().getView();
            roomsPanel.removeAll();

            for (ChatRoom room : rooms) {
                JPanel roomCard = createRoomCard(room);
                roomsPanel.add(roomCard);
                roomsPanel.add(Box.createVerticalStrut(5));
            }

            roomsPanel.revalidate();
            roomsPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load chat rooms");
        }
    }

    private JPanel createRoomCard(ChatRoom room) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel nameLabel = new JLabel(room.getOtherPartyName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel statusLabel = new JLabel("● " + room.getStatus());
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(room.getStatus().equals("open") ?
                new Color(34, 197, 94) : new Color(234, 179, 8));

        card.add(nameLabel, BorderLayout.CENTER);
        card.add(statusLabel, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openChatRoom(room);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(243, 244, 246));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BG);
            }
        });

        return card;
    }

    private void openChatRoom(ChatRoom room) {
        try {
            int userId = databaseConnector.getUserIdByUsername(username);

            if (activeChatPanel != null) {
                activeChatPanel.cleanup();
            }

            activeChatPanel = new ChatPanel(databaseConnector, userId, userRole, room);

            JPanel rightPanel = (JPanel) getContentPane().getComponent(1);
            rightPanel.removeAll();
            rightPanel.add(activeChatPanel, BorderLayout.CENTER);
            rightPanel.revalidate();
            rightPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to open chat room");
        }
    }
}
