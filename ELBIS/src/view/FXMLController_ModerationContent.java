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
        submissionTable.getColumns().add(new TableColumn<Article, String>("Titel"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Erstellt"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Ablauf"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Bearbeitet"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Status"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Bereich"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Autor"));
        submissionTable.getColumns().add(new TableColumn<Article, String>("Herausgeber"));
        TableColumn commentColumn = new TableColumn<Article, Boolean>("Herausgeber Kommentar");
        commentColumn.setPrefWidth(150);
        submissionTable.getColumns().add(commentColumn);
        TableColumn openColumn = new TableColumn<Article, Boolean>(" ");
        openColumn.setSortable(false);
        submissionTable.getColumns().add(openColumn);
        TableColumn manageSubmitColumn = new TableColumn<Article, Boolean>(" ");
        manageSubmitColumn.setSortable(false);
        submissionTable.getColumns().add(manageSubmitColumn);

        articleTable.getColumns().add(new TableColumn<Article, String>("ID"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Titel"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Erstellt"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Ablauf"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Bearbeitet"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Status"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Bereich"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Autor"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Herausgeber"));
        TableColumn commentColumn2 = new TableColumn<Article, Boolean>("Herausgeber Kommentar");
        commentColumn2.setPrefWidth(150);
        articleTable.getColumns().add(commentColumn2);
        TableColumn editArticleColumn = new TableColumn<Article, Boolean>(" ");
        editArticleColumn.setSortable(false);
        articleTable.getColumns().add(editArticleColumn);
        TableColumn deleteArticleColumn = new TableColumn<Article, Boolean>(" ");
        deleteArticleColumn.setSortable(false);
        articleTable.getColumns().add(deleteArticleColumn);
        TableColumn showCommentColumn2 = new TableColumn<Article, Boolean>(" ");
        showCommentColumn2.setSortable(false);
        articleTable.getColumns().add(showCommentColumn2);

        userTable.getColumns().add(new TableColumn<User, String>("ID"));
        userTable.getColumns().add(new TableColumn<User, String>("E-Mail"));
        userTable.getColumns().add(new TableColumn<User, String>("Name"));
        userTable.getColumns().add(new TableColumn<User, String>("Geschlecht"));
        userTable.getColumns().add(new TableColumn<User, String>("Rolle"));
        userTable.getColumns().add(new TableColumn<User, String>("Anschrift"));
        userTable.getColumns().add(new TableColumn<User, String>("Geburtsdatum"));
        TableColumn deleteUserColumn = new TableColumn<User, Boolean>(" ");
        deleteUserColumn.setSortable(false);
        userTable.getColumns().add(deleteUserColumn);
        TableColumn editUserColumn = new TableColumn<User, Boolean>(" ");
        editUserColumn.setSortable(false);
        userTable.getColumns().add(editUserColumn);
        TableColumn changePasswordColumn = new TableColumn<User, Boolean>(" ");
        changePasswordColumn.setSortable(false);
        userTable.getColumns().add(changePasswordColumn);


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
