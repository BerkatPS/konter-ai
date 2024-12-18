package entities;

public class Solusi {
    private int solusiId;
    private int gejalaId;
    private String hasilSolusi;

    // Default Constructor
    public Solusi() {}

    // Parameterized Constructor
    public Solusi(int solusiId, int gejalaId, String hasilSolusi) {
        this.solusiId = solusiId;
        this.gejalaId = gejalaId;
        this.hasilSolusi = hasilSolusi;
    }

    // Getters and Setters
    public int getSolusiId() {
        return solusiId;
    }

    public void setSolusiId(int solusiId) {
        this.solusiId = solusiId;
    }

    public int getGejalaId() {
        return gejalaId;
    }

    public void setGejalaId(int gejalaId) {
        this.gejalaId = gejalaId;
    }

    public String getHasilSolusi() {
        return hasilSolusi;
    }

    public void setHasilSolusi(String hasilSolusi) {
        this.hasilSolusi = hasilSolusi;
    }
}

