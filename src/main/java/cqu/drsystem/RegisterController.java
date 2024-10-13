package cqu.drsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void register(ActionEvent event) throws Exception {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        // Input validation
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username cannot be empty.");
            return;
        }

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Password cannot be empty.");
            return;
        }

        if (confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Confirm Password cannot be empty.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
            return;
        }

        // Additional validation rules (e.g., minimum length, password strength)
        if (username.length() < 3) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username must be at least 3 characters long.");
            return;
        }

        if (password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Password must be at least 6 characters long.");
            return;
        }

        if (!password.matches(".*[A-Z].*")) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Password must contain at least one uppercase letter.");
            return;
        }

        if (!password.matches(".*[0-9].*")) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Password must contain at least one digit.");
            return;
        }

        // Register user and navigate to login page
        LoginController.registerUser(username, password);
        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You can now log in.");
        App.setRoot("login");  // Redirect to login page
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
