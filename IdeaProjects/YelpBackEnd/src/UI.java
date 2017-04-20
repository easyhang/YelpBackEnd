import Model.Food;
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
    static String BOUNDRY = "-----------------------------------" +
            "---------------------------------------------------------------";
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
            ui.print1("type here:\t");
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
    public void print1(String s) {
        System.out.print(s);
    }
    public void printt(String s) {
        System.out.print(s + "\t");
    }

    public boolean Admin() {
        ad = new AdminRepository();
        println(BOUNDRY);
        println("Admin Interface -- Please select your action -------------");
        print("(1) Exit");
        print("(2) Add Restrurant");
        print("(3) Add food");
        print("(11) Browse all user Information");
        println("(12) Browse all restrurant Info");
        print("(13) Browse all food info");
        print("(21) Delete record by username");
        println("");
        println(BOUNDRY);
        print1("type here:\t");
        Scanner sc = new Scanner(System.in);
        int action = sc.nextInt();

        if (action == 1) {
            return false;
        }

        if (action == 2) {
            Restrurant restrurant = new Restrurant();
            println(">>>>>>>>>>>>>>>>> restrurant add page >>>>>>>>>>>>>>>>>>>");
            sc.nextLine();
            print1("Type restrurant name:\t");
            String name = sc.nextLine();
            print1("Enter address here:\t");
            String address = sc.nextLine();
            print1("Enter type here (chinese food, cubian food, etc):\t");
            String type = sc.nextLine();
            restrurant.setRestrurantName(name);
            restrurant.setAddress(address);
            restrurant.setType(type);
            ad.saveRestrurant(restrurant);
        }

        if (action == 3) {
            Food food = new Food();
            println(">>>>>>>>>>>>>>>>> food add page >>>>>>>>>>>>>>>>>>>");
            sc.nextLine();
            print1("Type food name:\t");
            String name = sc.next();
            print1("Enter restrurant Id here:\t");
            int resId = sc.nextInt();
            food.setFoodName(name);
            food.setRestrurantId(resId);
            ad.saveFood(food);
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
        print1("type here:\t");
        Scanner sc = new Scanner(System.in);
        int action = sc.nextInt();

        if (action == 1) {
            return false;
        }

        if (action == 2) {
            println("--------Login page-------");
            print1("Type username:\t");
            String username = sc.next();
            print1("Enter password here:\t");
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
            print1("Type username:\t");
            String username = sc.next();
            print1("Enter password here:\t");
            String password = sc.next();
            print1("Type firstname:\t");
            String firstname = sc.next();
            print1("Type lastname:\t");
            String lastname = sc.next();
            print1("Type emailaddress:\t");
            String email = sc.next();
            print1("Type birthdate (MM/dd/yyyy):\t");
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
            print1("Type username:\t");
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
                println("------------- Logged in user interface ------------");
                println("Please select you action: ");
                print("(1) Log out");
                print("(2) add comments");
                print("(3) follow other users");
                print("(4) browse followings");
                println("(5) watch one followings' comments");
                println("(11) search for restrurants");
                printt("Type action number:\t");

                int action = sc.nextInt();
                switch (action) {
                    case 1:
                        res = false;
                        break;
                    case 2:
                        printt("Enter restrurant Id:\t");
                        int restrurantId = sc.nextInt();
                        sc.nextLine();
                        printt("Enter your content here:\t");
                        String content = sc.nextLine();

                        RestrurantComment restrurantComment = new RestrurantComment();
                        restrurantComment.setContent(content);
                        restrurantComment.setRestrurantId(restrurantId);
                        restrurantComment.setUserId(ur.getUserId(username));
                        break;
                    case 3:
                        printt("Enter followee's ID:\t");
                        int followeeId = sc.nextInt();
                        if (!ur.findbyUserId(followeeId)) {
                            println("user doesn't exist");
                            break;
                        }
                        int followerId = ur.getUserId(username);
                        int val = ur.follow(followerId, followeeId);
                        if (val == -1) {
                            println("followship meets an error");
                        } else if (val == 0) {
                            println("followee already been followed by current user");
                        } else if (val == 1) {
                            println("follow success!");
                        }
                        break;
                    case 4:
                        int id = ur.getUserId(username);
                        ur.browseFollowings(id);
                        break;
                    case 5:
                        int thisId = ur.getUserId(username);
                        printt("Enter a following's Id: ");
                        int followingId = sc.nextInt();
                        ur.watchOneFollowingComments_AllRestrurants(thisId, followingId);
                        break;
                    case 11:
                        printt("enter a segment of the restrurant name (* for all):\t");
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
