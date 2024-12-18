package frameworks;

import entities.User;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static int loggedInUserId = -1;
    private static String loggedInUsername = null;
    private static String loggedInRole = null;

    public static void startSession(int userId, String username, String role) {
        loggedInUserId = userId;
        loggedInUsername = username;
        loggedInRole = role;
    }

    public static void endSession() {
        loggedInUserId = -1;
        loggedInUsername = null;
        loggedInRole = null;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static String getLoggedInRole() {
        return loggedInRole;
    }

    public static boolean isLoggedIn() {
        return loggedInUserId != -1;
    }
}

