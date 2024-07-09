package penerimaanmaba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Calon_maba extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel mainPanel, buttonPanel, panelCalonMahasiswa, panelNilaiUjian, panelHasilSeleksi;
    private JFrame frame;
    private Connection conn;

    public Calon_maba() {
        setTitle("Sistem Penerimaan Mahasiswa Baru");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initDatabase();
        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        panelCalonMahasiswa = new JPanel();
        panelNilaiUjian = new JPanel();
        panelHasilSeleksi = new JPanel();

        tabbedPane.addTab("Calon Mahasiswa", panelCalonMahasiswa);
        tabbedPane.addTab("Nilai Ujian", panelNilaiUjian);
        tabbedPane.addTab("Hasil Seleksi", panelHasilSeleksi);
        
        add(tabbedPane);

        initCalonMahasiswaPanel();
        initNilaiUjianPanel();
        initHasilSeleksiPanel();
    }

    private void initDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/penerimaan_maba";
            String user = "root";
            String password = "";
        
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Gagal terhubung ke database: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("Calon Mahasiswa Baru");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        mainPanel = new JPanel(new CardLayout());

        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, "Login");

        panelCalonMahasiswa = new JPanel();
        mainPanel.add(panelCalonMahasiswa, "CalonMahasiswa");

        panelNilaiUjian = new JPanel();
        mainPanel.add(panelNilaiUjian, "NilaiUjian");

        panelHasilSeleksi = new JPanel();
        mainPanel.add(panelHasilSeleksi, "HasilSeleksi");

        frame.add(mainPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton btnCalonMahasiswa = new JButton("Calon Mahasiswa");
        JButton btnNilaiUjian = new JButton("Nilai Ujian");
        JButton btnHasilSeleksi = new JButton("Hasil Seleksi");
        JButton btnMataKuliah = new JButton("Mata Kuliah");

        btnCalonMahasiswa.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "CalonMahasiswa");
        });

        btnNilaiUjian.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "NilaiUjian");
        });

        btnHasilSeleksi.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "HasilSeleksi");
        });
        
        buttonPanel.add(btnCalonMahasiswa);
        buttonPanel.add(btnNilaiUjian);
        buttonPanel.add(btnHasilSeleksi);

        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(lblUsername, gbc);

        JTextField tfUsername = new JTextField(20);
        gbc.gridx = 1;
        loginPanel.add(tfUsername, gbc);

        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(lblPassword, gbc);

        JPasswordField pfPassword = new JPasswordField(20);
        gbc.gridx = 1;
        loginPanel.add(pfPassword, gbc);

        JButton btnLogin = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String username = tfUsername.getText();
            String password = new String(pfPassword.getPassword());

            // Simple authentication logic (replace with your own)
            if ((username.equals("admin") && password.equals("pass")) || 
                (username.equals("moona") && password.equals("moonafic"))) {
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                cl.show(mainPanel, "CalonMahasiswa");
                initCalonMahasiswaPanel();
                initNilaiUjianPanel();
                initHasilSeleksiPanel();
                
                frame.add(buttonPanel, BorderLayout.NORTH);
                frame.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return loginPanel;
    }
    
    private void initCalonMahasiswaPanel() {
        panelCalonMahasiswa.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField tfNamaMahasiswa = new JTextField(20);
        JTextField tfAlamat = new JTextField(20);
        JTextField tfTelepon = new JTextField(20);
        JTextField tfNamaProdi = new JTextField(20);
        JTextField tfKodeProdi = new JTextField(20);
        JTextField tfAsalSekolah = new JTextField(20);
        JTextField tfNamaOrangTua = new JTextField(20);

        formPanel.add(new JLabel("Nama Mahasiswa:"));
        formPanel.add(tfNamaMahasiswa);
        formPanel.add(new JLabel("Alamat:"));
        formPanel.add(tfAlamat);
        formPanel.add(new JLabel("Telepon:"));
        formPanel.add(tfTelepon);
        formPanel.add(new JLabel("Nama Prodi:"));
        formPanel.add(tfNamaProdi);
        formPanel.add(new JLabel("Kode Prodi:"));
        formPanel.add(tfKodeProdi);
        formPanel.add(new JLabel("Asal Sekolah:"));
        formPanel.add(tfAsalSekolah);
        formPanel.add(new JLabel("Nama Orang Tua:"));
        formPanel.add(tfNamaOrangTua);

        inputPanel.add(formPanel);

        String[] columnNames = {"Nama Mahasiswa", "Alamat", "Telepon", "Nama Prodi", "Kode Prodi", "Asal Sekolah", "Nama Orang Tua"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable tabelHasil = new JTable(tableModel);
        JScrollPane scrollHasil = new JScrollPane(tabelHasil);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Add margin above buttons

        JButton btnSimpan = new JButton("Simpan");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");

        btnSimpan.addActionListener(e -> {
            try {
                saveCalonMaba(tfNamaMahasiswa.getText(), tfAlamat.getText(), tfTelepon.getText(), tfNamaProdi.getText(), tfKodeProdi.getText(), tfAsalSekolah.getText(), tfNamaOrangTua.getText());

                tableModel.addRow(new Object[]{
                    tfNamaMahasiswa.getText(),
                    tfAlamat.getText(),
                    tfTelepon.getText(),
                    tfNamaProdi.getText(),
                    tfKodeProdi.getText(),
                    tfAsalSekolah.getText(),
                    tfNamaOrangTua.getText()
                });

                tfNamaMahasiswa.setText("");
                tfAlamat.setText("");
                tfTelepon.setText("");
                tfNamaProdi.setText("");
                tfKodeProdi.setText("");
                tfAsalSekolah.setText("");
                tfNamaOrangTua.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelCalonMahasiswa,
                    "Gagal menyimpan data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = tabelHasil.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String namaMahasiswa = tableModel.getValueAt(selectedRow, 0).toString();
                    String alamat = tableModel.getValueAt(selectedRow, 1).toString();
                    String telepon = tableModel.getValueAt(selectedRow, 2).toString();
                    String namaProdi = tableModel.getValueAt(selectedRow, 3).toString();
                    String kodeProdi = tableModel.getValueAt(selectedRow, 4).toString();
                    String asalSekolah = tableModel.getValueAt(selectedRow, 5).toString();
                    String namaOrangTua = tableModel.getValueAt(selectedRow, 6).toString();

                    tfNamaMahasiswa.setText(namaMahasiswa);
                    tfAlamat.setText(alamat);
                    tfTelepon.setText(telepon);
                    tfNamaProdi.setText(namaProdi);
                    tfKodeProdi.setText(kodeProdi);
                    tfAsalSekolah.setText(asalSekolah);
                    tfNamaOrangTua.setText(namaOrangTua);

                    updateCalonMaba(namaMahasiswa, alamat, telepon, namaProdi, kodeProdi, asalSekolah, namaOrangTua);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelCalonMahasiswa,
                        "Gagal mengedit data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panelCalonMahasiswa, "Pilih baris yang akan diedit", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnHapus.addActionListener(e -> {
            int selectedRow = tabelHasil.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String namaMahasiswa = tableModel.getValueAt(selectedRow, 0).toString();
                    deleteCalonMaba(namaMahasiswa);
                    tableModel.removeRow(selectedRow);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelCalonMahasiswa,
                        "Gagal menghapus data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panelCalonMahasiswa, "Pilih baris yang akan dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);

        inputPanel.add(buttonPanel);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(inputPanel);
        northPanel.add(scrollHasil);

        panelCalonMahasiswa.add(northPanel, BorderLayout.NORTH);

        // Tabel untuk menampilkan semua data calon mahasiswa dan program studi
        DefaultTableModel allDataModel = new DefaultTableModel(columnNames, 0);
        JTable allDataTable = new JTable(allDataModel);
        JScrollPane allDataScrollPane = new JScrollPane(allDataTable);

        panelCalonMahasiswa.add(allDataScrollPane, BorderLayout.CENTER);

        // Load existing data
        loadCalonMaba(allDataModel);
    }

    private void initNilaiUjianPanel() {
        panelNilaiUjian.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField tfId = new JTextField(20);
        JTextField tfNamaMahasiswa = new JTextField(20);
        JTextField tfMatematika = new JTextField(20);
        JTextField tfBahasaIndonesia = new JTextField(20);
        JTextField tfBahasaInggris = new JTextField(20);

        formPanel.add(new JLabel("ID:"));
        formPanel.add(tfId);
        formPanel.add(new JLabel("Nama Mahasiswa:"));
        formPanel.add(tfNamaMahasiswa);
        formPanel.add(new JLabel("Matematika:"));
        formPanel.add(tfMatematika);
        formPanel.add(new JLabel("Bahasa Indonesia:"));
        formPanel.add(tfBahasaIndonesia);
        formPanel.add(new JLabel("Bahasa Inggris:"));
        formPanel.add(tfBahasaInggris);

        inputPanel.add(formPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        JButton btnSimpan = new JButton("Simpan");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);

        inputPanel.add(buttonPanel);

        panelNilaiUjian.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nama Mahasiswa", "Matematika", "Bahasa Indonesia", "Bahasa Inggris"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable tabelHasil = new JTable(tableModel);
        JScrollPane scrollHasil = new JScrollPane(tabelHasil);

        panelNilaiUjian.add(scrollHasil, BorderLayout.CENTER);

        btnSimpan.addActionListener(e -> {
            try {
                saveNilaiUjian(tfId.getText(), tfNamaMahasiswa.getText(), Integer.parseInt(tfMatematika.getText()), Integer.parseInt(tfBahasaIndonesia.getText()), Integer.parseInt(tfBahasaInggris.getText()));

                tableModel.addRow(new Object[]{
                    tfId.getText(),
                    tfNamaMahasiswa.getText(),
                    tfMatematika.getText(),
                    tfBahasaIndonesia.getText(),
                    tfBahasaInggris.getText()
                });

                tfId.setText("");
                tfNamaMahasiswa.setText("");
                tfMatematika.setText("");
                tfBahasaIndonesia.setText("");
                tfBahasaInggris.setText("");
            
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelNilaiUjian, 
                    "Gagal menyimpan data: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = tabelHasil.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String id = tableModel.getValueAt(selectedRow, 0).toString();
                    String namaMahasiswa = tableModel.getValueAt(selectedRow, 1).toString();
                    int matematika = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());
                    int bahasaIndonesia = Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString());
                    int bahasaInggris = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());

                    tfId.setText(id);
                    tfNamaMahasiswa.setText(namaMahasiswa);
                    tfMatematika.setText(String.valueOf(matematika));
                    tfBahasaIndonesia.setText(String.valueOf(bahasaIndonesia));
                    tfBahasaInggris.setText(String.valueOf(bahasaInggris));

                    updateNilaiUjian(id, namaMahasiswa, matematika, bahasaIndonesia, bahasaInggris);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelNilaiUjian, 
                        "Gagal mengedit data: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panelNilaiUjian, "Pilih baris yang akan diedit", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnHapus.addActionListener(e -> {
            int selectedRow = tabelHasil.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String id = tableModel.getValueAt(selectedRow, 0).toString();
                    deleteNilaiUjian(id);
                    tableModel.removeRow(selectedRow);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelNilaiUjian, 
                        "Gagal menghapus data: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panelNilaiUjian, "Pilih baris yang akan dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void initHasilSeleksiPanel() {
        panelHasilSeleksi.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Nama Mahasiswa", "Matematika", "Bahasa Indonesia", "Bahasa Inggris", "Lulus"}, 0);
        JTable tabelHasil = new JTable(tableModel);
        JScrollPane scrollHasil = new JScrollPane(tabelHasil);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> {
        tableModel.setRowCount(0); // Clear the table
        loadHasilSeleksi(tableModel); // Reload data
        });

        panelHasilSeleksi.add(scrollHasil, BorderLayout.CENTER);
        panelHasilSeleksi.add(btnRefresh, BorderLayout.SOUTH);

        loadHasilSeleksi(tableModel);
    }

    private void loadHasilSeleksi(DefaultTableModel model) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM nilai_ujian");

        while (rs.next()) {
            String namaMahasiswa = rs.getString("nama_mahasiswa");
            int matematika = rs.getInt("matematika");
            int bahasaIndonesia = rs.getInt("bahasa_indonesia");
            int bahasaInggris = rs.getInt("bahasa_inggris");
            String lulus = isLulus(matematika, bahasaIndonesia, bahasaInggris) ? "Lulus" : "Tidak Lulus";

            model.addRow(new Object[]{
                namaMahasiswa,
                matematika,
                bahasaIndonesia,
                bahasaInggris,
                lulus
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    private boolean isLulus(int matematika, int bahasaIndonesia, int bahasaInggris) {
        return ((matematika + bahasaIndonesia + bahasaInggris) / 3) >= 60;
    }

    private void saveCalonMaba(String nama, String alamat, String telepon, String namaProdi, String kodeProdi, String asalSekolah, String namaOrangTua) throws SQLException {
        String query = "INSERT INTO calon_mahasiswa_prodi (nama_mahasiswa, alamat, telepon, nama_prodi, kode_prodi, asal_sekolah, nama_orang_tua) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, nama);
        ps.setString(2, alamat);
        ps.setString(3, telepon);
        ps.setString(4, namaProdi);
        ps.setString(5, kodeProdi);
        ps.setString(6, asalSekolah);
        ps.setString(7, namaOrangTua);
        ps.executeUpdate();
    }

    private void updateCalonMaba(String nama, String alamat, String telepon, String namaProdi, String kodeProdi, String asalSekolah, String namaOrangTua) throws SQLException {
        String query = "UPDATE calon_mahasiswa_prodi SET alamat = ?, telepon = ?, nama_prodi = ?, kode_prodi = ?, asal_sekolah = ?, nama_orang_tua = ? WHERE nama_mahasiswa = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, alamat);
        ps.setString(2, telepon);
        ps.setString(3, namaProdi);
        ps.setString(4, kodeProdi);
        ps.setString(5, asalSekolah);
        ps.setString(6, namaOrangTua);
        ps.setString(7, nama);
        ps.executeUpdate();
    }

    private void deleteCalonMaba(String nama) throws SQLException {
        String query = "DELETE FROM calon_mahasiswa_prodi WHERE nama_mahasiswa = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, nama);
        ps.executeUpdate();
    }

    private void loadCalonMaba(DefaultTableModel model) {
        String query = "SELECT * FROM calon_mahasiswa_prodi";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String nama = rs.getString("nama_mahasiswa");
                String alamat = rs.getString("alamat");
                String telepon = rs.getString("telepon");
                String namaProdi = rs.getString("nama_prodi");
                String kodeProdi = rs.getString("kode_prodi");
                String asalSekolah = rs.getString("asal_sekolah");
                String namaOrangTua = rs.getString("nama_orang_tua");

                model.addRow(new Object[]{nama, alamat, telepon, namaProdi, kodeProdi, asalSekolah, namaOrangTua});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveNilaiUjian(String id, String namaMahasiswa, int matematika, int bahasaIndonesia, int bahasaInggris) throws SQLException {
        String query = "INSERT INTO nilai_ujian (id, nama_mahasiswa, matematika, bahasa_indonesia, bahasa_inggris) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, namaMahasiswa);
        ps.setInt(3, matematika);
        ps.setInt(4, bahasaIndonesia);
        ps.setInt(5, bahasaInggris);
        ps.executeUpdate();
    }

    private void updateNilaiUjian(String id, String namaMahasiswa, int matematika, int bahasaIndonesia, int bahasaInggris) throws SQLException {
        String query = "UPDATE nilai_ujian SET nama_mahasiswa = ?, matematika = ?, bahasa_indonesia = ?, bahasa_inggris = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, namaMahasiswa);
        ps.setInt(2, matematika);
        ps.setInt(3, bahasaIndonesia);
        ps.setInt(4, bahasaInggris);
        ps.setString(5, id);
        ps.executeUpdate();
    }

    private void deleteNilaiUjian(String id) throws SQLException {
        String query = "DELETE FROM nilai_ujian WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, id);
        ps.executeUpdate();
    }
    
    private void saveHasilSeleksi(String id, String nama, int matematika, int bahasaIndonesia, int bahasaInggris, String lulus) throws SQLException {
        String query = "INSERT INTO hasil_seleksi (id, nama_mahasiswa, matematika, bahasa_indonesia, bahasa_inggris, lulus) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, nama);
        ps.setInt(3, matematika);
        ps.setInt(4, bahasaIndonesia);
        ps.setInt(5, bahasaInggris);
        ps.setString(6, lulus);
        ps.executeUpdate();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calon_maba().createAndShowGUI();
            //new Calon_maba().setVisible(true);
        });
    }
}
