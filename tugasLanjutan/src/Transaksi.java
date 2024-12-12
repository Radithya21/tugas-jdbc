public class Transaksi extends Produk {
    private String noFaktur;
    private int jumlahBeli;
    private double total;

    // Konstruktor
    public Transaksi(String kodeBarang, String namaBarang, double hargaBarang, String noFaktur, int jumlahBeli) {
        super(kodeBarang, namaBarang, hargaBarang); // Memanggil konstruktor superclass (Produk)
        
        if (noFaktur == null || noFaktur.isEmpty()) {
            throw new IllegalArgumentException("No Faktur tidak boleh kosong");
        }
        if (jumlahBeli <= 0) {
            throw new IllegalArgumentException("Jumlah beli harus lebih besar dari 0");
        }

        this.noFaktur = noFaktur;
        this.jumlahBeli = jumlahBeli;
        this.total = 0; // Inisialisasi total awal
    }

    // Metode untuk menghitung total transaksi
    public void hitungTotal() {
        this.total = jumlahBeli * getHargaBarang(); // Menggunakan getter untuk mengambil hargaBarang
    }

    // Getter dan Setter
    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        if (noFaktur == null || noFaktur.isEmpty()) {
            throw new IllegalArgumentException("No Faktur tidak boleh kosong");
        }
        this.noFaktur = noFaktur;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    public void setJumlahBeli(int jumlahBeli) {
        if (jumlahBeli <= 0) {
            throw new IllegalArgumentException("Jumlah beli harus lebih besar dari 0");
        }
        this.jumlahBeli = jumlahBeli;
        hitungTotal(); // Hitung ulang total setiap kali jumlah beli diubah
    }

    public double getTotal() {
        return total;
    }

    // Metode untuk menampilkan invoice transaksi
    public String displayInvoice() {
        return "No Faktur: " + noFaktur + "\n"
                + super.toString() + "\n" // Memanggil metode toString() dari kelas Produk
                + "Jumlah Beli: " + jumlahBeli + " buah\n"
                + "Total: Rp." + total;
    }
}
