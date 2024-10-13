package cqu.drsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static String loggedInUser = null;  // Tracks the logged-in user

    @Override
    public void start(Stage stage) throws IOException {
        // Load login screen first
        scene = new Scene(loadFXML("login"), 600, 400);  // Initial scene is the login page
        stage.setTitle("Disaster Response System - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(String username) {
        loggedInUser = username;
    }

    public static void logout() throws IOException {
        loggedInUser = null;
        setRoot("login");  // Redirect to login page
    }

    public static void main(String[] args) {
        launch();
    }
}
