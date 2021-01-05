package view;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Article;
import model.Moderator;
import model.Status;
import model.User;
import org.springframework.security.access.method.P;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FXMLController_VideoEditor extends ELBIS_FXMLController
{
    @FXML
    TextField titleField;

    @FXML
    Button exportButton;

    @FXML
    Button videoButton;
    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;

    @FXML
    WebView articleView;

    final static String DESKTOP = System.getProperty("user.home")+ "\\Desktop\\";
    final static String SAMPLE = DESKTOP + "sample.pdf";
    final static String DEST = System.getProperty("user.home")+ "\\Desktop\\output.pdf";

    private FXMLController_Editor editor;
    private Stage editorStage;
    private Pane editorPane;
    private Scene editorScene;
    private FXMLLoader editorLoader;
    private FXMLController_Editor editorController;

    String schemaPath= "/tinymce/schema.pdf";
    String videoPath;
    File temp;

    String html;
    String title;
    Article currentArticle;

    @FXML
    public void initialize() throws IOException
    {
        /*editorStage= new Stage();
        editorLoader= new FXMLLoader(this.getClass().getResource("/view/Pane_Editor.fxml"));
        editorPane = (Pane) editorLoader.load();
        editorController = editorLoader.getController();

        editorScene= new Scene(editorPane);
        editorStage.setScene(editorScene);

        editorStage.show();*/

    }

    public void setEditorController(FXMLController_Editor editorController)
    {
       this.editorController= editorController;
    }

    public void openArticleEditor()
    {
        mainController.openEditorforVideo();
    }
    @FXML
    private void closeVideoEditor()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void openNewArticle()
    {
        currentArticle= new Article();
    }

    public void openArticle(Article article)
    {
        currentArticle=article;
        String content= article.getContent();

        String regex="%title%([\\s\\S]*)%\\/title%%src%([\\s\\S]*)%\\/src%%article%([\\s\\S]*)%\\/article%";
        Pattern pattern=Pattern.compile(regex);

        Matcher matcher= pattern.matcher(content);

        if (matcher.find())
        {
            titleField.setText(matcher.group(1));
            videoPath=matcher.group(2);
            html= matcher.group(3);

            editorController.openArticle(html);
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }


    }

    public void saveArticle() throws IOException
    {
        html= editorController.getHtml();
        String content;

        content= "%vid%"+"%title%" + titleField.getText()+ "%/title%"
                + "%src%" + videoPath+ "%/src%"
                + "%article%" + html + "%/article%";


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
        saveDialog.initOwner(cancelButton.getScene().getWindow());
        saveDialog.initStyle(StageStyle.UTILITY);
        saveDialog.initModality(Modality.APPLICATION_MODAL);


        User activeUser= mainController.getActiveUser();


        //System.out.println("Status: "+ currentArticle.getStatus());

        // load current attributes of article
        saveController.saveTitle.setText(currentArticle.getTitle());
        if (currentArticle.getStatus()!=null)
        {
            saveController.statusChoice.getSelectionModel().select(currentArticle.getStatus());

        }
        if (currentArticle.getTopic()!=null)
        {
            saveController.topicChoice.setValue(currentArticle.getTopic());
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
            saveController.statusChoice.getItems().setAll(Status.Entwurf, Status.Eingereicht);
            if (currentArticle.getStatus()==null)
            {
                saveController.statusChoice.setValue(Status.Entwurf);
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
                // System.out.println(chosenStatus);

                // check if user left the status on an invalid status not in their privileges
                if (!(activeUser instanceof Moderator))
                {
                    if (chosenStatus!=Status.Entwurf && chosenStatus != Status.Eingereicht)
                    {
                        Alert alert= new Alert(Alert.AlertType.ERROR,
                                "Sie haben keine Rechte für den aktuellen Status! \n\n"
                                        + "Setzen Sie ihn auf Submitted, damit ein Redakteur den Artikel prüfen und ggf. freigeben kann.");
                        alert.showAndWait();
                    }
                }

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
                }
                else // write field data to article object and send to db via mainController
                {
                    // update values of current article according to filled in fields
                    currentArticle.setTitle(saveController.saveTitle.getText());
                    currentArticle.setContent(content);
                    currentArticle.setExpireDate(saveController.getExpireDate());
                    currentArticle.setTopic(saveController.topicChoice.getValue());
                    // for compatibility of old topic int value. needs to +1 because index in DB starts at 1
                    currentArticle.setStatus(saveController.statusChoice.getValue());
                    currentArticle.setAuthor(activeUser);

                    //System.out.println(saveController.topicChoice.getValue());
                    //System.out.println(currentArticle.getTopic());
                    //System.out.println(currentArticle.getTopic().getId());
                    //System.out.println(currentArticle.getExpireDate());

                    // send current Article with updated values to main controller to write into db
                    mainController.saveArticle(currentArticle);

                    // close window
                    saveDialog.close();

                } // open article again after saving
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

    public void getVideoPath()
    {
        // create and open  file dialog window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("PDF-Pfad auswählen");

        // Extension filter to only show MP4s
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("MP4 Videos", "*.mp4")
        );

        fileChooser.setInitialDirectory(new File(DESKTOP));
        // get File of chosen pdf path
        File video= fileChooser.showOpenDialog(new Stage());

        if(video!=null)
        {
            videoPath= video.getAbsolutePath();
        }

    }

    public File writeVideo (File file) throws IOException
    {
            temp= new File(file.getParent()+"\\temp.pdf");
            // writer
            PdfWriter writer = new PdfWriter(temp);



            PdfReader reader = new PdfReader(schemaPath);
            PdfDocument sample = new PdfDocument(reader, writer);
            Document sampleDoc = new Document(sample);

            PdfPage page = sample.getFirstPage();
            PdfDictionary pageDic = new PdfDictionary(page.getPdfObject());
            PdfArray annots = pageDic.getAsArray(PdfName.Annots);


            PdfName richmedia = new PdfName("RichMediaContent");
            // Annot 17 0 R
            PdfDictionary array = annots.getAsDictionary(0);
            // Assets 26 0 R
            PdfDictionary assets = array.getAsDictionary(richmedia);

            // Instances 21 0 R
            PdfName configs = new PdfName("Configurations");
            PdfDictionary config = assets.getAsArray(configs).getAsDictionary(0);

            PdfName instancesName = new PdfName("Instances");
            PdfName assetName = new PdfName("Asset");
            // Dictionary 22 0 R
            PdfDictionary dict = config.getAsArray(instancesName).getAsDictionary(0);

            // Asset 23 0 R
            PdfDictionary asset = dict.getAsDictionary(assetName);
            System.out.println(asset);

            PdfDictionary ef = asset.getAsDictionary(PdfName.EF);
            System.out.println(ef);

            // get the video as bytestream in F
            PdfObject f = ef.get(PdfName.F);
            PdfStream content = ef.getAsStream(PdfName.F);

            byte[] video = reader.readStreamBytes(content, false);

            // test output
            /*try (FileOutputStream out = new FileOutputStream(DESKTOP + "mamama.mp4"))
            {
                out.write(video);
            }*/


            // overwrite embedded video with another video
            byte[] inputBytes;
            // Input the video as byte array
            FileInputStream fin = new FileInputStream(videoPath);
            inputBytes = fin.readAllBytes();

            // set the content stream's video with new video
            content.setData(inputBytes);
            ef.put(PdfName.F, content);

        /* test output
        content= ef.getAsStream(PdfName.F);
        byte[] outBytes;
        outBytes = content.getBytes();

        // test output
        try (FileOutputStream out = new FileOutputStream(DESKTOP + "pipipi.mp4"))
        {
            out.write(outBytes);
        }*/



            sample.close();

            reader.close();
            writer.close();


            return temp;
    }

    public void exportPDF2() throws IOException
    {
        // get contents of HTML editor
        html= editorController.getHtml();

        // create and open file dialog window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("PDF-Pfad auswählen");
        // Extension filter to only show PDFs
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Dateien", "*.pdf")
        );
        fileChooser.setInitialDirectory(new File(DESKTOP));
        // get File of chosen pdf path for saving
        File pdf= fileChooser.showSaveDialog(new Stage());



        /*if (html!=null)
        {
            tempArticle= new File(pdf.getParent()+"\\temp2.pdf");
            // writing tempArticle file
            System.out.println("Writing temp article");
            HtmlConverter.convertToPdf(html, new FileOutputStream(tempArticle));
        }*/

        if (pdf!= null)
        {
            PdfReader reader;
            if (videoPath!=null)
            {
                reader= new PdfReader(writeVideo(pdf));
            }else
            {
                reader= new PdfReader(schemaPath);
            }

            PdfWriter writer = new PdfWriter(pdf);

            PdfDocument videoDocument= new PdfDocument(reader);
            PdfDocument pdfDocument = new PdfDocument(writer.setSmartMode(true));

            //PageSize docSize= pdfDocument.getDefaultPageSize();

            //Document document = new Document(pdfDocument);
            //document.setMargins(50,40,40,40);

            videoDocument.copyPagesTo(1,1,pdfDocument);
            videoDocument.close();

            if (temp!=null)
            {
                temp.delete();
            }

            if (html!=null)
            {
                File tempArticle= new File(pdf.getParent()+"\\temp2.pdf");
                // writing tempArticle file
                System.out.println("Writing temp article");
                HtmlConverter.convertToPdf(html, new FileOutputStream(tempArticle));

                PdfDocument articleDocument= new PdfDocument(new PdfReader(tempArticle));

                articleDocument.copyPagesTo(1,articleDocument.getNumberOfPages(),pdfDocument);
                articleDocument.close();
                tempArticle.delete();

            }


            Paragraph titleParagraph= new Paragraph(titleField.getText());
            titleParagraph.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //titleParagraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
            titleParagraph.setTextAlignment(TextAlignment.CENTER);
            titleParagraph.setMarginTop(30);
            titleParagraph.setFontSize(24);
            titleParagraph.setMargins(40,40,40,40);

            PdfCanvas pdfCanvas = new PdfCanvas(pdfDocument.getFirstPage());

            PageSize docSize= pdfDocument.getDefaultPageSize();
            Rectangle rectangle = new Rectangle(docSize.getLeft(),docSize.getTop()-200, docSize.getWidth(),200);

            Canvas canvas = new Canvas(pdfCanvas, pdfDocument, rectangle);


            canvas.add(titleParagraph);
            canvas.close();
            pdfCanvas.release();

            pdfDocument.close();

            if (temp!=null)
            {
                temp.delete();
            }


        }


    }

    /*public void exportPDF() throws IOException
    {

        String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                "\n" +
                "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                "\n" +
                "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n" +
                "\n" +
                "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat.   \n" +
                "\n" +
                "Consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus.   \n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                "\n" +
                "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                "\n" +
                "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo";


        html= editorController.getHtml();

        List<IElement> elements= HtmlConverter.convertToElements(html);

        for (int i = 0; i < elements.size(); i++)
        {
            System.out.println(elements.get(i).toString());
        }

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

        if (pdf!=null)
        {
            PdfReader reader;
            if (videoPath!=null)
            {
                reader= new PdfReader(writeVideo(pdf));
            }else
            {
                reader= new PdfReader(schemaPath);
            }

            PdfWriter writer = new PdfWriter(pdf);

            PdfDocument schema= new PdfDocument(reader);
            PdfDocument resultDoc = new PdfDocument(writer.setSmartMode(true));

            PageSize docSize= resultDoc.getDefaultPageSize();

            Document document = new Document(resultDoc);
            //document.setMargins(50,40,40,40);

            schema.copyPagesTo(1,1,resultDoc);

            Paragraph titleParagraph= new Paragraph(titleField.getText());
            titleParagraph.setHorizontalAlignment(HorizontalAlignment.CENTER);
            titleParagraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
            titleParagraph.setTextAlignment(TextAlignment.CENTER);
            titleParagraph.setMarginTop(30);
            titleParagraph.setFontSize(24);

            PdfCanvas pdfCanvas = new PdfCanvas(resultDoc.getFirstPage());

            Rectangle rectangle = new Rectangle(docSize.getLeft(),docSize.getTop()-150, docSize.getWidth(),100);

            Canvas canvas = new Canvas(pdfCanvas, resultDoc, rectangle);


            Paragraph textParagraph = (Paragraph) elements.get(0);



            //textParagraph.setMargins(50,40,40,40);
            Rectangle textRec= new Rectangle(docSize.getLeft(),docSize.getBottom(),docSize.getWidth(),400);
            textRec.applyMargins(50,40,40,40, false);


            addParagraph(textParagraph, resultDoc, 1, textRec);



            canvas.add(titleParagraph);
            canvas.close();
            pdfCanvas.release();


            resultDoc.close();
            schema.close();
            reader.close();
            writer.close();

            if (temp!=null)
            {
                System.out.println(temp.delete());

            }
        }

    }*/

    private void writeTextFromElements(File pdfDest, List<IElement> htmlElements) throws IOException
    {
        File pdf= pdfDest;
        List<IElement> elements=htmlElements;

        PdfReader reader;
        if (videoPath!=null)
        {
            reader= new PdfReader(writeVideo(pdf));
        }else
        {
            reader= new PdfReader(schemaPath);
        }

        PdfWriter writer = new PdfWriter(pdf);

        PdfDocument schema= new PdfDocument(reader);
        PdfDocument resultDoc = new PdfDocument(writer.setSmartMode(true));

        PageSize docSize= resultDoc.getDefaultPageSize();

        Document document = new Document(resultDoc);
        //document.setMargins(50,40,40,40);

        schema.copyPagesTo(1,1,resultDoc);

        Paragraph titleParagraph= new Paragraph(titleField.getText());
        titleParagraph.setHorizontalAlignment(HorizontalAlignment.CENTER);
        titleParagraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
        titleParagraph.setTextAlignment(TextAlignment.CENTER);
        titleParagraph.setMarginTop(30);
        titleParagraph.setFontSize(24);

        PdfCanvas pdfCanvas = new PdfCanvas(resultDoc.getFirstPage());

        Rectangle rectangle = new Rectangle(docSize.getLeft(),docSize.getTop()-150, docSize.getWidth(),100);

        Canvas canvas = new Canvas(pdfCanvas, resultDoc, rectangle);


        Paragraph textParagraph = (Paragraph) elements.get(0);
        //textParagraph.setMargins(50,40,40,40);
        Rectangle textRec= new Rectangle(docSize.getLeft(),docSize.getBottom(),docSize.getWidth(),400);
        textRec.applyMargins(50,40,40,40, false);


        addParagraph(textParagraph, resultDoc, 1, textRec);



        canvas.add(titleParagraph);
        canvas.close();
        pdfCanvas.release();


        resultDoc.close();
        schema.close();
        reader.close();
        writer.close();

        System.out.println(temp.delete());
    }
    /**
     * Adds a paragraph to PdfDocument, splitting overflowing content and adding it to new pages recursively
     * @param paragraph - Paragraph with content
     * @param pdfDocument - Document to write to
     * @param pageNum - Initial page number
     * @param rectangle - Rectangle where to put paragraph
     * @author Onur Hokkaömeroglu
     */
    public void addParagraph (Paragraph paragraph, PdfDocument pdfDocument, int pageNum, Rectangle rectangle)
    {
        PdfPage page = pdfDocument.getFirstPage();

        LayoutResult result;

        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(),pdfDocument);
        Canvas canvas = new Canvas(pdfCanvas, pdfDocument, rectangle);

        ParagraphRenderer renderer = (ParagraphRenderer) paragraph.createRendererSubTree();
        renderer.setParent(new DocumentRenderer(new Document(pdfDocument)));
        result= renderer.layout(new LayoutContext(new LayoutArea(pageNum,rectangle)));

        IRenderer rendererToAdd = result.getStatus() == LayoutResult.FULL ? renderer : result.getSplitRenderer();

        canvas.getRenderer().addChild(rendererToAdd.setParent(canvas.getRenderer()));


        while (result.getStatus() != LayoutResult.FULL)
        {
            rendererToAdd = result.getSplitRenderer();
            canvas.getRenderer().addChild(rendererToAdd.setParent(canvas.getRenderer()));

            page= pdfDocument.addNewPage();
            pdfCanvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(),pdfDocument);
            rectangle = new Rectangle(PageSize.A4.getLeft(), PageSize.A4.getBottom(),PageSize.A4.getWidth(),PageSize.A4.getHeight());
            rectangle.applyMargins(50,40,40,40, false);
            canvas = new Canvas(pdfCanvas, pdfDocument, rectangle);

            renderer = (ParagraphRenderer) result.getOverflowRenderer();
            result= renderer.layout(new LayoutContext(new LayoutArea(pageNum,rectangle)));
        }

        canvas.getRenderer().addChild(renderer.setParent(canvas.getRenderer()));


    }

}
