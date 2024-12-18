package entities;

// entities/Diagnosis.java
public class Diagnosis {
    private String hasilDiagnosa;
    private String solusi;

    public Diagnosis(String hasilDiagnosa, String solusi) {
        this.hasilDiagnosa = hasilDiagnosa;
        this.solusi = solusi;
    }

    public String getDiagnosisResult() {
        return hasilDiagnosa;
    }

    public String getSolution() {
        return solusi;
    }
}
