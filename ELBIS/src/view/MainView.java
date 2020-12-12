package view;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Application {

	// Atrrib_______________________________________________________________________________________________________
	private MainController controller;
	private Stage primaryStage;
	private Stage secondStage;
	private Stage thirdStage;
	private FXMLController fxmlController;
	// Ctor_______________________________________________________________________________________________________
	public MainView() {
		fxmlController = new FXMLController(this);
	}

	public MainView(MainController controller) {
		super();
		this.controller = controller;
	}
	// Start_______________________________________________________________________________________________________
	@Override
	public void start(Stage primaryStage) throws Exception {

		// ###### Stages ######
		this.primaryStage = primaryStage; // Login window
		secondStage = new Stage(); // Application Window
		thirdStage = new Stage(); // Editor Window

		//Stage content
		try {
			primaryStage.setScene(newLoginScene());
			thirdStage.setScene(newEditorScene());
		} catch (IOException io){
			System.out.println("Couldnt load scene File");
			io.printStackTrace();
		}
		primaryStage.show();

		// test of the ArticleEditor layout

	}
	// Methods_______________________________________________________________________________________________________
	public Scene newLoginScene() throws IOException {
		Pane loginPane = (Pane) FXMLLoader.load(MainView.class.getResource("Pane_Login.fxml"));
		Scene loginScene = new Scene(loginPane);
		return loginScene;
	}

	public Scene newEditorScene() throws IOException {

		Pane editorPane = (Pane) FXMLLoader.load(MainView.class.getResource("article_editor.fxml"));
		Scene editorScene = new Scene(editorPane);
		return editorScene;
	}

	public void openEditorScene() {
		thirdStage.show();
	}


}
