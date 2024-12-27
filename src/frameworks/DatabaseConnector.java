    package frameworks;
    import entities.*;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class DatabaseConnector {
        private Connection connection;

        public DatabaseConnector() throws SQLException {
            // Koneksi ke database MySQL
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistem_pakar_ai", "root", "");
        }
        public Connection getConnection() {
            return connection;
        }
        // Method to get the list of technicians
        public List<String> getTechnicianList() throws SQLException {
            List<String> technicians = new ArrayList<>();
            String query = "SELECT username FROM user WHERE LOWER(role) = 'teknisi'";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    technicians.add(rs.getString("username"));
                }
            }
            return technicians;
        }


        // Method to send message to a chat room
        public void sendMessage(int chatRoomId, int senderId, String message) throws SQLException {
            String query = "INSERT INTO messages (chat_room_id, sender_id, message) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, chatRoomId);
                stmt.setInt(2, senderId);
                stmt.setString(3, message);
                stmt.executeUpdate();
            }
        }

        // Method to get all messages from a chat room
        public List<String> getMessagesFromChatRoom(int chatRoomId) throws SQLException {
            List<String> messages = new ArrayList<>();
            String query = "SELECT m.message, u.username FROM messages m JOIN user u ON m.sender_id = u.id WHERE m.chat_room_id = ? ORDER BY m.created_at";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, chatRoomId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String message = rs.getString("username") + ": " + rs.getString("message");
                    messages.add(message);
                }
            }
            return messages;
        }

        public void updateChatStatus(int chatRoomId, String status) throws SQLException {
            String query = "INSERT INTO chat_room_status (chat_room_id, status) VALUES (?, ?) " +
                    "ON DUPLICATE KEY UPDATE status = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, chatRoomId);
                stmt.setString(2, status);
                stmt.setString(3, status);
                stmt.executeUpdate();
            }
        }


        public void updateChatRoomStatus(int chatRoomId, String status) throws SQLException {
            String query = "UPDATE chat_rooms SET status = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, status);
                stmt.setInt(2, chatRoomId);
                stmt.executeUpdate();
            }
        }

        // Method to send notification to user
        public void sendNotification(int userId, String message) throws SQLException {
            String query = "INSERT INTO notifications (user_id, message) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setString(2, message);
                stmt.executeUpdate();
            }
        }



        public int getTotalFromQuery(String query) throws SQLException {
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
            return 0;
        }

        public double getAverageFromQuery(String query) throws SQLException {
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    return rs.getDouble("average");
                }
            }
            return 0.0;
        }

        // =====================================
        // USER METHODS
        // =====================================
        public boolean registerUser(String username, String password, String nama) throws SQLException {
            // Mengecek apakah username sudah ada
            String checkQuery = "SELECT * FROM user WHERE username = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    return false;  // Username sudah ada
                }
            }

            // Menambahkan user baru
            String insertQuery = "INSERT INTO user (username, password, nama) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);  // Pastikan Anda mengenkripsi password untuk keamanan
                insertStmt.setString(3, nama);
                insertStmt.executeUpdate();
            }

            return true;  // Pendaftaran berhasil
        }

        public User verifikasiLogin(String username, String password) throws SQLException {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("nama"),
                            rs.getString("role"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
            return null;
        }

        // =====================================
        // GEJALA METHODS
        // =====================================
        public List<Gejala> getAllGejala() throws SQLException {
            List<Gejala> gejalas = new ArrayList<>();
            String query = "SELECT * FROM gejala";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    gejalas.add(new Gejala(
                            rs.getInt("id"),
                            rs.getString("kode_gejala"),
                            rs.getString("nama_gejala"),
                            rs.getString("deskripsi"),
                            rs.getDouble("certainty_factor")
                    ));
                }
            }
            return gejalas;
        }

        // =====================================
        // SOLUSI METHODS
        // =====================================
        public Solusi getSolusiByGejalaId(int gejalaId) throws SQLException {
            String query = "SELECT * FROM solusi WHERE gejala_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, gejalaId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Solusi(
                            rs.getInt("solusi_id"),
                            rs.getInt("gejala_id"),
                            rs.getString("hasil_solusi")
                    );
                }
            }
            return null;
        }

        // Method untuk mendapatkan unread notifications
        public List<Notification> getUnreadNotifications(int userId) throws SQLException {
            List<Notification> notifications = new ArrayList<>();
            String query = "SELECT * FROM notifications WHERE user_id = ? AND is_read = false ORDER BY created_at DESC";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    notifications.add(new Notification(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("message"),
                            rs.getTimestamp("created_at"),
                            rs.getBoolean("is_read")
                    ));
                }
            }
            return notifications;
        }



        // Method untuk mendapatkan riwayat chat
        public List<ChatMessage> getChatHistory(int chatRoomId, int limit, int offset) throws SQLException {
            List<ChatMessage> messages = new ArrayList<>();
            String query = "SELECT m.*, u.username, u.role FROM messages m " +
                    "JOIN user u ON m.sender_id = u.id " +
                    "WHERE m.chat_room_id = ? " +
                    "ORDER BY m.created_at DESC LIMIT ? OFFSET ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, chatRoomId);
                stmt.setInt(2, limit);
                stmt.setInt(3, offset);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    messages.add(new ChatMessage(
                            rs.getInt("id"),
                            rs.getInt("chat_room_id"),
                            rs.getInt("sender_id"),
                            rs.getString("username"),
                            rs.getString("role"),
                            rs.getString("message"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
            return messages;
        }

        // Method untuk mendapatkan statistik chat
        public Map<String, Object> getChatStatistics(int chatRoomId) throws SQLException {
            Map<String, Object> stats = new HashMap<>();
            String query = "SELECT " +
                    "COUNT(*) as total_messages, " +
                    "MAX(created_at) as last_message, " +
                    "SUM(CASE WHEN sender_id IN (SELECT id FROM user WHERE role='teknisi') THEN 1 ELSE 0 END) as teknisi_messages, " +
                    "SUM(CASE WHEN sender_id IN (SELECT id FROM user WHERE role='user') THEN 1 ELSE 0 END) as user_messages " +
                    "FROM messages WHERE chat_room_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, chatRoomId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    stats.put("totalMessages", rs.getInt("total_messages"));
                    stats.put("lastMessage", rs.getTimestamp("last_message"));
                    stats.put("teknisiMessages", rs.getInt("teknisi_messages"));
                    stats.put("userMessages", rs.getInt("user_messages"));
                }
            }
            return stats;
        }

        // Method untuk mendapatkan active chat rooms berdasarkan teknisi
        public List<ChatRoom> getActiveChatRoomsByTeknisi(int teknisiId) throws SQLException {
            List<ChatRoom> chatRooms = new ArrayList<>();
            String query = "SELECT cr.*, crs.status, u.username as user_name " +
                    "FROM chat_rooms cr " +
                    "JOIN chat_room_status crs ON cr.id = crs.chat_room_id " +
                    "JOIN user u ON cr.user_id = u.id " +
                    "WHERE cr.teknisi_id = ? AND crs.status != 'closed' " +
                    "ORDER BY cr.created_at DESC";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, teknisiId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    chatRooms.add(new ChatRoom(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("teknisi_id"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
            return chatRooms;
        }

        // Method untuk mendapatkan response time statistics
        public Map<String, Object> getResponseTimeStats(int teknisiId) throws SQLException {
            Map<String, Object> stats = new HashMap<>();
            String query = "SELECT " +
                    "AVG(TIMESTAMPDIFF(MINUTE, m1.created_at, m2.created_at)) as avg_response_time, " +
                    "MAX(TIMESTAMPDIFF(MINUTE, m1.created_at, m2.created_at)) as max_response_time " +
                    "FROM messages m1 " +
                    "JOIN messages m2 ON m1.chat_room_id = m2.chat_room_id " +
                    "JOIN chat_rooms cr ON m1.chat_room_id = cr.id " +
                    "WHERE cr.teknisi_id = ? " +
                    "AND m1.sender_id != ? AND m2.sender_id = ? " +
                    "AND m2.created_at > m1.created_at";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, teknisiId);
                stmt.setInt(2, teknisiId);
                stmt.setInt(3, teknisiId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    stats.put("avgResponseTime", rs.getDouble("avg_response_time"));
                    stats.put("maxResponseTime", rs.getInt("max_response_time"));
                }
            }
            return stats;
        }

        public List<ChatRoom> getActiveChatRooms(int userId, String userRole) throws SQLException {
            List<ChatRoom> rooms = new ArrayList<>();
            String query;

            if (userRole.equals("user")) {
                query = "SELECT cr.id, cr.user_id, cr.teknisi_id, " +
                        "u.nama AS other_party_name, " +
                        "cr.status, cr.created_at " + // Ambil status dari tabel chat_rooms
                        "FROM chat_rooms cr " +
                        "JOIN user u ON cr.teknisi_id = u.id " +
                        "WHERE cr.user_id = ? AND (cr.status = 'open' OR cr.status = 'pending')";
            } else if (userRole.equals("teknisi")) {
                query = "SELECT cr.id, cr.user_id, cr.teknisi_id, " +
                        "u.nama AS other_party_name, " +
                        "cr.status, cr.created_at " + // Ambil status dari tabel chat_rooms
                        "FROM chat_rooms cr " +
                        "JOIN user u ON cr.user_id = u.id " +
                        "WHERE cr.teknisi_id = ? AND (cr.status = 'open' OR cr.status = 'pending')";
            } else {
                return rooms; // Return empty list for invalid role
            }


            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        ChatRoom room = new ChatRoom(
                                rs.getInt("id"),
                                rs.getInt("user_id"),
                                rs.getInt("teknisi_id"),
                                rs.getString("status"),
                                rs.getTimestamp("created_at")
                        );
                        rooms.add(room);
                    }
                }
            }

            return rooms;
        }
        public int calculateTotalDiagnoses(int userId) throws SQLException {
            String query = "SELECT COUNT(*) as total FROM riwayat_diagnosa WHERE user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("total");
                    }
                }
            }
            return 0;
        }
        // Method untuk menghitung Active Consultations
        private int calculateActiveConsultations(int userId) throws SQLException {
            String query = "SELECT COUNT(*) as active_consultations " +
                    "FROM chat_rooms " +
                    "WHERE (user_id = ? OR teknisi_id = ?) " +
                    "AND status = 'open'";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("active_consultations");
                    }
                }
            }
            return 0;
        }

        public String getUsernameById(int userId) throws SQLException {
            String query = "SELECT username FROM user WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("username");
                    }
                }
            }
            return "Unknown";
        }

        // Method untuk menghitung Pending Consultations


        // Method untuk menghitung Completed Consultations
        public int calculateCompletedConsultations(int userId) throws SQLException {
            String query = "SELECT COUNT(*) as completed_consultations " +
                    "FROM chat_rooms " +
                    "WHERE (user_id = ? OR teknisi_id = ?) " +
                    "AND status = 'closed'";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("completed_consultations");
                    }
                }
            }
            return 0;
        }

        // Method to calculate pending consultations
        public int calculatePendingConsultations(int userId) throws SQLException {
            String query = "SELECT COUNT(*) as total FROM chat_rooms " +
                    "WHERE (user_id = ?  OR teknisi_id = ?) AND status = 'open'";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("total");
                    }
                }
            }
            return 0;
        }

        // Method to calculate success rate
        public double calculateSuccessRate(int userId) throws SQLException {
            // Total diagnoses
            String totalQuery = "SELECT COUNT(*) as total FROM riwayat_diagnosa WHERE user_id = ?";

            // Successful diagnoses (you can define criteria for success)
            String successQuery = "SELECT COUNT(*) as success FROM riwayat_diagnosa " +
                    "WHERE user_id = ? AND (hasil_diagnosa IS NOT NULL AND hasil_diagnosa != '')";

            int totalDiagnoses = 0;
            int successfulDiagnoses = 0;

            // Get total diagnoses
            try (PreparedStatement stmt = connection.prepareStatement(totalQuery)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        totalDiagnoses = rs.getInt("total");
                    }
                }
            }

            // Get successful diagnoses
            try (PreparedStatement stmt = connection.prepareStatement(successQuery)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        successfulDiagnoses = rs.getInt("success");
                    }
                }
            }

            // Calculate success rate
            return totalDiagnoses > 0
                    ? ((double) successfulDiagnoses / totalDiagnoses) * 100
                    : 0;
        }



        public List<ChatMessage> getChatMessages(int chatRoomId, int limit) throws SQLException {
            List<ChatMessage> messages = new ArrayList<>();
            String query = "SELECT m.*, u.username, u.role " +
                    "FROM messages m " +
                    "JOIN user u ON m.sender_id = u.id " +
                    "WHERE m.chat_room_id = ? " +
                    "ORDER BY m.created_at DESC LIMIT ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, chatRoomId);
                stmt.setInt(2, limit);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    ChatMessage message = new ChatMessage(
                            rs.getInt("id"),
                            rs.getInt("chat_room_id"),
                            rs.getInt("sender_id"),
                            rs.getString("username"),
                            rs.getString("role"),
                            rs.getString("message"),
                            rs.getTimestamp("created_at")
                    );
                    messages.add(message);
                }
            }
            return messages;
        }

        public void markMessagesAsRead(int chatRoomId, int userId) throws SQLException {
            String query = "UPDATE messages SET is_read = true " +
                    "WHERE chat_room_id = ? AND sender_id != ? AND is_read = false";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, chatRoomId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
        }



        // =====================================
    // METHOD UNTUK MENGAMBIL SEMUA PERTANYAAN
    // =====================================
        public List<Pertanyaan> getAllPertanyaan() throws SQLException {
            List<Pertanyaan> pertanyaanList = new ArrayList<>();
            String query = "SELECT * FROM pertanyaan";

            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    pertanyaanList.add(new Pertanyaan(
                            rs.getInt("id"),
                            rs.getInt("gejala_id"),
                            rs.getString("pertanyaan")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Error loading all questions: " + e.getMessage());
            }
            return pertanyaanList;
        }

        public int getUserIdByUsername(String username) throws SQLException {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be null or empty");
            }

            // Normalisasi username (trim dan lowercase)
            username = username.trim().toLowerCase();

            String query = "SELECT id FROM user WHERE LOWER(username) = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        System.out.println("Fetching user ID for username: " + username);
                        System.out.println("Found user ID: " + userId);
                        return userId;
                    } else {
                        System.err.println("User not found: " + username);
                        throw new SQLException("User not found: " + username);
                    }
                }
            }
        }



        // =====================================
        // RIWAYAT DIAGNOSA METHODS
        // =====================================
        public boolean saveRiwayatDiagnosa(int userId, String hasilDiagnosa, String solusi) throws SQLException {
            String sql = "INSERT INTO riwayat_diagnosa (user_id, hasil_diagnosa, solusi, created_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setString(2, hasilDiagnosa);
                stmt.setString(3, solusi);
                stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        }




        public List<RiwayatDiagnosa> getRiwayatByUserId(int userId) throws SQLException {
            List<RiwayatDiagnosa> riwayats = new ArrayList<>();
            String query = "SELECT * FROM riwayat_diagnosa WHERE user_id = ? ORDER BY id DESC  LIMIT 10";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    riwayats.add(new RiwayatDiagnosa(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("hasil_diagnosa"),
                            rs.getString("solusi"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
            return riwayats;
        }

        public List<RiwayatDiagnosa> getRiwayatByUsername(String username) throws SQLException {
            List<RiwayatDiagnosa> riwayats = new ArrayList<>();
            String query = "SELECT * FROM riwayat_diagnosa WHERE user_id = (SELECT id FROM user WHERE username = ?)";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    riwayats.add(new RiwayatDiagnosa(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("hasil_diagnosa"),
                            rs.getString("solusi"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
            return riwayats;
        }


        public int createChatRoom(int userId, int teknisiId) throws SQLException {
            String roomQuery = "INSERT INTO chat_rooms (user_id, teknisi_id, status) VALUES (?, ?, 'open')"; // Set status ke 'open'

            try (PreparedStatement roomStmt = connection.prepareStatement(roomQuery, Statement.RETURN_GENERATED_KEYS)) {
                roomStmt.setInt(1, userId);
                roomStmt.setInt(2, teknisiId);
                roomStmt.executeUpdate();

                ResultSet generatedKeys = roomStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            return -1;
        }

        public void closeChatRoom(int chatRoomId) throws SQLException {
            String query = "UPDATE chat_rooms SET status = 'closed' WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, chatRoomId);
                stmt.executeUpdate();
            }
        }
    }



