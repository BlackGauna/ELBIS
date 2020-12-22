package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class FXMLController_ArticleTable {
    //TODO import Table variables
    //TODO buttonEvents

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
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
    void createArticleClicked(ActionEvent event) {
        mainController.openEditorScene();
    }

    // Methods_______________________________________________________________________________________________________
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }


    // Getters,Setters_________________________________________________________________________________________________

}
