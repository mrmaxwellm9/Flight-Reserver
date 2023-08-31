package UI.adminControls;

import java.io.IOException;
import UI.PageFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class adminMenuController {
	// Method to open the "Add Airport" page
	public void onAddAirport(ActionEvent event) {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/adminControls/addAirport.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the admin controls page fails
        }
	}
	
	// Method to open the "Add Flight" page
	public void onAddFlight(ActionEvent event) {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/adminControls/addFlight.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the admin controls page fails
        }
	}
	
	// Method to open the "Remove Airport" page
	public void onRemoveAirport(ActionEvent event) {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/adminControls/removeAirport.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the admin controls page fails
        }
	}
	
	// Method to open the "Remove Flight" page
	public void onRemoveFlight(ActionEvent event) {
        try {
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            PageFunctions.openFxml(window, "/UI/adminControls/removeFlight.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the admin controls page fails
        }
	}
	
	// Method to go back to the home page
    @FXML
    private void onBack(ActionEvent event) throws IOException {
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/homePage/homePage.fxml");
    }
}
