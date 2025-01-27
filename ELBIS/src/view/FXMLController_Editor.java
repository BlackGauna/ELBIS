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
import javafx.collections.FXCollections;
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

    final static String DESKTOP = System.getProperty("user.home")+ "\\Desktop\\";


    @FXML
    WebView webView;
    private WebEngine webEngine;


    @FXML
    public void initialize() throws Exception
    {
        try
        {
            // html source for the WebView
            editorPath= "/tinymce/textArticle.html";

            // setup WebView and WebEngine
            webEngine= webView.getEngine();

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
            // System.out.println(this.getClass().getResource("tinymce/textArticle.html"));

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

    public void createOnePager (File pdf) throws IOException
    {
        javaConnector.createOnePager(pdf);
    }

    public void autoExport(Article article) throws IOException
    {
        javaConnector.autoExport(article);
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


            //System.out.println("Status: "+ currentArticle.getStatus());

            // load current attributes of article
            saveController.saveTitle.setText(currentArticle.getTitle());
            if (currentArticle.getStatus()!=null)
            {
                saveController.statusChoice.setValue(currentArticle.getStatus());

            }
            if (currentArticle.getTopic()!=null)
            {
                saveController.topicChoice.setValue(currentArticle.getTopic());
            }
            if (currentArticle.getExpireDate()!=null)
            {
                saveController.setExpireDate(currentArticle.getExpireDate());
            }

            // if only user privileges then limited options for status
            // but can still see the current status
            if (!(activeUser instanceof Moderator))
            {
                saveController.statusChoice.setItems(FXCollections.observableArrayList
                        (Status.Entwurf, Status.Eingereicht));
                // set status according to current article status
                if (currentArticle.getStatus()==Status.Entwurf)
                {
                    saveController.statusChoice.getSelectionModel().selectFirst();
                }else if (currentArticle.getStatus()==Status.Eingereicht)
                {
                    saveController.statusChoice.getSelectionModel().select(1);
                }else
                {
                    saveController.statusChoice.setValue(currentArticle.getStatus());
                }

                // if only user get the allowed topics of active user
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


                    // Check empty fields
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
                    }else if (!(activeUser instanceof Moderator) &&
                            chosenStatus!=Status.Entwurf && chosenStatus != Status.Eingereicht)
                    {
                        // check if user left the status on an invalid status not in their privileges
                        Alert alert= new Alert(Alert.AlertType.ERROR,
                                "Sie haben keine Rechte für den aktuellen Status! \n\n"
                                + "Setzen Sie ihn auf Eingereicht, damit ein Redakteur den Artikel prüfen und ggf. freigeben kann.");
                        alert.showAndWait();

                    }
                    else // write field data to article object and send to db via mainController
                    {
                        // update values of current article according to filled in fields
                        currentArticle.setTitle(saveController.saveTitle.getText());
                        currentArticle.setContent(html);
                        currentArticle.setExpireDate(saveController.getExpireDate());
                        currentArticle.setTopic(saveController.topicChoice.getValue());
                        currentArticle.setStatus(saveController.statusChoice.getValue());
                        currentArticle.setAuthor(activeUser);

                        // send current Article with updated values to main controller to write into db
                        mainController.saveArticle(currentArticle);

                        // close window
                        saveDialog.close();

                        mainController.refreshAllContent();

                    }
                    // open article again after saving
                    /*if (currentArticle.getContent()!=null)
                    {
                        openArticle(currentArticle.getContent());
                    }*/
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
            fileChooser.setInitialDirectory(new File(DESKTOP));

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
         * Auto export article to PDF without asking for explicit path
         * @param article - Article to export
         * @throws IOException
         */
        public void autoExport(Article article) throws IOException
        {
            // create folder on desktop
            File folder = new File(DESKTOP+ "\\ELBIS_Articles");
            folder.mkdirs();

            // create file inside folder
            File pdf = new File(folder.getAbsolutePath()+"\\" + article.getTitle()+".pdf");

            if (article.getContent()!=null)
            {
                try
                {
                    HtmlConverter.convertToPdf(article.getContent(), new FileOutputStream(pdf.getAbsolutePath()));
                    System.out.println("Exported PDF to: "+ pdf.getAbsolutePath());
                } catch (IOException e)
                {
                    System.out.println("Konnte nicht automatisch exportieren.");
                    e.printStackTrace();
                }

                createOnePager(pdf);
            }

            System.out.println("Exported released articles to: " + folder.getAbsolutePath());

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
         * Import image into editor. Opens a file chooser dialog for choosing
         */
        public void openImage()
        {
            // create and open  file dialog window
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Bild öffnen");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Bilder", "*.jpg","*.jpeg","*.png","*.bmp","*.gif"));

            // get File of chosen image
            File image= fileChooser.showOpenDialog(new Stage());

            System.out.println("Bild importiert von: "+ image.getAbsolutePath());

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
            imgWidth= Math.min(imgWidth, 625);
            html = "<p><img src=\""+ path + "\""
                    +"width="+ "\"" + imgWidth +"px\"" + "/></p>";

            javascriptConnector.call("importImage", html);
        }

    }

}
