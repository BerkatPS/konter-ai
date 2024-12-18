package entities;

import java.sql.Timestamp;

public class RiwayatDiagnosa {
    private int id;
    private int userId;
    private String hasilDiagnosa;
    private String solusi;
    private Timestamp createdAt;

    // Default Constructor
    public RiwayatDiagnosa() {
    }

    // Parameterized Constructor
    public RiwayatDiagnosa(int id, int userId, String hasilDiagnosa, String solusi, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.hasilDiagnosa = hasilDiagnosa;
        this.solusi = solusi;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHasilDiagnosa() {
        return hasilDiagnosa;
    }

    public void setHasilDiagnosa(String hasilDiagnosa) {
        this.hasilDiagnosa = hasilDiagnosa;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
