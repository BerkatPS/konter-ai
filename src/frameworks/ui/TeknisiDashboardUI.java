package frameworks.ui;

import frameworks.DatabaseConnector;
import frameworks.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;

public class TeknisiDashboardUI {
    // Color palette constants
    private static final Color PRIMARY_DARK = new Color(42, 52, 94);    // Dark blue for primary elements
    private static final Color PRIMARY_LIGHT = new Color(52, 152, 219); // Light blue for accents
    private static final Color BACKGROUND = new Color(248, 250, 252);   // Light gray for background
    private static final Color CARD_BG = Color.WHITE;                   // White for cards
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);    // Dark gray for primary text
    private static final Color TEXT_SECONDARY = new Color(71, 85, 105); // Medium gray for secondary text

    private DatabaseConnector databaseConnector;
    private UIComponents uiComponents;

    public TeknisiDashboardUI(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.uiComponents = new UIComponents();
    }

    public void show(String username) {
        JFrame frame = new JFrame("Teknisi Dashboard");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel with modern background
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Modern Sidebar
        JPanel sidebar = createModernSidebar(username, frame);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Modern Header
        JPanel headerPanel = createModernHeader(username);



        // Stats Cards Container
        JPanel statsContainer = new JPanel(new GridLayout(1, 3, 20, 0));
        statsContainer.setOpaque(false);


        try {
            // Get user ID
            int userId = SessionManager.getLoggedInUserId();

            // Hitung statistik konsultasi
            int activeConsultations = databaseConnector.calculatePendingConsultations(userId);
            int completedConsultations = databaseConnector.calculateCompletedConsultations(userId);

            // Modern stat cards dengan data dinamis
            statsContainer.add(createModernStatCard(
                    "Active Consultations",
                    String.valueOf(activeConsultations),
                    "💬"
            ));
            statsContainer.add(createModernStatCard(
                    "Completed Consultations",
                    String.valueOf(completedConsultations),
                    "✅"
            ));

        } catch (SQLException e) {
            e.printStackTrace();
            // Fallback to default values
            statsContainer.add(createModernStatCard("Active Consultations", "0", "💬"));
            statsContainer.add(createModernStatCard("Completed Consultations", "0", "✅"));

            JOptionPane.showMessageDialog(
                    frame,
                    "Error loading consultation statistics: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        // Recent Consultations Panel
        JPanel recentPanel = createRecentConsultationsPanel();

        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(statsContainer, BorderLayout.NORTH);
        centerPanel.add(recentPanel, BorderLayout.CENTER);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createModernSidebar(String username, JFrame frame) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PRIMARY_DARK);
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 15));
        sidebar.setPreferredSize(new Dimension(280, 0));

        // Profile Section
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        profilePanel.setOpaque(false);

        JLabel profileIcon = new JLabel("👨‍💼");
        profileIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        profileIcon.setForeground(Color.WHITE);

        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);

        profilePanel.add(profileIcon);
        profilePanel.add(usernameLabel);

        // Navigation Buttons
        JButton btnHome = createModernNavButton("Home", "🏠");
        JButton btnConsultation = createModernNavButton("Active Consultations", "💬");
        JButton btnHistory = createModernNavButton("Consultation History", "📋");
        JButton btnLogout = createModernNavButton("Logout", "🚪");

        sidebar.add(profilePanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        sidebar.add(btnHome);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnConsultation);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnHistory);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        // Event Listeners
        btnHome.addActionListener(e -> {
            frame.dispose();
            show(username);
        });
        btnConsultation.addActionListener(e -> {
            frame.dispose();
            showConsultationPage(username);
        });

        btnHistory.addActionListener(e -> {
            frame.dispose();
            showRiwayatConsultationPage(username);
        });

        btnLogout.addActionListener(e -> {
            frame.dispose();
            new AuthUI(databaseConnector).showLoginPage();
        });

        return sidebar;
    }
    public void showConsultationPage(String username) {
        ChatWindow chatWindow = new ChatWindow(databaseConnector, username, "teknisi");
        chatWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chatWindow.setVisible(true);
    }


    public void showRiwayatConsultationPage(String username) {
        JFrame frame = new JFrame("Consultation History");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Sidebar
        JPanel sidebar = createModernSidebar(username, frame);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header with Search
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setBackground(PRIMARY_DARK);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel headerLabel = new JLabel("Consultation History", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchPanel.add(searchField, BorderLayout.CENTER);

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // History Container
        JPanel historyContainer = new JPanel(new BorderLayout());
        historyContainer.setBackground(CARD_BG);
        historyContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Create table model
        String[] columnNames = {"Date", "Customer", "Issue", "Status", "Duration"};
        Object[][] data = {
                {"2024-12-18", "John Doe", "Printer Issue", "Completed", "45 min"},
                {"2024-12-17", "Jane Smith", "Network Problem", "Completed", "30 min"},
                {"2024-12-16", "Mike Johnson", "Software Update", "Completed", "20 min"}
        };

        JTable historyTable = new JTable(data, columnNames);
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        historyTable.setRowHeight(40);
        historyTable.setShowGrid(false);
        historyTable.setIntercellSpacing(new Dimension(0, 10));

        // Table Header Styling
        JTableHeader header = historyTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(TEXT_PRIMARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BG);

        historyContainer.add(scrollPane);

        // Filter Panel at the bottom
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(BACKGROUND);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JComboBox<String> timeFilter = new JComboBox<>(new String[]{"All Time", "This Week", "This Month"});
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"All Status", "Completed", "Pending"});

        filterPanel.add(new JLabel("Time: "));
        filterPanel.add(timeFilter);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(new JLabel("Status: "));
        filterPanel.add(statusFilter);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(historyContainer, BorderLayout.CENTER);
        contentPanel.add(filterPanel, BorderLayout.SOUTH);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createModernHeader(String username) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel welcomeLabel = new JLabel("Welcome back, " + username);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(TEXT_PRIMARY);

        JLabel dateLabel = new JLabel("Today's " + java.time.LocalDate.now().toString());
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(TEXT_SECONDARY);

        headerPanel.add(welcomeLabel, BorderLayout.NORTH);
        headerPanel.add(dateLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createModernStatCard(String title, String value, String icon) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Icon and Title Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        headerPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY);

        headerPanel.add(iconLabel);
        headerPanel.add(titleLabel);

        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(TEXT_PRIMARY);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createRecentConsultationsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel headerLabel = new JLabel("Recent Consultations");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(TEXT_PRIMARY);

        JButton viewAllButton = uiComponents.createTextButton("View All →");

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(viewAllButton, BorderLayout.EAST);

        // Consultations List
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        // Add sample consultations
        listPanel.add(createConsultationItem("John Doe", "Printer Issue", "10 minutes ago", "Active"));
        listPanel.add(createConsultationItem("Jane Smith", "Network Problem", "1 hour ago", "Pending"));
        listPanel.add(createConsultationItem("Mike Johnson", "Software Update", "2 hours ago", "Completed"));

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createConsultationItem(String customer, String issue, String time, String status) {
        JPanel item = new JPanel(new BorderLayout(15, 0));
        item.setOpaque(false);
        item.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        // Left side - Customer info
        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        leftPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(customer);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(TEXT_PRIMARY);

        JLabel issueLabel = new JLabel(issue);
        issueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        issueLabel.setForeground(TEXT_SECONDARY);

        leftPanel.add(nameLabel);
        leftPanel.add(issueLabel);

        // Right side - Time and status
        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        rightPanel.setOpaque(false);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(TEXT_SECONDARY);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(getPriorityColor(status));
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        rightPanel.add(timeLabel);
        rightPanel.add(statusLabel);

        item.add(leftPanel, BorderLayout.WEST);
        item.add(rightPanel, BorderLayout.EAST);

        return item;
    }

    private Color getPriorityColor(String status) {
        switch (status.toLowerCase()) {
            case "active": return new Color(34, 197, 94);  // Green
            case "pending": return new Color(234, 179, 8); // Yellow
            default: return TEXT_SECONDARY;
        }
    }

    private JButton createModernNavButton(String text, String icon) {
        JButton button = new JButton(icon + "  " + text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_DARK);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(250, 40));
        button.setMaximumSize(new Dimension(250, 40));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_DARK.darker());
                button.setContentAreaFilled(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(false);
            }
        });

        return button;
    }
}