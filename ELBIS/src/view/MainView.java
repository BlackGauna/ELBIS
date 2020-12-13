package view;

import controller.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Application {

	// Atrrib_______________________________________________________________________________________________________
	private MainController controller;
	FXMLLoader loader;
	//Stages
	private Stage loginStage;
	private Stage applicationStage;
	private Stage editorStage;
	//Scenes
	private Scene loginScene;
	private Scene applicationScene;
	private Scene editorScene;
	//Panes
	private Pane loginPane;
	private Pane applicationPane;
	private Pane editorPane;
	// Ctor_______________________________________________________________________________________________________
	public MainView() {
		 loader = new FXMLLoader();

		// ###### Stages ######
		this.loginStage = new Stage(); // Login window
		applicationStage = new Stage(); // Application Window
		editorStage = new Stage(); // Editor Window
	}

	public MainView(MainController controller) {
		this();
		this.controller = controller;

	}
	// Start_______________________________________________________________________________________________________
	@Override
	public void start(Stage primaryStage) throws Exception {

		//Stage content
		try {
			loginPane = (Pane) loader.load(MainView.class.getResource("/view/Pane_Login.fxml"));
			applicationPane = (Pane) loader.load(MainView.class.getResource("/view/Pane_MainApplication.fxml"));

			loginScene = new Scene(loginPane);
			applicationScene = new Scene(applicationPane);

			loginStage.setScene(loginScene);
			applicationStage.setScene(applicationScene);


		} catch (IOException io){
			System.out.println("Couldn't load scene File");
			io.printStackTrace();
		}
		openLoginStage();


	}
	// Methods_______________________________________________________________________________________________________
	public Scene createScene(String path) throws IOException {
		Pane Pane = (Pane) FXMLLoader.load(MainView.class.getResource(path));
		Scene Scene = new Scene(Pane);
		return Scene;
	}

	public void openLoginStage(){
		loginStage.show();
	}

	public void openApplicationStage(){
		applicationStage.show();
	}

	public void openEditorScene() {
		//TODO move EditorController to MainView
		editorStage.show();
	}

	// UI-Elements_______________________________________________________________________________________________________

	//Pane_Login
	@FXML
	private Button btnLogin;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private TextField txtEmail;

	@FXML
	void loginClicked(ActionEvent event) {
		System.out.println("loginClicked with: " + txtEmail.getText());
		openApplicationStage();
	}

	//Pane_MainApplication
	@FXML
	private TabPane tabPane;

}

