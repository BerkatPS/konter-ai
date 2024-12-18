import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Bahan1 extends JFrame {

    // Komponen untuk login
    private HashMap<String, String> userDatabase;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    // Komponen untuk halaman sistem pakar
    private JTextArea txtKeluhan, txtResult, txtSolusi;
    private JButton btnDiagnosa, btnLogout;
    private HashMap<String, Double> gejalaMap;

    // Variabel untuk menyimpan nama pengguna yang sedang login
    private String UsernameAktif;

    public Bahan1() {
        // Inisialisasi database pengguna
        userDatabase = new HashMap<>();
        userDatabase.put("jocel", "1234");
        userDatabase.put("user", "password");

        // Tampilkan halaman login
        tampilkanHalamanLogin();
    }

    private void tampilkanHalamanLogin() {
        getContentPane().removeAll();
        setTitle("Login Pengguna - Sistem Pakar Smartphone");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBackground(Color.LIGHT_GRAY);

        JLabel lblTitle = new JLabel("Login Pengguna");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.BLUE);

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField(15);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtUsername.getPreferredSize().height));

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(15);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtPassword.getPreferredSize().height));

        btnLogin = new JButton("Login");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setBackground(Color.GREEN);

        panelMain.add(lblTitle);
        panelMain.add(Box.createRigidArea(new Dimension(0, 20)));
        panelMain.add(lblUsername);
        panelMain.add(txtUsername);
        panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMain.add(lblPassword);
        panelMain.add(txtPassword);
        panelMain.add(Box.createRigidArea(new Dimension(0, 20)));
        panelMain.add(btnLogin);

        add(panelMain, BorderLayout.CENTER);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
                    UsernameAktif = username; // Simpan nama pengguna yang login
                    JOptionPane.showMessageDialog(Bahan1.this, "Login berhasil sebagai: " + username);
                    tampilkanHalamanUtama();
                } else {
                    JOptionPane.showMessageDialog(Bahan1.this, "Username atau password salah!");
                }
            }
        });

        setVisible(true);
    }

    private void tampilkanHalamanUtama() {
        getContentPane().removeAll();
        setTitle("Sistem Pakar Diagnosa Kerusakan Smartphone");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inisialisasi daftar gejala
        gejalaMap = new HashMap<>();
        gejalaMap.put("layar tidak menyala", 0.9);
        gejalaMap.put("baterai cepat habis", 0.7);
        gejalaMap.put("ponsel sering restart", 0.6);
        gejalaMap.put("ponsel cepat panas", 0.8);

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBackground(Color.LIGHT_GRAY);

        JLabel lblTitle = new JLabel("Diagnosa Kerusakan Smartphone - pengguna: " + UsernameAktif);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.BLUE);

        txtKeluhan = new JTextArea(10, 40);
        txtKeluhan.setLineWrap(true);
        txtKeluhan.setWrapStyleWord(true);
        txtKeluhan.setBorder(BorderFactory.createTitledBorder("Ceritakan masalah smartphone Anda:"));
        JScrollPane scrollKeluhan = new JScrollPane(txtKeluhan);

        btnDiagnosa = new JButton("Diagnosa");
        btnDiagnosa.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDiagnosa.setBackground(Color.GREEN);

        txtResult = new JTextArea(5, 40);
        txtResult.setEditable(false);
        txtResult.setLineWrap(true);
        txtResult.setWrapStyleWord(true);
        JScrollPane scrollPaneResult = new JScrollPane(txtResult);
        scrollPaneResult.setBorder(BorderFactory.createTitledBorder("Hasil Diagnosa:"));

        txtSolusi = new JTextArea(5, 40);
        txtSolusi.setEditable(false);
        txtSolusi.setLineWrap(true);
        txtSolusi.setWrapStyleWord(true);
        JScrollPane scrollPaneSolusi = new JScrollPane(txtSolusi);
        scrollPaneSolusi.setBorder(BorderFactory.createTitledBorder("Solusi:"));

        btnLogout = new JButton("Logout");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);

        panelMain.add(lblTitle);
        panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMain.add(scrollKeluhan);
        panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMain.add(btnDiagnosa);
        panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMain.add(scrollPaneResult);
        panelMain.add(scrollPaneSolusi);
        panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMain.add(btnLogout);

        add(panelMain, BorderLayout.CENTER);

        btnDiagnosa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diagnosaBerdasarkanKeluhan();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsernameAktif = null; // Reset nama pengguna aktif
                tampilkanHalamanLogin();
            }
        });

        revalidate();
        repaint();
    }

    private void diagnosaBerdasarkanKeluhan() {
        String keluhan = txtKeluhan.getText().toLowerCase();
        if (keluhan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan keluhan terlebih dahulu.");
            return;
        }

        double cfLayar = keluhan.contains("layar tidak menyala") ? gejalaMap.get("layar tidak menyala") : 0;
        double cfBaterai = keluhan.contains("baterai cepat habis") ? gejalaMap.get("baterai cepat habis") : 0;
        double cfRestart = keluhan.contains("ponsel sering restart") ? gejalaMap.get("ponsel sering restart") : 0;
        double cfPanas = keluhan.contains("ponsel cepat panas") ? gejalaMap.get("ponsel cepat panas") : 0;

        String hasilDiagnosa = CertaintyFactorCalculator.hitungCF(cfLayar, cfBaterai, cfRestart, cfPanas);
        String solusi = CertaintyFactorCalculator.hitungSolusi(cfLayar, cfBaterai, cfRestart, cfPanas);

        txtResult.setText(hasilDiagnosa);
        txtSolusi.setText(solusi);
    }

    public static class CertaintyFactorCalculator {
        public static String hitungCF(double cfLayar, double cfBaterai, double cfRestart, double cfPanas) {
            StringBuilder hasilDiagnosa = new StringBuilder("Hasil Diagnosa:\n");

            if (cfLayar > 0) hasilDiagnosa.append("Kerusakan Layar: " + (cfLayar * 100) + "% keyakinan\n");
            if (cfBaterai > 0) hasilDiagnosa.append("Kerusakan Baterai: " + (cfBaterai * 100) + "% keyakinan\n");
            if (cfRestart > 0) hasilDiagnosa.append("Kerusakan Restart: " + (cfRestart * 100) + "% keyakinan\n");
            if (cfPanas > 0) hasilDiagnosa.append("Kerusakan Panas: " + (cfPanas * 100) + "% keyakinan\n");

            return hasilDiagnosa.toString();
        }

        public static String hitungSolusi(double cfLayar, double cfBaterai, double cfRestart, double cfPanas) {
            StringBuilder solusi = new StringBuilder("Solusi:\n");

            if (cfLayar > 0) solusi.append("Periksa kabel layar atau komponen LCD.\n");
            if (cfBaterai > 0) solusi.append("Ganti baterai dengan yang baru.\n");
            if (cfRestart > 0) solusi.append("Lakukan reset pabrik atau periksa firmware.\n");
            if (cfPanas > 0) solusi.append("Bersihkan area ventilasi dan hindari pemakaian berlebihan.\n");

            return solusi.toString();
        }
    }

    public static void main(String[] args) {
        new Bahan1();
    }
}
