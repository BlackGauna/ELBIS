package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FXMLController_EditorSelector extends ELBIS_FXMLController
{
    @FXML
    Button standardButton;

    @FXML
    Button videoButton;

    public void onStandardButtonClicked () throws Exception
    {
        mainController.openEditorScene();
    }

    public void onVideoButtonClicked() throws Exception
    {
        mainController.openVideoEditor();
    }

}
