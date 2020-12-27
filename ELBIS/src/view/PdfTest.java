package view;

import com.itextpdf.io.codec.Base64;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfRichMediaAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfStampAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.kernel.utils.PageRange;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.*;
import java.util.List;
import java.util.Map;

public class PdfTest
{
    final static String TARGET= System.getProperty("user.home")+ "\\Desktop\\";
    final static String DEST = System.getProperty("user.home")+ "\\Desktop\\output.pdf";
    final static String VIDSRC= TARGET + "sample.mp4";
    final static String SAMPLE = TARGET + "sample.pdf";

    public static void main(String[] args) throws IOException
    {
        File file = new File(DEST);

        //new PdfTest().createPdf(DEST,VIDSRC);

        new PdfTest().writeVideo(DEST);
    }


    public void writeVideo (String path) throws IOException
    {
        // writer
        PdfWriter writer = new PdfWriter(path);
        PdfDocument pdf = new PdfDocument(writer.setSmartMode(true));
        Document document = new Document(pdf);


        PdfReader reader = new PdfReader(SAMPLE);
        PdfDocument sample= new PdfDocument(reader);
        Document sampleDoc= new Document(sample);

        PdfPage page= sample.getFirstPage();
        PdfDictionary pageDic= new PdfDictionary(page.getPdfObject());
        PdfArray annots = pageDic.getAsArray(PdfName.Annots);


        PdfName richmedia= new PdfName("RichMediaContent");
        // Annot 17 0 R
        PdfDictionary array= annots.getAsDictionary(0);
        // Assets 26 0 R
        PdfDictionary assets =array.getAsDictionary(richmedia);

        // Instances 21 0 R
        PdfName configs= new PdfName("Configurations");
        PdfDictionary config = assets.getAsArray(configs).getAsDictionary(0);

        PdfName instancesName = new PdfName("Instances");
        PdfName assetName = new PdfName("Asset");
        // Dictionary 22 0 R
        PdfDictionary dict= config.getAsArray(instancesName).getAsDictionary(0);

        // Asset 23 0 R
        PdfDictionary asset = dict.getAsDictionary(assetName);
        //System.out.println(asset);

        PdfDictionary ef= asset.getAsDictionary(PdfName.EF);
        //System.out.println(ef);

        // get the video as bytestream in F
        PdfObject f =  ef.get(PdfName.F);
        PdfStream content =ef.getAsStream(PdfName.F);

        byte[] video =  reader.readStreamBytes(content,false);

        // test output
        try (FileOutputStream out = new FileOutputStream(TARGET + "test.mp4"))
        {
            out.write(video);
        }


        // test overwrite embedded video with another video
        byte[] inputBytes;
        FileInputStream fin = new FileInputStream(TARGET + "input.mp4");
        inputBytes=fin.readAllBytes();

        content.setData(inputBytes);

        ef.put(PdfName.F, content);





        // test copy page
        //sample.copyPagesTo(1,1, pdf);



        pdf.addNewPage();
        Rectangle rectangle = new Rectangle(0,0,400,800);

        pdf.getFirstPage();
        PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());

        canvas.rectangle(rectangle);
        canvas.stroke();
        canvas.release();











        // test getting whole annotation dictionary
        /*System.out.println(pageDic);
        PdfObject annotObj = pageDic.get(PdfName.Annots);

        List<PdfAnnotation> annotationList =sampleDoc.getPdfDocument().getFirstPage().getAnnotations();

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);

        oout.writeObject(annotationList);

        byte[] bytesAnnot = bout.toByteArray();
        System.out.println(bytesAnnot);

        System.out.println(sample.getNumberOfPdfObjects());

        try (FileOutputStream outAnnot= new FileOutputStream(TARGET + "annots"))
        {
            outAnnot.write(bytesAnnot);
        }*/







        /*PdfStructTreeRoot root = pdf.getStructTreeRoot();
        PdfDictionary dic = new PdfDictionary();


        dic.put(PdfName.Type, PdfName.Annot);



        PdfArray rich = new PdfArray();
        rich.add(dic);

        pdf.addNewPage();
        PdfAnnotation annoation = new PdfAnnotation(dic)
        {
            @Override
            public PdfName getSubtype()
            {
                return PdfName.Annot;
            }
        };
        pdf.getFirstPage().addAnnotation(annoation);*/

        document.close();

    }


    public void createPdf (String dest, String video) throws IOException
    {
        PdfWriter writer = new PdfWriter(dest);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);




        document.add(new Paragraph("Peee!"));


        document.close();
    }

    public void createPdf (String dest) throws java.io.IOException
    {
        PdfWriter writer = new PdfWriter(dest);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        document.add(new Paragraph("Hello World!"));

        document.close();
    }
}
