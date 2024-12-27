package frameworks.ui;

import frameworks.DatabaseConnector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDashboardUI {
    private static final Color PRIMARY_DARK = new Color(42, 52, 94);
    private static final Color PRIMARY_LIGHT = new Color(52, 152, 219);
    private static final Color BACKGROUND = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(71, 85, 105);

    private DatabaseConnector databaseConnector;
    private UserDashboardUI userDashboard;
    private UIComponents uiComponents;
    private JPanel contentPanel;
    private String currentPage = "dashboard";

    public AdminDashboardUI(DatabaseConnector databaseConnector, UserDashboardUI userDashboard) {
        this.databaseConnector = databaseConnector;
        this.userDashboard = userDashboard;
        this.uiComponents = new UIComponents();
    }

    public void show(String nama) {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Content Panel with Card Layout
        contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create shared table
        JTable sharedTable = createModernTable();

        // Create sidebar
        JPanel sidebar = createModernSidebar(frame, sharedTable);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Initial load - show dashboard
        showPage("dashboard", nama);
    }

    private void showPage(String page, String nama) {
        contentPanel.removeAll();
        currentPage = page;

        // Add header for all pages
        JPanel headerPanel = createModernHeader(getPageTitle(page));
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Content specific to each page
        switch (page) {
            case "dashboard":
                showAnalytics(contentPanel);
                break;
            case "users":
                showUsersContent();
                break;
            case "gejala":
                showGejalaContent();
                break;
            case "kategori":
                showKategoriContent();
                break;
            case "solusi":
                showSolusiContent();
                break;
            case "teknisi":
                showTeknisiContent();
                break;
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private String getPageTitle(String page) {
        switch (page) {
            case "dashboard": return "Dashboard Analytics";
            case "users": return "User Management";
            case "gejala": return "Gejala Management";
            case "kategori": return "Kategori Management";
            case "solusi": return "Solusi Management";
            case "teknisi": return "Teknisi Management";
            default: return "Admin Dashboard";
        }
    }
    private void showAnalytics(JPanel contentPanel) {
        try {
            // Ambil data total dari database
            int totalUsers = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM user WHERE role = 'user'");
            int totalGejala = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM gejala ");
            int totalSolusi = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM solusi");
            int totalRiwayat = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM riwayat_diagnosa");
            int totalKategori = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM kategori");
            int totalTeknisi = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM user WHERE role = 'teknisi'");


            // Bersihkan dan reset panel
            contentPanel.removeAll();
            contentPanel.setLayout(new BorderLayout());

            // Header
            JPanel headerPanel = createModernHeader("Dashboard Analytics");
            contentPanel.add(headerPanel, BorderLayout.NORTH);

            // Stats Cards Container in GridLayout
            JPanel statsContainer = new JPanel(new GridLayout(2, 2, 20, 20));
            statsContainer.setBackground(BACKGROUND);
            statsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add modern stat cards
            statsContainer.add(createModernStatCard("Total Users", String.valueOf(totalUsers), "👥"));
            statsContainer.add(createModernStatCard("Total Gejala", String.valueOf(totalGejala), "🔍"));
            statsContainer.add(createModernStatCard("Kategori", String.valueOf(totalKategori), "📊"));
            statsContainer.add(createModernStatCard("Total Solusi", String.valueOf(totalSolusi), "💡"));
            statsContainer.add(createModernStatCard("History", String.valueOf(totalRiwayat), "📋"));
            statsContainer.add(createModernStatCard("Total Teknisi", String.valueOf(totalTeknisi), "👨‍💻"));

            contentPanel.add(statsContainer, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();

        } catch (SQLException e) {
            showError("Failed to load analytics data");
            e.printStackTrace();
        }

    }


    private void showUsersContent() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(createCardBorder());

        JTable usersTable = createModernTable();
        loadTableData("SELECT * FROM user", usersTable);

        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BG);

        tablePanel.add(scrollPane);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
    }

    private void showGejalaContent() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(createCardBorder());
        JTable gejalaTable = createModernTable();
        loadTableData("SELECT id,kode_gejala,nama_gejala,deskripsi,certainty_factor * 100 AS certainty_factor_persen FROM gejala;\n", gejalaTable);

        JScrollPane scrollPane = new JScrollPane(gejalaTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BG);

        tablePanel.add(scrollPane);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
    }
    private void showKategoriContent() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(createCardBorder());
        JTable kategoriTable = createModernTable();
        loadTableData("SELECT * FROM kategori;\n", kategoriTable);

        JScrollPane scrollPane = new JScrollPane(kategoriTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BG);

        tablePanel.add(scrollPane);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
    }

    private void showSolusiContent() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(createCardBorder());

        JTable solusiTable = createModernTable();
        loadTableData("SELECT * FROM solusi", solusiTable);

        JScrollPane scrollPane = new JScrollPane(solusiTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BG);

        tablePanel.add(scrollPane);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
    }

    private void showTeknisiContent() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_BG);
        tablePanel.setBorder(createCardBorder());

        JTable teknisiTable = createModernTable();
        loadTableData("SELECT * FROM user WHERE role = 'teknisi'", teknisiTable);

        JScrollPane scrollPane = new JScrollPane(teknisiTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BG);

        tablePanel.add(scrollPane);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createModernSidebar(JFrame frame, JTable sharedTable) {
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

        JLabel roleLabel = new JLabel("Administrator");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        roleLabel.setForeground(Color.WHITE);

        profilePanel.add(profileIcon);
        profilePanel.add(roleLabel);

        // Navigation Buttons
        JButton btnDashboard = createModernNavButton("Dashboard", "📊");
        JButton btnUsers = createModernNavButton("Users", "👥");
        JButton btnGejala = createModernNavButton("Gejala", "🔍");
        JButton btnKategori = createModernNavButton("Kategori", "📦");
        JButton btnSolusi = createModernNavButton("Solusi", "💡");
        JButton btnTeknisi = createModernNavButton("Teknisi", "👨‍💼");
        JButton btnLogout = createModernNavButton("Logout", "🚪");

        // Add action listeners
        btnDashboard.addActionListener(e -> showPage("dashboard", null));
        btnUsers.addActionListener(e -> showPage("users", null));
        btnGejala.addActionListener(e -> showPage("gejala", null));
        btnKategori.addActionListener(e -> showPage("kategori", null));
        btnSolusi.addActionListener(e -> showPage("solusi", null));
        btnTeknisi.addActionListener(e -> showPage("teknisi", null));
        btnLogout.addActionListener(e -> {
            frame.dispose();
            new AuthUI(databaseConnector).showLoginPage();
        });

        // Add components to sidebar
        sidebar.add(profilePanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        sidebar.add(btnDashboard);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnUsers);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnGejala);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnKategori);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnSolusi);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnTeknisi);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        return sidebar;
    }

    private JPanel createModernHeader(String title) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel dateLabel = new JLabel("Today's " + java.time.LocalDate.now().toString());
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(TEXT_SECONDARY);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(dateLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createModernStatCard(String title, String value, String icon) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(CARD_BG);
        card.setBorder(createCardBorder());

        // Icon and Title
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        headerPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconLabel.setForeground(PRIMARY_DARK);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY);

        headerPanel.add(iconLabel);
        headerPanel.add(titleLabel);

        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(TEXT_PRIMARY);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_LIGHT, 1),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(createCardBorder());
            }
        });

        return card;
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

    private JTable createModernTable() {
        JTable table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 10));

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(TEXT_PRIMARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));

        // Row Styling
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(243, 244, 246));
                    c.setForeground(TEXT_PRIMARY);
                } else {
                    c.setBackground(row % 2 == 0 ? CARD_BG : new Color(249, 250, 251));
                    c.setForeground(TEXT_PRIMARY);
                }

                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });

        return table;
    }

    private void loadTableData(String query, JTable table) {
        try {
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            try (Statement stmt = databaseConnector.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Add column names
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    columnName = formatColumnName(columnName);
                    model.addColumn(columnName);
                }

                // Add row data
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    model.addRow(row);
                }

                table.setModel(model);
                formatTableColumns(table);

            }
        } catch (SQLException e) {
            showError("Failed to load table data");
            e.printStackTrace();
        }
    }

    private String formatColumnName(String columnName) {
        // Convert snake_case to Title Case
        String[] words = columnName.split("_");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                formatted.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return formatted.toString().trim();
    }

    private void formatTableColumns(JTable table) {
        // Auto-size columns with minimum and maximum widths
        for (int i = 0; i < table.getColumnCount(); i++) {
            int minWidth = 100;
            int maxWidth = 300;

            // Get maximum width needed for this column
            int preferredWidth = 0;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, i);
                Component comp = table.prepareRenderer(cellRenderer, row, i);
                preferredWidth = Math.max(preferredWidth, comp.getPreferredSize().width);
            }

            // Also consider header width
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, table.getColumnModel().getColumn(i).getHeaderValue(),
                    false, false, 0, i);
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

            // Apply width constraints
            preferredWidth += 20; // Add padding
            preferredWidth = Math.max(minWidth, Math.min(preferredWidth, maxWidth));

            table.getColumnModel().getColumn(i).setPreferredWidth(preferredWidth);
            table.getColumnModel().getColumn(i).setMinWidth(minWidth);
            table.getColumnModel().getColumn(i).setMaxWidth(maxWidth);
        }
    }

    private javax.swing.border.Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}