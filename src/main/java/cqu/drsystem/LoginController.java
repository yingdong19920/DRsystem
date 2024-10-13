package cqu.drsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private static Map<String, String> registeredUsers = new HashMap<>();  // Stores registered users

    public static void registerUser(String username, String password) {
        registeredUsers.put(username, password);
    }

    @FXML
    private void login(ActionEvent event) throws Exception {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Input validation
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            return;
        }

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password cannot be empty.");
            return;
        }

        // Optionally, you can add more validation like minimum length
        if (username.length() < 3) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username must be at least 3 characters long.");
            return;
        }

        if (password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password must be at least 6 characters long.");
            return;
        }

        // Check credentials
        if (registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password)) {
            App.setLoggedInUser(username);
            App.setRoot("primary");  // Load the main system page
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) throws Exception {
        App.setRoot("register");  // Load the registration page
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
