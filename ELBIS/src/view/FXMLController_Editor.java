package view;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
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
import model.*;
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
    private Article currentArticle= new Article();

    private String html;

    private String editorPath;

    @FXML
    WebView webView;


    @FXML
    public void initialize() throws Exception
    {
        try
        {
            // html source for the WebView
            //URL url= Thread.currentThread().getContextClassLoader().getResource("tinymce/tinymce_test.html");
            editorPath="/tinymce/tinymce_test.html";

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


            // System.out.println("Slash: "+this.getClass().getResource(editorPath));
            // System.out.println(this.getClass().getResource("tinymce/tinymce_test.html"));

            // now load the page
            webEngine.load(this.getClass().getResource(editorPath).toString());


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //reference to mainView
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

    ///------EDITOR METHODS----------------------------------------------------------------------///
    public void openVideoEditor()
    {
        editorPath="/tinymce/videoArticle.html";
        webView.getEngine().load(this.getClass().getResource(editorPath).toString());
    }

    public void openNewArticle()
    {
        currentArticle= new Article();
        javaConnector.openArticle("");
    }

    public void openArticle(Article article)
    {
        currentArticle=article;
        if (currentArticle.getContent()!=null)
        {
            javaConnector.openArticle(article.getContent());
        }else
        {
            javaConnector.openArticle("");
        }
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



        public void openArticle(String html)
        {
            javascriptConnector.call("openArticle", html);
        }

        /**
         * save open article in database
         * @param html - html content as String
         * @throws IOException
         */
        public void saveArticle(String html) throws IOException
        {
            // TODO: Add controls for time of expireDate and update Date format
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


            User activeUser= mainController.getActiveUser();

            // currentArticle.setTopic(mainController.getTopic(1));

            // load current attributes of article
            saveController.saveTitle.setText(currentArticle.getTitle());
            if (currentArticle.getStatus()!=null)
            {
                saveController.statusChoice.setValue(currentArticle.getStatus());

            }
            if (currentArticle.getTopic()!=null)
            {
                //saveController.topicChoice.getSelectionModel().select(currentArticle.getTopic());
            }
            if (currentArticle.getExpireDate()!=null)
            {
                saveController.setExpireDate(currentArticle.getExpireDate());
            }

            // TODO: topic selection based on user and privileges
            // if only user privileges then limited options for status
            // but can still see the current status
            if (activeUser instanceof User && !(activeUser instanceof Moderator))
            {
                saveController.statusChoice.getItems().setAll(Status.Offen, Status.Eingereicht);
                if (currentArticle.getStatus()==null)
                {
                    saveController.statusChoice.setValue(Status.Offen);
                }

                if (activeUser.getTopics()!=null)
                {
                    saveController.topicChoice.getItems().setAll(activeUser.getTopics());
                }

            }

            // save button action
            saveController.saveButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    String titleText= saveController.saveTitle.getText();
                    Status chosenStatus= saveController.statusChoice.getValue();
                    System.out.println(chosenStatus);

                    if (!(activeUser instanceof Moderator))
                    {
                        // check if user left the status on an invalid status not in their privileges
                        if (chosenStatus!=Status.Offen && chosenStatus != Status.Eingereicht)
                        {
                            Alert alert= new Alert(Alert.AlertType.ERROR,
                                    "Sie haben keine Rechte für den aktuellen Status! \n\n"
                                            + "Setzen Sie ihn auf Submitted, damit ein Redakteur den Artikel prüfen und ggf. freigeben kann.");
                            alert.showAndWait();
                        }
                    }

                    if (titleText==null || titleText.matches("^\\s*$"))
                    {
                        Alert alert= new Alert(Alert.AlertType.ERROR,
                                "Bitte keinen leeren Titel angeben!");
                        alert.showAndWait();

                    }else if (saveController.topicChoice.getValue()==null)
                    {
                        Alert alert= new Alert(Alert.AlertType.ERROR,
                                "Bitte einen Bereich für den Artikel wählen!");
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
                        currentArticle.setTopicID(saveController.topicChoice.getSelectionModel().getSelectedIndex()+1);
                        currentArticle.setStatus(saveController.statusChoice.getValue());

                        System.out.println(activeUser.getId());
                        currentArticle.setAuthor(activeUser);

                        // send current Article with updated values to main controller to write into db
                        mainController.saveArticle(currentArticle);
                        // close window
                        saveDialog.close();

                    }
                    openArticle(currentArticle.getContent());
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

            saveDialog.show();
        }


        public void saveArticleForVideo(String html)
        {
            setHtml(html);
        }


        /**
         * get article html from JS side, then convert and save to PDF
         *
         * @param htmlSource - String containing HTML code from editor
         * @author Onur Hokkaömeroglu
         */
        public void exportPDF(String htmlSource) throws IOException
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
                HtmlConverter.convertToPdf(htmlSource, new FileOutputStream(pdf.getAbsolutePath()));
                System.out.println("Exported PDF to: "+ pdf.getAbsolutePath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            createOnePager(pdf);

        }

        /**
         * Creates a One-Pager version of article PDF and adds QR Code,
         * if PDF has more than one page.
         * @param originalPdf
         * @throws IOException
         * @author Onur Hokkaömeroglu
         */
        public void createOnePager (File originalPdf) throws IOException
        {

            PdfReader reader = new PdfReader(originalPdf);
            PdfDocument origDoc= new PdfDocument(reader);

            if (origDoc.getNumberOfPages()>1)
            {
                String onePagerFileName= originalPdf.getParent()+"\\"
                        + originalPdf.getName().substring(0, originalPdf.getName().lastIndexOf("."))
                        + "_OnePager.pdf";
                File onePagerFile= new File(onePagerFileName);
                PdfWriter writer= new PdfWriter(onePagerFile);
                PdfDocument onePager= new PdfDocument(writer);

                // copy first page of original pdf to one-pager
                origDoc.copyPagesTo(1,1,onePager);

                // open a PdfCanvas to manipulate the first page
                PdfCanvas pdfCanvas = new PdfCanvas(onePager.getFirstPage());

                // add gradient image with transparency
                ImageData gradientData = ImageDataFactory.create(Thread.currentThread().
                        getContextClassLoader().getResource("tinymce/gradient.png"));
                pdfCanvas.addImage(gradientData, PageSize.A4.getLeft(),PageSize.A4.getBottom(),false);

                // add text to bottom with information regarding qr code
                pdfCanvas.beginText().setFontAndSize(
                        PdfFontFactory.createFont(FontConstants.HELVETICA),12)
                        .moveText(PageSize.A4.getLeft()+50,PageSize.A4.getBottom()+30)
                        .showText("Um den gesamten Artikel zu lesen. scannen Sie den QR-Code:")
                        .endText();

                // add QR code image
                ImageData qr = ImageDataFactory.create(Thread.currentThread().
                        getContextClassLoader().getResource("tinymce/frame.png"));
                pdfCanvas.addImage(qr, PageSize.A4.getRight()-150,PageSize.A4.getBottom()+10,80,false);


                // release canvas and close document
                pdfCanvas.release();
                onePager.close();
                System.out.println("Exported One-Pager to: " + onePagerFile.getAbsolutePath());
            }


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
