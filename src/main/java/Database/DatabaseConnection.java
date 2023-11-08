package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

        static String databaseName = "yugioh";
        static String url = "jdbc:mysql://localhost:3306/" + databaseName;

        static String username = "YugiohDB";
        static String password = System.getenv("YugiohDBPassword");


        public static Connection getConnection() {
            try {

                return DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                throw new RuntimeException("Error connecting to the database", ex);
            }
        }

        public static void main(String[] args) {
            getConnection();
        }
    }

