package main;

// main/App.java
import frameworks.*;
import frameworks.ui.AuthUI;
import frameworks.ui.LandingPageUI;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        LandingPageUI landingPageUI = new LandingPageUI(databaseConnector);
        landingPageUI.show();

    }
}