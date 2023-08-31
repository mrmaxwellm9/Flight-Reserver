package UI.adminControls;

import java.io.IOException;
import UI.PageFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.Airport;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addAirportController {
	@FXML
	private TextField codeField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField countryField;	
	
	// Method to navigate back to the admin menu
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/adminControls/adminMenu.fxml");
    }
    
    // Method to handle adding a new airport
    @FXML
    private void onAddAirport() {
    	// Create an Airport object and insert it into the database
    	try {
    		Airport airport = new Airport(codeField.getText(), nameField.getText(), cityField.getText(), countryField.getText());
    		FlightReservationSQLiteDAO.insertAirport(airport);
    		// Clear input fields
    		codeField.clear();
    		nameField.clear();
    		cityField.clear();
    		countryField.clear();
    		showSuccessPrompt("Airport successfully added!");
    	} catch (Exception e) {
    		// Show a success prompt to the user
    		e.printStackTrace();
    		showFailurePrompt("Airport failed to be added.");
    	}
    }
    
    // Method to display a success prompt to the user
    private void showSuccessPrompt(String message) {
    	// Create and show an alert with success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Method to display a success prompt to the user
    private void showFailurePrompt(String message) {
    	// Create and show an alert with success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Failure");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
