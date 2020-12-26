package view;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SQLConnection;
import model.User;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FXMLController_AdministrationTable implements Initializable {
    //TODO import Table variables
    //TODO buttonEvents

    private static Connection con;
    private ResultSet rs;
    private ObservableList<User> userList;

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    // Ini_______________________________________________________________________________________________________

    public void initialize() {
        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }
    // UI_______________________________________________________________________________________________________

    @FXML
    private TableView<User> userTable = new TableView<>();

    @FXML
    private Accordion dropDownAccordion;

    @FXML
    private TitledPane tPane_ManageTopics;

    @FXML
    private TitledPane tPane_ManageRoles;

    @FXML
    private ButtonBar btnBar;

    @FXML
    private Button btnCreateTopic;

    @FXML
    private TableColumn<User, String> col_id = new TableColumn<>();

    @FXML
    private TableColumn<User, String> col_email = new TableColumn<>();

    @FXML
    private TableColumn<User, String> col_name = new TableColumn<>();

    @FXML
    private TableColumn<User, String> col_address = new TableColumn<>();

    @FXML
    private TableColumn<User, String> col_gender = new TableColumn<>();

    @FXML
    private TableColumn<User, String> col_dob = new TableColumn<>();

    // Methods_______________________________________________________________________________________________________
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    //TODO Throws warnings
        //getUserTable();
    }

    // UserTable to manage users
    private void getUserTable() {
        try {
            con = SQLConnection.ConnectDB();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM USER");
            rs = pst.executeQuery();


            userList = FXCollections.observableArrayList();

            while (rs.next()){
                userList.add(new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("address")
//                        rs.getString("gender"), // TODO: gender
//                        rs.getString("dateOfBirth") // TODO: dateOfBirth
                ));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        col_email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
//        col_role.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        col_name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        col_address.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
        col_gender.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
        col_dob.setCellValueFactory(new PropertyValueFactory<User, String>("dateOfBirth"));


        userTable.setItems(userList);
    }

    // Getters,Setters_________________________________________________________________________________________________

}