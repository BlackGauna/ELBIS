package model;

import java.util.LinkedList;
import java.util.List;

public class Test {

    public static void main(String[] args) {


        DataController dataController = new DataController();
        if(!dataController.open()) {
            System.out.println("Can't open datacontroller");
            return;
        }



        dataController.DBSendNewArticle("Hello World","This is Mambo no.5","Hell Yeah");

        dataController.close();

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter an Article title: ");
//        String title = scanner.nextLine();



    }
}

