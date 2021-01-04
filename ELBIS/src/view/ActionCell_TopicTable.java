package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import model.Topic;

public class ActionCell_TopicTable extends TableCell<Topic, Boolean> {

    // Atrrib_______________________________________________________________________________________________________
    Button btn;
    MainController mainController;
    Topic hisTopic;

    // Ctor_______________________________________________________________________________________________________
    public ActionCell_TopicTable(MainController mainController, String label, sideStageState state) {
        this.mainController = mainController;
        btn = new Button(label);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainController.setStatus("Aktion auf TopicID " + hisTopic.getId() + " ausgeführt.");
                mainController.callSideStage(state, hisTopic.getId());
            }
        });
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            hisTopic = (Topic) getTableView().getItems().get(getIndex());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(btn);
        } else {
            setGraphic(null);
        }
    }

}
