package view;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FXMLController_VideoEditor
{
    @FXML
    TextField titleField;

    @FXML
    Button saveButton;


    public void exportPDF() throws IOException
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


        PdfReader reader= new PdfReader("/tinymce/schema.pdf");
        PdfWriter writer = new PdfWriter(pdf);

        PdfDocument schema= new PdfDocument(reader);
        PdfDocument resultDoc = new PdfDocument(writer);

        Document document = new Document(resultDoc);

        schema.copyPagesTo(1,1,resultDoc);

        Paragraph p= new Paragraph(titleField.getText());
        p.setMarginTop(2.0f);

        PdfCanvas pdfCanvas = new PdfCanvas(resultDoc.getFirstPage());

        Rectangle rectangle = new Rectangle(PageSize.A4.getLeft(),PageSize.A4.getTop(), PageSize.A4.getWidth(),50);
        //rectangle.applyMargins(2,2,2,2,false);

        Canvas canvas = new Canvas(pdfCanvas, resultDoc, rectangle);

        canvas.add(p);
        canvas.close();
        pdfCanvas.release();
        resultDoc.close();




    }
}
