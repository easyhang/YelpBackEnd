package Repository;

import DBUtil.DBManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by leon on 4/9/17.
 */
public class AdminRepository {
    Connection conn = null;

    public void println(String s) {
        System.out.println(s);
    }

    public void print(String s) {
        System.out.print(s + " ");
    }

    public AdminRepository() {
        conn = DBManagement.CreateConnection();
    }

    public void saveRestrurant(String name, String address, String type) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into restrurants (" +
                    "name, address, type) values (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, type);

            preparedStatement.executeUpdate();
            println("add restrurant success.");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void saveFood(String name, int restrurantId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into food (" +
                    "name, restruantId) VALUEs (? ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, restrurantId);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
