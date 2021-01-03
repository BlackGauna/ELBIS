package view;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.*;

import java.io.IOException;


public class FXMLController_MainApplication extends ELBIS_FXMLController {

    // Atrrib_______________________________________________________________________________________________________
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
    FXMLController_UserContent articleTableController;
    FXMLController_ModerationContent moderationTableController;
    FXMLController_AdministrationContent administrationTableController;

    // Ini_______________________________________________________________________________________________________
    // UI_______________________________________________________________________________________________________
    @FXML
    private TabPane tabPane;
    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize() {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Methods_______________________________________________________________________________________________________
    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);
        headBarController.setMainController(mainController);
    }

    public void setStatus(String newStatus) {
        System.out.println("Status set: \"" + newStatus + "\"");
        statusBarController.setStatus("\"" + newStatus + "\"");
    }

    public void refreshAllContent(User user){
        if (user instanceof Administrator) {
            articleTableController.refreshUserContent();
            moderationTableController.refreshModerationContent();
            administrationTableController.refreshAdministrationContent();
        } else if (user instanceof Moderator) {
            articleTableController.refreshUserContent();
            moderationTableController.refreshModerationContent();
        } else if (user instanceof User) {
            articleTableController.refreshUserContent();
        }
        mainController.setStatus("Kontent neu geladen");
    }

    public void openTabs(User user) {
        try {

            if (user instanceof Administrator) {
                headBarController.showUser(mainController.getActiveUser().getName() + " (" + mainController.getActiveUser().getEmail() + ")", "Administrator");
                addUserTab();
                addModeratorTab();
                addAdminTab();
            } else if (user instanceof Moderator) {
                headBarController.showUser(mainController.getActiveUser().getName() + " (" + mainController.getActiveUser().getEmail() + ")", "Moderator");
                addUserTab();
                addModeratorTab();
            } else if (user instanceof User) {
                headBarController.showUser(mainController.getActiveUser().getName() + " (" + mainController.getActiveUser().getEmail() + ")", "User");
                addUserTab();
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void removeTabs(){
        tabPane.getTabs().removeAll(tabPane.getTabs());
    }

    public void addUserTab() throws IOException {

        userTableLoader = new FXMLLoader(getClass().getResource("/view/Pane_UserContent.fxml"));
        articleTable = (Pane) userTableLoader.load();
        articleTableController = userTableLoader.getController();
        articleTableController.setMainController(mainController);

        userTab = new Tab();
        userTab.setText("Artikelverwaltung");
        userTab.setContent(articleTable);

        tabPane.getTabs().add(userTab);
        //initial load of UserContents
        //articleTableController.setContent_ArticleTable(mainController.refreshUserContent_ArticleTable(articleTableController.getContent_ArticleTable()));
        articleTableController.refreshUserContent();

    }

    public void addModeratorTab() throws IOException {
        moderationTableLoader = new FXMLLoader(getClass().getResource("/view/Pane_ModerationContent.fxml"));
        moderationTable = (Pane) moderationTableLoader.load();
        moderationTableController = moderationTableLoader.getController();
        moderationTableController.setMainController(mainController);

        moderationTab = new Tab();
        moderationTab.setText("Moderation");
        moderationTab.setContent(moderationTable);

        tabPane.getTabs().add(moderationTab);
        //initial load of ModerationContents
        //moderationTableController.setContent_UserTable(mainController.refreshModerationContent_UserTable(moderationTableController.getContent_UserTable()));
        moderationTableController.refreshModerationContent();

    }

    public void addAdminTab() throws IOException {
        administrationTableLoader = new FXMLLoader(getClass().getResource("/view/Pane_AdministrationContent.fxml"));
        administrationTable = (Pane) administrationTableLoader.load();
        administrationTableController = administrationTableLoader.getController();
        administrationTableController.setMainController(mainController);

        administrationTab = new Tab();
        administrationTab.setText("Administration");
        administrationTab.setContent(administrationTable);

        tabPane.getTabs().add(administrationTab);
        //initial load of AdministrationContent
        //administrationTableController.setContent_TopicTable(mainController.refreshAdministrationContent_TopicTable(administrationTableController.getContent_TopicTable()));
        administrationTableController.refreshAdministrationContent();
    }

}
