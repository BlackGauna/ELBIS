package view;

import com.itextpdf.html2pdf.HtmlConverter;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.*;
import java.net.URL;

/**
 * class containing TinyMCE editor in a JavaFX WebView
 * base structure from: https://javafxpedia.com/en/tutorial/5156/webview-and-webengine

 @author Onur Hokkaömeroglu
 */

public class ArticleEditor extends Application
{
    // for communication Java -> Javascript
    private JSObject javascriptConnector;

    // for communication Javascript -> Java
    private JavaConnector javaConnector = new JavaConnector();



    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // html source for the WebView
        URL url= Thread.currentThread().getContextClassLoader().getResource("tinymce/tinymce_test.html");

        // setup WebView and WebEngine
        WebView webView = new WebView();
        final WebEngine webEngine= webView.getEngine();

        webEngine.setJavaScriptEnabled(true);
        webEngine.setUserStyleSheetLocation(Thread.currentThread().getContextClassLoader()
                .getResource("tinymce/style.css").toString());

        //listener setup
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

        Scene scene = new Scene(webView, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();

        // now load the page
        webEngine.load(url.toString());

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


        /**
         * called when the JS side wants a String to be converted.
         *
         * @param value
         *         the String to convert
         */
        public void toLowerCase(String value) {
            if (null != value) {

                System.out.println(value);
                //javascriptConnector.call("showResult", value);
            }
        }

        /**
         * get html source from JS side, then convert and save to PDF
         *
         * @param htmlSource - String containing HTML code.
         * @author Onur Hokkaömeroglu
         */
        public void saveHtml(String htmlSource)
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
    }

}
