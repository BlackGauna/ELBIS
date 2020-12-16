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

//        dataController.DBSendNewArticle("Delicious Marshmellows","Food",1,"yes I really do.");
 //dataController.DBSendNewUser("asd.123@gmail.com","12345","Hakuna Matata","Mars",1,"13.02.1997");

        //dataController.DBEditArticle(1,"SANTA","WEIHNACHTEN","I LIKE GIFTS","HES FAT");
        //dataController.DBEditUser(1,"FATBOI@gmail.com","santaistgeil","Santa","NORTH POLE",1,"20.10.1940");

        //dataController.DBEditTopic(1,"Christmas",null);

       //User user = dataController.DBLoadUser(0);
      // System.out.println(user.getDateOfBirth());
//       dataController.DBEditUser(6,"MARSHMELLO",null,"TASTY",null,1,null);
        //dataController.DBEditTopic(2,"RAMBO","1");
        //System.out.println(user.getName());
        //System.out.println(user.geteMail());
       //System.out.println(dataController.DBLoadUser(0));
//         dataController.DBLoadTopic(1);
        //System.out.println(dataController.DBLoadTopic(1));
//        dataController.DBLoadAllArticle();


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

