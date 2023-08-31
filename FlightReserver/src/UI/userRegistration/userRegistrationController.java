package UI.userRegistration;
import javafx.scene.control.PasswordField;
import FlightReserveData.UserData;
import UI.PageFunctions;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.User;


public class userRegistrationController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private GridPane mainContainer;
    
    // Constructor (called when the FXML file is loaded)
    public userRegistrationController() { }
    
    // Method to handle back button
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the main menu page fails
        }
    }
    
    // Method to handle user registration
    @FXML
    private void handleRegistration(ActionEvent event) throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        
        if (FlightReservationSQLiteDAO.userAlreadyExists(email)) {
        	showErrorDialog("Registration Failed", "An account with this email already exists.");
        } else {
            if (FlightReservationSQLiteDAO.registerUser(firstName, lastName, email, password)) {
                // Pass the user object to homePageController
            	UserData.getInstance().setCurrentUser(FlightReservationSQLiteDAO.getUser(email, password));
            	
                Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                PageFunctions.openFxml(window, "/UI/homePage/homePage.fxml");
            } else {
            	showErrorDialog("Registration Failed", "Error occurred during registration.");
            }
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