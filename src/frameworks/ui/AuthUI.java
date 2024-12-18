package frameworks.ui;

import entities.User;
import frameworks.DatabaseConnector;
import frameworks.SessionManager;
import static frameworks.ui.UIComponents.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AuthUI {
    private DatabaseConnector databaseConnector;
    private UserDashboardUI userDashboard;
    private AdminDashboardUI adminDashboard;
    private TeknisiDashboardUI teknisiDashboard;
    private UIComponents uiComponents;

    // Colors
    private static final Color PRIMARY_BLUE = new Color(42, 52, 94);
    private static final Color SECONDARY_BLUE = new Color(52, 152, 219);
    private static final Color LIGHT_BLUE = new Color(230, 240, 255);
    private static final Color BACKGROUND_WHITE = Color.WHITE;

    public AuthUI(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.userDashboard = new UserDashboardUI(databaseConnector);
        this.adminDashboard = new AdminDashboardUI(databaseConnector, userDashboard);
        this.teknisiDashboard = new TeknisiDashboardUI(databaseConnector);
        this.uiComponents = new UIComponents();

    }

    public void showLoginPage() {
        JFrame frame = new JFrame("Expert System Login");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_WHITE);

        // Left Panel (Dark Blue Sidebar)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(PRIMARY_BLUE);
        leftPanel.setPreferredSize(new Dimension(300, 600));

        // Welcome Text in Left Panel
        JLabel welcomeLabel = new JLabel("Welcome Back", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subWelcomeLabel = new JLabel("Login to continue", SwingConstants.CENTER);
        subWelcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subWelcomeLabel.setForeground(new Color(200, 200, 200));
        subWelcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(subWelcomeLabel);
        leftPanel.add(Box.createVerticalGlue());

        // Right Panel (Login Form)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(BACKGROUND_WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Login Form Title
        JLabel formTitle = new JLabel("User Login", SwingConstants.LEFT);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        formTitle.setForeground(PRIMARY_BLUE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        rightPanel.add(formTitle, gbc);

        // Username Field
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 1; gbc.gridwidth = 1;
        rightPanel.add(lblUsername, gbc);

        JTextField txtUsername = createStyledTextField();
        gbc.gridy = 2;
        rightPanel.add(txtUsername, gbc);

        // Password Field
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 3;
        rightPanel.add(lblPassword, gbc);

        JPasswordField txtPassword = createStyledPasswordField();
        gbc.gridy = 4;
        rightPanel.add(txtPassword, gbc);

        // Login Button
        JButton btnLogin = uiComponents.createPrimaryButton("Login");
        gbc.gridy = 5; gbc.insets = new Insets(20, 30, 10, 30);
        rightPanel.add(btnLogin, gbc);

        // Register Link
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setBackground(BACKGROUND_WHITE);
        JLabel lblRegister = createHyperlinkLabel("Don't have an account? Register here");
        linkPanel.add(lblRegister);
        gbc.gridy = 6;
        rightPanel.add(linkPanel, gbc);

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        btnLogin.addActionListener(e -> handleLogin(frame, txtUsername.getText(),
                new String(txtPassword.getPassword())));

        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                showRegisterPage();
            }
        });
    }

    public void showRegisterPage() {
        JFrame frame = new JFrame("Expert System Registration");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_WHITE);

        // Left Panel (Dark Blue Sidebar)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(PRIMARY_BLUE);
        leftPanel.setPreferredSize(new Dimension(300, 600));

        JLabel welcomeLabel = new JLabel("Create Account", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subWelcomeLabel = new JLabel("Join our expert system", SwingConstants.CENTER);
        subWelcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subWelcomeLabel.setForeground(new Color(200, 200, 200));
        subWelcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(subWelcomeLabel);
        leftPanel.add(Box.createVerticalGlue());

        // Right Panel (Registration Form)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(BACKGROUND_WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Registration Form Title
        JLabel formTitle = new JLabel("Registration Form", SwingConstants.LEFT);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        formTitle.setForeground(PRIMARY_BLUE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        rightPanel.add(formTitle, gbc);

        // Full Name Field
        JLabel lblFullName = new JLabel("Full Name");
        lblFullName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 1; gbc.gridwidth = 1;
        rightPanel.add(lblFullName, gbc);

        JTextField txtFullName = createStyledTextField();
        gbc.gridy = 2;
        rightPanel.add(txtFullName, gbc);

        // Username Field
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 3;
        rightPanel.add(lblUsername, gbc);

        JTextField txtUsername = createStyledTextField();
        gbc.gridy = 4;
        rightPanel.add(txtUsername, gbc);

        // Password Field
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 5;
        rightPanel.add(lblPassword, gbc);

        JPasswordField txtPassword = createStyledPasswordField();
        gbc.gridy = 6;
        rightPanel.add(txtPassword, gbc);

        // Register Button
        JButton btnRegister = uiComponents.createPrimaryButton("Register");
        gbc.gridy = 7; gbc.insets = new Insets(20, 30, 10, 30);
        rightPanel.add(btnRegister, gbc);

        // Login Link
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setBackground(BACKGROUND_WHITE);
        JLabel lblLogin = createHyperlinkLabel("Already have an account? Login here");
        linkPanel.add(lblLogin);
        gbc.gridy = 8;
        rightPanel.add(linkPanel, gbc);

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        btnRegister.addActionListener(e -> handleRegistration(frame,
                txtFullName.getText(), txtUsername.getText(),
                new String(txtPassword.getPassword())));

        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                showLoginPage();
            }
        });
    }

    // Helper Methods
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(300, 35));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(300, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return passwordField;
    }

//    private JButton createPrimaryButton(String text) {
//        JButton button = new JButton(text);
//        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
//        button.setForeground(Color.WHITE);
//        button.setBackground(PRIMARY_BLUE); // Mulai dengan SECONDARY_BLUE
//        button.setPreferredSize(new Dimension(300, 40));
//        button.setMaximumSize(new Dimension(300, 40)); // Tambahkan maximum size
//        button.setMinimumSize(new Dimension(300, 40)); // Tambahkan minimum size
//        button.setBorderPainted(false); // Hilangkan border default
//        button.setOpaque(true); // Pastikan button opaque
//        button.setFocusPainted(false);
//        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//        // Hover effect
//        button.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                button.setBackground(PRIMARY_BLUE); // Gunakan warna lebih gelap saat hover
//            }
//
//        });
//
//        return button;
//    }

    private JLabel createHyperlinkLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(SECONDARY_BLUE);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return label;
    }

    private void handleLogin(JFrame frame, String username, String password) {
        try {
            User user = databaseConnector.verifikasiLogin(username, password);
            if (user != null) {
                SessionManager.startSession(user.getId(), user.getUsername(), user.getRole());
                frame.dispose();

                switch (user.getRole()) {
                    case "admin":
                        adminDashboard.show(user.getNama());
                        break;
                    case "user":
                        userDashboard.show(user.getNama());
                        break;
                    case "teknisi":
                        teknisiDashboard.show(user.getNama());
                        break;
                    default:
                        JOptionPane.showMessageDialog(frame,
                                "Unknown user role!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            } else {
                showError(frame, "Invalid credentials!");
            }
        } catch (SQLException ex) {
            showError(frame, "Database error occurred!");
        }
    }

    private void handleRegistration(JFrame frame, String fullName,
                                    String username, String password) {
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showError(frame, "Please fill all fields!");
            return;
        }

        try {
            boolean success = databaseConnector.registerUser(username, password, fullName);
            if (success) {
                JOptionPane.showMessageDialog(frame,
                        "Account created successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                showLoginPage();
            } else {
                showError(frame, "Username already exists!");
            }
        } catch (SQLException ex) {
            showError(frame, "Database error occurred!");
        }
    }

    private void showError(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}