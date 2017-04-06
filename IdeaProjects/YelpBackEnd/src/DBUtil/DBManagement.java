package DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by leon on 4/3/17.
 */

public class DBManagement {
    static Connection conn = null;
    static Statement stmt = null;
    static String DRIVER = "com.mysql.jdbc.Driver";
    static String USER = "root";
    static String PASS = "111111";
    static String URL = "jdbc:mysql://localhost:3306/yelp";

    public static Connection CreateConnection() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("database connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Statement CreateStatement(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            System.out.println("create statement success");
            return stmt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmt;
    }
}
