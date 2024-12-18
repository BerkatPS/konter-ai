package frameworks;

import controller.DiagnosisController;
import entities.*;
import usecases.DiagnoseUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SwingUI {
    private DatabaseConnector databaseConnector;
    private DiagnoseUseCase diagnoseUseCase;
    private DiagnosisController diagnosisController;

    public SwingUI() {
        try {
            databaseConnector = new DatabaseConnector();
            diagnoseUseCase = new DiagnoseUseCase(databaseConnector);
            diagnosisController = new DiagnosisController(diagnoseUseCase, databaseConnector);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showLoginPage() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel lblTitle = new JLabel("Welcome to Expert System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 144, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(lblUsername, gbc);

        JTextField txtUsername = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblPassword, gbc);

        JPasswordField txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setForeground(new Color(30, 144, 255));
        btnLogin.setBackground(new Color(30, 144, 255));
        btnLogin.setPreferredSize(new Dimension(200, 40));
        btnLogin.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        // Hyperlink ke Register
        JLabel lblRegister = new JLabel("Don't have an account? Register here.");
        lblRegister.setForeground(Color.BLUE);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridy = 4;
        panel.add(lblRegister, gbc);

        // Add to Frame
        frame.add(panel);
        frame.setVisible(true);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            try {
                User user = databaseConnector.verifikasiLogin(username, password);
                if (user != null) {
                    SessionManager.startSession(user.getId(), user.getUsername(), user.getRole());
                    int userId = SessionManager.getLoggedInUserId();
                    System.out.println("User ID: " + userId);

                    // Check user role and navigate accordingly
                    if ("admin".equals(user.getRole())) {
                        frame.dispose();
                        showAdminDashboard();  // Show Admin Dashboard
                    } else if ("user".equals(user.getRole())) {
                        frame.dispose();
                        showUserDashboard(user.getNama());  // Show User Dashboard
                    } else if ("teknisi".equals( user.getRole())) {
                        frame.dispose();
                        showTeknisiPage(user.getNama());
                    } else {
                        JOptionPane.showMessageDialog(frame, "Unknown user role!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                showRegisterPage();  // Navigate to the register page
            }
        });
    }


    // ======================== REGISTER PAGE ============================
    public void showRegisterPage() {
        JFrame frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel lblTitle = new JLabel("Create New Account", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 144, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Nama Lengkap
        JLabel lblName = new JLabel("Full Name:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(lblName, gbc);

        JTextField txtName = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtName, gbc);

        // Username
        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblUsername, gbc);

        JTextField txtUsername = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblPassword, gbc);

        JPasswordField txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Register Button
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegister.setForeground(new Color(30, 144, 255));
        btnRegister.setBackground(new Color(34, 139, 34));
        btnRegister.setPreferredSize(new Dimension(200, 40));
        btnRegister.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnRegister, gbc);

        // Hyperlink to Login
        JLabel lblLogin = new JLabel("Already have an account? Login here.");
        lblLogin.setForeground(Color.BLUE);
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogin.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridy = 5;
        panel.add(lblLogin, gbc);

        // Add to Frame
        frame.add(panel);
        frame.setVisible(true);

        // Event Listeners
        btnRegister.addActionListener(e -> {
            String fullName = txtName.getText();
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    boolean success = databaseConnector.registerUser(username, password, fullName);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Account Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        showLoginPage();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                showLoginPage();
            }
        });
    }

    public void showUserDashboard(String username) {
        JFrame frame = new JFrame("User Dashboard");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(42, 52, 94));
        sidebar.setPreferredSize(new Dimension(250, 700));

        JLabel sidebarTitle = new JLabel("User Menu", SwingConstants.CENTER);
        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton btnDiagnosis = createSidebarButton("Start Diagnosis");
        JButton btnRiwayat = createSidebarButton("Riwayat");
        JButton btnKonsultasi = createSidebarButton("Konsultasi Online");
        JButton btnLogout = createSidebarButton("Logout");

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(sidebarTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(btnDiagnosis);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnRiwayat);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnKonsultasi);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(42, 52, 94));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Card Container (Centered)
        JPanel cardContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        cardContainer.setBackground(Color.WHITE);

        JPanel panel1 = createInfoCard("Diagnoses Completed", "23", new Dimension(200, 250));
        JPanel panel2 = createInfoCard("Pending Diagnoses", "5", new Dimension(200, 250));

        cardContainer.add(panel1);
        cardContainer.add(panel2);

        contentPanel.add(lblWelcome, BorderLayout.NORTH);
        contentPanel.add(cardContainer, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        btnDiagnosis.addActionListener(e -> {
            frame.dispose();
            showDiagnosisPage(username);
        });

        btnRiwayat.addActionListener(e -> {
            frame.dispose();
            showRiwayatPage(username);
        });

        btnKonsultasi.addActionListener(e -> {
            frame.dispose();
            showUserConsultationPage(username);
        });


        btnLogout.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
    }

    public void showUserConsultationPage(String username) {
        JFrame frame = new JFrame("Konsultasi Online");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar (same as previous sidebar)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(42, 52, 94));
        sidebar.setPreferredSize(new Dimension(250, 700));

        JLabel sidebarTitle = new JLabel("User Menu", SwingConstants.CENTER);
        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton btnDiagnosis = createSidebarButton("Start Diagnosis");
        JButton btnRiwayat = createSidebarButton("Riwayat");
        JButton btnKonsultasi = createSidebarButton("Konsultasi Online");
        JButton btnLogout = createSidebarButton("Logout");

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(sidebarTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(btnDiagnosis);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnRiwayat);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnKonsultasi);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Konsultasi Online - " + username, SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(42, 52, 94));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Chat Container
        JPanel chatContainer = new JPanel();
        chatContainer.setLayout(new BoxLayout(chatContainer, BoxLayout.Y_AXIS));
        chatContainer.setBackground(Color.WHITE);

        // Scrollable chat area
        JTextArea chatArea = new JTextArea(15, 40);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatContainer.add(scrollPane);

        // Dropdown for selecting technician
        JLabel lblSelectTechnician = new JLabel("Pilih Teknisi:");
        String[] technicians = {"Teknisi 1", "Teknisi 2", "Teknisi 3"};  // Replace with actual technician names
        JComboBox<String> technicianComboBox = new JComboBox<>(technicians);
        JPanel selectTechnicianPanel = new JPanel(new FlowLayout());
        selectTechnicianPanel.add(lblSelectTechnician);
        selectTechnicianPanel.add(technicianComboBox);

        // Text Field and Send Button for sending messages
        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField messageField = new JTextField(30);
        JButton sendButton = new JButton("Send");

        inputPanel.add(messageField);
        inputPanel.add(sendButton);

        // Add panels to content panel
        contentPanel.add(lblWelcome, BorderLayout.NORTH);
        contentPanel.add(selectTechnicianPanel, BorderLayout.SOUTH);
        contentPanel.add(chatContainer, BorderLayout.CENTER);
        contentPanel.add(inputPanel, BorderLayout.SOUTH);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                String selectedTechnician = (String) technicianComboBox.getSelectedItem();
                if (!message.isEmpty() && selectedTechnician != null) {
                    // Add message to chat area
                    chatArea.append("User: " + message + "\n");

                    // Reset the message field
                    messageField.setText("");

                    // Simulate technician's reply
                    chatArea.append(selectedTechnician + ": Reply to your message\n");
                }
            }
        });

        // Event listeners for other buttons
        btnDiagnosis.addActionListener(e -> {
            frame.dispose();
            showDiagnosisPage(username);
        });

        btnRiwayat.addActionListener(e -> {
            frame.dispose();
            showRiwayatPage(username);
        });

        btnLogout.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
    }


    public void showTeknisiPage(String username) {
        JFrame frame = new JFrame("Teknisi Dashboard");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(42, 52, 94));
        sidebar.setPreferredSize(new Dimension(250, 700));

        JLabel sidebarTitle = new JLabel("Teknisi Menu", SwingConstants.CENTER);
        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton btnConsultation = createSidebarButton("Terima Konsultasi");
        JButton btnRiwayat = createSidebarButton("Riwayat Konsultasi");
        JButton btnLogout = createSidebarButton("Logout");

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(sidebarTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(btnConsultation);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnRiwayat);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Welcome Label
        JLabel lblWelcome = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(42, 52, 94));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Card Container (Centered)
        JPanel cardContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        cardContainer.setBackground(Color.WHITE);

        // Card for incoming consultations
        JPanel panel1 = createInfoCard("Consultations Pending", "10", new Dimension(200, 250));

        cardContainer.add(panel1);

        contentPanel.add(lblWelcome, BorderLayout.NORTH);
        contentPanel.add(cardContainer, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        btnConsultation.addActionListener(e -> {
            frame.dispose();
            showConsultationPage(username);
        });

        btnRiwayat.addActionListener(e -> {
            frame.dispose();
            showRiwayatConsultationPage(username);
        });

        btnLogout.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
    }

    public void showConsultationPage(String username) {
        JFrame frame = new JFrame("Live Chat with Customer");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(42, 52, 94));
        sidebar.setPreferredSize(new Dimension(250, 700));

        JLabel sidebarTitle = new JLabel("Teknisi Menu", SwingConstants.CENTER);
        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton btnBack = createSidebarButton("Back to Dashboard");
        JButton btnLogout = createSidebarButton("Logout");

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(sidebarTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(btnBack);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalGlue());

        // Chat Area (Content)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Chat Window (Display messages)
        JTextArea chatArea = new JTextArea(20, 50);
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Text Field for Input
        JTextField inputField = new JTextField(40);
        JButton sendButton = new JButton("Send");
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(inputField);
        inputPanel.add(sendButton);
        contentPanel.add(inputPanel, BorderLayout.SOUTH);

        // Add components to main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        btnBack.addActionListener(e -> {
            frame.dispose();
            showTeknisiPage(username); // Return to Technician Dashboard
        });

        btnLogout.addActionListener(e -> {
            frame.dispose();
            showLoginPage(); // Go to Login Page
        });

        sendButton.addActionListener(e -> {
            String message = inputField.getText();
            if (!message.trim().isEmpty()) {
                chatArea.append("Teknisi: " + message + "\n");
                inputField.setText(""); // Clear the input field
                // Here you can also implement the logic for sending the message to the customer
            }
        });
    }

    public void showRiwayatConsultationPage(String username) {
        JFrame frame = new JFrame("Riwayat Konsultasi");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(42, 52, 94));
        sidebar.setPreferredSize(new Dimension(250, 700));

        JLabel sidebarTitle = new JLabel("Teknisi Menu", SwingConstants.CENTER);
        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton btnBack = createSidebarButton("Back to Dashboard");
        JButton btnLogout = createSidebarButton("Logout");

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(sidebarTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(btnBack);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalGlue());

        // Content Panel (for showing consultation history)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // List of consultations (For example purposes, using JList)
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Consultation 1 - [Customer A] - Date: 2024-12-10");
        model.addElement("Consultation 2 - [Customer B] - Date: 2024-12-08");
        model.addElement("Consultation 3 - [Customer C] - Date: 2024-12-05");

        JList<String> consultationList = new JList<>(model);
        consultationList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane listScrollPane = new JScrollPane(consultationList);
        contentPanel.add(listScrollPane, BorderLayout.CENTER);

        // Add components to main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        btnBack.addActionListener(e -> {
            frame.dispose();
            showTeknisiPage(username); // Return to Technician Dashboard
        });

        btnLogout.addActionListener(e -> {
            frame.dispose();
            showLoginPage(); // Go to Login Page
        });
    }


    // ========================= Sidebar Button Utility ===========================
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(180, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 73, 94));
            }
        });
        return button;
    }


    // Helper Method: Create Information Cards
    private JPanel createInfoCard(String title, String value, Dimension size) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(230, 240, 255));
        card.setBorder(BorderFactory.createLineBorder(new Color(42, 52, 94), 2));
        card.setPreferredSize(size); // Fixed size for the card

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(new Color(42, 52, 94));

        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(new Color(42, 52, 94));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }

    private JPanel createSidebar(String username, JFrame currentFrame) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(42, 52, 94));
        sidebar.setPreferredSize(new Dimension(250, 700));

        JLabel lblTitle = new JLabel("User Menu", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);

        JButton btnDashboard = createStyledButton("Dashboard");
        JButton btnLogout = createStyledButton("Logout");

        btnDashboard.addActionListener(e -> {
            currentFrame.dispose();
            showUserDashboard(username);
        });

        btnLogout.addActionListener(e -> {
            currentFrame.dispose();
            showLoginPage();
        });

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(lblTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnDashboard);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        return sidebar;
    }



    public void showRiwayatPage(String username) {
        JFrame frame = new JFrame("Riwayat Diagnosa");
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        JButton sidebar = createSidebarButton(username);

        // Header
        JLabel lblHeader = new JLabel("Riwayat Diagnosa Anda", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(52, 152, 219));
        lblHeader.setPreferredSize(new Dimension(0, 80));

        // Content Panel
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listRiwayat = new JList<>(listModel);
        listRiwayat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listRiwayat.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        listRiwayat.setBackground(new Color(230, 240, 255));

        JScrollPane scrollPane = new JScrollPane(listRiwayat);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPane.setBackground(Color.WHITE);

        // Load Data in Thread
        new Thread(() -> {
            try {
                List<RiwayatDiagnosa> riwayatList = databaseConnector.getRiwayatByUsername(username);
                if (riwayatList.isEmpty()) {
                    listModel.addElement("Tidak ada riwayat diagnosa.");
                } else {
                    for (RiwayatDiagnosa riwayat : riwayatList) {
                        listModel.addElement("🗓 " + riwayat.getCreatedAt() + " - " + riwayat.getHasilDiagnosa());
                    }
                }
            } catch (SQLException e) {
                listModel.addElement("Gagal memuat riwayat diagnosa.");
            }
        }).start();

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(lblHeader, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }





    public void showDiagnosisPage(String username) {
        JFrame frame = new JFrame("Diagnosis");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar (Reusable Sidebar Method)
        JPanel sidebar = createSidebar(username, frame);

        // Header
        JLabel lblHeader = new JLabel("Diagnosis Process", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(52, 152, 219));
        lblHeader.setPreferredSize(new Dimension(0, 80));

        // Center Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Question Display Area
        JPanel questionCard = new JPanel(new BorderLayout());
        questionCard.setBackground(new Color(230, 240, 255));
        questionCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        questionCard.setPreferredSize(new Dimension(600, 200));

        JLabel lblQuestion = new JLabel("Loading questions...", SwingConstants.CENTER);
        lblQuestion.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        questionCard.add(lblQuestion, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnYes = createStyledButton("Ya");
        JButton btnNo = createStyledButton("Tidak");
        JButton btnBack = createStyledButton("Kembali");

        buttonPanel.add(btnYes);
        buttonPanel.add(btnNo);
        buttonPanel.add(btnBack);

        contentPanel.add(questionCard, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Combine Panels
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(lblHeader, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Load Questions in Background
        new Thread(() -> {
            try {
                List<Pertanyaan> pertanyaanList = databaseConnector.getAllPertanyaan();
                List<Gejala> gejalaList = databaseConnector.getAllGejala();

                if (pertanyaanList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> lblQuestion.setText("No questions available."));
                    return;
                }

                int[] index = {0};
                boolean[] answers = new boolean[pertanyaanList.size()];
                SwingUtilities.invokeLater(() -> lblQuestion.setText(pertanyaanList.get(index[0]).getPertanyaan()));

                // Button Actions
                btnYes.addActionListener(e -> {
                    answers[index[0]] = true;
                    nextQuestion(pertanyaanList, gejalaList, lblQuestion, index, frame, answers, username);
                });

                btnNo.addActionListener(e -> {
                    answers[index[0]] = false;
                    nextQuestion(pertanyaanList, gejalaList, lblQuestion, index, frame, answers, username);
                });

                btnBack.addActionListener(e -> {
                    frame.dispose();
                    showUserDashboard(username);
                });

            } catch (SQLException e) {
                SwingUtilities.invokeLater(() -> lblQuestion.setText("Failed to load questions."));
            }
        }).start();
    }

    private void nextQuestion(List<Pertanyaan> pertanyaanList, List<Gejala> gejalaList, JLabel lblQuestion, int[] index, JFrame frame, boolean[] answers, String username) {
        index[0]++;
        if (index[0] < pertanyaanList.size()) {
            lblQuestion.setText(pertanyaanList.get(index[0]).getPertanyaan());
        } else {
            frame.dispose();
            showDiagnosisResult(pertanyaanList, gejalaList, answers, username);
        }
    }

    private void showDiagnosisResult(List<Pertanyaan> pertanyaanList, List<Gejala> gejalaList, boolean[] answers, String username) {
        JFrame frame = new JFrame("Diagnosis Result");
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel lblHeader = new JLabel("Diagnosis Result", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(52, 152, 219));
        lblHeader.setPreferredSize(new Dimension(0, 60));

        // Result Area
        JTextArea txtResult = new JTextArea();
        txtResult.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtResult.setEditable(false);
        txtResult.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        StringBuilder result = new StringBuilder("Hasil Diagnosis:\n");
        StringBuilder solusiBuilder = new StringBuilder();
        double totalCF = 0;
        int count = 0;

        // Iterate through answers
        for (int i = 0; i < answers.length; i++) {
            if (answers[i]) {
                Pertanyaan currentQuestion = pertanyaanList.get(i);
                for (Gejala gejala : gejalaList) {
                    if (gejala.getId() == currentQuestion.getGejalaId()) {
                        result.append("- ").append(gejala.getNamaGejala())
                                .append(" (CF: ").append(gejala.getCertaintyFactor()).append(")\n");
                        totalCF += gejala.getCertaintyFactor();
                        count++;
                        try {
                            Solusi solusi = databaseConnector.getSolusiByGejalaId(gejala.getId());
                            if (solusi != null) {
                                solusiBuilder.append("- Solusi untuk ").append(gejala.getNamaGejala())
                                        .append(": ").append(solusi.getHasilSolusi()).append("\n");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            solusiBuilder.append("- Solusi tidak tersedia.\n");
                        }
                    }
                }
            }
        }

        // Calculate average CF
        double averageCF = (count > 0) ? totalCF / count : 0;
        result.append("\nRata-rata Certainty Factor: ").append(String.format("%.2f", averageCF));
        result.append("\n\nSolusi:\n").append(solusiBuilder.toString());

        // Set the result text
        txtResult.setText(result.toString());

        // Save to Database
        try {
            SessionManager.startSession(databaseConnector.getUserIdByUsername(username), username, "user");
            int userId = databaseConnector.getUserIdByUsername(username);
            databaseConnector.saveRiwayatDiagnosa(userId, result.toString(), solusiBuilder.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Gagal menyimpan hasil diagnosis.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Back Button
        JButton btnBack = createStyledButton("Back to Dashboard");
        btnBack.addActionListener(e -> {
            frame.dispose();
            showUserDashboard(username);
        });

        mainPanel.add(lblHeader, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(txtResult), BorderLayout.CENTER);
        mainPanel.add(btnBack, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }




    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(new Color(52, 152, 219));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185)));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219));
            }
        });
        return button;
    }

    public void showAdminDashboard() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(60, 90, 180));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 600));

        JButton btnAnalytics = createSidebarButton("Data Analytics");
        JButton btnUsers = createSidebarButton("User");
        JButton btnGejala = createSidebarButton("Gejala");
        JButton btnSolusi = createSidebarButton("Solusi");
        JButton btnRiwayat = createSidebarButton("Riwayat");
        JButton btnLogout = createSidebarButton("Logout");

        sidebar.add(Box.createRigidArea(new Dimension(0, 50)));
        sidebar.add(btnAnalytics);
        sidebar.add(btnUsers);
        sidebar.add(btnGejala);
        sidebar.add(btnSolusi);
        sidebar.add(btnRiwayat);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel lblHeader = new JLabel("Welcome, Admin", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setForeground(new Color(60, 90, 180));
        contentPanel.add(lblHeader, BorderLayout.NORTH);

        // Create a JTable to hold dynamic data
        JTable table = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(table);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        btnAnalytics.addActionListener(e -> showAnalytics(contentPanel));
        btnUsers.addActionListener(e -> loadUserTableData(table)); // Load User Data when clicked
        btnGejala.addActionListener(e -> loadTableData("SELECT * FROM gejala", table, "Gejala Data"));
        btnSolusi.addActionListener(e -> loadTableData("SELECT * FROM solusi", table, "Solusi Data"));
        btnRiwayat.addActionListener(e -> loadTableData("SELECT * FROM riwayat_diagnosa", table, "Riwayat Diagnosa"));
        btnLogout.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
    }

    // Method to load User data into JTable
    private void loadUserTableData(JTable table) {
        String query = "SELECT * FROM user"; // SQL query to fetch all user data

        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            try (Statement stmt = databaseConnector.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Add column names
                for (int i = 1; i <= columnCount; i++) {
                    model.addColumn(metaData.getColumnName(i));
                }

                // Add row data
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAnalytics(JPanel contentPanel) {
        try {
            // Ambil data total dari database
            int totalUsers = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM user");
            int totalGejala = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM gejala");
            int totalSolusi = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM solusi");
            int totalRiwayat = databaseConnector.getTotalFromQuery("SELECT COUNT(*) AS total FROM riwayat_diagnosa");

            // Bersihkan panel sebelum menampilkan data baru
            contentPanel.removeAll();

            // Buat panel baru dengan GridBagLayout
            JPanel analyticsPanel = new JPanel(new GridBagLayout());
            analyticsPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(20, 20, 20, 20); // Spacing between cards

            // Tambahkan kartu analytics ke panel
            gbc.gridx = 0; gbc.gridy = 0;
            analyticsPanel.add(createAnalyticsCard("Total Users", totalUsers), gbc);

            gbc.gridx = 1; gbc.gridy = 0;
            analyticsPanel.add(createAnalyticsCard("Total Gejala", totalGejala), gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            analyticsPanel.add(createAnalyticsCard("Total Solusi", totalSolusi), gbc);

            gbc.gridx = 1; gbc.gridy = 1;
            analyticsPanel.add(createAnalyticsCard("Total Riwayat Diagnosa", totalRiwayat), gbc);

            // Tambahkan analyticsPanel ke contentPanel
            contentPanel.add(analyticsPanel, BorderLayout.CENTER);

            // Refresh contentPanel
            contentPanel.revalidate();
            contentPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Helper method to create a "card" panel for analytics
    private JPanel createAnalyticsCard(String title, int count) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBackground(new Color(240, 248, 255)); // Light modern background
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1), // Outer border
                BorderFactory.createEmptyBorder(15, 15, 15, 15))); // Padding inside the panel
        cardPanel.setPreferredSize(new Dimension(220, 150));

        // Title Label
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(70, 130, 180)); // Steel Blue Title

        // Count Label
        JLabel lblCount = new JLabel(String.valueOf(count), SwingConstants.CENTER);
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblCount.setForeground(new Color(25, 25, 112)); // Midnight Blue for Numbers

        cardPanel.add(lblTitle, BorderLayout.NORTH);
        cardPanel.add(lblCount, BorderLayout.CENTER);

        return cardPanel;
    }


    // Existing method to load generic table data
    private void loadTableData(String query, JTable table, String header) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            // Styling JTable
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.setRowHeight(25);
            table.setGridColor(new Color(230, 230, 230)); // Grid color

            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
            tableHeader.setBackground(new Color(41, 128, 185));
            tableHeader.setForeground(Color.WHITE);
            tableHeader.setOpaque(true);

            // Alternating row colors
            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                               boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (!isSelected) {
                        c.setBackground(row % 2 == 0 ? new Color(245, 245, 250) : Color.WHITE); // Alternating colors
                    } else {
                        c.setBackground(new Color(174, 214, 241)); // Selected row color
                    }
                    return c;
                }
            });

            try (Statement stmt = databaseConnector.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Add column names
                for (int i = 1; i <= columnCount; i++) {
                    model.addColumn(metaData.getColumnName(i));
                }

                // Add row data
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    model.addRow(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createModernTableLayout(JPanel contentPanel, JTable table, String title) {
        contentPanel.removeAll();

        // Title Header
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(41, 128, 185));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Table Container
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // Main Panel for Layout
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the table
        tablePanel.add(lblTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

}
