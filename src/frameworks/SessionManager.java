package frameworks;

import entities.User;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static int loggedInUserId = -1;
    private static String loggedInUsername = "";
    private static String loggedInUserRole = "";

    public static void startSession(int userId, String username, String role) {
        // Normalisasi username (trim dan lowercase)
        username = username.trim().toLowerCase();

        loggedInUserId = userId;
        loggedInUsername = username;
        loggedInUserRole = role;

        System.out.println("Session started for user: " + username +
                " (ID: " + userId +
                ", Role: " + role + ")");
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    public static String getLoggedInUsername() {
        // Kembalikan username yang telah dinormalisasi
        return loggedInUsername;
    }

    public static String getLoggedInUserRole() {
        return loggedInUserRole;
    }

    public static void clearSession() {
        loggedInUserId = -1;
        loggedInUsername = "";
        loggedInUserRole = "";
    }
}

