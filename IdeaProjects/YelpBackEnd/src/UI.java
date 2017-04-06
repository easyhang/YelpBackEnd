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
        ui.println("something wrong.");
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void print(String s) {
        System.out.print(s + "; ");
    }

    public boolean UIbody() {
        ur = new UserRepository();
        println(BOUNDRY);
        println("Please select your action:");
        print("(1) Login");
        print("(2) Register");
        print("(3) Check User");
        print("(4) Browse all Information");
        println("");
        println(BOUNDRY);
        println("type here: ");
        Scanner sc = new Scanner(System.in);
        int action = sc.nextInt();

        if (action == 1) {
            println("--------Login page-------");
            println("Type username: ");
            String username = sc.next();
            println("Enter password here: ");
            String password = sc.next();
            boolean res = login(username, password);
            return res;
        }

        if (action == 2) {
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
            return res;
        }
        return true;
    }

    public boolean login(String username, String password) {
        boolean res = ur.findbyLogin(username, password);
        if (res) {
            println("Login success!");
        } else {
            println("Login fail!");
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
            println("User name already been users, please choose another username.");
            return false;
        } else {
            ur.save(username, password, firstname, lastname, email, birthdate);
            return true;
        }
    }
}
