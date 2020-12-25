package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class FXMLController_StatusBar {

    // Atrrib_______________________________________________________________________________________________________
    LinkedList<String> statusHist = new LinkedList<String>();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");


    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private Button btnHistory;

    @FXML
    private Label lblStatus;

    @FXML
    void historyClicked(ActionEvent event) {
        openHistory();
    }

    // Methods_______________________________________________________________________________________________________

    public void setStatus(String status){
        String now = dtf.format(LocalDateTime.now());
        String stampedStatus = now+" "+status;
        lblStatus.setText(status);
        statusHist.add(stampedStatus);
    }

    public void openHistory(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("History");

        TextArea statusArea = new TextArea();
        statusArea.setWrapText(true);
        statusArea.setEditable(false);

        for(int i = 0; i < statusHist.size(); i++){
            statusArea.setText(statusArea.getText()+"\n"+statusHist.get(i));
        }
        alert.getDialogPane().setContent(statusArea);
        alert.setResizable(true);

        alert.setHeaderText("Status history");
        //statusArea.setScrollTop(Double.MAX_VALUE);
        statusArea.selectPositionCaret(statusArea.getLength());
        statusArea.deselect();
        alert.showAndWait();
    }

}
