import java.sql.*;
import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Bagian Login
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            System.out.println("\n<<--- SELAMAT DATANG DI INDOMARET --->>\n\n");
            System.out.println("Silakan login terlebih dahulu untuk \nmengakses aplikasi ini.");
            System.out.println("\n+-------------------------------+");
            System.out.println("|\t     Log in\t\t|");
            System.out.println("+-------------------------------+");

            System.out.print("Username : ");
            String username = scanner.nextLine().trim();

            System.out.print("Password : ");
            String password = scanner.nextLine().trim();

            System.out.print("Captcha (Ketik 'AkuManusia') : ");
            String captcha = scanner.nextLine().trim();

            if (username.equals("admin") && password.equals("admin") && captcha.equals("AkuManusia")) {
                isLoggedIn = true;
                System.out.println("\nLogin berhasil cuy!");
            } else {
                System.out.println("\nLogin gagal. Coba lagi ya dek.");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n\n+-----------------------------------------------------+");
            System.out.println("\t\t   Menu CRUD");
            System.out.println("+-----------------------------------------------------+");
            System.out.println("1. Tambah Transaksi (Create)");
            System.out.println("2. Lihat Semua Transaksi (Read)");
            System.out.println("3. Perbarui Transaksi (Update)");
            System.out.println("4. Hapus Transaksi (Delete)");
            System.out.println("5. Keluar");
            System.out.print("Pilihan Anda: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    tambahTransaksi(scanner);
                    break;
                case 2:
                    lihatSemuaTransaksi();
                    break;
                case 3:
                    perbaruiTransaksi(scanner);
                    break;
                case 4:
                    hapusTransaksi(scanner);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }

        scanner.close();
        System.out.println("\nTerima kasih telah menggunakan aplikasi kami.");
    }

    // Fungsi untuk menambah transaksi (CREATE)
    public static void tambahTransaksi(Scanner scanner) {
        System.out.print("\nMasukkan No. Faktur: ");
        String noFaktur = scanner.nextLine().trim();

        System.out.print("Masukkan Kode Barang: ");
        String kodeBarang = scanner.nextLine().trim().toUpperCase();

        System.out.print("Masukkan Nama Barang: ");
        String namaBarang = scanner.nextLine().trim().toUpperCase();

        System.out.print("Masukkan Harga Barang: ");
        double hargaBarang = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Masukkan Jumlah Beli: ");
        int jumlahBeli = Integer.parseInt(scanner.nextLine().trim());

        double total = hargaBarang * jumlahBeli;
        saveToDatabase(noFaktur, kodeBarang, namaBarang, hargaBarang, jumlahBeli, total);
    }

    // Fungsi untuk melihat semua transaksi (READ)
    public static void lihatSemuaTransaksi() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/DB_INDOMARET";
        String username = "root";
        String password = "Kahuripan21";

        String sql = "SELECT * FROM Transaksi";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("\t\t\tDaftar Transaksi");
            System.out.println("+-----------------------------------------------------+");

            while (rs.next()) {
                System.out.println("No. Faktur      : " + rs.getString("noFaktur"));
                System.out.println("Kode Barang     : " + rs.getString("kodeBarang"));
                System.out.println("Nama Barang     : " + rs.getString("namaBarang"));
                System.out.println("Harga Barang    : Rp" + rs.getDouble("hargaBarang"));
                System.out.println("Jumlah Beli     : " + rs.getInt("jumlahBeli"));
                System.out.println("Total           : Rp" + rs.getDouble("total"));
                System.out.println("Waktu Transaksi : " + rs.getTimestamp("waktuTransaksi"));
                System.out.println("+-----------------------------------------------------+");
            }

        } catch (SQLException e) {
            System.out.println("\nError saat mengambil data dari database: " + e.getMessage());
        }
    }

   // Fungsi untuk memperbarui transaksi (UPDATE)
public static void perbaruiTransaksi(Scanner scanner) {
    System.out.print("Masukkan No. Faktur yang ingin diperbarui: ");
    String noFaktur = scanner.nextLine().trim();

    System.out.print("Masukkan Kode Barang baru: ");
    String kodeBarang = scanner.nextLine().trim().toUpperCase();

    System.out.print("Masukkan Nama Barang baru: ");
    String namaBarang = scanner.nextLine().trim().toUpperCase();

    System.out.print("Masukkan Harga Barang baru: ");
    double hargaBarang = Double.parseDouble(scanner.nextLine().trim());

    System.out.print("Masukkan Jumlah Beli baru: ");
    int jumlahBeli = Integer.parseInt(scanner.nextLine().trim());

    double total = hargaBarang * jumlahBeli;

    String jdbcUrl = "jdbc:mysql://localhost:3306/DB_INDOMARET";
    String username = "root";
    String password = "Kahuripan21";

    String sql = "UPDATE Transaksi SET kodeBarang = ?, namaBarang = ?, hargaBarang = ?, jumlahBeli = ?, total = ? WHERE noFaktur = ?";

    try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, kodeBarang);
        pstmt.setString(2, namaBarang);
        pstmt.setDouble(3, hargaBarang);
        pstmt.setInt(4, jumlahBeli);
        pstmt.setDouble(5, total);
        pstmt.setString(6, noFaktur);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("\nTransaksi berhasil diperbarui!");
        } else {
            System.out.println("\nTransaksi dengan No. Faktur tersebut tidak ditemukan.");
        }
    } catch (SQLException e) {
        System.out.println("\nError saat memperbarui data: " + e.getMessage());
    }
}


    // Fungsi untuk menghapus transaksi (DELETE)
    public static void hapusTransaksi(Scanner scanner) {
        System.out.print("Masukkan No. Faktur yang ingin dihapus: ");
        String noFaktur = scanner.nextLine().trim();

        String jdbcUrl = "jdbc:mysql://localhost:3306/DB_INDOMARET";
        String username = "root";
        String password = "Kahuripan21";

        String sql = "DELETE FROM Transaksi WHERE noFaktur = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, noFaktur);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\nTransaksi berhasil dihapus!");
            } else {
                System.out.println("\nTransaksi dengan No. Faktur tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("\nError saat menghapus data: " + e.getMessage());
        }
    }

    // Fungsi untuk menyimpan transaksi ke database
    public static void saveToDatabase(String noFaktur, String kodeBarang, String namaBarang, double hargaBarang, int jumlahBeli, double total) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/DB_INDOMARET";
        String username = "root";
        String password = "Kahuripan21";

        String sql = "INSERT INTO Transaksi (noFaktur, kodeBarang, namaBarang, hargaBarang, jumlahBeli, total, waktuTransaksi) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, noFaktur);
            pstmt.setString(2, kodeBarang);
            pstmt.setString(3, namaBarang);
            pstmt.setDouble(4, hargaBarang);
            pstmt.setInt(5, jumlahBeli);
            pstmt.setDouble(6, total);
            pstmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\nTransaksi berhasil disimpan ke database!");
            } else {
                System.out.println("\nTransaksi gagal disimpan.");
            }
        } catch (SQLException e) {
            System.out.println("\nError saat menyimpan data ke database: " + e.getMessage());
        }
    }
}
