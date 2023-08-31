package UI.adminControls;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import FlightReserveData.FlightReservationSQLiteDAO;
import UI.PageFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class removeAirportController implements Initializable{

    @FXML
    private ComboBox<String> airportComboBox;
    @FXML
    private Button backButton;

    @FXML
    private Button removeButton;

    // Observable list to store airport codes
    private ObservableList<String> airportCodes = FXCollections.observableArrayList();   
    
    // Initialize the controller
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {       
        List<String> availableAirports = FlightReservationSQLiteDAO.getAvailableAirports();
        airportComboBox.getItems().addAll(availableAirports);
        
        airportCodes.addAll(availableAirports);
    }
    
    // Handle the "Back" button click and navigate to the admin menu
    @FXML
    void onBack(ActionEvent event) throws IOException {
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/adminControls/adminMenu.fxml");
    }

    // Handle the "Remove" button click and remove the selected airport and associated flights
    @FXML
    void onRemove(ActionEvent event) throws IOException {
    	FlightReservationSQLiteDAO.removeAirport(airportComboBox.getValue());
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/adminControls/removeAirport.fxml");
    }

}