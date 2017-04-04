import Model.User;
import Repository.UserRepository;

import java.util.Scanner;

/**
 * Created by leon on 4/3/17.
 */

public class UI {
    static String BOUNDRY = "-------------------------------------";
    UserRepository ur = null;

    public static void main(String[] args) {

    }

    public void println(String s) {
        System.out.println(s);
    }

    public void print(String s) {
        System.out.print(s + "; ");
    }

    public void UIbody() {
        ur = new UserRepository();
        println(BOUNDRY);
        println("Please select your action:");
        print("(1) Login");
        print("(2) Register");
        print("(3) Check User");
        print("(4) Browse all Information");
        println(BOUNDRY);
        print("type here: ");
        Scanner sc = new Scanner(System.in);
        int action = sc.nextInt();

        if (action == 1) {

        }
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

    public boolean register(User user) {
        if (findUser(user.getUsername())) {
            println("User name already been users, please choose another username.");
            register(user);
        } else {

        }
    }
}
