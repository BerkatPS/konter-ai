package entities;

public class Kategori {
    private int kategoriId;
    private String namaKategori;
    private String gejala;

    // Default Constructor
    public Kategori() {}

    // Parameterized Constructor
    public Kategori(int kategoriId, String namaKategori, String gejala) {
        this.kategoriId = kategoriId;
        this.namaKategori = namaKategori;
        this.gejala = gejala;
    }

    // Getters and Setters
    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }
}

