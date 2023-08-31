package UI.editProfile;

import java.io.IOException;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.User;
import FlightReserveData.UserData;
import UI.PageFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;


public class editProfileController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button backButton;
	
	User currentUser;
	
	public editProfileController() { }
	
	// Method to set the current user's information in the UI fields
    public void setCurrentUser(User user) {
    	this.currentUser = user;
    	firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        emailField.setText(currentUser.getEmail());
    }
    
    // Handle the "Save" button click
    @FXML
    private void onSave(ActionEvent event) {
    	User tempUser = new User(firstNameField.getText(), lastNameField.getText(), emailField.getText(), passwordField.getText());
    	tempUser.setUserId(UserData.getInstance().getCurrentUser().getUserId());
    	
        if (tempUser.getFirstName().isEmpty() || tempUser.getLastName().isEmpty() || tempUser.getEmail().isEmpty() || tempUser.getPassword().isEmpty()) {
            showErrorDialog("Error", "All fields are required.");
            return;
        }
        if (FlightReservationSQLiteDAO.userAlreadyExists(tempUser.getEmail())) {
        	showErrorDialog("Registration Failed", "An account with this email already exists.");
        } else {
            int rowsUpdated = FlightReservationSQLiteDAO.updateUser(tempUser);

            if (rowsUpdated > 0) {
                // Update the currentUser object with the new information
            	UserData.getInstance().setCurrentUser(tempUser);
            	
            	firstNameField.clear();
            	lastNameField.clear();
            	emailField.clear();
            	passwordField.clear();
            	
            	showConfirmationDialog("Success", "Profile updated successfully!");
            } else {
            	showErrorDialog("Error", "Failed to update profile.");
            }
        }

    }
    
    // Handle the "Go Back" button click and return to the home page
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/homePage/homePage.fxml");
    }
    

    // Show an error dialog with the given title and message
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Show a confirmation dialog with the given title and message
    private void showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
