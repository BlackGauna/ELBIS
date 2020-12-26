package view;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
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

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    // Ini_______________________________________________________________________________________________________
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }
    // UI_______________________________________________________________________________________________________

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


    // Methods_______________________________________________________________________________________________________

    // Getters,Setters_________________________________________________________________________________________________
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

}