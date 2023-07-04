package DB;

import java.sql.*;

public class MySQLConnector {
    private static Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/magazineDB";
    private static final String username = "root";
    private static final String password = "1";

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }
}
