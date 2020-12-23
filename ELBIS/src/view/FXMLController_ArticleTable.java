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
    private ResultSet rs;
    private ObservableList<Article> articleList;

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    // Ini_______________________________________________________________________________________________________

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

    // Methods_______________________________________________________________________________________________________
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTable();
    }

    private void getTable() {
        try {
            con = SQLConnection.ConnectDB();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM ARTICLE");
//            PreparedStatement pst = con.prepareStatement("SELECT article.id, article.title, article.creationDate, article.expireDate, article.lastEdit, status.name AS status, article.publisherComment\n" +
//                    "FROM article\n" +
//                    "JOIN status on\n" +
//                    "article.status = status.id");
            rs = pst.executeQuery();


            articleList = FXCollections.observableArrayList();

            while (rs.next()){
                articleList.add(new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("creationDate"),
                        rs.getString("expireDate"),
                        rs.getString("lastEdit"),
                        // status?
                        rs.getString("publisherComment")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<Article, String>("id"));
        col_title.setCellValueFactory(new PropertyValueFactory<Article, String>("Title"));
        col_creationDate.setCellValueFactory(new PropertyValueFactory<Article, String>("creationDate"));
        col_expireDate.setCellValueFactory(new PropertyValueFactory<Article, String>("expireDate"));
        col_lastEdit.setCellValueFactory(new PropertyValueFactory<Article, String>("lastEdit"));
        col_status.setCellValueFactory(new PropertyValueFactory<Article, String>("status"));
        col_publisherComment.setCellValueFactory(new PropertyValueFactory<Article, String>("publisherComment"));

        articleTable.setItems(articleList);
    }


    // Getters,Setters_________________________________________________________________________________________________

    public void setTableView(TableView table){
        this.articleTable = table;
    }

    public TableView getTableView(){
        return articleTable;
    }

    public TableColumn<Article, String> getCol_id() {
        return col_id;
    }

    public TableColumn<Article, String> getCol_title() {
        return col_title;
    }

    public TableColumn<Article, String> getCol_creationDate() {
        return col_creationDate;
    }

    public TableColumn<Article, String> getCol_expireDate() {
        return col_expireDate;
    }

    public TableColumn<Article, String> getCol_lastEdit() {
        return col_lastEdit;
    }

    public TableColumn<Article, String> getCol_status() {
        return col_status;
    }

    public TableColumn<Article, String> getCol_publisherComment() {
        return col_publisherComment;
    }
}
