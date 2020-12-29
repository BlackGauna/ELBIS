package view;

import com.itextpdf.html2pdf.HtmlConverter;
import controller.MainController;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Article;
import netscape.javascript.JSObject;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;

/**
 * class containing TinyMCE editor in a JavaFX WebView
 * base structure from: https://javafxpedia.com/en/tutorial/5156/webview-and-webengine

 @author Onur Hokkaömeroglu
 */

public class FXMLController_Editor
{
    // for communication Java -> Javascript
    private JSObject javascriptConnector;

    // for communication Javascript -> Java
    private JavaConnector javaConnector = new JavaConnector();

    private MainController mainController;

    // current Article loaded in editor
    private Article currentArticle;

    @FXML
    WebView webView;



    @FXML
    public void initialize() throws Exception
    {
        try
        {
            // html source for the WebView
            URL url= Thread.currentThread().getContextClassLoader().getResource("tinymce/tinymce_test.html");

            // setup WebView and WebEngine
            final WebEngine webEngine= webView.getEngine();

            webEngine.setJavaScriptEnabled(true);
            webEngine.setUserStyleSheetLocation(Thread.currentThread().getContextClassLoader()
                    .getResource("tinymce/style.css").toString());

            // listener setup
            webEngine.getLoadWorker().stateProperty().addListener(((observableValue, oldValue, newValue) ->{
                if (Worker.State.SUCCEEDED == newValue)
                {
                    // set an interface object named 'javaConnector' in the web engine's page
                    JSObject window= (JSObject) webEngine.executeScript("window");
                    window.setMember("javaConnector", javaConnector);

                    // get the Javascript connector object.
                    javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
                }
            } ));


            // now load the page
            webEngine.load(url.toString());

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    //reference to mainView
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }


    public void openNewArticle()
    {
        currentArticle= new Article();
    }

    public void openArticle(Article article)
    {
        currentArticle=article;
        javaConnector.openArticle(article.getContent());
    }

    public void openArticle (String html)
    {
        javaConnector.openArticle(html);
    }

    /**
     * Helper class for communication between the Javascript and Java
     * Javascript can call functions in this class
     */
    public class JavaConnector {

        // IO for pdfhtml
        // save to desktop for now
        // TODO: add way to choose save location and filename

        final String TARGET= System.getProperty("user.home")+ "/Desktop/";
        final String DEST = System.getProperty("user.home")+ "\\Desktop\\output.pdf";


        public void openArticle(String html)
        {
            javascriptConnector.call("openArticle", html);
        }

        /**
         * save open article in database
         * @param html
         * @throws IOException
         */
        public void saveArticle(String html) throws IOException
        {
            // setup save dialog window
            Stage saveDialog = new Stage();
            FXMLController_Save saveController= new FXMLController_Save(mainController);

            // laod FXML and controller
            FXMLLoader saveLoader = new FXMLLoader(getClass().getResource("/view/SavePrompt.fxml"));
            saveLoader.setController(saveController);
            saveLoader.load();

            //FXMLController_Save saveController= saveLoader.getController();

            saveDialog.setTitle("Artikel speichern:");

            // define save stage as modal and set owner window as the editor
            saveDialog.initOwner(webView.getScene().getWindow());
            saveDialog.initStyle(StageStyle.UTILITY);
            saveDialog.initModality(Modality.APPLICATION_MODAL);


            // load current attributes of article
            saveController.saveTitle.setText(currentArticle.getTitle());
            if (currentArticle.getStatus()!=null)
            {
                saveController.statusChoice.setValue(currentArticle.getStatus());
            }
            if (currentArticle.getTopic_int()!=0)
            {
                saveController.topicChoice.getSelectionModel().select(currentArticle.getTopic_int()-1);
            }

            //TODO: update save for Topic and status
            // TODO: status and topic selection based on user and privileges
            // save button action
            saveController.saveButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    String titleText= saveController.saveTitle.getText();

                    if (titleText==null || titleText.matches("^\\s*$"))
                    {
                        Alert alert= new Alert(Alert.AlertType.ERROR,
                                "Bitte keinen leeren Titel angeben!");
                        alert.showAndWait();

                    }else if (saveController.expireDate.getValue()==null)
                    {
                        Alert alert= new Alert(Alert.AlertType.ERROR,
                                "Bitte ein Ablaufdatum angeben!");
                        alert.showAndWait();
                    }
                    else
                    {
                        // update values of current article according to filled in fields
                        currentArticle.setTitle(saveController.saveTitle.getText());
                        currentArticle.setContent(html);
                        currentArticle.setExpireDate(saveController.getExpireDate());
                        currentArticle.setTopic(saveController.topicChoice.getValue());
                        // for compatibility of old topic int value. needs to +1 because index in DB starts at 1
                        currentArticle.setTopic_int(saveController.topicChoice.getSelectionModel().getSelectedIndex()+1);
                        currentArticle.setStatus(saveController.statusChoice.getValue());

                        // send current Article with updated values to main controller to write into db
                        mainController.saveArticle(currentArticle);
                        // close window
                        saveDialog.close();
                    }

                }
            });

            saveController.cancelButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    saveDialog.close();
                }
            });


            Scene saveScene= new Scene(saveController.anchorPane);
            //saveLoader.setController(saveController);
            saveDialog.setScene(saveScene);

            saveDialog.showAndWait();
        }



        /**
         * get article html from JS side, then convert and save to PDF
         *
         * @param htmlSource - String containing HTML code from editor
         * @author Onur Hokkaömeroglu
         */
        public void exportPDF(String htmlSource)
        {

            // create and open  file dialog window
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("PDF-Pfad auswählen");

            // Extension filter to only show PDFs
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF Dateien", "*.pdf")
            );

            // get File of chosen pdf path
            File pdf= fileChooser.showSaveDialog(new Stage());



            try
            {
                //File file= new File();
                HtmlConverter.convertToPdf(htmlSource, new FileOutputStream(pdf.getAbsolutePath()));
                System.out.println("Exported PDF to: "+ pdf.getAbsolutePath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void createPdf(String html, String dest) throws IOException
        {
            HtmlConverter.convertToPdf(html, new FileOutputStream(dest));
        }



        /**
         * Open file dialog for importing image into editor
         */

        public void openImage()
        {
            // create and open  file dialog window
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Bild öffnen");

            // get File of chosen image
            File image= fileChooser.showOpenDialog(new Stage());

            System.out.println(image.getAbsolutePath());

            // image width for resizing if too big for editor
            int imgWidth=0;

            // get image width
            try
            {
                // using ImageReader for getting size without loading whole image in memory
                ImageInputStream in = ImageIO.createImageInputStream(image);
                Iterator readers = ImageIO.getImageReaders(in);

                if (readers.hasNext())
                {
                    ImageReader reader= (ImageReader) readers.next();

                    try
                    {
                        reader.setInput(in);
                        imgWidth= reader.getWidth(0);
                    }finally
                    {
                        reader.dispose();
                    }
                }

            }
            catch (IOException e)
            {
                System.out.println("Error loading: "+ image.getAbsolutePath());
            }

            String html;
            URI path= image.toURI();
            imgWidth= Math.min(imgWidth, 800);
            html = "<p><img src=\""+ path + "\""
                    +"width="+ "\"" + imgWidth +"px\"" + "/></p>";

            javascriptConnector.call("importImage", html);
        }

    }

}
