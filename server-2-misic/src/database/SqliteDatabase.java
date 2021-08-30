package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import utils.CustomException;

public class SqliteDatabase implements IDatabase {

    private static SqliteDatabase instance = null;
    private Connection connection = null;

    public static SqliteDatabase getInstance() {
        if (instance == null) {
            instance = new SqliteDatabase();
        }
        return instance;
    }

    @Override
    public void connect(String pathToDatabase) {

        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:"+pathToDatabase;
            connection = DriverManager.getConnection(url);
            Statement stmt = connection.createStatement();
            String sqlCreate = "CREATE TABLE IF NOT EXISTS LOGS (\n"
                    + "\tACTION TEXT NOT NULL,\n"
                    + "\tTIME TEXT NOT NULL\n"
                    + ");";
            stmt.execute(sqlCreate);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getKey(String fileName) throws CustomException {

        Statement stmt;
        String key = null;

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT KEY FROM AES_KEYS WHERE FILE = '" + fileName + "'");
            rs.next();
            key = rs.getString(1);
        } catch (SQLException e) {
            throw new CustomException("Reading key from the database failed");
        }

        return key;
    }

    @Override
    public void logAction(String action, String datetime) {
        Statement stmt;
        try {

            stmt = connection.createStatement();
            stmt.execute("INSERT INTO LOGS VALUES ( '" + action + "', '" + datetime + "')");

        } catch (SQLException e) {
            System.err.println("Failed to log action into the database!");
        }
    }

}
