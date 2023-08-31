package UI.homePage;

import java.io.IOException;
import FlightReserveData.UserData;
import FlightReserveData.User;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import UI.PageFunctions;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class homeController {
    @FXML
    private GridPane mainContainer;
    @FXML
    private Button adminButton;
    
    private User currentUser;

    public homeController() { }
     
    // Method to show the admin control button if the user's id is 0
    public void initialize() {
    	this.currentUser = UserData.getInstance().getCurrentUser();
        if (currentUser.getUserId() == 0) {
            adminButton.setVisible(true); // Show admin button
        } else {
            adminButton.setVisible(false); // Hide admin button
        }
    }
    
    // Method to navigate to the flight s earch menu
    @FXML
    private void goToFlightSearch(ActionEvent event) {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/flightSearch/flightSearch.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the admin controls page fails
        }
    }
    
    // Method to navigate to the users reserved flights
    @FXML
    private void goToReservedFlights(ActionEvent event) {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/viewReserved/viewReserved.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the admin controls page fails
        }
    }
    
    // Method to navigate to the edit profile menu
    @FXML
    private void goToEditProfile(ActionEvent event) {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/editProfile/editProfile.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the admin controls page fails
        }
    }
    
    // Method to navigate to the admin controls
    @FXML
    private void goToAdminControls(ActionEvent event) {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/adminControls/adminMenu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the admin controls page fails
        }
    }
    
    // Method to navigate back to the main menu
    public void onLogout(ActionEvent event) {
        try {
        	UserData.getInstance().setCurrentUser(null);
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the login page fails
        }
    }
}

