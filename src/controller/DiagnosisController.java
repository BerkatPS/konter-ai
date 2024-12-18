package controller;

import frameworks.DatabaseConnector;
import usecases.DiagnoseUseCase;
import entities.Gejala;
import entities.Solusi;
import entities.RiwayatDiagnosa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller untuk menangani permintaan diagnosis dari user.
 */
public class DiagnosisController {
    private final DiagnoseUseCase diagnoseUseCase;
    private final DatabaseConnector databaseConnector;


    // Constructor untuk menginisialisasi use case
    public DiagnosisController(DiagnoseUseCase diagnoseUseCase, DatabaseConnector databaseConnector) {
        this.diagnoseUseCase = diagnoseUseCase;
        this.databaseConnector = databaseConnector;
    }

    // Method to start a chat with a technician
    public String startChat(int userId, int teknisiId) {
        try {
            int chatRoomId = databaseConnector.createChatRoom(userId, teknisiId);
            if (chatRoomId != -1) {
                return "Chat room created successfully. You can start chatting now.";
            } else {
                return "Failed to create a chat room.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error creating chat room.";
        }
    }

    // Method to send message
    public String sendChatMessage(int chatRoomId, int senderId, String message) {
        try {
            databaseConnector.sendMessage(chatRoomId, senderId, message);

            // Determine receiver (if sender is user, receiver is technician, and vice versa)
            int receiverId = (senderId == 1) ? 2 : 1;  // Example: ID 1 is user, ID 2 is technician

            // Send notification to receiver
            String notificationMessage = (senderId == 1) ? "New message from user" : "New message from technician";
            databaseConnector.sendNotification(receiverId, notificationMessage);

            return "Message sent successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error sending message.";
        }
    }

    // Method to get all messages from chat room
    public List<String> getChatMessages(int chatRoomId) {
        try {
            return databaseConnector.getMessagesFromChatRoom(chatRoomId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Method to close chat room
    public String closeChat(int chatRoomId) {
        try {
            databaseConnector.updateChatRoomStatus(chatRoomId, "closed");
            return "Chat room closed successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error closing chat room.";
        }
    }
    /**
     * Method untuk menangani permintaan diagnosis berdasarkan keluhan.
     *
     * @param userId  ID pengguna yang melakukan diagnosis
     * @param keluhan Keluhan yang diberikan user
     * @return Hasil diagnosis dan solusi dalam bentuk String
     */
    public String handleDiagnosisRequest(int userId, String keluhan) {
        // Validasi input keluhan
        if (keluhan == null || keluhan.trim().isEmpty()) {
            return "Keluhan tidak boleh kosong. Silakan masukkan keluhan Anda.";
        }

        // Dapatkan gejala-gejala yang cocok berdasarkan keluhan
        List<Gejala> gejalas = diagnoseUseCase.getGejalasFromKeluhan(keluhan);

        // Jika tidak ada gejala yang cocok
        if (gejalas.isEmpty()) {
            return "Tidak ditemukan gejala yang cocok berdasarkan keluhan Anda. Silakan periksa kembali.";
        }

        // Menghitung certainty factor dan mendapatkan solusi
        double totalCF = 0.0;
        StringBuilder hasilDiagnosa = new StringBuilder("Hasil Diagnosis:\n");
        StringBuilder solusi = new StringBuilder("Solusi:\n");

        for (Gejala gejala : gejalas) {
            // Tambahkan informasi gejala yang ditemukan
            hasilDiagnosa.append(String.format("- %s (CF: %.2f)\n", gejala.getNamaGejala(), gejala.getCertaintyFactor()));
            totalCF += gejala.getCertaintyFactor();

            // Ambil solusi untuk setiap gejala
            Solusi solusiGejala = diagnoseUseCase.getSolusiByGejalaId(gejala.getId());
            if (solusiGejala != null) {
                solusi.append(String.format("- Solusi untuk %s: %s\n", gejala.getNamaGejala(), solusiGejala.getHasilSolusi()));
            } else {
                solusi.append(String.format("- Solusi untuk %s tidak ditemukan.\n", gejala.getNamaGejala()));
            }
        }

        // Rata-rata certainty factor
        totalCF = totalCF / gejalas.size();

        // Tambahkan certainty factor ke hasil diagnosis
        hasilDiagnosa.append(String.format("Rata-rata Certainty Factor: %.2f\n", totalCF));
        hasilDiagnosa.append(solusi);

        // Simpan riwayat diagnosa ke database
        diagnoseUseCase.simpanRiwayatDiagnosa( hasilDiagnosa.toString(), solusi.toString());

        // Mengembalikan hasil diagnosis dan solusi
        return hasilDiagnosa.toString();
    }
}
