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
    public boolean darkMode = false;
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

    private FXMLController_EditUser editUserController;
    private Pane editUserPane;

    private FXMLController_ChangeUserRole changeUserRoleController;
    private Pane changeUserRolePane;

    private FXMLController_CreateTopic createTopicController;
    private Pane createTopicPane;

    private FXMLController_ManageSubmission manageSubmissionController;
    private Pane manageSubmissionPane;

    private FXMLController_ChangePassword changePasswordController;
    private Pane changePasswordPane;


    // Ctor_______________________________________________________________________________________________________
    public MainController() {

        dc = new DataController(this);
        // ###### Create Stages ######
        loginStage = new Stage(); // Login window
        loginStage.setTitle("Anmeldung");
        loginStage.setResizable(false);
        applicationStage = new Stage(); // Application Window
        applicationStage.setTitle("Verwaltung");
        applicationStage.setMinHeight(840); //DONT CHANGE
        applicationStage.setMinWidth(1220); //DONT CHANGE
        applicationStage.setMaximized(true);
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
            videoController.setMainController(this);

            selectorLoader = new FXMLLoader(getClass().getResource("/view/Pane_EditorSelector.fxml"));
            selectorPane = selectorLoader.load();
            selectorController = selectorLoader.getController();
            selectorController.setMainController(this);


            //Manage scenes
            loginScene = new Scene(loginPane);
            applicationScene = new Scene(applicationPane);

            editorScene = new Scene(editorPane);
            videoScene = new Scene(videoPane);
            selectorScene = new Scene(selectorPane);

            loginStage.setScene(loginScene);
            applicationStage.setScene(applicationScene);
            editorStage.setScene(editorScene);
            editorStage.initOwner(applicationStage);
            videoStage.setScene(videoScene);
            videoStage.initOwner(applicationStage);
            selectorStage.setScene(selectorScene);
            selectorStage.initOwner(applicationStage);
            sideStage.initOwner(applicationStage);


        } catch (IOException io) {
            System.out.println("Couldn't load scene File");
            io.printStackTrace();
        }
        switchDarkMode();
        openLoginStage();
    }

    // Methods_______________________________________________________________________________________________________
    public void setStatus(String newStatus) {
        mainApplicationController.setStatus(newStatus);
    }

    public void refreshAllContent() {
        mainApplicationController.refreshAllContent(activeUser);
        setStatus("Kontent neu geladen.");
    }

    public void switchDarkMode() {
        darkMode = !darkMode;
        if (darkMode == true) {
            //loginScene.getStylesheets().add("/ELBIS_graphic/dark.css");
            //applicationScene.getStylesheets().add("/ELBIS_graphic/dark.css");
            //sideScene.getStylesheets().add("/ELBIS_graphic/dark.css");

            loginScene.getStylesheets().add(getClass()
                    .getResource("/ELBIS_graphic/dark.css")
                    .toExternalForm());
            applicationScene.getStylesheets().add(getClass()
                    .getResource("/ELBIS_graphic/dark.css")
                    .toExternalForm());
            sideScene.getStylesheets().add(getClass()
                    .getResource("/ELBIS_graphic/dark.css")
                    .toExternalForm());
            setStatus("Es ist nun dunkel.");

        } else if (darkMode == false) {
            loginScene.getStylesheets().clear();
            applicationScene.getStylesheets().clear();
            sideScene.getStylesheets().clear();
            setStatus("Das Licht wurde eingeschaltet.");
        }
    }

    public boolean logout() {
        boolean logout = false;
        setStatus("Nutzer ausgeloggt: " + activeUser.getEmail());
        mainApplicationController.removeTabs();
        loginController.clear();
        openLoginStage();
        this.activeUser = new User();
        return logout;
    }

    public boolean login(String email, String pw) throws SQLException {
        setStatus("Versuche "+email+" einzuloggen...");
        boolean login = dc.login(email, pw);
        if (login) {
            activeUser = dc.DBLoadUserByEmail(email);
            if (activeUser instanceof Moderator || activeUser instanceof Administrator) {
                activeUser.setTopics(dc.DBLoadAllTopics());
            } else {
                activeUser.setTopics(dc.DBLoadAllowedTopics(activeUser.getId()));
            }
            setStatus("Nutzer eingeloggt: " + activeUser.getEmail());
            mainApplicationController.openTabs(activeUser);
            dc.DBUpdateAllArticles();
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
        videoController.setEditorController(editorController);
        selectorStage.close();
    }

    public void openEditorforVideo() {
        editorController.openVideoEditor();
        editorStage.show();
    }

    public void openSelector() throws Exception {
        selectorStage.show();
    }

    /**
     * open an article in editor
     */
    public void openEditorScene(Article article) {
        editorController.openArticle(article);
        editorStage.show();
    }

    public void callSideStage(sideStageState state) {
        String title = "";
        ObservableList<String> style = sideScene.getStylesheets();
        try {
            switch (state) {
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
                    createTopicController.setTopicID(0);
                    sideScene = new Scene(createTopicPane);
                    title = "Bereichserstellung";
                    break;
            }
            sideScene.getStylesheets().addAll(style);
            sideStage.setTitle(title);
            sideStage.setScene(sideScene);
            sideStage.showAndWait();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void callSideStage(sideStageState state, int id) {
        String title = "";
        Boolean opensideStage = true;
        ObservableList<String> style = sideScene.getStylesheets();
        try {
            switch (state) {
                case editArticle:
                    openEditorScene((Article) dc.DBLoadArticle(id));
                    setStatus("Der Artikel mit der ID "+id+ " wurde zum bearbeiten geöffnet.");
                    opensideStage = false;
                    break;
                case deleteArticle:
                    opensideStage = false;
                    Alert deleteArticleAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    deleteArticleAlert.setTitle("Artikel löschen");
                    deleteArticleAlert.setContentText(" Dies kann nicht rückgängig gemacht werden.");
                    deleteArticleAlert.setHeaderText("Sind sie sich sicher das sie diesen Artikel löschen möchten?");
                    Optional<ButtonType> deleteResult = deleteArticleAlert.showAndWait();
                    if (deleteResult.get() == ButtonType.OK) {
                        dc.DBDeleteArticle(id);
                        setStatus("Artikel " + id + " gelöscht.");
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
                            article.setStatus(Status.Eingereicht);
                            dc.DBEditArticle(article);
                            setStatus("Artikel " + id + " zur veröffentlichung freigegeben.");
                        } else {
                            setStatus("Aktion abgebrochen.");
                        }
                    }
                    break;
                case manageSubmission:
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_ManageSubmission.fxml"));
                    manageSubmissionPane = (Pane) sideLoader.load();
                    manageSubmissionController = sideLoader.getController();
                    manageSubmissionController.setMainController(this);
                    manageSubmissionController.setArticleId(id);
                    sideScene = new Scene(manageSubmissionPane);
                    title = "Nutzererstellung";
                    break;
                case deleteUser:
                    opensideStage = false;
                    Alert deleteUserAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    deleteUserAlert.setTitle("Nutzer löschen");
                    deleteUserAlert.setContentText("Dies kann nicht rückgängig gemacht werden.");
                    deleteUserAlert.setHeaderText("Sind sie sich sicher das sie diesen Nutzer löschen möchten?");
                    Optional<ButtonType> deleteUserResult = deleteUserAlert.showAndWait();
                    if (deleteUserResult.get() == ButtonType.OK) {
                        dc.DBDeleteUser(id);
                        setStatus("Nutzer " + id + " gelöscht.");
                    } else {
                        setStatus("Aktion abgebrochen.");
                    }
                    break;
                case editUser:
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_EditUser.fxml"));
                    editUserPane = (Pane) sideLoader.load();
                    editUserController = sideLoader.getController();
                    editUserController.setMainController(this);
                    editUserController.setUserID(id);
                    sideScene = new Scene(editUserPane);
                    title = "Nutzerbearbeitung";
                    break;
                case deleteTopic:
                    opensideStage = false;
                    Alert deleteTopicAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    deleteTopicAlert.setTitle("Bereich löschen");
                    deleteTopicAlert.setContentText("Dies kann nicht rückgängig gemacht werden.");
                    deleteTopicAlert.setHeaderText("Sind sie sich sicher das sie diesen Bereich löschen möchten?");
                    Optional<ButtonType> deleteTopicResult = deleteTopicAlert.showAndWait();
                    if (deleteTopicResult.get() == ButtonType.OK) {
                        dc.DBDeleteTopic(id);
                        setStatus("Bereich " + id + " gelöscht.");
                    } else {
                        setStatus("Aktion abgebrochen.");
                    }
                    break;
                case changeUserPassword:
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_ChangePassword.fxml"));
                    changePasswordPane = (Pane) sideLoader.load();
                    changePasswordController = sideLoader.getController();
                    changePasswordController.setMainController(this);
                    changePasswordController.setUserID(id);
                    sideScene = new Scene(changePasswordPane);
                    title = "Nutzer-Passwort ändern";
                    break;
                case editTopic:
                    //use createTopic to edit via id
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_CreateTopic.fxml"));
                    createTopicPane = (Pane) sideLoader.load();
                    createTopicController = sideLoader.getController();
                    createTopicController.setMainController(this);
                    createTopicController.setTopicID(id);
                    createTopicController.setCurrentTopic(dc.DBLoadTopic(id));
                    sideScene = new Scene(createTopicPane);
                    title = "Bereichsbearbeitung";
                    break;
                case showComment:
                    opensideStage = false;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Kommentar Anzeigen");

                    TextArea statusArea = new TextArea();
                    statusArea.setWrapText(true);
                    statusArea.setEditable(false);

                    statusArea.setText(dc.DBLoadArticle(id).getPublisherComment());

                    alert.getDialogPane().setContent(statusArea);
                    alert.setResizable(true);

                    alert.setHeaderText("Herausgeberkommentar");
                    statusArea.selectPositionCaret(statusArea.getLength());
                    statusArea.deselect();
                    alert.showAndWait();
                    break;
                case changeUserRole:
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_ChangeUserRole.fxml"));
                    changeUserRolePane = (Pane) sideLoader.load();
                    changeUserRoleController = sideLoader.getController();
                    changeUserRoleController.setMainController(this);
                    changeUserRoleController.setUserID(id);
                    sideScene = new Scene(changeUserRolePane);
                    title = "Nutzer-Rolle ändern";
                    break;
            }
            if (opensideStage == true) {
                sideScene.getStylesheets().addAll(style);
                sideStage.setTitle(title);
                sideStage.setScene(sideScene);
                sideStage.showAndWait();
            }
            refreshAllContent();
        } catch (Exception e) {
            e.printStackTrace();

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
                        return new ActionCell_ArticleTable(maincontroller, "Herausgeben", sideStageState.userSubmit);
                    }
                });
            } else if (i == 13) {
                //deletebutton
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        return new ActionCell_ArticleTable(maincontroller, "Kommentar anzeigen", sideStageState.showComment);
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
            System.out.println("Warning: Empty ArticleTable loaded?");
        }
        return table;
    }

    public TableView refreshUserContent_TopicTable(TableView table) {

        //ObservableList<Topic> topicList = dc.DBLoadAllowedTopics(activeUser.getId());
        ObservableList<Topic> topicList = activeUser.getTopics();

        List<String> propertyKeys = Arrays.asList("id", "name", "parent");

        for (int i = 0; i < table.getColumns().size(); i++) {
            //setStatus("TopicTable loading... " + ((TableColumn<Topic, String>) table.getColumns().get(i)).getText());
            ((TableColumn<Topic, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Topic, String>((String) (propertyKeys.get(i))));
        }

        table.setItems(topicList);

        if (table.getItems().size() == 0) {
            System.out.println("Warning: Empty TopicTable loaded?");
        }
        return table;
    }

    public TableView refreshModerationContent_UserTable(TableView table) {
        MainController maincontroller = this;
        ObservableList<User> userList = dc.DBLoadAllUsers();
        // Getter from User Class
        List<String> propertyKeys = Arrays.asList("id", "email", "name", "gender", "role", "address", "dateOfBirth");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            //Check if button column reached
            if (i == 7) {
                ((TableColumn<User, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                    @Override
                    public TableCell<User, Boolean> call(TableColumn<User, Boolean> BooleanTableColumn) {
                        return new ActionCell_UserTable(maincontroller, "Löschen", sideStageState.deleteUser);
                    }
                });
            } else if (i == 8) {
                ((TableColumn<User, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                    @Override
                    public TableCell<User, Boolean> call(TableColumn<User, Boolean> BooleanTableColumn) {
                        return new ActionCell_UserTable(maincontroller, "Bearbeiten", sideStageState.editUser);
                    }
                });
            } else if (i == 9) {
                ((TableColumn<User, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                    @Override
                    public TableCell<User, Boolean> call(TableColumn<User, Boolean> BooleanTableColumn) {
                        return new ActionCell_UserTable(maincontroller, "Passwort ändern", sideStageState.changeUserPassword);
                    }
                });
            } else {
                //setStatus("UserTable loading... " + ((TableColumn<User, String>) table.getColumns().get(i)).getText());
                ((TableColumn<User, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<User, String>((String) (propertyKeys.get(i))));
            }
        }
        table.setItems(userList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            System.out.println("Warnung: Empty ModerationTable loaded?");
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
            } else if (i == 11) {
                ((TableColumn<Article, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Article, Boolean>, TableCell<Article, Boolean>>() {
                    @Override
                    public TableCell<Article, Boolean> call(TableColumn<Article, Boolean> BooleanTableColumn) {
                        return new ActionCell_ArticleTable(maincontroller, "Verwalten", sideStageState.manageSubmission);
                    }
                });
            } else {
                //setStatus("ArticleTable loading... " + ((TableColumn<Article, String>) table.getColumns().get(i)).getText());
                ((TableColumn<Article, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Article, String>((String) (propertyKeys.get(i))));
            }
        }
        table.setItems(submissionList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            System.out.println("Warning: Empty ModerationTable loaded?");
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
                        return new ActionCell_ArticleTable(maincontroller, "Öffnen", sideStageState.editArticle);
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
                        return new ActionCell_ArticleTable(maincontroller, "Kommentar anzeigen", sideStageState.showComment);
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
            System.out.println("Warning: Empty ModerationTable loaded?");
        }
        return table;
    }

    public TableView refreshAdministrationContent_TopicTable(TableView table) {
        MainController maincontroller = this;
        ObservableList<Topic> topicList = dc.DBLoadAllTopics();
        // Getter from User Class
        List<String> propertyKeys = Arrays.asList("id", "name", "parent");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            //Check if button column reached
            if (i == 3) {
                ((TableColumn<Topic, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Topic, Boolean>, TableCell<Topic, Boolean>>() {
                    @Override
                    public TableCell<Topic, Boolean> call(TableColumn<Topic, Boolean> BooleanTableColumn) {
                        return new ActionCell_TopicTable(maincontroller, "Bearbeiten", sideStageState.editTopic);
                    }
                });
            } else if (i == 4) {
                ((TableColumn<Topic, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<Topic, Boolean>, TableCell<Topic, Boolean>>() {
                    @Override
                    public TableCell<Topic, Boolean> call(TableColumn<Topic, Boolean> BooleanTableColumn) {
                        return new ActionCell_TopicTable(maincontroller, "Löschen", sideStageState.deleteTopic);
                    }
                });
            } else {
                //setStatus("TopicTable loading... " + ((TableColumn<Topic, String>) table.getColumns().get(i)).getText());
                ((TableColumn<Topic, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Topic, String>((String) (propertyKeys.get(i))));
            }
        }
        table.setItems(topicList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            System.out.println("Warning: Empty AdministrationTable loaded?");
        }
        return table;
    }

    public TableView refreshAdministrationContent_UserTable(TableView table) {
        MainController maincontroller = this;
        ObservableList<User> userList = dc.DBLoadAllUsers();
        // Getter from User Class
        List<String> propertyKeys = Arrays.asList("id", "email", "name", "gender", "role", "address", "dateOfBirth");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            if (i == 7) {
                ((TableColumn<User, Boolean>) table.getColumns().get(i)).setCellFactory(new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                    @Override
                    public TableCell<User, Boolean> call(TableColumn<User, Boolean> BooleanTableColumn) {
                        return new ActionCell_UserTable(maincontroller, "Rolle ändern", sideStageState.changeUserRole);
                    }
                });
            } else {
                //setStatus("UserTable loading... " + ((TableColumn<User, String>) table.getColumns().get(i)).getText());
                ((TableColumn<User, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<User, String>((String) (propertyKeys.get(i))));
            }
        }
        table.setItems(userList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            System.out.println("Warning: Empty ModerationTable loaded?");
        }
        return table;
    }

    /******************************
     *
     *  Creation and edit methods
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
        result = dc.DBSendNewUser(email, password, name, genderInt, roleInt, address, dateOfBirth);
        setStatus("Nutzer "+" ("+email+") wurde erstellt.");
        return result;
    }

    public boolean editUser(int id, String email, String name, String gender, String role, String address, String dateOfBirth) {
        boolean result = false;
        User editedUser = dc.DBLoadUserById(id);

        int genderInt;
        if (gender.equals("Maennlich")) {
            genderInt = 1;
        } else if (gender.equals("Weiblich")) {
            genderInt = 2;
        } else if (gender.equals("Divers")) {
            genderInt = 3;
        } else {
            genderInt = 0;
        }

        int roleInt;
        if (role.equals("Admin")) {
            roleInt = 1;
        } else if (role.equals("Moderator")) {
            roleInt = 2;
        } else if (role.equals("User")) {
            roleInt = 3;
        } else {
            roleInt = 0;
        }

        result = dc.DBEditUser(id, email, name, address, genderInt, dateOfBirth, roleInt);
        setStatus("Nutzer "+name+" ("+email+") wurde bearbeitet.");
        return result;
    }

    public boolean changeUserRole(int userID, String Role) {
        boolean result = false;
        User editUser = dc.DBLoadUserById(userID);
        int roleInt = 0;
        if (Role.equals("User")) {
            roleInt = 3;
        } else if (Role.equals("Moderator")) {
            roleInt = 2;
        } else if (Role.equals("Administrator")) {
            roleInt = 1;
        }
        dc.DBEditUser(userID, editUser.getEmail(), editUser.getName(), editUser.getAddress(), editUser.getGenderAsInt(), editUser.getDateOfBirth().toString(), roleInt);
        setStatus("Nutzerrolle von "+editUser.getName()+" ("+editUser.getEmail()+") wurde zu \""+Role+"\" geändert.");
        return result;
    }

    public boolean createArticle(Article article) {
        boolean result = false;
        result = dc.DBSendNewArticle(article);
        setStatus("neuer Artikel (\""+article.getTitle()+"\") wurde gespeichert.");
        return result;
    }

    public boolean createTopic(String name, String parent) {
        boolean result = false;
        ObservableList<Topic> topicList = dc.DBLoadAllTopics();
        Topic currentTopic;
        int topicInt = 0;

        for (int i = 0; i < topicList.size(); i++) {
            currentTopic = topicList.get(i);
            if (currentTopic.getName().equals(parent)) {
                topicInt = currentTopic.getId();
            }
        }
        dc.DBSendNewTopic(name, topicInt);
        setStatus("neues Topic angelegt: "+name);
        return result;
    }

    public boolean editTopic(int id, String name, String parent) {

        boolean result = false;
        ObservableList<Topic> topicList = dc.DBLoadAllTopics();
        Topic currentTopic;
        int topicInt = 0;

        for (int i = 0; i < topicList.size(); i++) {
            currentTopic = topicList.get(i);
            if (currentTopic.getName().equals(parent)) {
                topicInt = currentTopic.getId();
            }
        }
        result = dc.DBEditTopic(id, name, topicInt);
        setStatus("Topic "+id+" bearbeitet.");
        return result;
    }

    /******************************
     *
     * Other
     *
     ******************************/

    public boolean submitArticle(int articleID, Status status, String comment) {
        boolean submitted = false;
        Article article = dc.DBLoadArticle(articleID);
        article.setStatus(status);
        article.setPublisherComment(comment);
        article.setPublisher(activeUser);
        dc.DBEditArticle(article);
        setStatus("Artikel " + articleID + " wurde auf den Status: \"" + status.toString() + "\" gesetzt.");
        return submitted;
    }

    public boolean changePassword(int userID, String password) {
        boolean result = false;
        try {
            result = dc.DBChangePassword(userID, password);
            setStatus("Passwort von Nutzer "+userID +" wurde geändert.");
        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
        }
        return result;
    }

    public boolean saveArticle(Article article) {
        boolean result = false;
        if (article.getId() == 0) {
            result = createArticle(article);
            Article newArticle = dc.DBLoadLastArticle();
            editorController.openArticle(newArticle);
        } else {
            System.out.println(article.getTopic());
            result = dc.DBEditArticle(article);
            setStatus("Artikel (\""+article.getTitle()+"\") wurde gespeichert.");
        }

        return result;
    }

    public Topic getTopic(int topicId) {
        return dc.DBLoadTopic(topicId);
    }

    //Getters,Setters_______________________________________________________________________________________________________
    public User getActiveUser() {
        return activeUser;
    }

}

