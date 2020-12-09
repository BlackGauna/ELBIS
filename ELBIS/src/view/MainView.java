package view;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainView extends Application {

	// Atrrib_______________________________________________________________________________________________________
	private MainController controller;
	// Ctor_______________________________________________________________________________________________________
	public MainView() {
	}
	
	public MainView(MainController controller) {
		this.controller = controller;
	}

	// Methods_______________________________________________________________________________________________________
	@Override
	public void start(Stage primaryStage) throws Exception {
			
			//Primary
				//Scene creation
			Pane loginPane = (Pane) FXMLLoader.load(MainView.class.getResource("Pane_Login.fxml"));
			Scene loginScene = new Scene(loginPane);
				//(Login)start Scene
			primaryStage.setScene(loginScene);
			primaryStage.show();

			//Secondary
				//test ArticleEditor layout
			Stage secondStage = new Stage();
			Pane editorPane = (Pane) FXMLLoader.load(MainView.class.getResource("article_editor.fxml"));
			secondStage.setScene(new Scene(editorPane));

			secondStage.show();


	}
	
}
