import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final double PAJAK = 0.10;
    private static final long BIAYA_PELAYANAN = 20_000;
    private static final long AMBAT_PROMO_BELI_SATU = 50_000;
    private static final long AMBAT_DISKON = 100_000;

    private static final Locale LOKAL_ID = Locale.forLanguageTag("id-ID");
    private static final NumberFormat FORMAT_ANGKA = NumberFormat.getInstance(LOKAL_ID);
    private static final Scanner input = new Scanner(System.in);

    private static Menu[] daftarMenu = {
            new Menu("Nasi Goreng Spesial", 25_000, Menu.Kategori.MAKANAN),
            new Menu("Ayam Bakar Madu", 30_000, Menu.Kategori.MAKANAN),
            new Menu("Mie Goreng Jawa", 22_000, Menu.Kategori.MAKANAN),
            new Menu("Soto Ayam Lamongan", 20_000, Menu.Kategori.MAKANAN),
            new Menu("Es Teh Manis", 8_000, Menu.Kategori.MINUMAN),
            new Menu("Es Jeruk Peras", 10_000, Menu.Kategori.MINUMAN),
            new Menu("Kopi Susu Gula Aren", 18_000, Menu.Kategori.MINUMAN),
            new Menu("Jus Alpukat", 15_000, Menu.Kategori.MINUMAN)
    };

    private static int[] jumlahPesanan = new int[daftarMenu.length];

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("      SELERA NUSANTARA - Aplikasi Restoran");
        System.out.println("===================================================");
        boolean aplikasiBerjalan = true;
        while (aplikasiBerjalan) {
            System.out.println("\n--------------- MENU UTAMA ---------------");
            System.out.println("1. Pesan makanan / minuman (pelanggan)");
            System.out.println("2. Kelola menu restoran (pemilik)");
            System.out.println("0. Keluar aplikasi");
            String pilihan = bacaTeks("Pilih menu: ");
            switch (pilihan) {
                case "1":
                    prosesPemesanan();
                    break;
                case "2":
                    kelolaMenu();
                    break;
                case "0":
                    aplikasiBerjalan = false;
                    System.out.println("Terima kasih telah menggunakan aplikasi.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia, silakan pilih kembali.");
            }
        }
        input.close();
    }

    private static String bacaTeks(String label) {
        System.out.print(label);
        return input.nextLine().trim();
    }

    private static long bacaAngka(String label) {
        while (true) {
            String teks = bacaTeks(label);
            try {
                return Long.parseLong(teks);
            } catch (NumberFormatException salah) {
                System.out.println("Input harus berupa angka, silakan ulangi.");
            }
        }
    }

    private static int bacaJumlah(String label) {
        int jumlah;
        do {
            jumlah = (int) bacaAngka(label);
            if (jumlah < 1) {
                System.out.println("Jumlah minimal adalah 1.");
            }
        } while (jumlah < 1);
        return jumlah;
    }

    private static int bacaIndeksMenu(String label) {
        int[] peta = urutanTampil();
        while (true) {
            long nomor = bacaAngka(label);
            if (nomor >= 1 && nomor <= daftarMenu.length) {
                return peta[(int) nomor - 1];
            }
            System.out.println("Nomor menu hanya 1 sampai " + daftarMenu.length + ".");
        }
    }

    private static boolean bacaKonfirmasi(String label) {
        while (true) {
            String jawaban = bacaTeks(label);
            if (jawaban.equalsIgnoreCase("ya")) {
                return true;
            } else if (jawaban.equalsIgnoreCase("tidak")) {
                return false;
            } else {
                System.out.println("Jawaban hanya boleh 'Ya' atau 'Tidak'.");
            }
        }
    }

    private static String namaKategori(Menu.Kategori kategori) {
        if (kategori == Menu.Kategori.MAKANAN) {
            return "MAKANAN";
        } else if (kategori == Menu.Kategori.MINUMAN) {
            return "MINUMAN";
        } else {
            return "LAINNYA";
        }
    }

    private static String rupiah(long angka) {
        return "Rp" + FORMAT_ANGKA.format(angka);
    }

    private static int[] urutanTampil() {
        int[] peta = new int[daftarMenu.length];
        int posisi = 0;
        for (Menu.Kategori kategori : Menu.Kategori.values()) {
            for (int i = 0; i < daftarMenu.length; i++) {
                if (daftarMenu[i].getKategori() == kategori) {
                    peta[posisi] = i;
                    posisi++;
                }
            }
        }
        return peta;
    }

    private static void tampilkanMenu() {
        System.out.println("\n================ DAFTAR MENU ================");
        int nomor = 1;
        for (Menu.Kategori kategori : Menu.Kategori.values()) {
            System.out.println("-- " + namaKategori(kategori) + " --");
            for (int i = 0; i < daftarMenu.length; i++) {
                if (daftarMenu[i].getKategori() == kategori) {
                    System.out.printf("%2d. %-26s %16s%n", nomor,
                            daftarMenu[i].getNama(), rupiah(daftarMenu[i].getHarga()));
                    nomor++;
                }
            }
        }
        System.out.println("=============================================");
    }

    private static void tampilkanPesanan() {
        System.out.println("\n--- Item yang sudah dipesan ---");
        int[] peta = urutanTampil();
        boolean adaItem = false;
        for (int posisi = 0; posisi < peta.length; posisi++) {
            int indeks = peta[posisi];
            if (jumlahPesanan[indeks] > 0) {
                System.out.printf("%2d. %-26s x%d%n", posisi + 1,
                        daftarMenu[indeks].getNama(), jumlahPesanan[indeks]);
                adaItem = true;
            }
        }
        if (!adaItem) {
            System.out.println("(pesanan masih kosong)");
        }
    }

    private static void prosesPemesanan() {
        tampilkanMenu();
        System.out.println("Ketik nomor menu untuk memesan, 'hapus' untuk membatalkan item,");
        System.out.println("atau 'selesai' untuk mengakhiri pemesanan.");
        int[] peta = urutanTampil();
        jumlahPesanan = new int[daftarMenu.length];
        boolean pemesananSelesai = false;
        while (!pemesananSelesai) {
            String masukan = bacaTeks("\nNomor menu / 'hapus' / 'selesai': ");
            if (masukan.equalsIgnoreCase("selesai")) {
                pemesananSelesai = true;
            } else if (masukan.equalsIgnoreCase("hapus")) {
                hapusItemPesanan();
            } else if (masukan.isEmpty()) {
                System.out.println("Input tidak boleh kosong, silakan pilih kembali.");
            } else {
                try {
                    int nomor = Integer.parseInt(masukan);
                    if (nomor >= 1 && nomor <= daftarMenu.length) {
                        Menu menuTerpilih = daftarMenu[peta[nomor - 1]];
                        int jumlah = bacaJumlah("Jumlah " + menuTerpilih.getNama() + ": ");
                        jumlahPesanan[peta[nomor - 1]] += jumlah;
                        System.out.println(menuTerpilih.getNama() + " x" + jumlah + " masuk ke pesanan.");
                    } else {
                        System.out.println("Nomor di luar daftar menu, silakan pilih kembali.");
                    }
                } catch (NumberFormatException salah) {
                    System.out.println("Input tidak dikenali, silakan pilih kembali.");
                }
            }
        }
        if (totalItemPesanan() > 0) {
            cetakStruk();
        } else {
            System.out.println("Tidak ada item yang dipesan, kembali ke menu utama.");
        }
    }

    private static void hapusItemPesanan() {
        tampilkanPesanan();
        if (totalItemPesanan() == 0) {
            System.out.println("Belum ada item pesanan yang bisa dihapus.");
            return;
        }
        int indeks = bacaIndeksMenu("Nomor menu yang dihapus dari pesanan: ");
        if (jumlahPesanan[indeks] == 0) {
            System.out.println(daftarMenu[indeks].getNama() + " tidak ada di dalam pesanan.");
            return;
        }
        boolean yakin = bacaKonfirmasi("Hapus " + daftarMenu[indeks].getNama() + " dari pesanan? (Ya/Tidak): ");
        if (yakin) {
            jumlahPesanan[indeks] = 0;
            System.out.println("Item berhasil dihapus dari pesanan.");
        } else {
            System.out.println("Penghapusan dibatalkan, pesanan tidak berubah.");
        }
    }

    private static int totalItemPesanan() {
        int total = 0;
        for (int jumlah : jumlahPesanan) {
            total += jumlah;
        }
        return total;
    }

    private static void cetakStruk() {
        long subtotal = 0;
        for (int i = 0; i < daftarMenu.length; i++) {
            subtotal += daftarMenu[i].getHarga() * jumlahPesanan[i];
        }

        long totalDasar = Math.round(subtotal * (1 + PAJAK)) + BIAYA_PELAYANAN;
        boolean promoAktif = totalDasar > AMBAT_PROMO_BELI_SATU;

        long nilaiPromo = 0;
        for (int i = 0; i < daftarMenu.length; i++) {
            if (promoAktif && daftarMenu[i].getKategori() == Menu.Kategori.MINUMAN) {
                nilaiPromo += (jumlahPesanan[i] / 2) * daftarMenu[i].getHarga();
            }
        }

        long subtotalBersih = subtotal - nilaiPromo;
        long pajak = Math.round(subtotalBersih * PAJAK);
        long totalBiaya = subtotalBersih + pajak + BIAYA_PELAYANAN;
        boolean diskonAktif = totalBiaya > AMBAT_DISKON;
        long nilaiDiskon = diskonAktif ? Math.round(totalBiaya * PAJAK) : 0;
        long totalBayar = totalBiaya - nilaiDiskon;

        System.out.println("\n=============================================================");
        System.out.println("                    SELERA NUSANTARA");
        System.out.println("                      Struk Pesanan");
        System.out.println("=============================================================");
        System.out.printf("%2s  %-24s %15s %5s %15s%n", "No", "Menu", "Harga", "Qty", "Subtotal");
        System.out.println("-------------------------------------------------------------");
        int[] peta = urutanTampil();
        for (int posisi = 0; posisi < peta.length; posisi++) {
            int indeks = peta[posisi];
            if (jumlahPesanan[indeks] > 0) {
                Menu menu = daftarMenu[indeks];
                long totalPerItem = menu.getHarga() * jumlahPesanan[indeks];
                System.out.printf("%2d. %-24s %15s %5d %15s%n", posisi + 1,
                        menu.getNama(), rupiah(menu.getHarga()),
                        jumlahPesanan[indeks], rupiah(totalPerItem));
                if (promoAktif) {
                    int unitGratis = jumlahPesanan[indeks] / 2;
                    if (unitGratis > 0 && menu.getKategori() == Menu.Kategori.MINUMAN) {
                        System.out.printf("     * Promo beli 1 gratis 1: %d unit gratis%n", unitGratis);
                    }
                }
            }
        }
        System.out.println("-------------------------------------------------------------");
        cetakBaris("Subtotal", rupiah(subtotal));
        if (nilaiPromo > 0) {
            cetakBaris("Promo Beli 1 Gratis 1 (Minuman)", "-" + rupiah(nilaiPromo));
            cetakBaris("Subtotal setelah promo", rupiah(subtotalBersih));
        }
        cetakBaris("Biaya Pelayanan", rupiah(BIAYA_PELAYANAN));
        cetakBaris("Pajak (10%)", rupiah(pajak));
        cetakBaris("Total Biaya", rupiah(totalBiaya));
        if (diskonAktif) {
            cetakBaris("Diskon 10% (total > Rp100.000)", "-" + rupiah(nilaiDiskon));
        }
        cetakBaris("TOTAL BAYAR", rupiah(totalBayar));
        System.out.println("=============================================================");
        System.out.println("              Terima kasih atas kunjungan Anda!");
    }

    private static void cetakBaris(String label, String nilai) {
        System.out.printf("%-32s : %15s%n", label, nilai);
    }

    private static void kelolaMenu() {
        String pilihan;
        do {
            System.out.println("\n----------- KELOLA MENU (PEMILIK) -----------");
            System.out.println("1. Tambah menu baru");
            System.out.println("2. Ubah harga menu");
            System.out.println("3. Hapus menu");
            System.out.println("0. Kembali ke menu utama");
            pilihan = bacaTeks("Pilih menu kelola: ");
            switch (pilihan) {
                case "1":
                    tambahMenu();
                    break;
                case "2":
                    ubahHargaMenu();
                    break;
                case "3":
                    hapusMenu();
                    break;
                case "0":
                    System.out.println("Kembali ke menu utama.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia, silakan pilih kembali.");
            }
        } while (!pilihan.equals("0"));
    }

    private static Menu.Kategori pilihKategori() {
        while (true) {
            String pilihan = bacaTeks("Kategori (1 = Makanan, 2 = Minuman): ");
            if (pilihan.equals("1")) {
                return Menu.Kategori.MAKANAN;
            } else if (pilihan.equals("2")) {
                return Menu.Kategori.MINUMAN;
            } else {
                System.out.println("Kategori tidak valid, silakan pilih kembali.");
            }
        }
    }

    private static void tambahMenu() {
        boolean tambahLagi = true;
        while (tambahLagi) {
            System.out.println("\n--- Tambah Menu Baru ---");
            String nama = "";
            while (nama.isEmpty()) {
                nama = bacaTeks("Nama menu baru: ");
                if (nama.isEmpty()) {
                    System.out.println("Nama menu tidak boleh kosong.");
                }
            }
            long harga = 0;
            while (harga < 1) {
                harga = bacaAngka("Harga (Rp): ");
                if (harga < 1) {
                    System.out.println("Harga minimal Rp1.");
                }
            }
            Menu.Kategori kategori = pilihKategori();
            daftarMenu = Arrays.copyOf(daftarMenu, daftarMenu.length + 1);
            daftarMenu[daftarMenu.length - 1] = new Menu(nama, harga, kategori);
            jumlahPesanan = new int[daftarMenu.length];
            System.out.println("Menu \"" + nama + "\" berhasil ditambahkan.");
            tambahLagi = bacaKonfirmasi("Tambah menu lain sekaligus? (Ya/Tidak): ");
        }
    }

    private static void ubahHargaMenu() {
        if (daftarMenu.length == 0) {
            System.out.println("Belum ada menu yang dapat diubah.");
            return;
        }
        tampilkanMenu();
        int indeks = bacaIndeksMenu("Nomor menu yang harganya diubah: ");
        Menu menu = daftarMenu[indeks];
        System.out.println("Menu: " + menu.getNama() + " | Harga saat ini: " + rupiah(menu.getHarga()));
        boolean yakin = bacaKonfirmasi("Yakin ingin mengubah harga menu ini? (Ya/Tidak): ");
        if (yakin) {
            long hargaBaru = 0;
            while (hargaBaru < 1) {
                hargaBaru = bacaAngka("Harga baru (Rp): ");
                if (hargaBaru < 1) {
                    System.out.println("Harga minimal Rp1.");
                }
            }
            menu.setHarga(hargaBaru);
            System.out.println("Harga " + menu.getNama() + " berhasil diubah menjadi " + rupiah(hargaBaru) + ".");
        } else {
            System.out.println("Perubahan dibatalkan, data menu tidak berubah.");
        }
    }

    private static void hapusMenu() {
        if (daftarMenu.length == 0) {
            System.out.println("Belum ada menu yang dapat dihapus.");
            return;
        }
        tampilkanMenu();
        int indeks = bacaIndeksMenu("Nomor menu yang akan dihapus: ");
        Menu menu = daftarMenu[indeks];
        System.out.println("Anda akan menghapus: " + menu.getNama() + " (" + rupiah(menu.getHarga()) + ")");
        boolean yakin = bacaKonfirmasi("Yakin ingin menghapus menu ini? (Ya/Tidak): ");
        if (yakin) {
            Menu[] daftarBaru = new Menu[daftarMenu.length - 1];
            int posisi = 0;
            for (int i = 0; i < daftarMenu.length; i++) {
                if (i != indeks) {
                    daftarBaru[posisi] = daftarMenu[i];
                    posisi++;
                }
            }
            daftarMenu = daftarBaru;
            jumlahPesanan = new int[daftarMenu.length];
            System.out.println("Menu \"" + menu.getNama() + "\" berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan, data menu tidak berubah.");
        }
    }
}
