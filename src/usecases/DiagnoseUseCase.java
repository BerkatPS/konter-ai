    package usecases;

    import entities.Gejala;
    import entities.Solusi;
    import frameworks.DatabaseConnector;
    import frameworks.SessionManager;

    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class DiagnoseUseCase {
        private DatabaseConnector databaseConnector;

        // Constructor untuk inisialisasi database connector
        public DiagnoseUseCase(DatabaseConnector databaseConnector) {
            this.databaseConnector = databaseConnector;
        }



        /**
         * Method untuk mendapatkan gejala berdasarkan keluhan user.
         * @param keluhan Keluhan yang diberikan oleh user.
         * @return List gejala yang cocok dengan keluhan.
         */
        public List<Gejala> getGejalasFromKeluhan(String keluhan) {
            List<Gejala> matchedGejalas = new ArrayList<>();
            try {
                // Ambil semua gejala dari database
                List<Gejala> allGejalas = databaseConnector.getAllGejala();

                // Periksa kecocokan nama gejala dengan keluhan
                for (Gejala gejala : allGejalas) {
                    if (keluhan.toLowerCase().contains(gejala.getNamaGejala().toLowerCase())) {
                        matchedGejalas.add(gejala);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return matchedGejalas;
        }

        /**
         * Method untuk mendapatkan solusi berdasarkan gejala ID.
         * @param gejalaId ID dari gejala.
         * @return Solusi yang sesuai dengan gejala ID.
         */
        public Solusi getSolusiByGejalaId(int gejalaId) {
            try {
                return databaseConnector.getSolusiByGejalaId(gejalaId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Method untuk menyimpan riwayat diagnosa ke dalam database.
         *
         * @param hasilDiagnosa Hasil diagnosa (string).
         * @param solusi        Solusi dari diagnosa (string).
         * @return
         */
        public boolean simpanRiwayatDiagnosa(String hasilDiagnosa, String solusi) {
            try {
                int userId = SessionManager.getLoggedInUserId(); // Mengambil userId dari session
                if (userId == -1) {
                    System.out.println("Invalid User ID. Cannot save to database.");
                    return false;
                }
                return databaseConnector.saveRiwayatDiagnosa(userId, hasilDiagnosa, solusi);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }


        /**
         * Method untuk menghitung Certainty Factor dan mengembalikan hasil diagnosa.
         * @param keluhan Keluhan yang diberikan oleh user.
         * @return Hasil diagnosa dan solusi dalam bentuk String.
         */
        public String prosesDiagnosa(String keluhan) {
            List<Gejala> matchedGejalas = getGejalasFromKeluhan(keluhan);
            if (matchedGejalas.isEmpty()) {
                return "Tidak ditemukan gejala yang sesuai dengan keluhan Anda.";
            }

            double totalCF = 0.0;
            StringBuilder hasilDiagnosa = new StringBuilder("Hasil Diagnosa:\n");
            StringBuilder solusi = new StringBuilder("Solusi:\n");

            // Iterasi gejala dan hitung Certainty Factor (CF)
            for (Gejala gejala : matchedGejalas) {
                hasilDiagnosa.append(String.format("- %s (CF: %.2f)\n", gejala.getNamaGejala(), gejala.getCertaintyFactor()));
                totalCF += gejala.getCertaintyFactor();

                // Ambil solusi berdasarkan ID gejala
                Solusi solusiGejala = getSolusiByGejalaId(gejala.getId());
                if (solusiGejala != null) {
                    solusi.append(String.format("- Solusi untuk %s: %s\n", gejala.getNamaGejala(), solusiGejala.getHasilSolusi()));
                } else {
                    solusi.append(String.format("- Solusi untuk %s tidak ditemukan.\n", gejala.getNamaGejala()));
                }
            }

            // Rata-rata Certainty Factor
            totalCF = totalCF / matchedGejalas.size();
            hasilDiagnosa.append(String.format("\nRata-rata Certainty Factor: %.2f\n", totalCF));
            hasilDiagnosa.append(solusi);

            return hasilDiagnosa.toString();
        }
    }
