package cqu.drsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PrimaryController {
    @FXML private TextField disasterTypeField;
    @FXML private TextField locationField;
    @FXML private ComboBox<String> severityComboBox;
    @FXML private TextArea descriptionArea;
    @FXML private TextArea disasterLogArea;
    @FXML private ListView<String> departmentListView;

    private final List<Disaster> disasterLog = new ArrayList<>();
    private final Map<String, List<String>> departmentCoordination = new HashMap<>();

    /**
     * Initializes the controller.
     * Sets up the severity combo box and initializes departments.
     */
    @FXML
    public void initialize() {
        severityComboBox.getItems().addAll("Low", "Medium", "High");
        initializeDepartments();
    }

    /**
     * Initializes the departments and their coordination lists.
     * Updates the department list view.
     */
    private void initializeDepartments() {
        departmentCoordination.put("Fire Department", new ArrayList<>());
        departmentCoordination.put("Emergency Response", new ArrayList<>());
        departmentCoordination.put("Hospital", new ArrayList<>());
        departmentCoordination.put("Transportation", new ArrayList<>());
        departmentCoordination.put("Utility Services", new ArrayList<>());
        departmentCoordination.put("Law Enforcement", new ArrayList<>());
        updateDepartmentListView();
    }

      /**
     * Handles the disaster reporting process.
     * Validates input, creates a new Disaster object, adds it to the log,
     * prioritizes the response, notifies relevant departments, and updates the UI.
     *
     * @param event The action event triggering this method
     */
    @FXML
    public void reportDisaster(ActionEvent event) {
        String type = disasterTypeField.getText();
        String location = locationField.getText();
        String severity = severityComboBox.getValue();
        String description = descriptionArea.getText();

        if (type.isEmpty() || location.isEmpty() || severity == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields");
            return;
        }

        Disaster disaster = new Disaster(type, location, severity, description);
        disasterLog.add(disaster);
        
        prioritizeResponse(disaster);
        notifyDepartments(disaster);
        updateDisasterLog();
        clearInputFields();
    }

    /**
     * Determines the response priority based on the disaster severity.
     * Displays an alert with the priority classification.
     *
     * @param disaster The reported disaster
     */
    private void prioritizeResponse(Disaster disaster) {
        String priority;
        switch (disaster.getSeverity().toLowerCase()) {
            case "high":
                priority = "Immediate Response Required";
                break;
            case "medium":
                priority = "Moderate Response Required";
                break;
            case "low":
                priority = "Low Response Priority";
                break;
            default:
                priority = "Unknown Severity";
        }
        showAlert(Alert.AlertType.INFORMATION, "Disaster Response Priority", 
                  "The disaster reported has been classified as: " + priority);
    }

     /**
     * Notifies relevant departments based on the disaster type.
     * Updates the department coordination map and the UI.
     * Displays an alert with the notified departments.
     *
     * @param disaster The reported disaster
     */
    private void notifyDepartments(Disaster disaster) {
        List<String> notifiedDepartments = new ArrayList<>();
        switch (disaster.getType().toLowerCase()) {
            case "fire":
                notifiedDepartments.add("Fire Department");
                notifiedDepartments.add("Emergency Response");
                break;
            case "earthquake":
                notifiedDepartments.add("Emergency Response");
                notifiedDepartments.add("Hospital");
                notifiedDepartments.add("Fire Department");
                break;
            case "hurricane":
                notifiedDepartments.add("Transportation");
                notifiedDepartments.add("Utility Services");
                notifiedDepartments.add("Emergency Response");
                break;
            default:
                notifiedDepartments.add("Emergency Response");
        }

        for (String dept : notifiedDepartments) {
            departmentCoordination.get(dept).add(disaster.toString());
        }

        updateDepartmentListView();
        showAlert(Alert.AlertType.INFORMATION, "Department Notification", 
                  String.join(", ", notifiedDepartments) + " have been notified for this disaster.");
    }

      /**
     * Updates the department list view with the current coordination status.
     */
    private void updateDepartmentListView() {
        departmentListView.getItems().clear();
        for (Map.Entry<String, List<String>> entry : departmentCoordination.entrySet()) {
            departmentListView.getItems().add(entry.getKey() + ": " + entry.getValue().size() + " disasters");
        }
    }

    /**
     * Displays the current disaster log in the UI.
     */
    @FXML
    public void showDisasterLog() {
        updateDisasterLog();
    }

     /**
     * Updates the disaster log text area with the current list of disasters.
     */
    private void updateDisasterLog() {
        StringBuilder log = new StringBuilder();
        for (int i = 0; i < disasterLog.size(); i++) {
            log.append(i + 1).append(". ").append(disasterLog.get(i)).append("\n");
        }
        disasterLogArea.setText(log.toString());
    }

     /**
     * Clears all input fields after a disaster report is submitted.
     */
    private void clearInputFields() {
        disasterTypeField.clear();
        locationField.clear();
        severityComboBox.getSelectionModel().clearSelection();
        descriptionArea.clear();
    }

    /**
     * Displays an alert dialog with the specified type, title, and content.
     *
     * @param alertType The type of alert to display
     * @param title The title of the alert dialog
     * @param content The content message of the alert dialog
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Getter methods for testing
    public TextField getDisasterTypeField() { return disasterTypeField; }
    public void setDisasterTypeField(TextField disasterTypeField) { this.disasterTypeField = disasterTypeField; }

    public TextField getLocationField() { return locationField; }
    public void setLocationField(TextField locationField) { this.locationField = locationField; }

    public ComboBox<String> getSeverityComboBox() { return severityComboBox; }
    public void setSeverityComboBox(ComboBox<String> severityComboBox) { this.severityComboBox = severityComboBox; }

    public TextArea getDescriptionArea() { return descriptionArea; }
    public void setDescriptionArea(TextArea descriptionArea) { this.descriptionArea = descriptionArea; }

    public TextArea getDisasterLogArea() { return disasterLogArea; }
    public void setDisasterLogArea(TextArea disasterLogArea) { this.disasterLogArea = disasterLogArea; }

    public ListView<String> getDepartmentListView() { return departmentListView; }
    public void setDepartmentListView(ListView<String> departmentListView) { this.departmentListView = departmentListView; }
    
    public void setDisasterDetails(String type, String location, String severity, String description) {
        if (disasterTypeField != null) disasterTypeField.setText(type);
        if (locationField != null) locationField.setText(location);
        if (severityComboBox != null) severityComboBox.setValue(severity);
        if (descriptionArea != null) descriptionArea.setText(description);
    }

    public String getDisasterLogContent() {
        return disasterLogArea != null ? disasterLogArea.getText() : "";
    }

    public List<String> getDepartmentCoordinationStatus() {
        return departmentListView != null ? departmentListView.getItems() : new ArrayList<>();
    }

    public List<Disaster> getDisasterLog() {
        return new ArrayList<>(disasterLog);
    }
    
    public void reportDisasterForTesting(String type, String location, String severity, String description) {
        if (type.isEmpty() || location.isEmpty() || severity == null) {
            // 模拟显示错误提示
            System.out.println("Error: Please fill in all fields");
            return;
        }

        Disaster disaster = new Disaster(type, location, severity, description);
        disasterLog.add(disaster);
        
        prioritizeResponse(disaster);
        notifyDepartments(disaster);
        // 不调用updateDisasterLog()和clearInputFields()，因为它们依赖于JavaFX组件
    }

}