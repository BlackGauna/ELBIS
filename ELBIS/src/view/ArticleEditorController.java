package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;


/** Controller class for the the article editor pane.
 * @author Onur Hokkaömeroglu
 */

// TODO: maybe create a more extended HTMLEditor with more options
public class ArticleEditorController
{
    private String text;

    @FXML
    private HTMLEditor htmlEditor;


    /**
     * Get article text from HTMLEditor and save in database.
     */
    @FXML
    public void saveArticle(ActionEvent event)
    {
        text= htmlEditor.getHtmlText();
        System.out.println(text);
    }





}
