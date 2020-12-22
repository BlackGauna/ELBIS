package view;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.*;

import java.io.IOException;


public class FXMLController_MainApplication {

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    //Loaders
    FXMLLoader statusBarLoader;
    FXMLLoader headBarLoader;
    FXMLLoader userTableLoader;
    FXMLLoader moderationTableLoader;
    FXMLLoader administrationTableLoader;
    //Panes
    Pane statusBar;
    Pane headBar;
    Pane articleTable;
    Pane moderationTable;
    Pane administrationTable;
    Tab userTab;
    Tab moderationTab;
    Tab administrationTab;
    //Controllers
    FXMLController_StatusBar statusBarController;
    FXMLController_HeadBar headBarController;
    FXMLController_ArticleTable articleTableController;
    FXMLController_ModerationTable moderationTableController;
    FXMLController_AdministrationTable administrationTableController;

    // Ini_______________________________________________________________________________________________________

    @FXML
    public void initialize() {
        //TODO Create Head bar and add refresh button
        try {
            //StatusBar
            statusBarLoader = new FXMLLoader(getClass().getResource("/view/StatusBar.fxml"));
            statusBar = (Pane) statusBarLoader.load();
            statusBarController = statusBarLoader.getController();
            borderPane.setBottom(statusBar);

            //HeadBar
            headBarLoader = new FXMLLoader(getClass().getResource("/view/HeadBar.fxml"));
            headBar = (Pane) headBarLoader.load();
            headBarController = headBarLoader.getController();
            borderPane.setTop(headBar);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // UI_______________________________________________________________________________________________________
    @FXML
    private TabPane tabPane;

    @FXML
    private BorderPane borderPane;


    // Methods_______________________________________________________________________________________________________
    //reference to mainView
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void setStatus(String newStatus){
        System.out.println("Status set: \"" + newStatus+"\"");
        statusBarController.setStatus("\""+newStatus+"\"");
    }

    public void openTabs(User user) {
        try {
            if (user instanceof Administrator) {
                addUserTab();
                addModeratorTab();
                addAdminTab();

            } else if (user instanceof Moderator) {
                addUserTab();
                addModeratorTab();
            } else if (user instanceof User) {
                addUserTab();
            }
        } catch(IOException io){
            io.printStackTrace();
        }
    }

    public void addUserTab() throws IOException {
        userTableLoader = new FXMLLoader(getClass().getResource("/view/Pane_ArticleTable.fxml"));
        articleTable = (Pane) userTableLoader.load();
        articleTableController = userTableLoader.getController();
        articleTableController.setMainController(mainController);

        userTab = new Tab();
        userTab.setText("Artikelverwaltung");
        userTab.setContent(articleTable);

        tabPane.getTabs().add(userTab);
        articleTableController.setTableView(mainController.refreshArticleTable(articleTableController.getTableView()));

    }
    public void addModeratorTab()throws IOException{
        moderationTableLoader = new FXMLLoader(getClass().getResource("/view/Pane_ModerationTable.fxml"));
        moderationTable = (Pane) moderationTableLoader.load();
        moderationTableController = moderationTableLoader.getController();
        articleTableController.setMainController(mainController);

        moderationTab = new Tab();
        moderationTab.setText("Moderation");
        moderationTab.setContent(moderationTable);

        tabPane.getTabs().add(moderationTab);
        //TODO give actual Table
        mainController.refreshModerationTable(new TableView());

    }
    public void addAdminTab()throws IOException{
        administrationTableLoader = new FXMLLoader(getClass().getResource("/view/Pane_AdministrationTable.fxml"));
        administrationTable = (Pane) administrationTableLoader.load();
        administrationTableController = administrationTableLoader.getController();
        articleTableController.setMainController(mainController);

        administrationTab = new Tab();
        administrationTab.setText("Administration");
        administrationTab.setContent(administrationTable);

        tabPane.getTabs().add(administrationTab);
        //TODO give actual Table
        mainController.refreshAdministrationTable(new TableView());
    }

}
