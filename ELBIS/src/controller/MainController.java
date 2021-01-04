package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import view.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainController extends Application {


    private static Connection con;
    public Stage sideStage;
    // Atrrib_______________________________________________________________________________________________________
    //Data
    private DataController dc;
    private User activeUser;
    //MainLoaders
    private FXMLLoader loginLoader;
    private FXMLLoader mainApplicationLoader;
    private FXMLLoader editorLoader;
    private FXMLLoader videoEditorLoader;
    private FXMLLoader selectorLoader;
    //MainControllers
    private FXMLController_Login loginController;
    private FXMLController_MainApplication mainApplicationController;
    private FXMLController_Editor editorController;
    private FXMLController_VideoEditor videoController;
    private FXMLController_EditorSelector selectorController;
    //MainStages
    private Stage loginStage;
    private Stage applicationStage;
    private Stage editorStage;
    private Stage videoStage;
    private Stage selectorStage;
    //MainScenes
    private Scene loginScene;
    private Scene applicationScene;
    private Scene editorScene;
    private Scene sideScene = new Scene(new Pane());
    private Scene videoScene;
    private Scene selectorScene;
    //MainPanes
    private Pane loginPane;
    private Pane applicationPane;
    private Pane editorPane;
    private Pane videoPane;
    private Pane selectorPane;

    //SideContent
    private FXMLLoader sideLoader = new FXMLLoader();

    private FXMLController_CreateUser createUserController;
    private Pane createUserPane;

    private FXMLController_CreateTopic createTopicController;
    private Pane createTopicPane;


    // Ctor_______________________________________________________________________________________________________
    public MainController() {

        dc = new DataController(this);
        // ###### Create Stages ######
        loginStage = new Stage(); // Login window
        loginStage.setTitle("Login");
        loginStage.setResizable(false);
        applicationStage = new Stage(); // Application Window
        applicationStage.setTitle("Verwaltung");
        applicationStage.setMinHeight(840); //DONT CHANGE
        applicationStage.setMinWidth(1220); //DONT CHANGE
        editorStage = new Stage(); // Editor Window
        sideStage = new Stage();
        sideStage.setResizable(false);
        videoStage = new Stage();
        selectorStage = new Stage();


    }

    // Start_______________________________________________________________________________________________________
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Stage content
        try {
            loginStage.getIcons().add(new Image("/ELBIS_graphic/ELBIS_E_small.png"));
            applicationStage.getIcons().add(new Image("/ELBIS_graphic/ELBIS_E_small.png"));
            sideStage.getIcons().add(new Image("/ELBIS_graphic/ELBIS_E_small.png"));
            //Manage loaders and load their controllers
            loginLoader = new FXMLLoader(getClass().getResource("/view/Pane_Login.fxml"));
            loginPane = (Pane) loginLoader.load();
            loginController = loginLoader.getController();
            loginController.setMainController(this);

            mainApplicationLoader = new FXMLLoader(getClass().getResource("/view/Pane_MainApplication.fxml"));
            applicationPane = (Pane) mainApplicationLoader.load();
            mainApplicationController = mainApplicationLoader.getController();
            mainApplicationController.setMainController(this);

            editorLoader = new FXMLLoader(getClass().getResource("/view/Pane_Editor.fxml"));
            editorPane = (Pane) editorLoader.load();
            editorController = editorLoader.getController();
            editorController.setMainController(this);

            videoEditorLoader = new FXMLLoader(getClass().getResource("/view/Pane_VideoEditor.fxml"));
            videoPane = videoEditorLoader.load();
            videoController = videoEditorLoader.getController();

            selectorLoader = new FXMLLoader(getClass().getResource("/view/Pane_EditorSelector.fxml"));
            selectorPane = selectorLoader.load();
            selectorController = selectorLoader.getController();
            selectorController.setMainController(this);


            //Manage scenes
            loginScene = new Scene(loginPane);
            loginScene.getStylesheets().add("/ELBIS_graphic/dark.css");
            applicationScene = new Scene(applicationPane);
            applicationScene.getStylesheets().add("/ELBIS_graphic/dark.css");

            editorScene = new Scene(editorPane);
            videoScene = new Scene(videoPane);
            selectorScene = new Scene(selectorPane);

            loginStage.setScene(loginScene);
            applicationStage.setScene(applicationScene);
            editorStage.setScene(editorScene);
            videoStage.setScene(videoScene);
            selectorStage.setScene(selectorScene);

        } catch (IOException io) {
            System.out.println("Couldn't load scene File");
            io.printStackTrace();
        }
        openLoginStage();


    }

    // Methods_______________________________________________________________________________________________________
    public void setStatus(String newStatus) {
        mainApplicationController.setStatus(newStatus);
    }

    public void refreshAllContent() {
        mainApplicationController.refreshAllContent(activeUser);
    }

    public boolean logout() {
        boolean logout = false;
        setStatus("Logging out: " + activeUser.getEmail());
        mainApplicationController.removeTabs();
        openLoginStage();
        this.activeUser = new User();
        return logout;
    }

    public boolean login(String email, String pw) throws SQLException {
        boolean login = dc.login(email, pw);
        if (login) {
            activeUser = dc.DBLoadUserByEmail(email);
            setStatus("Logged in \"" + activeUser.getEmail() + "\" with password \"" + activeUser.getPassword() + "\"");
            mainApplicationController.openTabs(activeUser);
            openApplicationStage();
        } else if (!login) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter correct email and password");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        return login;
    }

    /*****************************
     *
     * OPen-Up Methods
     *
     ******************************/

    public void openLoginStage() {
        editorStage.close();
        applicationStage.close();
        loginStage.show();
    }

    public void openApplicationStage() {
        loginStage.close();
        applicationStage.show();
    }

    /**
     * open new article in editor
     */
    public void openEditorScene() throws Exception {
        editorController.openNewArticle();
        editorStage.show();
        selectorStage.close();
    }

    public void openVideoEditor() throws Exception {
        videoStage.show();
        selectorStage.close();
    }

    public void openSelector() throws Exception {
        selectorStage.show();
    }

    /**
     * open an article in editor
     */
    // TODO: use this method for opening an article in ArticleTable for editing
    public void openEditorScene(Article article) {
        editorController.openArticle(article);
        editorStage.show();
    }

    public void callSideStage(sideStageState state) {
        String title = "";
        try {
            switch (state) {
                //TODO add all side Stages
                case createUser:
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_CreateUser.fxml"));
                    createUserPane = (Pane) sideLoader.load();
                    createUserController = sideLoader.getController();
                    createUserController.setMainController(this);
                    sideScene = new Scene(createUserPane);
                    title = "Nutzererstellung";
                    break;
                case createTopic:
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_CreateTopic.fxml"));
                    createTopicPane = (Pane) sideLoader.load();
                    createTopicController = sideLoader.getController();
                    createTopicController.setMainController(this);
                    sideScene = new Scene(createTopicPane);
                    title = "Bereichserstellung";
                    break;
            }
            sideStage.setTitle(title);
            sideScene.getStylesheets().add("/ELBIS_graphic/dark.css");
            sideStage.setScene(sideScene);
            sideStage.showAndWait();
        } catch (IOException io) {
            io.printStackTrace();
            setStatus("Could not load SideStage content");
        }
    }

    public void callSideStage(sideStageState state, int id) {
        String title = "";
        Boolean opensideStage = true;
        try {
            switch (state) {
                case editArticle:
                    openEditorScene((Article) dc.DBLoadArticle(id));
                    opensideStage = false;
                    break;
                case deleteArticle:
                    opensideStage = false;
                    Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    deleteAlert.setTitle("Artikel löschen");
                    deleteAlert.setContentText(" Dies kann nicht rückgängig gemacht werden.");
                    deleteAlert.setHeaderText("Sind sie sich sicher das sie diesen Artikel löschen möchten?");
                    Optional<ButtonType> deleteResult = deleteAlert.showAndWait();
                    if (deleteResult.get() == ButtonType.OK) {
                        //TODO actually delete an Article
                        //dc.DBDeleteArticle(id);
                        setStatus("Artikel gelöscht.");
                    } else {
                        setStatus("Aktion abgebrochen.");
                    }
                    break;
                case userSubmit:
                    opensideStage = false;
                    Article article = dc.DBLoadArticle(id);
                    Alert submitAlert;
                    if (article.getStatus().getStatusCode() > 1) {
                        submitAlert = new Alert(Alert.AlertType.INFORMATION);
                        submitAlert.setTitle("Veröffentlichung anfragen");
                        submitAlert.setContentText("Bitte überprüfen sie den Beitragsstatus und ggf. den Kommentar.");
                        submitAlert.setHeaderText("Der Beitrag wurde bereits zur Veröffentlichung freigegeben oder veröffentlicht.");
                        submitAlert.showAndWait();
                    } else {
                        submitAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        submitAlert.setTitle("Veröffentlichung anfragen");
                        submitAlert.setContentText("Der Beitrag kann im Folgenden von einem Moderator überprüft werden.");
                        submitAlert.setHeaderText("Sind sie sich sicher das sie diesen Artikel veröffentlichen möchten?");
                        Optional<ButtonType> submitResult = submitAlert.showAndWait();
                        if (submitResult.get() == ButtonType.OK) {
                            //TODO loadArticle does not load properly yet
                            article.setStatus(Status.Eingereicht);
                            dc.DBEditArticle(article);
                            setStatus("Artikel " + id + " zur veröffentlichung freigegeben.");
                        } else {
                            setStatus("Aktion abgebrochen.");
                        }
                    }
                    break;
                case manageSubmission:
                    //TODO create sideView
                    break;

            }
            if (opensideStage == true) {
                sideStage.setTitle(title);
                sideScene.getStylesheets().add("/ELBIS_graphic/dark.css");
                sideStage.setScene(sideScene);
                sideStage.showAndWait();
            }
            refreshAllContent();
        } catch (Exception e) {
            e.printStackTrace();
            setStatus("Could not load SideStage content");
        }
    }

    /******************************
     *
     * Refresh Table methods
     *
     ******************************/

    public TableView refreshUserContent_ArticleTable(TableView table) {
        MainController maincontroller = this;
        ObservableList<Article> articleList = dc.DBLoadOwnArticles(activeUser.getId());
        // Getter from Article Class
        List<String> propertyKeys = Arrays.asList("id", "title", "creationDate", "expireDate", "lastEdit", "status", "topicName", "author", "publisher", "publisherComment");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            //Check if button column reached
            if (i == 10) {
                //createButton via modded Cell class TableActionCell (usable for article Tables)
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        return new ActionCell_ArticleTable(maincontroller, "Bearbeiten", sideStageState.editArticle);
                    }
                });
            } else if (i == 11) {
                //deletebutton
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        return new ActionCell_ArticleTable(maincontroller, "Löschen", sideStageState.deleteArticle);
                    }
                });
            } else if (i == 12) {
                //deletebutton
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        //TODO fix userSubmit
                        return new ActionCell_ArticleTable(maincontroller, "Herausgeben", sideStageState.userSubmit);
                    }
                });
            } else {
                //setStatus("ArticleTable loading... " + ((TableColumn<Article, String>) table.getColumns().get(i)).getText());
                ((TableColumn<Article, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Article, String>((String) (propertyKeys.get(i))));
            }
        }
        table.setItems(articleList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ArticleTable loaded?");
        }
        return table;
    }

    public TableView refreshUserContent_TopicTable(TableView table) {

        ObservableList<Topic> topicList = dc.DBLoadAllowedTopics(activeUser.getId());

        List<String> propertyKeys = Arrays.asList("id", "name", "parentTopicString");

        for (int i = 0; i<table.getColumns().size(); i++){
            setStatus("TopicTable loading... " + ((TableColumn<Topic, String>) table.getColumns().get(i)).getText());
            ((TableColumn<Topic, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Topic, String>((String) (propertyKeys.get(i))));
        }

        table.setItems(topicList);

        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty TopicTable loaded?");
        }


        return table;
    }

    public TableView refreshModerationContent_UserTable(TableView table) {
        /*
        //TODO add buttonpanel to delete and edit (JUST) users per user in table
        */
        ObservableList<User> userList = dc.DBLoadAllUsers();
        // Getter from User Class
        List<String> propertyKeys = Arrays.asList("id", "email", "name", "gender", "role", "address", "dateOfBirth");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            setStatus("UserTable loading... " + ((TableColumn<User, String>) table.getColumns().get(i)).getText());
            ((TableColumn<User, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<User, String>((String) (propertyKeys.get(i))));
        }
        table.setItems(userList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ModerationTable loaded?");
        }
        return table;
    }

    public TableView refreshModerationContent_SubmissionTable(TableView table) {
        MainController maincontroller = this;
        ObservableList<Article> submissionList = dc.DBLoadAllSubmittedArticles();
        //a Getter from Article Class
        List<String> propertyKeys = Arrays.asList("id", "title", "creationDate", "expireDate", "lastEdit", "status", "topicName", "author", "publisher", "publisherComment");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            //Check if button column reached
            if (i == 10) {
                //createButton via modded Cell class TableActionCell (usable for article Tables)
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        return new ActionCell_ArticleTable(maincontroller, "Öffnen", sideStageState.editArticle);
                    }
                });
            } else if ( i == 11){
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        return new ActionCell_ArticleTable(maincontroller, "Verwalten", sideStageState.manageSubmission);
                    }
                });
            }
            else {
                //setStatus("ArticleTable loading... " + ((TableColumn<Article, String>) table.getColumns().get(i)).getText());
                ((TableColumn<Article, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Article, String>((String) (propertyKeys.get(i))));
            }
        }
        table.setItems(submissionList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ModerationTable loaded?");
        }
        return table;
    }

    public TableView refreshModerationContent_ArticleTable(TableView table) {
        MainController maincontroller = this;
        ObservableList<Article> articleList = dc.DBLoadAllArticles();
        // Getter from Article Class
        List<String> propertyKeys = Arrays.asList("id", "title", "creationDate", "expireDate", "lastEdit", "status", "topicName", "author", "publisher", "publisherComment");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            //Check if button column reached
            if (i == 10) {
                //createButton via modded Cell class TableActionCell (usable for article Tables)
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        return new ActionCell_ArticleTable(maincontroller, "Bearbeiten", sideStageState.editArticle);
                    }
                });
            } else if (i == 11) {
                //deletebutton
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        return new ActionCell_ArticleTable(maincontroller, "Löschen", sideStageState.deleteArticle);
                    }
                });
            }
                else {
                    //setStatus("ArticleTable loading... " + ((TableColumn<Article, String>) table.getColumns().get(i)).getText());
                    ((TableColumn<Article, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Article, String>((String) (propertyKeys.get(i))));
                }

        }
        table.setItems(articleList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ModerationTable loaded?");
        }
        return table;
    }

    public TableView refreshAdministrationContent_TopicTable(TableView table) {

        ObservableList<Topic> topicList = dc.DBLoadAllTopics();
        // Getter from User Class
        List<String> propertyKeys = Arrays.asList("id", "name", "parentTopicString");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            //setStatus("TopicTable loading... " + ((TableColumn<Topic, String>) table.getColumns().get(i)).getText());
            ((TableColumn<Topic, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Topic, String>((String) (propertyKeys.get(i))));
        }
        table.setItems(topicList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty AdministrationTable loaded?");
        }
        return table;
    }

    public TableView refreshAdministrationContent_UserTable(TableView table) {
        /*
        //TODO add buttonpanel to edit roles of an user or to delete an User
        */
        ObservableList<User> userList = dc.DBLoadAllUsers();
        // Getter from User Class
        List<String> propertyKeys = Arrays.asList("id", "email", "name", "gender", "role", "address", "dateOfBirth");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            //setStatus("UserTable loading... " + ((TableColumn<User, String>) table.getColumns().get(i)).getText());
            ((TableColumn<User, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<User, String>((String) (propertyKeys.get(i))));
        }
        table.setItems(userList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ModerationTable loaded?");
        }
        return table;
    }

    /******************************
     *
     *  Creation methods
     *
     ******************************/

    public boolean createUser(String email, String password, String name, String gender, String role, String address, String dateOfBirth) {
        boolean result = false;
        int genderInt = 0;

        if (gender.equals("Maennlich")) {
            genderInt = 1;
        } else if (gender.equals("Weiblich")) {
            genderInt = 2;
        } else if (gender.equals("Divers")) {
            genderInt = 3;
        }

        int roleInt = 0;

        if (role.equals("Admin")) {
            roleInt = 1;
        } else if (role.equals("Moderator")) {
            roleInt = 2;
        } else if (role.equals("User")) {
            roleInt = 3;
        }

        //TODO send actual User
        result = dc.DBSendNewUser(email, password, name, genderInt, roleInt, address, dateOfBirth);

        return result;
    }

    public boolean createArticle(Article article) {
        boolean result = false;
        result = dc.DBSendNewArticle(article);
        return result;
    }

    public boolean createTopic(String name, String parent) {
        boolean result = false;

        int topicInt = 0;

        if (parent.equals("Organisationen")) {
            topicInt = 1;
        } else if (parent.equals("Gemeinde")) {
            topicInt = 2;
        } else if (parent.equals("Industrie")) {
            topicInt = 3;
        }

        dc.DBSendNewTopic(name, topicInt);
        return result;
    }

    /******************************
     *
     * Other
     *
     ******************************/

    public boolean saveArticle(Article article) {
        boolean result = false;
        if (article.getId() == 0) {
            result = createArticle(article);
            Article newArticle = dc.DBLoadLastArticle();
            editorController.openArticle(newArticle);
        } else {
            result = dc.DBEditArticle(article);

        }

        return result;
    }

    public ObservableList<Topic> getAllTopics() {
        ObservableList<Topic> topics = dc.DBLoadAllTopics();

        return topics;
    }

    public Topic getTopic(int topicId)
    {
        return dc.DBLoadTopic(topicId);
    }

    //Getters,Setters_______________________________________________________________________________________________________
    public User getActiveUser() {
        return activeUser;
    }


}

