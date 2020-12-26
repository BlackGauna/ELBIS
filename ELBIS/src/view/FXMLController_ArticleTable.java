package view;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
import model.SQLConnection;
import model.Status;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class FXMLController_ArticleTable implements Initializable {

    private static Connection con;
    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    private ResultSet rs;
   // private ObservableList<Article> articleList;
    // Ini_______________________________________________________________________________________________________
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
       articleTable.getColumns().add(new TableColumn<Article, String>("id"));
       articleTable.getColumns().add(new TableColumn<Article, String>("title"));
       articleTable.getColumns().add(new TableColumn<Article, String>("creationDate"));
       articleTable.getColumns().add(new TableColumn<Article, String>("expireDate"));
       articleTable.getColumns().add(new TableColumn<Article, String>("lastEdit"));
       articleTable.getColumns().add(new TableColumn<Article, String>("publisherComment"));
       dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
   }
    // UI_______________________________________________________________________________________________________
    @FXML
    private TableView<Article> articleTable = new TableView<>();

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
    private TableColumn<Article, String> col_id = new TableColumn<>();

    @FXML
    private TableColumn<Article, String> col_title = new TableColumn<>();

    @FXML
    private TableColumn<Article, String> col_creationDate = new TableColumn<>();

    @FXML
    private TableColumn<Article, String> col_expireDate = new TableColumn<>();

    @FXML
    private TableColumn<Article, String> col_lastEdit = new TableColumn<>();

    @FXML
    private TableColumn<Article, String> col_status = new TableColumn<>();

    @FXML
    private TableColumn<Article, String> col_publisherComment = new TableColumn<>();

    @FXML
    void createArticleClicked(ActionEvent event) {
        mainController.openEditorScene();
    }

    @FXML
    private Button btnRefresh;

    @FXML
    void refreshClicked(ActionEvent event) {
        setTableView(mainController.refreshArticleTable(getTableView()));
        mainController.setStatus("Refreshing ArticleTable...");
    }

    // Methods_______________________________________________________________________________________________________
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Getters,Setters_________________________________________________________________________________________________

    public TableView getTableView() {
        return articleTable;
    }

    public void setTableView(TableView table) {
        this.articleTable = table;
    }



}
