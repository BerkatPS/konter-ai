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
    private static final Color HOVER_COLOR = new Color(230, 230, 230);
    private static final Color TEXT_SECONDARY = new Color(71, 85, 105);

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
        setTitle("Modern Chat Dashboard");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Left Sidebar
        JPanel sidebarPanel = createModernSidebar();

        // Left Panel - Chat Rooms List
        JPanel leftPanel = createChatRoomListPanel();

        // Right Panel - Chat Area
        JPanel rightPanel = createChatAreaPanel();

        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
    }

    private JPanel createModernSidebar() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(PRIMARY_DARK);
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setLayout(new BorderLayout());

        // Top Section - User Info
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setBackground(PRIMARY_DARK);
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Segoe UI", Font.BOLD, 36));
        userIcon.setForeground(Color.WHITE);
        userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel(userRole);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(new Color(200, 200, 200));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInfoPanel.add(userIcon);
        userInfoPanel.add(Box.createVerticalStrut(10));
        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(roleLabel);

        // Navigation Buttons
        JPanel navPanel = new JPanel();
        navPanel.setBackground(PRIMARY_DARK);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));


        // Add Back to Dashboard Button
        JButton backToDashboardButton = createNavButton("🏠","Back to Dashboard");
        backToDashboardButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backToDashboardButton.setForeground(Color.WHITE);
        backToDashboardButton.setBackground(PRIMARY_DARK);
        backToDashboardButton.setBorderPainted(false);
        backToDashboardButton.setFocusPainted(false);
        backToDashboardButton.setHorizontalAlignment(SwingConstants.LEFT);
        backToDashboardButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        backToDashboardButton.addActionListener(e -> {
            if (userRole.equals("teknisi")) {
                // Kembali ke dashboard teknisi
                new TeknisiDashboardUI(databaseConnector).show(username);
            } else {
                // Kembali ke dashboard user
                new UserDashboardUI(databaseConnector).show(username);
            }
            dispose(); // Tutup jendela chat
        });

        navPanel.add(backToDashboardButton);
        sidebarPanel.add(userInfoPanel, BorderLayout.NORTH);
        sidebarPanel.add(navPanel, BorderLayout.CENTER);

        return sidebarPanel;
    }

    private JButton createNavButton(String icon, String text) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_DARK);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_DARK);
            }
        });

        return button;
    }

    private JPanel createChatRoomListPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(CARD_BG);
        leftPanel.setPreferredSize(new Dimension(350, 0));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_DARK);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Active Chat Rooms");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JButton refreshButton = new JButton("🔄");
        refreshButton.addActionListener(e -> loadChatRooms());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);

        // Rooms Panel
        JPanel roomsPanel = new JPanel();
        roomsPanel.setLayout(new BoxLayout(roomsPanel, BoxLayout.Y_AXIS));
        roomsPanel.setBackground(CARD_BG);

        JScrollPane scrollPane = new JScrollPane(roomsPanel);
        scrollPane.setBorder(null);

        leftPanel.add(headerPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        return leftPanel;
    }

    private JPanel createChatAreaPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(CARD_BG);
        rightPanel.setPreferredSize(new Dimension(600, 0));

        JLabel welcomeLabel = new JLabel("Select a chat to start messaging");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setForeground(TEXT_SECONDARY);

        rightPanel.add(welcomeLabel, BorderLayout.CENTER);

        return rightPanel;
    }


    void loadChatRooms() {
        try {
            // Validasi username
            if (username == null || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Username tidak valid. Silakan login ulang.",
                        "Kesalahan",
                        JOptionPane.ERROR_MESSAGE);
                dispose(); // Tutup jendela
                return;
            }

            // Normalisasi username
            String normalizedUsername = username.trim().toLowerCase();

            // Pastikan sesi dimulai
            int userId = SessionManager.getLoggedInUserId();
            SessionManager.startSession(userId, normalizedUsername, userRole);

            // Debug: Cetak informasi user
            System.out.println("Loading chat rooms for user: " + normalizedUsername + " (ID: " + userId + ")");

            // Ambil chat rooms aktif
            java.util.List<ChatRoom> rooms = databaseConnector.getActiveChatRooms(userId, userRole);

            // Debug: Cetak jumlah rooms
            System.out.println("Active chat rooms found: " + rooms.size());

            // Temukan JScrollPane di dalam struktur panel
            JScrollPane scrollPane = findScrollPane(getContentPane());

            if (scrollPane == null) {
                System.err.println("Scroll Pane not found");
                return;
            }

            JPanel roomsPanel = (JPanel) scrollPane.getViewport().getView();
            roomsPanel.removeAll();

            if (rooms.isEmpty()) {
                // Tambahkan label jika tidak ada room
                JLabel noRoomsLabel = new JLabel("Belum ada ruang chat aktif");
                noRoomsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                noRoomsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                roomsPanel.add(noRoomsLabel);
            } else {
                for (ChatRoom room : rooms) {
                    JPanel roomCard = createRoomCard(room);
                    roomsPanel.add(roomCard);
                    roomsPanel.add(Box.createVerticalStrut(5));
                }
            }

            roomsPanel.revalidate();
            roomsPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat ruang chat: " + e.getMessage(),
                    "Kesalahan",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private JScrollPane findScrollPane(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JScrollPane) {
                return (JScrollPane) comp;
            }

            if (comp instanceof Container) {
                JScrollPane scrollPane = findScrollPane((Container) comp);
                if (scrollPane != null) {
                    return scrollPane;
                }
            }
        }
        return null;
    }



    private JPanel createRoomCard(ChatRoom room) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        try {
            // Tentukan nama pihak lain berdasarkan peran pengguna
            String otherPartyName = userRole.equals("user")
                    ? databaseConnector.getUsernameById(room.getTeknisiId())
                    : databaseConnector.getUsernameById(room.getUserId());

            JLabel nameLabel = new JLabel(otherPartyName);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            nameLabel.setForeground(TEXT_SECONDARY);

            JLabel statusLabel = new JLabel("● " + room.getStatus());
            statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            statusLabel.setForeground(room.getStatus().equals("open") ?
                    new Color(34, 197, 94) : new Color(234, 179, 8));

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setOpaque(false);
            textPanel.add(nameLabel, BorderLayout.NORTH);
            textPanel.add(statusLabel, BorderLayout.SOUTH);

            card.add(textPanel, BorderLayout.CENTER);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openChatRoom(room);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(HOVER_COLOR);
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
            int userId = SessionManager.getLoggedInUserId();

            if (activeChatPanel != null) {
                activeChatPanel.cleanup();
            }

            // Dapatkan nama pihak lain
            String otherPartyName = userRole.equals("user")
                    ? databaseConnector.getUsernameById(room.getTeknisiId())
                    : databaseConnector.getUsernameById(room.getUserId());

            activeChatPanel = new ChatPanel(databaseConnector, userId, userRole, room);

            // Dapatkan panel kanan (chat area)
            JPanel rightPanel = (JPanel) getContentPane().getComponent(2); // Pastikan indeks benar
            rightPanel.removeAll();

            // Buat panel header untuk chat
            JPanel chatHeaderPanel = createChatHeaderPanel(otherPartyName, room);

            // Layout baru untuk panel kanan
            rightPanel.setLayout(new BorderLayout());
            rightPanel.add(chatHeaderPanel, BorderLayout.NORTH);
            rightPanel.add(activeChatPanel, BorderLayout.CENTER);

            rightPanel.revalidate();
            rightPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to open chat room: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createChatHeaderPanel(String otherPartyName, ChatRoom room) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_DARK);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Nama pihak lain
        JLabel nameLabel = new JLabel(otherPartyName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);

        // Status chat
        JLabel statusLabel = new JLabel("Status: " + room.getStatus());
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(200, 200, 200));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(nameLabel, BorderLayout.NORTH);
        textPanel.add(statusLabel, BorderLayout.SOUTH);

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        // Tombol close chat
        JButton closeButton = uiComponents.createSecondaryButton("Close Chat");
        closeButton.setForeground(PRIMARY_DARK);
        closeButton.addActionListener(e -> {
            try {
                databaseConnector.closeChatRoom(room.getId());
                loadChatRooms(); // Refresh daftar chat rooms

                // Reset panel kanan ke tampilan awal
                JPanel rightPanel = (JPanel) getContentPane().getComponent(2);
                rightPanel.removeAll();
                rightPanel.add(createDefaultChatAreaPanel());
                rightPanel.revalidate();
                rightPanel.repaint();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Failed to close chat room",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tambahkan kondisi untuk role user
        if (userRole.equals("user")) {
            // Tombol Done
            JButton doneButton = uiComponents.createSecondaryButton("Done");
            doneButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to mark this consultation as done?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        // Update status chat room menjadi 'closed'
                        databaseConnector.closeChatRoom(room.getId());

                        // Kirim notifikasi ke teknisi
                        int teknisiId = room.getTeknisiId();
                        databaseConnector.sendNotification(
                                teknisiId,
                                "Consultation with user " + username + " has been marked as done."
                        );

                        // Refresh daftar chat rooms
                        loadChatRooms();

                        // Reset panel kanan ke tampilan awal
                        JPanel rightPanel = (JPanel) getContentPane().getComponent(2);
                        rightPanel.removeAll();
                        rightPanel.add(createDefaultChatAreaPanel());
                        rightPanel.revalidate();
                        rightPanel.repaint();

                        // Tampilkan konfirmasi
                        JOptionPane.showMessageDialog(
                                this,
                                "Consultation marked as done successfully.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(
                                this,
                                "Failed to mark consultation as done",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            });

            // Tambahkan tombol Done ke panel
            buttonPanel.add(doneButton);
        }

        // Tambahkan tombol close ke panel
        buttonPanel.add(closeButton);

        headerPanel.add(textPanel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        return headerPanel;
    }

    // Method untuk membuat panel default di area chat
    private JPanel createDefaultChatAreaPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(CARD_BG);
        rightPanel.setPreferredSize(new Dimension(600, 0));

        JLabel welcomeLabel = new JLabel("Select a chat to start messaging");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setForeground(TEXT_SECONDARY);

        rightPanel.add(welcomeLabel, BorderLayout.CENTER);

        return rightPanel;
    }


}