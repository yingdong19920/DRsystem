package cqu.drsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * The PrimaryController class manages the user interactions and functionality
 * for the Disaster Response System (DRS) user interface. It handles reporting
 * disasters, resource allocation, department coordination, and disaster logging.
 */
public class PrimaryController {
    @FXML private ComboBox<String> disasterTypeComboBox;  // Disaster type as ComboBox
    @FXML private TextField locationField;
    @FXML private ComboBox<String> severityComboBox;
    @FXML private TextArea descriptionArea;
    @FXML private TextArea disasterLogArea;
    @FXML private ListView<String> departmentListView;
    @FXML private ListView<String> resourceListView;

    // Resource selection components
    @FXML private CheckBox fireTruckCheckBox;
    @FXML private Spinner<Integer> fireTruckSpinner;
    @FXML private CheckBox ambulanceCheckBox;
    @FXML private Spinner<Integer> ambulanceSpinner;
    @FXML private CheckBox rescueTeamCheckBox;
    @FXML private Spinner<Integer> rescueTeamSpinner;

    private final List<Disaster> disasterLog = new ArrayList<>();
    private final Map<String, List<String>> departmentCoordination = new HashMap<>();
    private final ResourceManagement resourceManagement = new ResourceManagement();
    private final List<Resource> selectedResources = new ArrayList<>();

    /**
     * Initializes the disaster types, severity options, departments, and resource spinners.
     * This method is automatically called when the FXML is loaded.
     */
    @FXML
    public void initialize() {
        // Populate disaster types
        disasterTypeComboBox.getItems().addAll("Earthquake", "Flood", "Hurricane", "Fire", "Tornado");
        
        // Set default value to "Select"
        disasterTypeComboBox.setValue("Select");
        severityComboBox.setValue("Select");
        
        severityComboBox.getItems().addAll("Low", "Medium", "High");
        initializeDepartments();
        initializeResources();

        // Set value factory for spinners
        fireTruckSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        ambulanceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        rescueTeamSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));

        // Initially, the resource list view is empty
        resourceListView.getItems().clear();
    }

    /**
     * Initializes the department coordination map with available departments.
     * Updates the department list view to reflect the initialized departments.
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
     * Initializes the available resources in the ResourceManagement class.
     */
    private void initializeResources() {
        resourceManagement.addResource(new Resource("1", "Fire Truck", "Vehicle", "available", 10));
        resourceManagement.addResource(new Resource("2", "Ambulance", "Vehicle", "available", 8));
        resourceManagement.addResource(new Resource("3", "Rescue Team", "Personnel", "available", 15));
    }

    /**
     * Reports a disaster by collecting user input for disaster details and allocating resources.
     * Notifies the relevant departments and updates the disaster log.
     *
     * @param event the ActionEvent triggered by the report button
     */
    @FXML
    public void reportDisaster(ActionEvent event) {
        // Validate user input
        if (!validateInput()) {
            return;  // If validation fails, stop the process
        }

        String type = disasterTypeComboBox.getValue();  // Get selected disaster type
        String location = locationField.getText();
        String severity = severityComboBox.getValue();
        String description = descriptionArea.getText();

        // Create and add disaster to log
        Disaster disaster = new Disaster(type, location, severity, description);
        disasterLog.add(disaster);

        // Allocate resources based on user selection
        allocateResources(disaster);

        // Notify departments based on the disaster type
        notifyDepartments(disaster);

        // Update disaster log and clear input fields
        updateDisasterLog();
    }

    /**
     * Validates user input for disaster reporting.
     * Ensures that all fields are filled before proceeding with the report.
     *
     * @return true if validation passes, false otherwise
     */
    private boolean validateInput() {
    StringBuilder errorMessage = new StringBuilder();

     // Check disaster type selection (ensure it's not the default value "Select")
    if (disasterTypeComboBox.getValue() == null || disasterTypeComboBox.getValue().equals("Select")) {
        errorMessage.append("Please select a valid disaster type.\n");
    }

    // Check location field: ensure it is not empty and has at least 3 characters
    if (locationField.getText().isEmpty() || locationField.getText().trim().length() < 3) {
        errorMessage.append("Please enter a valid location (at least 3 characters).\n");
    }

    // Check for alphabetic characters in location
    if (!locationField.getText().matches("[a-zA-Z\\s]+")) {
        errorMessage.append("Location must contain only letters and spaces.\n");
    }

    // Check severity selection
    if (severityComboBox.getValue() == null || severityComboBox.getValue().equals("Select")) {
        errorMessage.append("Please select the severity of the disaster.\n");
    }

    // Check description field: minimum of 10 characters required
    if (descriptionArea.getText().isEmpty() || descriptionArea.getText().trim().length() < 10) {
        errorMessage.append("Please provide a more detailed description (at least 10 characters).\n");
    }

    // Check resource selection and quantities
    boolean resourceSelected = false;

    // Check if Fire Truck is selected and validate the quantity
    if (fireTruckCheckBox.isSelected()) {
        resourceSelected = true;
        if (fireTruckSpinner.getValue() == 0) {
            errorMessage.append("Please select a valid quantity for Fire Trucks (greater than 0).\n");
        } else if (fireTruckSpinner.getValue() > 10) {
            errorMessage.append("The quantity of Fire Trucks cannot exceed 10.\n");
        }
    }

    // Check if Ambulance is selected and validate the quantity
    if (ambulanceCheckBox.isSelected()) {
        resourceSelected = true;
        if (ambulanceSpinner.getValue() == 0) {
            errorMessage.append("Please select a valid quantity for Ambulances (greater than 0).\n");
        } else if (ambulanceSpinner.getValue() > 8) {
            errorMessage.append("The quantity of Ambulances cannot exceed 8.\n");
        }
    }

    // Check if Rescue Team is selected and validate the quantity
    if (rescueTeamCheckBox.isSelected()) {
        resourceSelected = true;
        if (rescueTeamSpinner.getValue() == 0) {
            errorMessage.append("Please select a valid quantity for Rescue Teams (greater than 0).\n");
        } else if (rescueTeamSpinner.getValue() > 15) {
            errorMessage.append("The quantity of Rescue Teams cannot exceed 15.\n");
        }
    }

    // Ensure at least one resource has been selected
    if (!resourceSelected) {
        errorMessage.append("Please select at least one resource to allocate.\n");
    }

    // Show error message if any validation fails
    if (errorMessage.length() > 0) {
        showAlert(Alert.AlertType.ERROR, "Input Validation Error", errorMessage.toString());
        return false;
    }

    return true;
}
    /**
     * Notifies relevant departments based on the type of disaster and updates the department list view.
     *
     * @param disaster the reported disaster
     */
    private void notifyDepartments(Disaster disaster) {
        List<String> notifiedDepartments = new ArrayList<>();
        switch (disaster.getType().toLowerCase()) {
            case "earthquake":
                notifiedDepartments.add("Emergency Response");
                notifiedDepartments.add("Hospital");
                notifiedDepartments.add("Fire Department");
                break;
            case "flood":
                notifiedDepartments.add("Emergency Response");
                notifiedDepartments.add("Utility Services");
                notifiedDepartments.add("Law Enforcement");
                break;
            case "hurricane":
                notifiedDepartments.add("Emergency Response");
                notifiedDepartments.add("Transportation");
                notifiedDepartments.add("Utility Services");
                break;
            case "fire":
                notifiedDepartments.add("Fire Department");
                notifiedDepartments.add("Emergency Response");
                break;
            case "tornado":
                notifiedDepartments.add("Emergency Response");
                notifiedDepartments.add("Law Enforcement");
                notifiedDepartments.add("Transportation");
                break;
            default:
                notifiedDepartments.add("Emergency Response");
                break;
        }

        // Update department coordination list with the notified departments
        for (String department : notifiedDepartments) {
            departmentCoordination.get(department).add(disaster.toString());
        }

        // Update the department coordination UI
        updateDepartmentListView();

        // Show a confirmation alert with the notified departments
        showAlert(Alert.AlertType.INFORMATION, "Department Notification", 
                  "The following departments have been notified: " + String.join(", ", notifiedDepartments));
    }

    /**
     * Allocates resources based on user selection and updates the resource list view.
     *
     * @param disaster the reported disaster
     */
    private void allocateResources(Disaster disaster) {
        selectedResources.clear();  // Clear previously selected resources

        // Check if Fire Truck is selected and the quantity is greater than 0
        if (fireTruckCheckBox.isSelected()) {
            int quantity = fireTruckSpinner.getValue();
            if (quantity > 0) {
                Resource fireTruck = resourceManagement.getResourceByName("Fire Truck");
                if (fireTruck != null && fireTruck.getAvailableQuantity() >= quantity) {
                    fireTruck.setAvailableQuantity(fireTruck.getAvailableQuantity() - quantity);
                    fireTruck.setAllocatedQuantity(quantity);  // Record allocated quantity
                    selectedResources.add(fireTruck);
                } else {
                    showAlert(Alert.AlertType.WARNING, "Resource Allocation", "Not enough Fire Trucks available.");
                }
            }
        }

        // Check if Ambulance is selected and the quantity is greater than 0
        if (ambulanceCheckBox.isSelected()) {
            int quantity = ambulanceSpinner.getValue();
            if (quantity > 0) {
                Resource ambulance = resourceManagement.getResourceByName("Ambulance");
                if (ambulance != null && ambulance.getAvailableQuantity() >= quantity) {
                    ambulance.setAvailableQuantity(ambulance.getAvailableQuantity() - quantity);
                    ambulance.setAllocatedQuantity(quantity);  // Record allocated quantity
                    selectedResources.add(ambulance);
                } else {
                    showAlert(Alert.AlertType.WARNING, "Resource Allocation", "Not enough Ambulances available.");
                }
            }
        }

        // Check if Rescue Team is selected and the quantity is greater than 0
        if (rescueTeamCheckBox.isSelected()) {
            int quantity = rescueTeamSpinner.getValue();
            if (quantity > 0) {
                Resource rescueTeam = resourceManagement.getResourceByName("Rescue Team");
                if (rescueTeam != null && rescueTeam.getAvailableQuantity() >= quantity) {
                    rescueTeam.setAvailableQuantity(rescueTeam.getAvailableQuantity() - quantity);
                    rescueTeam.setAllocatedQuantity(quantity);  // Record allocated quantity
                    selectedResources.add(rescueTeam);
                } else {
                    showAlert(Alert.AlertType.WARNING, "Resource Allocation", "Not enough Rescue Teams available.");
                }
            }
        }

        // Allocate the selected resources to the disaster and update the resource table
        for (Resource resource : selectedResources) {
            resourceManagement.allocateResource(disaster, resource); // Allocate resources to the disaster
        }

        // Update the resource management table (resource list view) only after selection and reporting
        updateResourceListView();
    }

    /**
     * Updates the resource list view with the currently selected resources.
     */
    private void updateResourceListView() {
        resourceListView.getItems().clear();
        for (Resource resource : selectedResources) {
            resourceListView.getItems().add(resource.toString());
        }
    }

    /**
     * Updates the department list view to reflect the current department coordination status.
     */
    private void updateDepartmentListView() {
        departmentListView.getItems().clear();
        for (Map.Entry<String, List<String>> entry : departmentCoordination.entrySet()) {
            departmentListView.getItems().add(entry.getKey() + ": " + entry.getValue().size() + " disasters");
        }
    }

    /**
     * Resets the system by clearing all data and restoring default values in the UI components.
     *
     * @param event the ActionEvent triggered by the clean log button
     */
    @FXML
    public void cleanLog(ActionEvent event) {
        // Clear all data
        disasterLog.clear();
        departmentCoordination.clear();
        selectedResources.clear();
        resourceListView.getItems().clear();
        disasterLogArea.clear();

        // Reset UI components to default state
        disasterTypeComboBox.getSelectionModel().clearSelection();  // Clear disaster type selection
        locationField.clear();
        severityComboBox.getSelectionModel().clearSelection();
        descriptionArea.clear();
        fireTruckCheckBox.setSelected(false);
        ambulanceCheckBox.setSelected(false);
        rescueTeamCheckBox.setSelected(false);
        fireTruckSpinner.getValueFactory().setValue(0);
        ambulanceSpinner.getValueFactory().setValue(0);
        rescueTeamSpinner.getValueFactory().setValue(0);
        
        // Reinitialize departments and resources
        initializeDepartments();
        initializeResources();

        showAlert(Alert.AlertType.INFORMATION, "System Reset", "The system has been reset to the default state.");
    }

    /**
     * Updates the disaster log view with the list of reported disasters.
     */
    private void updateDisasterLog() {
        StringBuilder log = new StringBuilder();
        for (int i = 0; i < disasterLog.size(); i++) {
            log.append(i + 1).append(". ").append(disasterLog.get(i)).append("\n");
        }
        disasterLogArea.setText(log.toString());
    }

    /**
     * Shows an alert dialog with the specified title and content.
     *
     * @param alertType the type of alert (ERROR, WARNING, INFORMATION)
     * @param title     the title of the alert dialog
     * @param content   the content message of the alert dialog
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    private void logout(ActionEvent event) throws Exception {
        App.logout();  // Log out the user and redirect to login page
    }
}
