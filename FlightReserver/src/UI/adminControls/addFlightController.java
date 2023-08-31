package UI.adminControls;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.RowConstraints;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import FlightReserveData.Flight;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.UserData;
import UI.PageFunctions;
import javafx.scene.control.Alert;


public class addFlightController implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private Button addFlightButton;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> departureAirportCodeField;
    @FXML
    private ComboBox<String> destinationAirportCodeField;
    @FXML
    private TextField departureTimeField;
    @FXML
    private TextField arrivalTimeField;
    @FXML
    private DatePicker departureDateField;
    @FXML
    private DatePicker arrivalDateField;
    @FXML
    private TextField fareField;

    
    private ObservableList<String> airportCodes = FXCollections.observableArrayList();   
    
    
    //private void setupComboBoxFiltering(ComboBox<String> comboBox, ObservableList<String> items) {
    //    comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
    //        Predicate<String> filter = item -> item.toLowerCase().contains(newValue.toLowerCase());
    //        comboBox.getItems().setAll(items.filtered(filter));
    //        comboBox.show();
    //    });
    //}
    
    // Method to initialize the controller
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Center the grid pane both horizontally and vertically
        gridPane.setAlignment(Pos.CENTER);
        
        List<String> availableAirports = FlightReservationSQLiteDAO.getAvailableAirports();
        departureAirportCodeField.getItems().addAll(availableAirports);
        destinationAirportCodeField.getItems().addAll(availableAirports);
        
        airportCodes.addAll(availableAirports);
        //ObservableList<String> airportCodes = FXCollections.observableArrayList(availableAirports);
        //setupComboBoxFiltering(departureAirportCodeField, airportCodes);
        //setupComboBoxFiltering(destinationAirportCodeField, airportCodes);    
    }
    
    // Method to navigate back to the admin menu
    @FXML
    private void goBack(ActionEvent event) throws IOException {       
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/adminControls/adminMenu.fxml");
    }
    
    // Handle the "Add Flight" button click
    @FXML
    private void addFlightButtonClicked() {
        // Show a dialog to get grid dimensions from the user
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Flight");
        dialog.setHeaderText("Enter Grid Dimensions");
        dialog.setContentText("Enter number of rows and columns (e.g., 4 6):");
        dialog.showAndWait().ifPresent(result -> {
            String[] dimensions = result.split(" ");
            if (dimensions.length == 2) {
                int rows = Integer.parseInt(dimensions[0]);
                int cols = Integer.parseInt(dimensions[1]);
                createGrid(rows, cols);
                
                saveButton.setVisible(true);
            } else {
                // Handle invalid input
            }
        });
    }

    // Create a grid with checkboxes for seat selection
    private void createGrid(int rows, int cols) {
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        // Add row and column constraints
        for (int i = 0; i < rows; i++) {
            gridPane.getRowConstraints().add(new RowConstraints());
        }
        for (int i = 0; i < cols; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints());
        }

        // Populate cells with selectable elements (e.g., buttons)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
            	CheckBox checkBox = new CheckBox();
                // Customize seatButton properties and event handling as needed
            	gridPane.add(checkBox, col, row);
            }
        }
    }
    
    // Handle the "Save Flight" button click
    @FXML
    private void saveFlightButtonClicked(ActionEvent event) throws SQLException {
        // Create a new flight entry in the Flights table
    	int flightId = FlightReservationSQLiteDAO.getNextAvailableFlightId();
        int totalSeats = 0;
        int availableSeats = 0;
        for (Node child : gridPane.getChildren()) {
            if (child instanceof CheckBox && ((CheckBox) child).isSelected()) {
                totalSeats++;
                availableSeats++;
            }
        }        
        // Combine date and time for departure datetime
        LocalDate departureDate = departureDateField.getValue();
        LocalTime departureTime = LocalTime.parse(departureTimeField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);        
        // Combine date and time for arrival datetime
        LocalDate arrivalDate = arrivalDateField.getValue();
        LocalTime arrivalTime = LocalTime.parse(arrivalTimeField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);
        
    	Flight flight = new Flight(Integer.toString(flightId), departureAirportCodeField.getValue(), destinationAirportCodeField.getValue(),
    			departureDateTime.toString(), arrivalDateTime.toString(), availableSeats, totalSeats, Integer.valueOf(fareField.getText()));
    	FlightReservationSQLiteDAO.insertFlight(flight);           
    	FlightReservationSQLiteDAO.createSeatsTable(flightId);
        // Get the currently logged in user's ID (Replace with actual logic)
    	int userId = UserData.getInstance().getCurrentUser().getUserId();
        // Insert selected seat data into the Seats table
    	for (Node child : gridPane.getChildren()) {
    		if (child instanceof CheckBox && ((CheckBox) child).isSelected()) {
                	int row = GridPane.getRowIndex(child);
                	int col = GridPane.getColumnIndex(child);
                	FlightReservationSQLiteDAO.saveSeatConfiguration(flightId, row, col, 1, userId);
    		}
    	}
    	// Clear input fields and grid
        departureTimeField.clear();
        arrivalTimeField.clear();
        fareField.clear();
        gridPane.getChildren().clear();
        // Display success message
        showSuccessPrompt("Flight successfully added!");
    }
    
    // Show a success prompt dialog
    private void showSuccessPrompt(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
