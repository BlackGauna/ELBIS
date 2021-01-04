package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Status;

public class FXMLController_ManageSubmission extends ELBIS_FXMLController {

    // Atrrib_______________________________________________________________________________________________________

    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private TextArea txtComment;

    @FXML
    private Button btnAuthorize;

    @FXML
    private Button btnDecline;
    // Methods_______________________________________________________________________________________________________
    @FXML
    void authorizeClicked(ActionEvent event) {
        try {
            //TODO throw exception if any field is empty
            //mainController.submitArticle(Status.Öffentlich,getTxtComment());
            mainController.sideStage.close();
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Bitte geben sie einen Kommentar ein.");
            alert.setHeaderText(null);
            alert.showAndWait();
            e.printStackTrace();
        } finally{
        }
    }

    @FXML
    void declineClicked(ActionEvent event) {
        try {
            //TODO throw exception if any field is empty
            //mainController.submitArticle(Status.Abgelehnt,getTxtComment());
            mainController.sideStage.close();
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Bitte geben sie einen Kommentar ein.");
            alert.setHeaderText(null);
            alert.showAndWait();
            e.printStackTrace();
        } finally{
        }
    }
    // Getters,Setters_________________________________________________________________________________________________
    public String getTxtComment(){
        return txtComment.getText();
    }
}
