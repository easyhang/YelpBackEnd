import Model.User;
import Repository.UserRepository;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by leon on 4/3/17.
 */

public class UI {
    static String BOUNDRY = "-------------------------------------";
    UserRepository ur = null;

    public static void main(String[] args) {
        UI ui = new UI();
        boolean flag = true;
        while(flag) {
            flag = ui.UIbody();
        }
        ui.println("Exit");
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void print(String s) {
        System.out.print(s + "; ");
    }

    public boolean Admin() {

    }

    public boolean UIbody() {
        ur = new UserRepository();
        println(BOUNDRY);
        println("Please select your action:");
        print("(1) Exit");
        print("(2) Login");
        print("(3) Register");
        print("(4) Check User");
        print("(5) Browse all Information");
        print("(6) Delete record by username");
        println("");
        println(BOUNDRY);
        println("type here: ");
        Scanner sc = new Scanner(System.in);
        int action = sc.nextInt();

        if (action == 1) {
            return false;
        }

        if (action == 2) {
            println("--------Login page-------");
            println("Type username: ");
            String username = sc.next();
            println("Enter password here: ");
            String password = sc.next();
            if (!findUser(username)) {
                println("No such user.");
                return true;
            }
            boolean res = login(username, password);
            return true;
        }

        if (action == 3) {
            println("--------register page-------");
            println("Type username: ");
            String username = sc.next();
            println("Enter password here: ");
            String password = sc.next();
            println("Type firstname: ");
            String firstname = sc.next();
            println("Type lastname: ");
            String lastname = sc.next();
            println("Type emailaddress: ");
            String email = sc.next();
            println("Type birthdate (MM/dd/yyyy): ");
            String birthdate = sc.next();
            boolean res = register(username, password, firstname,
                    lastname, email, birthdate);
            if (res) {
                println("register success");
            } else {
                println("register fail");
            }
            return true;
        }

        if (action == 4) {
            println("Type username: ");
            String username = sc.next();
            boolean res = findUser(username);
            if (res) {
                println("user exists");
            } else {
                println("user doesn't exist");
            }
            return true;
        }

        if (action == 5){
            ur.browseAllInfo();
        }

        if (action == 6) {
            println("Type username: ");
            String username = sc.next();
            delete(username);
            return true;
        }

        return true;
    }

    public boolean login(String username, String password) {
        boolean res = ur.findbyLogin(username, password);
        if (res) {
            println("Login success!");
        } else {
            println("Password incorrect");
        }
        return res;
    }

    public boolean findUser(String username) {
        boolean res = ur.findbyUsername(username);
        return res;
    }

    public void browseAll() {
        ur.browseAllInfo();
    }

    public boolean register(String username, String password, String firstname,
                            String lastname, String email, String birthdate ) {
        if (findUser(username)) {
            println("User name already been used, please choose another username.");
            return false;
        } else {
            ur.save(username, password, firstname, lastname, email, birthdate);
            return true;
        }
    }

    public boolean delete(String username) {
        boolean exist = findUser(username);
        if (exist) {
            ur.delete(username);
            println("User: " + username + " deleted successfully.");
            return true;
        } else {
            println("user doesn't exist");
            return false;
        }
    }
}
