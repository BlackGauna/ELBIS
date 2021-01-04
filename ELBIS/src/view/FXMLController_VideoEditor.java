package view;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FXMLController_VideoEditor
{
    @FXML
    TextField titleField;

    @FXML
    Button saveButton;

    @FXML
    Button videoButton;

    final static String DESKTOP = System.getProperty("user.home")+ "\\Desktop\\";
    final static String SAMPLE = DESKTOP + "sample.pdf";
    final static String DEST = System.getProperty("user.home")+ "\\Desktop\\output.pdf";

    String schemaPath= "/tinymce/schema.pdf";
    String videoPath;
    File temp;

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
        try (FileOutputStream out = new FileOutputStream(DESKTOP + "mamama.mp4"))
        {
            out.write(video);
        }


        // test overwrite embedded video with another video
        byte[] inputBytes;


        FileInputStream fin = new FileInputStream(videoPath);
        inputBytes = fin.readAllBytes();

        content.setData(inputBytes);



        ef.put(PdfName.F, content);


        content= ef.getAsStream(PdfName.F);
        byte[] outBytes;
        outBytes = content.getBytes();

        /*// test output
        try (FileOutputStream out = new FileOutputStream(DESKTOP + "pipipi.mp4"))
        {
            out.write(outBytes);
        }*/



        sample.close();

        reader.close();
        writer.close();


        return temp;
    }

    public void exportPDF() throws IOException
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

        String html = "<p>Lorem ipsum dolor sit amet, consectetuer adipiscing \n" +
                "elit. Aenean commodo ligula eget dolor. Aenean massa \n" +
                "<strong>strong</strong>. Cum sociis natoque penatibus \n" +
                "et magnis dis parturient montes, nascetur ridiculus \n" +
                "mus. Donec quam felis, ultricies nec, pellentesque \n" +
                "eu, pretium quis, sem. Nulla consequat massa quis \n" +
                "enim. Donec pede justo, fringilla vel, aliquet nec, \n" +
                "vulputate eget, arcu. In enim justo, rhoncus ut, \n" +
                "imperdiet a, venenatis vitae, justo. Nullam dictum \n" +
                "felis eu pede <a class=\"external ext\" href=\"#\">link</a> \n" +
                "mollis pretium. Integer tincidunt. Cras dapibus. \n" +
                "Vivamus elementum semper nisi. Aenean vulputate \n" +
                "eleifend tellus. Aenean leo ligula, porttitor eu, \n" +
                "consequat vitae, eleifend ac, enim. Aliquam lorem ante, \n" +
                "dapibus in, viverra quis, feugiat a, tellus. Phasellus \n" +
                "viverra nulla ut metus varius laoreet. Quisque rutrum. \n" +
                "Aenean imperdiet. Etiam ultricies nisi vel augue. \n" +
                "Curabitur ullamcorper ultricies nisi.</p>\n" +
                "\n" +
                "<h1>Lorem ipsum dolor sit amet consectetuer adipiscing \n" +
                "elit</h1>\n" +
                "\n" +
                "<h2>Aenean commodo ligula eget dolor aenean massa</h2>\n" +
                "\n" +
                "<p>Lorem ipsum dolor sit amet, consectetuer adipiscing \n" +
                "elit. Aenean commodo ligula eget dolor. Aenean massa. \n" +
                "Cum sociis natoque penatibus et magnis dis parturient \n" +
                "montes, nascetur ridiculus mus. Donec quam felis, \n" +
                "ultricies nec, pellentesque eu, pretium quis, sem.</p>\n" +
                "\n" +
                "<h2>Aenean commodo ligula eget dolor aenean massa</h2>\n" +
                "\n";

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


        PdfReader reader= new PdfReader(writeVideo(pdf));
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

        /*System.out.println(resultDoc.getDefaultPageSize().getLeft());
        System.out.println(resultDoc.getDefaultPageSize().getRight());
        System.out.println(resultDoc.getDefaultPageSize().getTop());
        System.out.println(resultDoc.getDefaultPageSize().getBottom());*/


        Paragraph textParagraph = new Paragraph(text);
        //textParagraph.setMargins(50,40,40,40);
        Rectangle textRec= new Rectangle(docSize.getLeft(),docSize.getBottom(),docSize.getWidth(),400);
        textRec.applyMargins(50,40,40,40, false);


        addParagraph(textParagraph, resultDoc, 1, textRec);

        //ParagraphRenderer rest = addParagraph(textParagraph, resultDoc, 1, textRec);



        //restCanvas.getRenderer().addChild(rest.setParent(restCanvas.getRenderer()));
        //fitString(resultDoc,textParagraph, 1, textRec);

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
     * Adds a paragraph to PdfDocument, splitting overflowing content and adding to new page
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
