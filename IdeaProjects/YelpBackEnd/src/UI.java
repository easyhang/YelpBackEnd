import Model.Restrurant;
import Model.RestrurantComment;
import Model.User;
import Repository.AdminRepository;
import Repository.UserRepository;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by leon on 4/3/17.
 */

public class UI {
    static String BOUNDRY = "-------------------------------------";
    UserRepository ur = null;
    AdminRepository ad = null;

    public static void main(String[] args) {
        UI ui = new UI();
        boolean flag = true;
        while(flag) {
            ui.println(BOUNDRY);
            ui.println("Root -- Please select your action");
            Scanner sc = new Scanner(System.in);
            ui.print("(1) Exit");
            ui.print("(2) Admin");
            ui.print("(3) User");
            ui.println("");
            ui.println(BOUNDRY);
            ui.println("type here: ");
            int action = sc.nextInt();
            switch (action) {
                case 1:
                    flag = false;
                    break;
                case 2:
                    boolean mark = true;
                    while(mark) {
                        mark = ui.Admin();
                    }
                    flag = true;
                    break;
                case 3:
                    mark = true;
                    while(mark) {
                        mark = ui.UIbody();
                    }
                    flag = true;
                    break;
            }
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
        ad = new AdminRepository();
        println(BOUNDRY);
        println("Admin Interface -- Please select your action");
        print("(1) Exit");
        print("(2) Add Restrurant");
        print("(11) Browse all user Information");
        print("(12) Browse all restrurant Info");
        print("(13) Browse all food info");
        print("(21) Delete record by username");
        println("");
        println(BOUNDRY);
        println("type here: ");
        Scanner sc = new Scanner(System.in);
        int action = sc.nextInt();

        if (action == 1) {
            return false;
        }

        if (action == 2) {
            Restrurant restrurant = new Restrurant();
            println("--------restrurant add page-------");
            println("Type restrurant name: ");
            String name = sc.next();
            println("Enter address here: ");
            String address = sc.next();
            println("Enter type here (chinese food, cubian food, etc): ");
            String type = sc.next();
            restrurant.setRestrurantName(name);
            restrurant.setAddress(address);
            restrurant.setType(type);
            ad.saveRestrurant(restrurant);
        }

        if (action == 11){
            ad.browseAllInfo();
        }

        if (action == 12) {
            ad.browseAllRestrurantInfo();
        }

        if (action == 13) {
            println("Type restrurant Id here: ");
            int restrurantId = sc.nextInt();
            ad.browseFoodInfo(restrurantId);
        }

        if (action == 21) {
            println("Type username: ");
            String username = sc.next();
            delete(username);
        }
        return true;
    }

    public boolean UIbody() {
        ur = new UserRepository();
        println(BOUNDRY);
        println("User Interfate -- Please select your action:");
        print("(1) Exit");
        print("(2) Login");
        print("(3) Register");
        print("(4) Check User");
        print("(5) Browse all Information");
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
            login(username, password);
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

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstname(firstname);
            user.setLasttname(lastname);
            user.setEmailaddress(email);
            user.setBirthdate(birthdate);
            boolean res = register(user);
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

        return true;
    }

    // Login page, users take actions here.
    public boolean login(String username, String password) {
        boolean res = ur.findbyLogin(username, password);
        if (res) {
            println("Login success!");
            Scanner sc = new Scanner(System.in);
            while (res) {
                println("--------Logged in user interface -------");
                println("Please select you action: ");
                print("(1) Log out");
                print("(2) add comments");
                println("(11) search for restrurants");
                println("Type action number: ");

                int action = sc.nextInt();
                switch (action) {
                    case 1:
                        res = false;
                        break;
                    case 2:
                        println("Enter restrurant Id: ");
                        int restrurantId = sc.nextInt();
                        println("Enter your conment here");
                        String content = sc.next();
                        RestrurantComment restrurantComment = new RestrurantComment();
                        restrurantComment.setContent(content);
                        restrurantComment.setRestrurantId(restrurantId);
                        restrurantComment.setUserId(ur.getUserId(username));
                        break;
                    case 11:
                        println("enter a segment of the restrurant name:");
                        String seg = sc.next();
                        ur.searchForRestrurants(seg);
                        break;
                }
            }

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

    public boolean register(User user) {
        if (findUser(user.getUsername())) {
            println("User name already been used, please choose another username.");
            return false;
        } else {
            ur.save(user);
            return true;
        }
    }

    public boolean delete(String username) {
        boolean exist = findUser(username);
        if (exist) {
            ad.delete(username);
            println("User: " + username + " deleted successfully.");
            return true;
        } else {
            println("user doesn't exist");
            return false;
        }
    }
}
