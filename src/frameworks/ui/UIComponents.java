package frameworks.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class UIComponents {
    private static final Color PRIMARY_BLUE = new Color(42, 52, 94);
    private static final Color SECONDARY_BLUE = new Color(52, 152, 219);
    private static final Color LIGHT_BLUE = new Color(230, 240, 255);
    private static final Color BACKGROUND_WHITE = Color.WHITE;

    public static JButton createSidebarButton(String text) {
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

    public static JButton createStyledButton(String text) {
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

    public static JPanel createInfoCard(String title, String value, Dimension size) {
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

    public static JPanel createAnalyticsCard(String title, int count) {
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

    public JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_BLUE); // Mulai dengan SECONDARY_BLUE
        button.setPreferredSize(new Dimension(300, 40));
        button.setMaximumSize(new Dimension(300, 40)); // Tambahkan maximum size
        button.setMinimumSize(new Dimension(300, 40)); // Tambahkan minimum size
        button.setBorderPainted(false); // Hilangkan border default
        button.setOpaque(true); // Pastikan button opaque
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_BLUE); // Gunakan warna lebih gelap saat hover
            }

        });

        return button;
    }

    public JButton createTextButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(PRIMARY_BLUE);
        button.setBackground(null);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Margin/Padding
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(PRIMARY_BLUE);
                // Optional: bisa tambahkan underline saat hover
                button.setText("<html><u>" + text + "</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(PRIMARY_BLUE);
                button.setText(text);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setForeground(PRIMARY_BLUE.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setForeground(PRIMARY_BLUE);
            }
        });

        return button;
    }

    public JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(PRIMARY_BLUE);
        button.setBackground(Color.WHITE);
        button.setPreferredSize(new Dimension(130, 40));
        button.setMaximumSize(new Dimension(130, 40));
        button.setMinimumSize(new Dimension(130, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_BLUE, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(243, 244, 246)); // Light gray on hover
                button.setForeground(PRIMARY_BLUE.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(PRIMARY_BLUE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(229, 231, 235)); // Slightly darker when pressed
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(new Color(243, 244, 246));
            }
        });

        return button;
    }



}
