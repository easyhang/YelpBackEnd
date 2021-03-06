package Repository;
import java.security.spec.ECField;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import DBUtil.DBManagement;
import Model.Restrurant;
import Model.RestrurantComment;
import Model.User;

/**
 * Created by leon on 4/3/17.
 */
public class UserRepository {
    Connection conn = null;
    static String BOUNDRY = "----------------------------------------------------------" +
            "----------------------";

    public void println(String s) {
        System.out.println(s);
    }

    public void print(String s) {
        System.out.print(s + " ");
    }

    public void print1(String s) {
        System.out.print(s + "\t");
    }

    public void print2(String s) {
        System.out.print(s + "; ");
    }

    public UserRepository() {
        conn = DBManagement.CreateConnection();
//        stmt = DBManagement.CreateStatement(conn);
    }

    public void save(User user) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into users (username, password, firstname, " +
                    "lastname, email, birthdate) values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname());
            preparedStatement.setString(5, user.getEmailaddress());
            preparedStatement.setDate(6, new java.sql.Date(new SimpleDateFormat
                    ("MM/dd/yyyy").parse(user.getBirthdate().substring(0,10)).getTime()));

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

    public boolean findbyUserId(int userId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from users" +
                    " where id = ?");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
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

    public int getUserId(String username) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select id " +
                    "from users where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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
                print(String.valueOf(id) + "\t");
                print(username + "\t");
                println(password);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRestrurantComment(RestrurantComment restrurantComment) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT into " +
                    "restrurantcomnents (content, userid, restrurantid) values (?, ?, ?)");
            preparedStatement.setString(1, restrurantComment.getContent());
            preparedStatement.setInt(2, restrurantComment.getUserId());
            preparedStatement.setInt(3, restrurantComment.getRestrurantId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchForRestrurants(String segments) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select id, name, address, type from restrurants " +
                            "where name like ?");
            preparedStatement.setString(1, "%" + segments + "%");
            ResultSet resultSet;
            if (segments.equals("*")) {
                String sql = "select * from restrurants";
                Statement statement = conn.createStatement();
                resultSet = statement.executeQuery(sql);
            } else {
                resultSet = preparedStatement.executeQuery();
            }
            println(BOUNDRY);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String type = resultSet.getString("type");
                print(String.valueOf(id) + "\t");
                print(name + "\t");
                print(address + "\t");
                println(type);
            }
            println(BOUNDRY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void saveRestrurantComments(RestrurantComment restrurantComment) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT " +
                    "into restrurantcomments (content, userid, restrurantid) values (?, ?, ?)");
            preparedStatement.setString(1, restrurantComment.getContent());
            preparedStatement.setInt(2, restrurantComment.getUserId());
            preparedStatement.setInt(3, restrurantComment.getRestrurantId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int follow(int followerId, int followeeId) {
        try {
            if (findFollowship(followerId, followeeId)) {
                return 0;
            }
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO " +
                    "followship (followerId, followeeId) VALUEs (?, ?)");
            preparedStatement.setInt(1, followerId);
            preparedStatement.setInt(2, followeeId);
            preparedStatement.executeUpdate();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean findFollowship(int followerId, int followeeId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT " +
                    "followeeId from followship where followerId = ?");
            preparedStatement.setInt(1, followerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("followeeId");
                if (id == followeeId) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void browseFollowings(int followerId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * " +
                    "from followship where followerId = ?");
            preparedStatement.setInt(1,followerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    int tmpId = resultSet.getInt("followeeId");
                    print2(String.valueOf(tmpId));
                }
            }
            println("");
            println(BOUNDRY);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void watchOneFollowingComments_AllRestrurants(int followerId, int followingId) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT" +
                    " users.username, restrurants.name, restrurantcomments.content " +
                    "from restrurantcomments " +
                    "  join users " +
                    "    on restrurantcomments.userId = users.id " +
                    "  join restrurants " +
                    "    on restrurantcomments.restrurantId = restrurants.id " +
                    "where users.id = ?");
            preparedStatement.setInt(1, followingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    String username = resultSet.getString("users.username");
                    String restrurantName = resultSet.getString("restrurants.name");
                    String content = resultSet.getString("restrurantcomments.content");
                    print1(username + ":");
                    print1("[" + restrurantName + "]");
                    println(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void watchAllFollowingComments_AllRestrurants(int id) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
