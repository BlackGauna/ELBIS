package view;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.File;
import java.net.URL;

/**
Test class for using TinyMCE as alternative editor in a WebView
base structure from: https://javafxpedia.com/en/tutorial/5156/webview-and-webengine

 @author Onur Hokkaömeroglu
 */

public class WebViewTest extends Application
{
    // for communication Java -> Javascript
    private JSObject javascriptConnector;

    // for communication Javascript -> Java
    private JavaConnector javaConnector = new JavaConnector();

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        URL url= getClass().getResource("/resource/tinymce/tinymce_test.html");

        WebView webView = new WebView();
        final WebEngine webEngine= webView.getEngine();



        webEngine.setJavaScriptEnabled(true);
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
    }
}
