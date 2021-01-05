package view;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.Article;
import model.Topic;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController_UserContent extends ELBIS_FXMLController implements Initializable {

    // Atrrib_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private TableView<Article> articleTable = new TableView<>();
    @FXML
    private TableView<Topic> topicTable = new TableView<>();
    @FXML
    private Accordion dropDownAccordion;
    @FXML
    private TitledPane tPane_MyArticles;
    @FXML
    private TitledPane tPane_Topics;
    @FXML
    private ButtonBar btnBar;
    @FXML
    private Button btnCreateArticle;
    @FXML
    private Button btnRefresh;

    // Ini_______________________________________________________________________________________________________
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        articleTable.getColumns().add(new TableColumn<Article, String>("ID"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Titel"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Erstellt"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Ablauf"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Bearbeitet"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Status"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Topic"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Autor"));
        articleTable.getColumns().add(new TableColumn<Article, String>("Herausgeber"));
        TableColumn commentColumn = new TableColumn<Article, Boolean>("Herausgeber Kommentar");
        commentColumn.setPrefWidth(150);
        articleTable.getColumns().add(commentColumn);
        TableColumn editColumn = new TableColumn<Article, Boolean>(" ");
        editColumn.setSortable(false);
        articleTable.getColumns().add(editColumn);
        TableColumn deleteColumn = new TableColumn<Article, Boolean>(" ");
        deleteColumn.setSortable(false);
        articleTable.getColumns().add(deleteColumn);
        TableColumn submitColumn = new TableColumn<Article, Boolean>(" ");
        submitColumn.setSortable(false);
        articleTable.getColumns().add(submitColumn);
        TableColumn showCommentColumn = new TableColumn<Article, Boolean>(" ");
        showCommentColumn.setSortable(false);
        articleTable.getColumns().add(showCommentColumn);

        topicTable.getColumns().add(new TableColumn<Topic, String>("ID"));
        topicTable.getColumns().add(new TableColumn<Topic, String>("Bereich"));
        topicTable.getColumns().add(new TableColumn<Topic, String>("Eltern-Bereich"));

        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }

    @FXML
    void createArticleClicked(ActionEvent event) throws Exception {
        mainController.openSelector();
    }

    @FXML
    void refreshClicked(ActionEvent event) {
        refreshUserContent();
    }

    // Methods_______________________________________________________________________________________________________

    public void refreshUserContent() {
        setContent_ArticleTable(mainController.refreshUserContent_ArticleTable(getContent_ArticleTable()));
        setContent_TopicTable(mainController.refreshUserContent_TopicTable(getContent_TopicTable()));
    }

    // Getters,Setters_________________________________________________________________________________________________

    public TableView getContent_ArticleTable() {
        return articleTable;
    }

    public void setContent_ArticleTable(TableView table) {
        this.articleTable = table;
    }

    public TableView getContent_TopicTable() {
        return topicTable;
    }

    public void setContent_TopicTable(TableView table) {
        this.topicTable = table;
    }
}
