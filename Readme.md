# Selera Nusantara — Aplikasi Restoran Sederhana (Java Console)

Program Java console untuk **Tugas Praktik 2 — Pemrograman Berbasis Desktop**.
Aplikasi ini menyimulasikan operasional restoran sederhana dengan dua peran:
**pelanggan** yang dapat memesan makanan/minuman, dan **pemilik restoran** yang
dapat mengelola daftar menu. Seluruh data dikelola menggunakan **array murni**
tanpa collection framework, serta sengaja mendemonstrasikan seluruh struktur
keputusan (`if`, `if-else`, `if-else if`, `switch`, nested `if`) dan struktur
perulangan (`for`, `for-each`, `while`, `do-while`) sebagai pemenuhan indikator
penilaian tugas.

## Fitur

1. **Data menu restoran** — 8 menu awal (4 makanan + 4 minuman) yang disimpan
   dalam array objek `Menu`. Setiap item memiliki nama, harga, dan kategori.
2. **Pemesanan pelanggan** — memesan dengan mengetik nomor menu dan jumlahnya,
   tanpa batas item, hingga mengetik `selesai`. Input di luar daftar akan
   ditolak dan sistem terus meminta input ulang. Tersedia perintah `hapus`
   untuk membatalkan item yang sudah dipesan.
3. **Perhitungan total biaya** — pajak 10%, biaya pelayanan Rp20.000, promo
   _beli 1 gratis 1_ minuman, dan diskon 10% (rincian di bawah).
4. **Cetak struk** — rincian item (jumlah, harga satuan, subtotal per item),
   nilai promo, pajak, pelayanan, diskon, dan total akhir.
5. **Manajemen menu (pemilik)** — menambah menu baru (bisa beberapa sekaligus),
   mengubah harga, dan menghapus menu. Setiap ubah/hapus menampilkan daftar,
   meminta nomor menu, lalu konfirmasi `Ya/Tidak`. Navigasi dapat kembali ke
   menu utama.

## Aturan Perhitungan Biaya

| Komponen              | Aturan                                                                                                                          |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| Subtotal              | Jumlah (harga x qty) seluruh item pesanan                                                                                       |
| Promo beli 1 gratis 1 | Aktif jika total dasar (subtotal + pajak + pelayanan, sebelum promo) > Rp50.000; setiap 2 unit minuman yang sama, 1 unit gratis |
| Pajak                 | 10% dari subtotal setelah promo                                                                                                 |
| Biaya pelayanan       | Rp20.000 (tetap)                                                                                                                |
| Diskon                | 10% dari Total Biaya jika Total Biaya > Rp100.000                                                                               |

Contoh: Nasi Goreng Spesial x4 (Rp100.000) + Kopi Susu Gula Aren x2 (Rp36.000)
→ subtotal Rp136.000 → promo 1 kopi gratis (−Rp18.000) → pajak Rp11.800 →
Total Biaya Rp149.800 → diskon −Rp14.980 → **TOTAL BAYAR Rp134.820**.

## Struktur Proyek

```text
.
├── Main.java     # Kelas utama: navigasi, pemesanan, struk, manajemen menu
├── Menu.java     # Kelas Menu: nama, harga, kategori (enum MAKANAN/MINUMAN)
├── .gitignore
└── README.md
```

### Method utama pada `Main`

| Method               | Fungsi                                                         |
| -------------------- | -------------------------------------------------------------- |
| `tampilkanMenu()`    | Menampilkan daftar menu terkelompok per kategori               |
| `prosesPemesanan()`  | Menerima dan mengolah pesanan (loop `while` sampai `selesai`)  |
| `hapusItemPesanan()` | Menghapus item dari pesanan berjalan (dengan konfirmasi)       |
| `cetakStruk()`       | Menghitung pajak/promo/diskon dan mencetak struk               |
| `kelolaMenu()`       | Submenu pengelolaan untuk pemilik (loop `do-while` + `switch`) |
| `tambahMenu()`       | Menambah menu baru, beberapa sekaligus, dengan resize array    |
| `ubahHargaMenu()`    | Mengubah harga menu terpilih setelah konfirmasi                |
| `hapusMenu()`        | Menghapus menu dari array (penggeseran manual)                 |
| `urutanTampil()`     | Membangun peta urutan indeks agar menu tampil per kategori     |

## Cara Menjalankan

Kebutuhan: JDK 8 atau lebih baru.

```bash
javac Menu.java Main.java
java Main
```

Atau melalui IDE (IntelliJ IDEA / VS Code / NetBeans / BlueJ): buka folder
proyek, kompilasi kedua file, lalu jalankan kelas `Main`.

## Contoh Keluaran

```text
=============================================================
                    SELERA NUSANTARA
                      Struk Pesanan
=============================================================
No  Menu                               Harga   Qty        Subtotal
-------------------------------------------------------------
 1. Nasi Goreng Spesial             Rp25.000     4       Rp100.000
 7. Kopi Susu Gula Aren             Rp18.000     2        Rp36.000
     * Promo beli 1 gratis 1: 1 unit gratis
-------------------------------------------------------------
Subtotal                         :       Rp136.000
Promo Beli 1 Gratis 1 (Minuman)  :       -Rp18.000
Subtotal setelah promo           :       Rp118.000
Biaya Pelayanan                  :        Rp20.000
Pajak (10%)                      :        Rp11.800
Total Biaya                      :       Rp149.800
Diskon 10% (total > Rp100.000)   :       -Rp14.980
TOTAL BAYAR                      :       Rp134.820
=============================================================
```

## Pemetaan Materi ke Kode

| Indikator Penilaian                      | Implementasi                                                                                                                |
| ---------------------------------------- | --------------------------------------------------------------------------------------------------------------------------- |
| Tipe data, variabel, identifier, keyword | Atribut `Menu` (`String`, `long`, enum), konstanta `static final`, modifier akses, instansiasi object                       |
| Array dan String dalam method            | `Menu[] daftarMenu`, parallel array `int[] jumlahPesanan`, `Arrays.copyOf`, `equalsIgnoreCase`, `parseLong`, `NumberFormat` |
| Struktur keputusan                       | `switch` pada navigasi, `if-else if` pada validasi, nested `if` pada struk (promo per item)                                 |
| Struktur perulangan                      | `for` (cetak daftar/struk), `for-each` (enum, total item), `while` (loop pemesanan), `do-while` (submenu kelola)            |

## Penulis

|               |                              |
| ------------- | ---------------------------- |
| Nama          | 【Nama Lengkap】             |
| NIM           | 【NIM】                      |
| Program Studi | 【Program Studi】            |
| Mata Kuliah   | Pemrograman Berbasis Desktop |

## Lisensi

Kode ini dibuat untuk keperluan pembelajaran dan bebas digunakan sebagai
referensi akademik dengan tetap memperhatikan etika akademik institusi
masing-masing.
