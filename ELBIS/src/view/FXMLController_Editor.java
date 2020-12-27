package view;

import com.itextpdf.html2pdf.HtmlConverter;
import controller.MainController;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Article;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

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
    private Article currentArticle= new Article();

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

        private String html;

        public void openArticle(String html)
        {
            javascriptConnector.call("openArticle", html);
        }

        public void saveArticle(String html) throws IOException
        {
            Stage saveDialog = new Stage();
            FXMLLoader saveLoader = new FXMLLoader(getClass().getResource("/view/SavePrompt.fxml"));
            Scene saveScene= new Scene(saveLoader.load());
            FXMLController_Save saveController= saveLoader.getController();

            saveController.saveTitle.setText(currentArticle.getTitle());

            saveController.saveButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    currentArticle.setTitle(saveController.saveTitle.getText());
                    currentArticle.setContent(html);
                    mainController.saveArticle(currentArticle);

                }
            });

            //saveLoader.setController(saveController);
            saveDialog.setScene(saveScene);

            saveDialog.show();
        }



        /**
         * get article html from JS side, then convert and save to PDF
         *
         * @param htmlSource - String containing HTML code.
         * @author Onur Hokkaömeroglu
         */
        public void exportPDF(String htmlSource)
        {
            //System.out.println(value);
            html = htmlSource;
            html= html.replace("file://","");
            try
            {
                //File file= new File();
                HtmlConverter.convertToPdf(html, new FileOutputStream(DEST));
                System.out.println(DEST);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void createPdf(String html, String dest) throws IOException
        {
            HtmlConverter.convertToPdf(html, new FileOutputStream(dest));
        }

        public String getHtml()
        {
            return html;
        }


        public void openImage()
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Bild öffnen");

            File image= fileChooser.showOpenDialog(new Stage());

            System.out.println(image.getAbsolutePath());

            String html;
            URI path= image.toURI();
            html = "<p><img src=\""+ path + "\" /></p>";

            javascriptConnector.call("importImage", html);
        }

    }

}
