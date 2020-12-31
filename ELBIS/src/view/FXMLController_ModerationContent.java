package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController_ModerationContent extends ELBIS_FXMLController implements Initializable {

    // Atrrib_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private TableView<User> userTable = new TableView<>();
    @FXML
    private TableView<Article> submissionTable = new TableView<>();
    @FXML
    private TableView<Article> articleTable = new TableView<>();

    @FXML
    private Accordion dropDownAccordion;
    @FXML
    private TitledPane tPane_NewSubmissions;
    @FXML
    private TitledPane tPane_ManageArticles;
    @FXML
    private TitledPane tPane_ManageUsers;
    @FXML
    private ButtonBar btnBar;
    @FXML
    private Button btn_CreateUser;
    @FXML
    private Button btnRefresh;

    // Ini_______________________________________________________________________________________________________
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submissionTable.getColumns().add(new TableColumn<Article, String>("ID"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Title"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Created"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Expires"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Last Edit"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Status"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Topic"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Author"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Publisher"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Publisher comment"));

        articleTable.getColumns().add(new TableColumn<Article, String>("ID"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Title"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Created"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Expires"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Last Edit"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Status"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Topic"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Author"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Publisher"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Publisher comment"));

        userTable.getColumns().add(new TableColumn<User, String>("ID"));
        userTable.getColumns().add(new TableColumn<User, String>("E-Mail"));
        userTable.getColumns().add(new TableColumn<User, String>("Name"));
        userTable.getColumns().add(new TableColumn<User, String>("Gender"));
        userTable.getColumns().add(new TableColumn<User, String>("Role"));
        userTable.getColumns().add(new TableColumn<User, String>("Address"));
        userTable.getColumns().add(new TableColumn<User, String>("Date of Birth"));
        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }

    @FXML
    void createUserClicked(ActionEvent event) {
        try {
            mainController.callSideStage(sideStageState.createUser);
            refreshModerationContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void refreshClicked(ActionEvent event) {
        refreshModerationContent();
    }

    // Methods_______________________________________________________________________________________________________

    public void refreshModerationContent() {
        mainController.setStatus("Refreshing ModeratorContent...");
        setContent_SubmissionTable(mainController.refreshModerationContent_SubmissionTable(getContent_SubmissionTable()));
        setContent_ArticleTable(mainController.refreshModerationContent_ArticleTable(getContent_ArticleTable()));
        setContent_UserTable(mainController.refreshModerationContent_UserTable(getContent_UserTable()));
    }

    // Getters,Setters_________________________________________________________________________________________________

    public TableView getContent_UserTable() {
        return userTable;
    }
    public void setContent_UserTable(TableView table) {
        this.userTable = table;
    }

    public TableView getContent_SubmissionTable() {
        return submissionTable;
    }
    public void setContent_SubmissionTable(TableView table) {
        this.submissionTable = table;
    }

    public TableView getContent_ArticleTable() {
        return articleTable;
    }
    public void setContent_ArticleTable(TableView table){
        this.articleTable = table;
    }

}
