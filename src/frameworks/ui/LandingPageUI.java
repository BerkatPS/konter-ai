package frameworks.ui;

import frameworks.DatabaseConnector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LandingPageUI {
    private static final Color PRIMARY_BLUE = new Color(42, 52, 94);
    private static final Color SECONDARY_BLUE = new Color(52, 152, 219);
    private static final Color LIGHT_BLUE = new Color(230, 240, 255);
    private static final Color BACKGROUND_WHITE = Color.WHITE;
    private static final Color TEXT_DARK = new Color(30, 41, 59);
    private static final Color TEXT_GRAY = new Color(71, 85, 105);

    private DatabaseConnector databaseConnector;
    private AuthUI authUI;
    private JFrame frame;

    public LandingPageUI(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        this.authUI = new AuthUI(databaseConnector);
    }

    public void show() {
        frame = new JFrame("ElectroExpert AI - Sistem Pakar Elektronik");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(800, 600));

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_WHITE);

        // Header
        JPanel headerPanel = createHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel with ScrollPane
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND_WHITE);

        // Add all sections
        contentPanel.add(createHeroSection());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(createFeaturesSection());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(createHowItWorksSection());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(createBenefitsSection());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(createCallToActionSection());

        // Wrap content in ScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = createFooter();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND_WHITE);
        header.setBorder(new EmptyBorder(15, 30, 15, 30));

        // Logo and Brand
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(BACKGROUND_WHITE);

        JLabel logoLabel = new JLabel("⚡ ElectroExpert AI");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoLabel.setForeground(PRIMARY_BLUE);
        logoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Refresh landing page
                frame.dispose();
                show();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                logoLabel.setForeground(SECONDARY_BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                logoLabel.setForeground(PRIMARY_BLUE);
            }
        });

        leftPanel.add(logoLabel);

        // Navigation Buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(BACKGROUND_WHITE);

        JButton registerButton = createHeaderButton("Register", false);



        registerButton.addActionListener(e -> {
            frame.dispose();
            authUI.showRegisterPage();
        });

        rightPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        rightPanel.add(registerButton);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createHeroSection() {
        JPanel heroPanel = new JPanel(new BorderLayout());
        heroPanel.setBackground(LIGHT_BLUE);
        heroPanel.setBorder(new EmptyBorder(80, 100, 80, 100));
        heroPanel.setMaximumSize(new Dimension(1200, 500));
        heroPanel.setPreferredSize(new Dimension(1200, 500));

        JPanel textContent = new JPanel();
        textContent.setLayout(new BoxLayout(textContent, BoxLayout.Y_AXIS));
        textContent.setBackground(LIGHT_BLUE);
        textContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Main Title with multi-line support
        JLabel mainTitle = new JLabel("<html><div style='text-align: center;'>Diagnosa Masalah<br>Elektronik dengan AI</div></html>");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 40));
        mainTitle.setForeground(PRIMARY_BLUE);
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle with multi-line support
        JLabel subtitle = new JLabel("<html><div style='text-align: center;'>Solusi cepat dan akurat untuk<br>masalah elektronik Anda</div></html>");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subtitle.setForeground(TEXT_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Get Started Button
        JButton getStartedBtn = createPrimaryButton("Mulai Sekarang");
        getStartedBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStartedBtn.addActionListener(e -> {
            frame.dispose();
            authUI.showRegisterPage();
        });

        textContent.add(mainTitle);
        textContent.add(Box.createRigidArea(new Dimension(0, 20)));
        textContent.add(subtitle);
        textContent.add(Box.createRigidArea(new Dimension(0, 30)));
        textContent.add(getStartedBtn);

        // Center the content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(LIGHT_BLUE);
        centerPanel.add(textContent);

        heroPanel.add(centerPanel, BorderLayout.CENTER);

        return heroPanel;
    }

    private JPanel createFeaturesSection() {
        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setBackground(BACKGROUND_WHITE);
        featuresPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
        featuresPanel.setMaximumSize(new Dimension(1200, 600));
        featuresPanel.setPreferredSize(new Dimension(1200, 600));

        JLabel sectionTitle = createSectionTitle("Fitur Utama");
        featuresPanel.add(sectionTitle);
        featuresPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Features Grid with hover effects
        JPanel featuresGrid = new JPanel(new GridLayout(2, 2, 30, 30));
        featuresGrid.setBackground(BACKGROUND_WHITE);

        featuresGrid.add(createFeatureCard("🔍", "Diagnosa Cepat",
                "Analisis masalah elektronik dalam hitungan menit dengan AI canggih"));
        featuresGrid.add(createFeatureCard("💡", "Solusi Akurat",
                "Rekomendasi solusi berdasarkan database pengetahuan yang komprehensif"));
        featuresGrid.add(createFeatureCard("👨‍💼", "Kontak Teknisi",
                "Terhubung langsung dengan teknisi berpengalaman jika diperlukan"));
        featuresGrid.add(createFeatureCard("📱", "Mudah Digunakan",
                "Interface yang user-friendly dan dapat diakses kapan saja"));

        featuresPanel.add(featuresGrid);
        return featuresPanel;
    }

    private JPanel createHowItWorksSection() {
        JPanel howItWorksPanel = new JPanel();
        howItWorksPanel.setLayout(new BoxLayout(howItWorksPanel, BoxLayout.Y_AXIS));
        howItWorksPanel.setBackground(LIGHT_BLUE);
        howItWorksPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
        howItWorksPanel.setMaximumSize(new Dimension(1200, 400));
        howItWorksPanel.setPreferredSize(new Dimension(1200, 400));

        JLabel sectionTitle = createSectionTitle("Cara Kerja");
        howItWorksPanel.add(sectionTitle);
        howItWorksPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Steps with hover effects
        JPanel stepsPanel = new JPanel(new GridLayout(1, 4, 30, 0));
        stepsPanel.setBackground(LIGHT_BLUE);

        stepsPanel.add(createStepCard("1", "Deskripsi Masalah",
                "Jelaskan gejala masalah elektronik yang Anda alami"));
        stepsPanel.add(createStepCard("2", "Analisis AI",
                "Sistem AI kami menganalisis masalah berdasarkan database"));
        stepsPanel.add(createStepCard("3", "Solusi",
                "Dapatkan rekomendasi solusi yang dapat diterapkan"));
        stepsPanel.add(createStepCard("4", "Bantuan Teknisi",
                "Hubungkan dengan teknisi jika diperlukan"));

        howItWorksPanel.add(stepsPanel);
        return howItWorksPanel;
    }

    private JPanel createBenefitsSection() {
        JPanel benefitsPanel = new JPanel();
        benefitsPanel.setLayout(new BoxLayout(benefitsPanel, BoxLayout.Y_AXIS));
        benefitsPanel.setBackground(BACKGROUND_WHITE);
        benefitsPanel.setBorder(new EmptyBorder(50, 100, 50, 100));
        benefitsPanel.setMaximumSize(new Dimension(1200, 400));
        benefitsPanel.setPreferredSize(new Dimension(1200, 400));

        JLabel sectionTitle = createSectionTitle("Keuntungan");
        benefitsPanel.add(sectionTitle);
        benefitsPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Benefits Grid with hover effects
        JPanel benefitsGrid = new JPanel(new GridLayout(1, 3, 30, 0));
        benefitsGrid.setBackground(BACKGROUND_WHITE);

        benefitsGrid.add(createBenefitCard("⏱️", "Hemat Waktu",
                "Diagnosa cepat tanpa perlu menunggu teknisi"));
        benefitsGrid.add(createBenefitCard("💰", "Hemat Biaya",
                "Hindari biaya konsultasi yang tidak perlu"));
        benefitsGrid.add(createBenefitCard("✅", "Solusi Tepat",
                "Dapatkan solusi yang sudah teruji dan terverifikasi"));

        benefitsPanel.add(benefitsGrid);
        return benefitsPanel;
    }

    private JPanel createCallToActionSection() {
        JPanel ctaPanel = new JPanel();
        ctaPanel.setLayout(new BoxLayout(ctaPanel, BoxLayout.Y_AXIS));
        ctaPanel.setBackground(PRIMARY_BLUE);
        ctaPanel.setBorder(new EmptyBorder(60, 100, 60, 100));
        ctaPanel.setMaximumSize(new Dimension(1200, 300));
        ctaPanel.setPreferredSize(new Dimension(1200, 300));

        // CTA Title with multi-line support
        JLabel ctaTitle = new JLabel("<html><div style='text-align: center;'>Siap Menyelesaikan<br>Masalah Elektronik Anda?</div></html>");
        ctaTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        ctaTitle.setForeground(Color.WHITE);
        ctaTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // CTA Subtitle with multi-line support
        JLabel ctaSubtitle = new JLabel("<html><div style='text-align: center;'>Bergabung sekarang dan dapatkan solusi cepat<br>untuk perangkat elektronik Anda</div></html>");
        ctaSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ctaSubtitle.setForeground(new Color(200, 200, 200));
        ctaSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // CTA Button with hover effect
        JButton ctaButton = createPrimaryButton("Daftar Gratis");
        ctaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ctaButton.addActionListener(e -> {
            frame.dispose();
            authUI.showRegisterPage();
        });

        ctaPanel.add(ctaTitle);
        ctaPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ctaPanel.add(ctaSubtitle);
        ctaPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        ctaPanel.add(ctaButton);

        return ctaPanel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(TEXT_DARK);
        footer.setBorder(new EmptyBorder(30, 100, 30, 100));

        // Footer content panel
        JPanel footerContent = new JPanel(new GridLayout(1, 3, 20, 0));
        footerContent.setBackground(TEXT_DARK);

        // Copyright
        JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        copyrightPanel.setBackground(TEXT_DARK);
        JLabel copyright = new JLabel("© 2024 ElectroExpert AI");
        copyright.setForeground(Color.WHITE);
        copyright.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        copyrightPanel.add(copyright);

        // Links
        JPanel linksPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linksPanel.setBackground(TEXT_DARK);
        JLabel privacyLink = createFooterLink("Privasi");
        JLabel termsLink = createFooterLink("Ketentuan");
        JLabel contactLink = createFooterLink("Kontak");
        linksPanel.add(privacyLink);
        linksPanel.add(new JLabel(" • ")).setForeground(Color.WHITE);
        linksPanel.add(termsLink);
        linksPanel.add(new JLabel(" • ")).setForeground(Color.WHITE);
        linksPanel.add(contactLink);

        // Additional info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setBackground(TEXT_DARK);
        JLabel infoLabel = new JLabel("All rights reserved.");
        infoLabel.setForeground(new Color(156, 163, 175));
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoPanel.add(infoLabel);

        footerContent.add(copyrightPanel);
        footerContent.add(linksPanel);
        footerContent.add(infoPanel);

        footer.add(footerContent, BorderLayout.CENTER);

        return footer;
    }

    // Helper Methods
    private JLabel createFooterLink(String text) {
        JLabel link = new JLabel(text);
        link.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        link.setForeground(Color.WHITE);
        link.setCursor(new Cursor(Cursor.HAND_CURSOR));

        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                link.setForeground(SECONDARY_BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                link.setForeground(Color.WHITE);
            }
        });

        return link;
    }

    private JButton createHeaderButton(String text, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(isPrimary ? BACKGROUND_WHITE : PRIMARY_BLUE);
        button.setBackground(isPrimary ? PRIMARY_BLUE : BACKGROUND_WHITE);
        button.setBorder(BorderFactory.createLineBorder(PRIMARY_BLUE, 2));
        button.setPreferredSize(new Dimension(100, 35));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isPrimary) {
                    button.setBackground(SECONDARY_BLUE);
                } else {
                    button.setBackground(LIGHT_BLUE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(isPrimary ? PRIMARY_BLUE : BACKGROUND_WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(isPrimary ? PRIMARY_BLUE.darker() : LIGHT_BLUE.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(isPrimary ? PRIMARY_BLUE : BACKGROUND_WHITE);
            }
        });

        return button;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_BLUE);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover and click effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(SECONDARY_BLUE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(PRIMARY_BLUE.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(SECONDARY_BLUE);
            }
        });

        return button;
    }

    private JLabel createSectionTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        return title;
    }

    private JPanel createFeatureCard(String icon, String title, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BACKGROUND_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Description with HTML formatting
        JLabel descLabel = new JLabel("<html><div style='text-align: center; width: 200px;'>" +
                description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 250, 252));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(SECONDARY_BLUE, 1),
                        BorderFactory.createEmptyBorder(30, 20, 30, 20)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(BACKGROUND_WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                        BorderFactory.createEmptyBorder(30, 20, 30, 20)
                ));
            }
        });

        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(descLabel);

        return card;
    }

    private JPanel createStepCard(String number, String title, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(LIGHT_BLUE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        // Number Badge
        JPanel numberBadge = new JPanel();
        numberBadge.setBackground(PRIMARY_BLUE);
        numberBadge.setPreferredSize(new Dimension(40, 40));
        numberBadge.setMaximumSize(new Dimension(40, 40));
        numberBadge.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        numberLabel.setForeground(Color.WHITE);
        numberBadge.add(numberLabel);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Description with HTML formatting
        JLabel descLabel = new JLabel("<html><div style='text-align: center; width: 180px;'>" +
                description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(LIGHT_BLUE.brighter());
                numberBadge.setBackground(SECONDARY_BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(LIGHT_BLUE);
                numberBadge.setBackground(PRIMARY_BLUE);
            }
        });

        card.add(numberBadge);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(descLabel);

        return card;
    }

    private JPanel createBenefitCard(String icon, String title, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BACKGROUND_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        // Icon with circular background
        JPanel iconContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(LIGHT_BLUE);
                g2d.fillOval(0, 0, 60, 60);
                g2d.dispose();
            }
        };
        iconContainer.setOpaque(false);
        iconContainer.setPreferredSize(new Dimension(60, 60));
        iconContainer.setMaximumSize(new Dimension(60, 60));
        iconContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconContainer.add(iconLabel);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Description with HTML formatting
        JLabel descLabel = new JLabel("<html><div style='text-align: center; width: 200px;'>" +
                description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 250, 252));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(SECONDARY_BLUE, 1),
                        BorderFactory.createEmptyBorder(30, 20, 30, 20)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(BACKGROUND_WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                        BorderFactory.createEmptyBorder(30, 20, 30, 20)
                ));
            }
        });

        card.add(iconContainer);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(descLabel);

        return card;
    }
}