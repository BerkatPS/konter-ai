package entities;

public class Gejala {
    private int id;
    private String kodeGejala;
    private String namaGejala;
    private String deskripsi;
    private double certaintyFactor;

    // Default Constructor
    public Gejala() {}

    // Parameterized Constructor
    public Gejala(int id, String kodeGejala, String namaGejala, String deskripsi, double certaintyFactor) {
        this.id = id;
        this.kodeGejala = kodeGejala;
        this.namaGejala = namaGejala;
        this.deskripsi = deskripsi;
        this.certaintyFactor = certaintyFactor;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeGejala() {
        return kodeGejala;
    }

    public void setKodeGejala(String kodeGejala) {
        this.kodeGejala = kodeGejala;
    }

    public String getNamaGejala() {
        return namaGejala;
    }

    public void setNamaGejala(String namaGejala) {
        this.namaGejala = namaGejala;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getCertaintyFactor() {
        return certaintyFactor * 100;
    }

    public void setCertaintyFactor(double certaintyFactor) {
        this.certaintyFactor = certaintyFactor;
    }
}


