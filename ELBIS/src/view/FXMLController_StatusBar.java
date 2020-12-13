package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FXMLController_StatusBar {

    // Atrrib_______________________________________________________________________________________________________

    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private Button btnHistory;

    @FXML
    private Label lblStatus;

    @FXML
    void historyClicked(ActionEvent event) {

    }

    // Methods_______________________________________________________________________________________________________

    public void setStatus(String status){
        lblStatus.setText(status);
        //Todo Create History
        //Todo Add status to history
    }

}
