package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Status;

public class FXMLController_ManageSubmission extends ELBIS_FXMLController {

    // Atrrib_______________________________________________________________________________________________________
    int articleId = 0;
    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private TextField txtComment;

    @FXML
    private Button btnAuthorize;

    @FXML
    private Button btnDecline;
    // Methods_______________________________________________________________________________________________________
    @FXML
    void authorizeClicked(ActionEvent event) {
        try {
            if(!getTxtComment().equals("")){
            mainController.submitArticle(articleId,Status.Öffentlich,getTxtComment());
            mainController.sideStage.close();
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Achtung");
                alert.setContentText("Bitte geben sie einen (längeren) Kommentar ein.");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
        }
    }

    @FXML
    void declineClicked(ActionEvent event) {
        try {
            if(!getTxtComment().equals("")){
                mainController.submitArticle(articleId,Status.Abgelehnt,getTxtComment());
                mainController.sideStage.close();
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Achtung");
                alert.setContentText("Bitte geben sie einen (längeren) Kommentar ein.");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
        }
    }
    // Getters,Setters_________________________________________________________________________________________________
    public String getTxtComment(){
        return txtComment.getText();
    }
    public void setArticleId(int articleID){
        this.articleId = articleID;
    }
}
