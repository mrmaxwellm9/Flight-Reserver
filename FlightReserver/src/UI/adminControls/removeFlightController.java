package UI.adminControls;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import FlightReserveData.Flight;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.User;
import UI.PageFunctions;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class removeFlightController {
    @FXML
    private TableColumn<Flight, String> arrivalTimeColumn;
    @FXML
    private TableColumn<Flight, Integer> availableSeatsColumn;
    @FXML
    private Button backButton;
    @FXML
    private Button onRemoveButton;
    @FXML
    private TableColumn<Flight, String> departureAirportColumn;
    @FXML
    private ComboBox<String> departureAirportField;
    @FXML
    private TableColumn<Flight, String> departureTimeColumn;
    @FXML
    private TableColumn<Flight, String> destinationAirportColumn;
    @FXML
    private ComboBox<String> destinationAirportField;
    @FXML
    private TableColumn<Flight, Double> fareColumn;
    @FXML
    private TableColumn<Flight, String> flightNumberColumn;
    @FXML
    private TableView<Flight> flightTableView;
    @FXML
    private DatePicker travelDatePicker;

    // Flag to prevent double-click event during flight removal
    private boolean doubleClickInProgress = false;
    
    User currentUser;
    
    Flight selectedFlight;

    
    // Method to handle double-click event on the flight table
    private void handleTableMouseClick(MouseEvent event) throws IOException {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            if (!doubleClickInProgress) {
                doubleClickInProgress = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        doubleClickInProgress = false;
                    }
                }, 500); // Delay (in milliseconds)
                onRemove();
            }
        }
    }
    
    // Initialize method executed when the controller is loaded
    @FXML
    private void initialize() {
    	// Set up the mouse click event listener for the flight table
    	flightTableView.setOnMouseClicked(event -> {
			try {
				handleTableMouseClick(event);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
    	
    	// Initialize the FlightReservationSQLiteDAO
    	FlightReservationSQLiteDAO flightReservationDAO = new FlightReservationSQLiteDAO();
    	// Retrieve all flights and display them in the table view
    	List<Flight> allFlights = null;
		try {
			allFlights = flightReservationDAO.retrieveAllFlights();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	showSearchResults(allFlights);

        // Retrieve available airports for the ComboBoxes
        List<String> availableAirports = null;
		try {
			availableAirports = flightReservationDAO.retrieveAvailableAirports();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        // Create an ObservableList to store the airport choices for the dropdowns
    	ObservableList<String> airportChoices = FXCollections.observableArrayList(availableAirports);
    	
        // Set up the event listener for flight selection
        flightTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onFlightSelected();
        });

        // Set the choices for both departure and destination airport fields
        departureAirportField.setItems(airportChoices);
        destinationAirportField.setItems(airportChoices);

        // Enable auto-complete for the airport fields
        departureAirportField.setEditable(true);
        destinationAirportField.setEditable(true);
    }
    
    // Method to handle flight search
    @FXML
    private void handleFlightSearch() {
    	String departureAirport = departureAirportField.getValue();
    	String destinationAirport = destinationAirportField.getValue();
    	LocalDate travelDate = travelDatePicker.getValue();
    	
    	// Perform flight search based on the user input
    	List<Flight> searchResults = performFlightSearch(departureAirport, destinationAirport, travelDate);
    	
    	// Display Search Results
    	showSearchResults(searchResults);
    }
    
    // Method to perform the flight search based on user input
    private List<Flight> performFlightSearch(String departureAirport, String destinationAirport, LocalDate travelDate) {
    	List<Flight> searchResults;
    	try {
    		searchResults = FlightReservationSQLiteDAO.searchFlights(departureAirport, destinationAirport, travelDate);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return Collections.emptyList();
    	}
    	
    	return searchResults;
    }
    
    
    // Method to display the search results in the TableView
    private void showSearchResults(List<Flight> searchResults) {
    	// Initialize the FlightReservationSQLiteDAO
    	FlightReservationSQLiteDAO flightReservationDAO = new FlightReservationSQLiteDAO();
    	// Set up cell value factories for columns
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureDateTime"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDateTime"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        fareColumn.setCellValueFactory(new PropertyValueFactory<>("fare"));
        
        departureAirportColumn.setCellValueFactory(cellData -> {
            Flight flight = cellData.getValue();
            if (flight != null && flight.getDepartureAirportCode() != null) {
                try {
                    String airportCode = flight.getDepartureAirportCode();
                    String airportName = flightReservationDAO.getAirportNameByCode(airportCode);
                    return new SimpleStringProperty(airportCode + " - " + airportName);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new SimpleStringProperty("N/A");
                }
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        destinationAirportColumn.setCellValueFactory(cellData -> {
            Flight flight = cellData.getValue();
            if (flight != null && flight.getDestinationAirportCode() != null) {
                try {
                    String airportCode = flight.getDestinationAirportCode();
                    String airportName = flightReservationDAO.getAirportNameByCode(airportCode);
                    return new SimpleStringProperty(airportCode + " - " + airportName);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new SimpleStringProperty("N/A");
                }
            } else {
                return new SimpleStringProperty("N/A");
            }
        });
        
        // Clear existing items and add search results to the TableView
        flightTableView.getItems().clear();
        flightTableView.getItems().addAll(searchResults);
        flightTableView.refresh();
    }   
    
    // Event handler for flight selection in the TableView
    @FXML
    private void onFlightSelected() {
        Flight SelectedFlight = flightTableView.getSelectionModel().getSelectedItem();

        // Enable the "Book Now" button only if a flight is selected
        boolean flightSelected = SelectedFlight != null;
        onRemoveButton.setDisable(!flightSelected);
        
        selectedFlight = SelectedFlight;
    }
    
    // Event handler for the "Back" button which navigates back to the admin menu
    @FXML
    void goBack(ActionEvent event) throws IOException {
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/adminControls/adminMenu.fxml");
    }

    // Event handler for the "Remove" button that removes the selected flight, its reservations, and seat chart
    @FXML
    void onRemove() throws IOException {
        // Remove flight, reservations, and seat chart
        FlightReservationSQLiteDAO.removeFlight(selectedFlight);
        // Refresh the table view after removing the flight
        handleFlightSearch();
        // If you want to reset the selection after removal, you can do this:
        flightTableView.getSelectionModel().clearSelection();
    }
}
