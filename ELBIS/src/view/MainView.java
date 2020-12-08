package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainView extends Application {

	// Atrrib_______________________________________________________________________________________________________

	// Ctor_______________________________________________________________________________________________________
	public MainView() {
	}

	// Methods_______________________________________________________________________________________________________
	@Override
	public void start(Stage primaryStage) throws Exception {
			Pane mainPane = (Pane) FXMLLoader.load(MainView.class.getResource("Pane_Login.fxml"));
			primaryStage.setScene(new Scene(mainPane));
			primaryStage.show();
		

	}
}
