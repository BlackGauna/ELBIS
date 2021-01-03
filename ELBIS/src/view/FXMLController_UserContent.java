package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Article;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController_UserContent extends ELBIS_FXMLController implements Initializable {

    // Atrrib_______________________________________________________________________________________________________

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
       articleTable.getColumns().add(new TableColumn<Article, String>("Herausgeber Kommentar"));
       TableColumn actionColumn = new TableColumn<Article, Boolean>(" ");
       actionColumn.setSortable(false);
       articleTable.getColumns().add(actionColumn);

       dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
   }
    // UI_______________________________________________________________________________________________________
    @FXML
    private TableView<Article> articleTable = new TableView<>();

    @FXML
    private TreeView<Article> topicTree = new TreeView<>();

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
    void createArticleClicked(ActionEvent event) throws Exception
    {
        mainController.openEditorScene();
    }

    @FXML
    private Button btnRefresh;

    @FXML
    void refreshClicked(ActionEvent event) {
        refreshUserContent();
    }

    // Methods_______________________________________________________________________________________________________

    public void refreshUserContent(){
        mainController.setStatus("Refreshing UserContent...");
        setContent_ArticleTable(mainController.refreshUserContent_ArticleTable(getContent_ArticleTable()));
        setContent_TopicTree(mainController.refreshUserContent_ArticleTree(getContent_TreeView()));
    }

    // Getters,Setters_________________________________________________________________________________________________

    public TableView getContent_ArticleTable() {
        return articleTable;
    }

    public void setContent_ArticleTable(TableView table) {
        this.articleTable = table;
    }

    public TreeView getContent_TreeView(){
        return topicTree;
    }

    public void setContent_TopicTree(TreeView tree) {
        this.topicTree = tree;
    }



}
