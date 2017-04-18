package Repository;

import DBUtil.DBManagement;
import Model.Food;
import Model.Restrurant;

import java.sql.*;

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

    public void saveRestrurant(Restrurant restrurant) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into restrurants (" +
                    "name, address, type) values (?, ?, ?)");
            preparedStatement.setString(1, restrurant.getRestrurantName());
            preparedStatement.setString(2, restrurant.getAddress());
            preparedStatement.setString(3, restrurant.getType());

            preparedStatement.executeUpdate();
            println("add restrurant success.");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void saveFood(Food food) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into food (" +
                    "name, restruantId) VALUEs (? ?)");
            preparedStatement.setString(1, food.getFoodName());
            preparedStatement.setInt(2, food.getRestrurantId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void browseAllInfo() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "select * from users";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
//                String password = resultSet.getString("password");
                print(String.valueOf(id));
                println(username);
//                println(password);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void browseAllRestrurantInfo() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "select * from restrurants";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String type = resultSet.getString("type");
                print(String.valueOf(id));
                print(name);
                print(address);
                println(type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void browseFoodInfo(int restrurantId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from " +
                    "food where restrurantid = ?");
            preparedStatement.setInt(1, restrurantId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                print(String.valueOf(id));
                println(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void browseRestrurantComnentInfo(int restrurantId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select restrurantComment.content, restrurant.name, user.name " +
                            "from restrurantComment, restrurant, user " +
                    "where restrurantComment.restrurantid = ? and restrurantComment.restrurantid = restrurant.id and user.id = restrurantComment.userid");
            preparedStatement.setInt(1, restrurantId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("user.username");
                String restrurantName = resultSet.getString("restrurant.name");
                String content = resultSet.getString("restrurantComment.content");
                print(userName);
                print(restrurantName);
                println(content);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String username) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "delete from yelp.users where username = ?");
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
