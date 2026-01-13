package CGETS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class asdas {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/dbase";
        String username = "root";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
            // Do further operations with the database as needed
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}