package Repository;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import DBUtil.DBManagement;

/**
 * Created by leon on 4/3/17.
 */
public class UserRepository {
    Connection conn = null;

    public void println(String s) {
        System.out.println(s);
    }

    public void print(String s) {
        System.out.print(s + " ");
    }

    public UserRepository() {
        conn = DBManagement.CreateConnection();
//        stmt = DBManagement.CreateStatement(conn);
    }

    public void save(String username, String password, String firstname,
                     String lastname, String email, String birthdate) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into users (username, password, firstname, " +
                    "lastname, email, birthdate) values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, lastname);
            preparedStatement.setString(5, email);
            preparedStatement.setDate(6, new java.sql.Date(new SimpleDateFormat
                    ("MM/dd/yyyy").parse(birthdate.substring(0,10)).getTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    public boolean findbyUsername(String username) {
        try {
            println("Username: " + username);
            PreparedStatement preparedStatement = conn.prepareStatement("select * from users" +
                    " where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                if (resultSet.next()) {
                    return true;
                }
            }
            else {
                println("No such user");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean findbyLogin(String username, String password) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from users" +
                    " where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    if (resultSet.getString("password").equals(password)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void browseAllInfo() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "select * from users";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                print(String.valueOf(id));
                print(username);
                println(password);

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
