package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;
    public static DBConnection instance;

    private DBConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/hotel_reservation_system", "root", "1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance() {
        return instance == null ? new DBConnection() : instance;
    }
}
