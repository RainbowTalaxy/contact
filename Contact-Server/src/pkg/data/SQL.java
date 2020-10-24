package pkg.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQL {
    static final String URL = "jdbc:mysql://localhost:3306/contact?serverTimezone=UTC";
    static final String USERNAME = "root";
    static final String PASSWORD = "huangdaju";

    static {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/?serverTimezone=UTC",
                    USERNAME,
                    PASSWORD
                    );
            String sql_1 = "CREATE DATABASE IF NOT EXISTS contact;";
            String sql_2 = "USE contact;";
            String sql_3 = "CREATE TABLE IF NOT EXISTS information (" +
                    "i_id INT(3) PRIMARY KEY AUTO_INCREMENT," +
                    "i_name NCHAR(10) not NULL," +
                    "i_addr TEXT," +
                    "i_tele CHAR(11)" +
                    ");";
            Statement statement = connection.createStatement();
            statement.execute(sql_1);
            statement.execute(sql_2);
            statement.execute(sql_3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ignored) { }
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
