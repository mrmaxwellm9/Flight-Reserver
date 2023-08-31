package UI;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;


public class menuController {
    @FXML
    private GridPane mainContainer;
	
	// This method is called when the "Log In" button is clicked
    @FXML
    private void openLoginPage() {
        try {
            // Get the current stage
            Stage currentStage = (Stage) mainContainer.getScene().getWindow();
            PageFunctions.openFxml(currentStage, "/UI/loginPage/loginPage.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the login page fails
        }
    }
    
    // This method is called when the "Register" button is clicked
    @FXML
    private void openRegistrationPage() {
        try {
            Stage currentStage = (Stage) mainContainer.getScene().getWindow();
            PageFunctions.openFxml(currentStage, "/UI/userRegistration/userRegistrationForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the registration page fails
        }
    }
}
