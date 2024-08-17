package javawithdb;

import java.sql.*;
import java.util.*;

public class JavaWithDb {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/a11_2022_14664";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showMenu();
    }

    public static void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("MENU:");
            System.out.println("1. Input data");
            System.out.println("2. Edit data");
            System.out.println("3. Delete data");
            System.out.println("4. Show data");
            System.out.println("5. Exit");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // membersihkan newline

            switch (choice) {
                case 1:
                    input();
                    break;
                case 2:
                    edit();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    show();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }

    public static void input() {
        System.out.println(" *Input Data* ");
        
        System.out.print("Masukkan Kode Barang: ");
        String kode_brg = scanner.nextLine();

        System.out.print("Masukkan Nama Barang: ");
        String nama_brg = scanner.nextLine(); 

        System.out.print("Masukkan Satuan: ");
        String satuan = scanner.nextLine();

        System.out.print("Masukkan Stok: ");
        int stok = scanner.nextInt();

        System.out.print("Masukkan Stok Minimal: ");
        int stok_min = scanner.nextInt();

        insert(kode_brg, nama_brg, satuan, stok, stok_min);
    }

    public static void insert(String kode_brg, String nama_brg, String satuan, int stok, int stok_min) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO penjualan (kode_brg, nama_brg, satuan, stok, stok_min) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, kode_brg);
            ps.setString(2, nama_brg);
            ps.setString(3, satuan);
            ps.setInt(4, stok);
            ps.setInt(5, stok_min);

            ps.execute();

            ps.close();
            conn.close();
            System.out.println("Data berhasil dimasukkan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void edit() {
        System.out.println(" *Edit Data* ");
        System.out.print("Masukkan Kode Barang yang akan diubah: ");
        String kode_brg = scanner.nextLine();

        System.out.print("Masukkan Nama Barang yang baru: ");
        String nama_brg = scanner.nextLine(); 

        System.out.print("Masukkan Satuan yang baru: ");
        String satuan = scanner.nextLine(); 

        System.out.print("Masukkan Stok yang baru: ");
        int stok = scanner.nextInt();

        System.out.print("Masukkan Stok minimal yang baru: ");
        int stok_min = scanner.nextInt();

        update(kode_brg, nama_brg, satuan, stok, stok_min);
    }

    public static void update(String kode_brg, String nama_brg, String satuan, int stok, int stok_min) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "UPDATE penjualan SET nama_brg=?, satuan=?, stok=?, stok_min=? WHERE kode_brg=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nama_brg);
            ps.setString(2, satuan);
            ps.setInt(3, stok);
            ps.setInt(4, stok_min);
            ps.setString(5, kode_brg);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data berhasil diubah.");
            } else {
                System.out.println("Data dengan kode barang '" + kode_brg + "' tidak ditemukan.");
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    public static void delete() {
        System.out.println(" *Delete Data* ");
        System.out.print("Masukkan Kode Barang yang akan dihapus: ");
        String kode_brg = scanner.nextLine();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "DELETE FROM penjualan WHERE kode_brg=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, kode_brg);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data berhasil dihapus.");
            } else {
                System.out.println("Data dengan kode barang '" + kode_brg + "' tidak ditemukan.");
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public static void show() {
        System.out.println(" *Show Data* ");
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT * FROM penjualan");
            while (rs.next()) {
                System.out.println("Kode Barang: " + rs.getString("kode_brg"));
                System.out.println("Nama Barang: " + rs.getString("nama_brg"));
                System.out.println("Satuan: " + rs.getString("satuan"));
                System.out.println("Stok: " + rs.getInt("stok"));
                System.out.println("Stok minimal: " + rs.getInt("stok_min"));
                System.out.println("------------------------");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
