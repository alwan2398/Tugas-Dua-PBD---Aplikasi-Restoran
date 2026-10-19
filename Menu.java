public class Menu {

    public enum Kategori {
        MAKANAN, MINUMAN
    }

    private final String nama;
    private final Kategori kategori;
    private long harga;

    public Menu(String nama, long harga, Kategori kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public long getHarga() {
        return harga;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }
}
