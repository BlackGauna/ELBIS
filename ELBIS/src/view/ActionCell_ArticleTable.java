package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import model.Article;
import model.Status;

public class ActionCell_ArticleTable extends TableCell<Article, Boolean> {

    // Atrrib_______________________________________________________________________________________________________
    Button btn;
    MainController mainController;
    Article hisArticle;

    // Ctor_______________________________________________________________________________________________________
    public ActionCell_ArticleTable(MainController mainController, String label, sideStageState state) {
        this.mainController = mainController;
        btn = new Button(label);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainController.setStatus("Aktion der ArtikelID " + hisArticle.getId() + " ausgeführt.");
                mainController.callSideStage(state, hisArticle.getId());
            }
        });
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            hisArticle = (Article) getTableView().getItems().get(getIndex());
            if((hisArticle.getStatus() == Status.Eingereicht || hisArticle.getStatus() == Status.Autorisiert || hisArticle.getStatus() == Status.Öffentlich) && btn.getText().equals("Herausgeben")){
                btn.setDisable(true);
            }
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(btn);
        } else {
            setGraphic(null);
        }
    }
}
