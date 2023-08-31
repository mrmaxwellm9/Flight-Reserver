package UI.loginPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.User;
import javafx.scene.control.Alert;
import FlightReserveData.UserData;
import UI.PageFunctions;

public class loginPageController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
	
    // Constructor (called when the FXML file is loaded)
    public loginPageController() { }
	
    // Method to navigate back to the main menu
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/menu.fxml");
    }
    
    // Method to submit login credentials and proceed to the home page
    @FXML
    private void submitLogin(ActionEvent event) throws IOException {
    	String email = emailField.getText();
    	String password = passwordField.getText();
    	User user = FlightReservationSQLiteDAO.getUser(email, password);
    	if ( user != null ) {       			
    		UserData.getInstance().setCurrentUser(user);
        			
    		Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
    		PageFunctions.openFxml(window, "/UI/homePage/homePage.fxml");
    	} else {
    		showErrorDialog("Login Failed", "Invalid email or password.");
    	}
    }
    
 // Method to show an error dialog
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
