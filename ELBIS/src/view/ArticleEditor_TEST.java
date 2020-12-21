package view;

import javax.swing.JOptionPane;

import javafx.application.Application;

public class ArticleEditor_TEST
{

    //VM args:
    //--module-path "\lib\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.web


    public static void main(String[] args) {

        new ArticleEditor_TEST();
        try {
            Application.launch(ArticleEditor.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error on launch in View_TEST", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}