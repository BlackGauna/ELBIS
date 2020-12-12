package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Test {

    public static void main(String[] args) {


        DataController dataController = new DataController();
        if(!dataController.open()) {
            System.out.println("Can't open datacontroller");
            return;
        }


        User user = dataController.DBLoadUser(0);
        System.out.println(user.getName());
        System.out.println(user.geteMail());
        dataController.DBLoadUser(5);

        dataController.close();

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter an Article title: ");
//        String title = scanner.nextLine();


        /*
        public User DBLoadUser(int id) {
            try {
                LoadUser.setInt(1, id);
                LoadUser.execute();
                ResultSet rs = LoadUser.getResultSet();
                String name = null;
                String email= null;
                while (rs.next()) {
                    //user.setId(id);
                    //user.seteMail(rs.getString(2));
                    //user.setName(rs.getString(5));

                    //System.out.println(rs.getString(2));
                    name = rs.getString(2);
                    email = rs.getString(1);

                    //user.setGender(rs.getInt(7));
                    //user.setDateOfBirth(rs.getDate(8));
                }
                User user = new User(id,name,email,null,null,null);
                return user;
            } catch (SQLException e) {
                System.out.println("Couldn't load User: " + e.getMessage());
                return null;
            }


        }
        */

    }
}

