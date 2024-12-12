public class Produk {
    private String kodeBarang;
    private String namaBarang;
    private double hargaBarang;

    // Konstruktor
    public Produk(String kodeBarang, String namaBarang, double hargaBarang) {
        if (kodeBarang == null || kodeBarang.isEmpty()) {
            throw new IllegalArgumentException("Kode barang tidak boleh kosong");
        }
        if (namaBarang == null || namaBarang.isEmpty()) {
            throw new IllegalArgumentException("Nama barang tidak boleh kosong");
        }
        if (hargaBarang <= 0) {
            throw new IllegalArgumentException("Harga barang harus lebih besar dari 0");
        }

        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
    }

    // Getter dan Setter
    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        if (hargaBarang <= 0) {
            throw new IllegalArgumentException("Harga barang harus lebih besar dari 0");
        }
        this.hargaBarang = hargaBarang;
    }

    @Override
    public String toString() {
        return "Kode: " + kodeBarang + "\nNama Barang: " + namaBarang + "\nHarga: Rp." + hargaBarang;
    }
}
