package entities;

public class Pertanyaan {
    private int id;
    private int gejalaId;
    private String pertanyaan;

    // Default Constructor
    public Pertanyaan() {}

    // Parameterized Constructor
    public Pertanyaan(int id, int gejalaId, String pertanyaan) {
        this.id = id;
        this.gejalaId = gejalaId;
        this.pertanyaan = pertanyaan;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGejalaId() {
        return gejalaId;
    }

    public void setGejalaId(int gejalaId) {
        this.gejalaId = gejalaId;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }
}

