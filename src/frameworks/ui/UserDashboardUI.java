package frameworks.ui;

import controller.DiagnosisController;
import entities.Gejala;
import entities.Pertanyaan;
import entities.RiwayatDiagnosa;
import entities.Solusi;
import frameworks.DatabaseConnector;
import frameworks.SessionManager;
import usecases.DiagnoseUseCase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static frameworks.ui.UIComponents.*;

public class UserDashboardUI {

    private static final Color PRIMARY_DARK = new Color(42, 52, 94);    // Dark blue for primary elements
    private static final Color PRIMARY_LIGHT = new Color(52, 152, 219); // Light blue for accents
    private static final Color BACKGROUND = new Color(248, 250, 252);   // Light gray for background
    private static final Color CARD_BG = Color.WHITE;                   // White for cards
    private static final Color TEXT_PRIMARY = new Color(30, 41, 59);    // Dark gray for primary text
    private static final Color TEXT_SECONDARY = new Color(71, 85, 105); // Medium gray for secondary text
    private static final Color ACCENT_SUCCESS = new Color(34, 197, 94); // Green for success states
    private static final Color HOVER_COLOR = new Color(30, 41, 59);     // Darker blue for hover states

    private DatabaseConnector databaseConnector;
    private DiagnoseUseCase diagnoseUseCase;
    private DiagnosisController diagnosisController;
    private UIComponents uiComponents;


    public UserDashboardUI(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.diagnoseUseCase = new DiagnoseUseCase(databaseConnector);
        this.diagnosisController = new DiagnosisController(diagnoseUseCase, databaseConnector);
        this.uiComponents = new UIComponents();
    }

    public void show(String username) {
        JFrame frame = new JFrame("User Dashboard");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel with modern background
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Modern Sidebar
        JPanel sidebar = createModernSidebar(username, frame);

        // Content Panel with shadow effect
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Modern Welcome Header
        JPanel headerPanel = createModernHeader(username);

        // Stats Cards Container
        JPanel statsContainer = new JPanel(new GridLayout(1, 3, 20, 0));
        statsContainer.setOpaque(false);

        // Modern stat cards
        statsContainer.add(createModernStatCard("Total Diagnoses", "23", "📊"));
        statsContainer.add(createModernStatCard("Pending Consultations", "5", "🔄"));
        statsContainer.add(createModernStatCard("Success Rate", "92%", "✨"));

        // Recent Activity Panel
        JPanel activityPanel = createRecentActivityPanel();

        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(statsContainer, BorderLayout.NORTH);
        centerPanel.add(activityPanel, BorderLayout.CENTER);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

    }

    public void showDiagnosisPage(String username) {
        JFrame frame = new JFrame("Diagnosis");
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
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_DARK);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel headerLabel = new JLabel("Diagnosis Process", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Question Card
        JPanel questionCard = new JPanel(new BorderLayout(20, 20));
        questionCard.setBackground(CARD_BG);
        questionCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // Question Number Label
        JLabel questionNumberLabel = new JLabel("Question 1 of X", SwingConstants.LEFT);
        questionNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        questionNumberLabel.setForeground(TEXT_SECONDARY);
        questionCard.add(questionNumberLabel, BorderLayout.NORTH);

        // Question Text
        JLabel lblQuestion = new JLabel("Loading questions...", SwingConstants.CENTER);
        lblQuestion.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblQuestion.setForeground(TEXT_PRIMARY);
        questionCard.add(lblQuestion, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton btnYes = uiComponents.createPrimaryButton("Yes");
        JButton btnNo = uiComponents.createPrimaryButton("No");

        buttonPanel.add(btnYes);
        buttonPanel.add(btnNo);
        questionCard.add(buttonPanel, BorderLayout.SOUTH);

        // Back Button at the bottom
        JButton btnBack = uiComponents.createTextButton("← Back to Dashboard");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(BACKGROUND);
        bottomPanel.add(btnBack);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(questionCard, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
        btnBack.addActionListener(e -> {
            frame.dispose();
            show(username); // Return to Technician Dashboard
        });

        // Load Questions in Background
        loadQuestions(lblQuestion, questionNumberLabel, btnYes, btnNo, frame, username);
    }

    private void showDiagnosisResult(List<Pertanyaan> pertanyaanList, List<Gejala> gejalaList,
                                     boolean[] answers, String username) {

        JFrame frame = new JFrame("Diagnosis Result");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Sidebar
        JPanel sidebar = createModernSidebar(username, frame);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_DARK);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel headerLabel = new JLabel("Diagnosis Results", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Result Card
        JPanel resultCard = new JPanel(new BorderLayout(20, 20));
        resultCard.setBackground(CARD_BG);
        resultCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // Process results
        ProcessedResults results = processResults(pertanyaanList, gejalaList, answers);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        summaryPanel.setOpaque(false);
        summaryPanel.add(createSummaryCard("Identifikasi Gejala",
                String.valueOf(results.symptomCount), "🔍"));
        summaryPanel.add(createSummaryCard("Certainty Factor",
                String.format("%.2f%%", results.averageCF), "📊"));
        summaryPanel.add(createSummaryCard("Solusi Ditemukan",
                String.valueOf(results.solutionCount), "💡"));

        // Detailed Results
        JTextArea detailsArea = createStyledTextArea(results.detailedResults);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBorder(null);

        resultCard.add(summaryPanel, BorderLayout.NORTH);
        resultCard.add(scrollPane, BorderLayout.CENTER);

        // Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        actionPanel.setBackground(BACKGROUND);

        JButton btnBack =  uiComponents.createPrimaryButton("Back to Dashboard");
        JButton btnPrint = uiComponents.createSecondaryButton("Print Results");

        actionPanel.add(btnBack);
        actionPanel.add(btnPrint);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(resultCard, BorderLayout.CENTER);
        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Event Listeners
        btnBack.addActionListener(e -> {
            frame.dispose();
            show(username);
        });

        saveResults(results, username);
    }



    public void showRiwayatPage(String username) {
        JFrame frame = new JFrame("Diagnosis History");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

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

        JLabel headerLabel = new JLabel("Diagnosis History", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);

        // Search Field
        JTextField searchField = createSearchField("Search history...");
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(PRIMARY_DARK);
        searchPanel.add(searchField, BorderLayout.CENTER);

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // History List Panel
        JPanel historyListPanel = new JPanel();
        historyListPanel.setLayout(new BoxLayout(historyListPanel, BoxLayout.Y_AXIS));
        historyListPanel.setBackground(BACKGROUND);

        // Scrollable area for the history list
        JScrollPane scrollPane = new JScrollPane(historyListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);

        // Load history data
        new Thread(() -> {
            try {
                // Get user ID from username
                int userId = SessionManager.getLoggedInUserId();
                if (userId == -1) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(frame, "Error: User not found", "Error", JOptionPane.ERROR_MESSAGE));
                    return;
                }

                // Get history data for the user
                List<RiwayatDiagnosa> riwayatList = databaseConnector.getRiwayatByUserId(userId);

                SwingUtilities.invokeLater(() -> {
                    historyListPanel.removeAll();

                    if (riwayatList.isEmpty()) {
                        JLabel emptyLabel = new JLabel("No diagnosis history available");
                        emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                        emptyLabel.setForeground(TEXT_SECONDARY);
                        emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                        historyListPanel.add(emptyLabel);
                    } else {
                        for (RiwayatDiagnosa riwayat : riwayatList) {
                            JPanel historyCard = createHistoryCard(riwayat, frame, username);
                            historyListPanel.add(historyCard);
                            historyListPanel.add(Box.createVerticalStrut(10));
                        }
                    }

                    historyListPanel.revalidate();
                    historyListPanel.repaint();
                });

            } catch (SQLException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(frame, "Error loading history: " + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add search functionality
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void insertUpdate(DocumentEvent e) { search(); }

            public void search() {
                String searchTerm = searchField.getText().toLowerCase();
                for (Component component : historyListPanel.getComponents()) {
                    if (component instanceof JPanel) {
                        JPanel card = (JPanel) component;
                        boolean visible = false;

                        for (Component cardComponent : card.getComponents()) {
                            if (cardComponent instanceof JLabel) {
                                JLabel label = (JLabel) cardComponent;
                                if (label.getText().toLowerCase().contains(searchTerm)) {
                                    visible = true;
                                    break;
                                }
                            }
                        }

                        card.setVisible(visible);
                    }
                }
                historyListPanel.revalidate();
                historyListPanel.repaint();
            }
        });
    }

    private JPanel createHistoryCard(RiwayatDiagnosa riwayat, JFrame parentFrame, String username) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Date and Time
        JLabel dateLabel = new JLabel("🗓 " + new SimpleDateFormat("dd MMM yyyy HH:mm")
                .format(riwayat.getCreatedAt()));
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dateLabel.setForeground(TEXT_SECONDARY);

        // Result Summary
        String summaryText = getSummaryFromDiagnosis(riwayat.getHasilDiagnosa());
        JLabel summaryLabel = new JLabel(summaryText);
        summaryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        summaryLabel.setForeground(TEXT_PRIMARY);

        // View Details Button
            JButton detailsButton = uiComponents.createSecondaryButton("View Details");
        detailsButton.addActionListener(e -> showDetailedResults(riwayat, parentFrame, username));

        // Left Panel for Date and Summary
        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        leftPanel.setOpaque(false);
        leftPanel.add(dateLabel);
        leftPanel.add(summaryLabel);

        card.add(leftPanel, BorderLayout.CENTER);
        card.add(detailsButton, BorderLayout.EAST);

        return card;
    }

    private String getSummaryFromDiagnosis(String fullDiagnosis) {
        String[] lines = fullDiagnosis.split("\n");
        for (String line : lines) {
            if (line.contains("Rata-rata Certainty Factor:")) {
                return line.trim();
            }
        }
        return "Diagnosis details available";
    }

    private void showDetailedResults(RiwayatDiagnosa riwayat, JFrame parentFrame, String username) {
        // Create ProcessedResults object from RiwayatDiagnosa
        ProcessedResults results = new ProcessedResults();
        results.detailedResults = riwayat.getHasilDiagnosa() + "\n\nSolusi:\n" + riwayat.getSolusi();

        // Parse the diagnosis result to get symptom count and CF
        String[] lines = riwayat.getHasilDiagnosa().split("\n");
        int symptomCount = 0;
        double cf = 0.0;

        for (String line : lines) {
            if (line.startsWith("-")) symptomCount++;
            if (line.contains("Rata-rata Certainty Factor:")) {
                try {
                    cf = Double.parseDouble(line.split(":")[1].trim());
                } catch (NumberFormatException e) {
                    cf = 0.0;
                }
            }
        }

        results.symptomCount = symptomCount;
        results.averageCF = cf;
        results.solutionCount = riwayat.getSolusi().split("\n").length;

        // Close the parent frame and show the detailed results
        parentFrame.dispose();
        showDiagnosisResult(new ArrayList<>(), new ArrayList<>(), new boolean[0], username);
    }

    public void showUserConsultationPage(String username) {
        try {
            // Get list of available technicians
            List<String> technicians = databaseConnector.getTechnicianList();

            if (technicians.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No technicians are available at the moment. Please try again later.",
                        "No Technicians Available",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Show technician selection dialog
            String selectedTechnician = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose a technician to start consultation:",
                    "Select Technician",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    technicians.toArray(),
                    technicians.get(0)
            );

            if (selectedTechnician != null) {
                // Get user ID from session
                int userId = SessionManager.getLoggedInUserId();
                if (userId == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Session expired. Please login again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    new AuthUI(databaseConnector).showLoginPage();
                    return;
                }

                // Get technician ID
                int teknisiId = databaseConnector.getUserIdByUsername(selectedTechnician);
                if (teknisiId == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Selected technician not found. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create chat room
                int chatRoomId = databaseConnector.createChatRoom(userId, teknisiId);

                if (chatRoomId != -1) {
                    // Open chat window
                    ChatWindow chatWindow = new ChatWindow(databaseConnector, username, "user");
                    chatWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    chatWindow.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Failed to create chat room. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    private void loadQuestions(JLabel lblQuestion, JLabel questionNumberLabel, JButton btnYes, JButton btnNo, JFrame frame, String username) {
        new Thread(() -> {
            try {
                List<Pertanyaan> pertanyaanList = databaseConnector.getAllPertanyaan();
                List<Gejala> gejalaList = databaseConnector.getAllGejala();

                if (pertanyaanList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        lblQuestion.setText("No questions available.");
                        btnYes.setEnabled(false);
                        btnNo.setEnabled(false);
                    });
                    return;
                }

                int[] index = {0};
                boolean[] answers = new boolean[pertanyaanList.size()];

                // Update initial question
                SwingUtilities.invokeLater(() -> {
                    lblQuestion.setText(pertanyaanList.get(0).getPertanyaan());
                    questionNumberLabel.setText(String.format("Question %d of %d", 1, pertanyaanList.size()));
                });

                // Button Actions
                btnYes.addActionListener(e -> {
                    answers[index[0]] = true;
                    nextQuestion(pertanyaanList, gejalaList, lblQuestion, questionNumberLabel, index, frame, answers, username);
                });

                btnNo.addActionListener(e -> {
                    answers[index[0]] = false;
                    nextQuestion(pertanyaanList, gejalaList, lblQuestion, questionNumberLabel, index, frame, answers, username);
                });

            } catch (SQLException e) {
                SwingUtilities.invokeLater(() -> {
                    lblQuestion.setText("Failed to load questions.");
                    btnYes.setEnabled(false);
                    btnNo.setEnabled(false);
                });
                e.printStackTrace();
            }
        }).start();
    }

    private void nextQuestion(List<Pertanyaan> pertanyaanList, List<Gejala> gejalaList,
                              JLabel lblQuestion, JLabel questionNumberLabel, int[] index, JFrame frame,
                              boolean[] answers, String username) {

        index[0]++;
        if (index[0] < pertanyaanList.size()) {
            SwingUtilities.invokeLater(() -> {
                lblQuestion.setText(pertanyaanList.get(index[0]).getPertanyaan());
                questionNumberLabel.setText(String.format("Question %d of %d",
                        index[0] + 1, pertanyaanList.size()));
            });
        } else {
            frame.dispose();
            showDiagnosisResult(pertanyaanList, gejalaList, answers, username);
        }
    }

    private void saveResults(ProcessedResults results, String username) {
        new Thread(() -> {
            try {
                // Ambil hasil diagnosis dan solusi
                String hasilDiagnosa = formatDiagnosisResults(results);
                String solusi = formatSolutionResults(results);

                // Simpan ke database melalui use case
                boolean success = diagnoseUseCase.simpanRiwayatDiagnosa(hasilDiagnosa, solusi);

                // Notifikasi jika berhasil
                SwingUtilities.invokeLater(() -> {
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Diagnosis berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Gagal menyimpan hasil diagnosis.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }


    private String formatDiagnosisResults(ProcessedResults results) {
        StringBuilder diagnosis = new StringBuilder();

        // Add header
        diagnosis.append("=== Hasil Diagnosa ===\n\n");

        // Add summary information
        diagnosis.append(String.format("Jumlah Gejala: %d\n", results.symptomCount));
        diagnosis.append(String.format("Certainty Factor: %.2f%%\n\n", results.averageCF));

        // Add detailed symptoms
        diagnosis.append("Detail Gejala:\n");
        String[] lines = results.detailedResults.split("\n");
        for (String line : lines) {
            if (line.matches("\\d+\\..*")) { // Lines starting with numbers
                diagnosis.append(line).append("\n");
            }
        }

        return diagnosis.toString().trim();
    }

    // Update the formatSolutionResults method to match database structure
    private String formatSolutionResults(ProcessedResults results) {
        StringBuilder solutions = new StringBuilder();

        solutions.append("=== Rekomendasi Solusi ===\n\n");

        String[] lines = results.detailedResults.split("\n");
        boolean inSolutionSection = false;

        for (String line : lines) {
            if (line.contains("Rekomendasi Solusi")) {
                inSolutionSection = true;
                continue;
            }
            if (inSolutionSection && line.startsWith("•")) {
                // Remove bullet point and trim
                String cleanedSolution = line.substring(1).trim();
                solutions.append(cleanedSolution).append("\n");
            }
        }

        return solutions.toString().trim();
    }


    private ProcessedResults processResults(List<Pertanyaan> pertanyaanList,
                                            List<Gejala> gejalaList,
                                            boolean[] answers) {
        ProcessedResults results = new ProcessedResults();
        StringBuilder resultBuilder = new StringBuilder();
        StringBuilder solutionBuilder = new StringBuilder();
        double totalCF = 0;
        int count = 0;

        resultBuilder.append("Identifikasi Gejala:\n\n");

        // Process each answer
        for (int i = 0; i < answers.length; i++) {
            if (answers[i]) {
                Pertanyaan currentQuestion = pertanyaanList.get(i);
                for (Gejala gejala : gejalaList) {
                    if (gejala.getId() == currentQuestion.getGejalaId()) {
                        count++;
                        totalCF += gejala.getCertaintyFactor();
                        results.matchedGejalaIds.add(gejala.getId());

                        resultBuilder.append(count)
                                .append(". ")
                                .append(gejala.getNamaGejala())
                                .append(" (CF: ")
                                .append(String.format("%.2f", gejala.getCertaintyFactor()))
                                .append("%)\n");

                        try {
                            Solusi solusi = databaseConnector.getSolusiByGejalaId(gejala.getId());
                            if (solusi != null) {
                                solutionBuilder.append("• ")
                                        .append(solusi.getHasilSolusi())
                                        .append("\n");
                                results.solutionCount++;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // Calculate final results
        results.symptomCount = count;
        results.averageCF = count > 0 ? (totalCF / count) : 0;

        // Add solutions to the final results
        resultBuilder.append("\nRekomendasi Solusi:\n\n")
                .append(solutionBuilder.toString());

        results.detailedResults = resultBuilder.toString();
        return results;
    }

    private void loadHistoryData(DefaultListModel<String> listModel, String username) {
        new Thread(() -> {
            try {
                List<RiwayatDiagnosa> riwayatList = databaseConnector.getRiwayatByUsername(username);

                SwingUtilities.invokeLater(() -> {
                    listModel.clear();

                    if (riwayatList.isEmpty()) {
                        listModel.addElement("No diagnosis history available.");
                    } else {
                        for (RiwayatDiagnosa riwayat : riwayatList) {
                            // Format the date nicely
                            String formattedDate = String.valueOf(formatDate(riwayat.getCreatedAt()));

                            // Format the diagnosis result to show only the first line or summary
                            String summary = formatDiagnosisSummary(riwayat.getHasilDiagnosa());

                            // Add to list model with emoji and formatting
                            listModel.addElement(String.format("🗓 %s - %s",
                                    formattedDate, summary));
                        }
                    }
                });

            } catch (SQLException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    listModel.clear();
                    listModel.addElement("Failed to load history data.");
                });
            }
        }).start();
    }

    private Timestamp formatDate(Timestamp date) {
        try {
            java.sql.Timestamp timestamp = date;
            java.util.Date utilDate = timestamp;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = dateFormat.format(utilDate);
            return Timestamp.valueOf(dateString);
        } catch (Exception e) {
            return date;
        }
    }

    private String formatDiagnosisSummary(String fullDiagnosis) {
        // If the diagnosis contains a summary line, use that
        String[] lines = fullDiagnosis.split("\n");
        for (String line : lines) {
            if (line.contains("Symptoms Found:") ||
                    line.contains("Certainty Factor:")) {
                return line.trim();
            }
        }

        // Otherwise, return the first line or a truncated version
        if (lines.length > 0) {
            String firstLine = lines[0].trim();
            return firstLine.length() > 100 ?
                    firstLine.substring(0, 97) + "..." : firstLine;
        }

        return "No details available";
    }

    private JTextField createMessageField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_PRIMARY);
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(TEXT_PRIMARY);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return comboBox;
    }

    private JList<String> createStyledList(DefaultListModel<String> model) {
        JList<String> list = new JList<>(model);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        list.setBackground(CARD_BG);
        list.setForeground(TEXT_PRIMARY);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ModernListCellRenderer());
        list.setFixedCellHeight(60);
        return list;
    }

    private JPanel createSummaryCard(String title, String value, String icon) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(TEXT_PRIMARY);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(valueLabel);

        return card;
    }

    private class ModernListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {

            JPanel panel = new JPanel(new BorderLayout(10, 5));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            if (isSelected) {
                panel.setBackground(new Color(243, 244, 246));
            } else {
                panel.setBackground(CARD_BG);
            }

            String text = value.toString();

            // Extract date and content if the format matches
            if (text.startsWith("🗓")) {
                String[] parts = text.split(" - ", 2);
                if (parts.length == 2) {
                    JLabel dateLabel = new JLabel(parts[0]);
                    dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    dateLabel.setForeground(TEXT_SECONDARY);

                    JLabel contentLabel = new JLabel(parts[1]);
                    contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    contentLabel.setForeground(TEXT_PRIMARY);

                    panel.add(dateLabel, BorderLayout.NORTH);
                    panel.add(contentLabel, BorderLayout.CENTER);
                }
            } else {
                JLabel label = new JLabel(text);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setForeground(TEXT_PRIMARY);
                panel.add(label, BorderLayout.CENTER);
            }

            return panel;
        }
    }

    // Data processing helper class
    private class ProcessedResults {
        int symptomCount;
        double averageCF;
        int solutionCount;
        String detailedResults;
        List<Integer> matchedGejalaIds = new ArrayList<>(); // Tambahkan ini

    }


    private void setupChatEventListeners(JButton sendButton, JTextField messageField,
                                         JTextArea chatArea, JComboBox<String> technicianComboBox) {

        sendButton.addActionListener(e -> sendMessage(messageField, chatArea, technicianComboBox));
        messageField.addActionListener(e -> sendMessage(messageField, chatArea, technicianComboBox));
    }

    private void sendMessage(JTextField messageField, JTextArea chatArea,
                             JComboBox<String> technicianComboBox) {

        String message = messageField.getText().trim();
        String selectedTechnician = (String) technicianComboBox.getSelectedItem();

        if (!message.isEmpty() && selectedTechnician != null &&
                !"Select Technician".equals(selectedTechnician)) {

            // Add timestamp
            String timestamp = new java.text.SimpleDateFormat("HH:mm")
                    .format(new java.util.Date());

            chatArea.append(String.format("[%s] You: %s\n", timestamp, message));
            messageField.setText("");

            // Simulate response (replace with actual implementation)
            Timer timer = new Timer(1000, e -> {
                String response = String.format("[%s] %s: Thanks for your message. I'll help you shortly.\n",
                        new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date()),
                        selectedTechnician);
                chatArea.append(response);
            });
            timer.setRepeats(false);
            timer.start();
        }
    }


    // Helper UI Component Methods
    private JTextField createSearchField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JTextArea createStyledTextArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBackground(CARD_BG);
        area.setForeground(TEXT_PRIMARY);
        return area;
    }

    private JTextArea createStyledChatArea() {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBackground(CARD_BG);
        area.setForeground(TEXT_PRIMARY);
        return area;
    }

    private JPanel createModernSidebar(String username, JFrame frame) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PRIMARY_DARK);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 15));

        // User Profile Section
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        profilePanel.setOpaque(false);

        // Profile Icon (circular)
        JLabel profileIcon = new JLabel("👤");
        profileIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        profileIcon.setForeground(Color.WHITE);

        // Username Label
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);

        profilePanel.add(profileIcon);
        profilePanel.add(usernameLabel);

        // Navigation Buttons
        JButton btnDiagnosis = createModernNavButton("Start Diagnosis", "🔍");
        JButton btnRiwayat = createModernNavButton("History", "📋");
        JButton btnKonsultasi = createModernNavButton("Online Consultation", "💬");
        JButton btnLogout = createModernNavButton("Logout", "🚪");

        // Add components to sidebar
        sidebar.add(profilePanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        sidebar.add(btnDiagnosis);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnRiwayat);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnKonsultasi);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        // Add event listeners
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
            new AuthUI(databaseConnector).showLoginPage();
        });

        return sidebar;
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
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
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

    private JPanel createRecentActivityPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Header
        JLabel headerLabel = new JLabel("Recent Activity");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(TEXT_PRIMARY);

        // Activity List
        JPanel activityList = new JPanel();
        activityList.setLayout(new BoxLayout(activityList, BoxLayout.Y_AXIS));
        activityList.setOpaque(false);

        // Add some sample activities
        activityList.add(createActivityItem("Completed diagnosis", "2 hours ago", "✅"));
        activityList.add(createActivityItem("Started consultation", "Yesterday", "💬"));
        activityList.add(createActivityItem("Viewed history", "2 days ago", "📋"));

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(activityList, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActivityItem(String text, String time, String icon) {
        JPanel item = new JPanel(new BorderLayout());
        item.setOpaque(false);
        item.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        textLabel.setForeground(TEXT_PRIMARY);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(TEXT_SECONDARY);

        leftPanel.add(iconLabel);
        leftPanel.add(textLabel);

        item.add(leftPanel, BorderLayout.WEST);
        item.add(timeLabel, BorderLayout.EAST);

        return item;
    }

    private JButton createModernNavButton(String text, String icon) {
        JButton button = new JButton(icon + "  " + text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_DARK);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(250, 40));
        button.setMaximumSize(new Dimension(250, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
                button.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
            }
        });

        return button;
    }

}
