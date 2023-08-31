package UI.flightSearch;
import FlightReserveData.Flight;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.User;
import UI.PageFunctions;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.DatePicker;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;
import java.util.Collections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class flightSearchController {
    @FXML
    private ComboBox<String> departureAirportField;
    @FXML
    private ComboBox<String> destinationAirportField;
    @FXML
    private DatePicker travelDatePicker;
    @FXML
    private TableView<Flight> flightTableView;
    @FXML
    private TableColumn<Flight, String> flightNumberColumn;
    @FXML
    private TableColumn<Flight, String> departureTimeColumn;
    @FXML
    private TableColumn<Flight, String> arrivalTimeColumn;
    @FXML
    private TableColumn<Flight, String> departureAirportColumn;
    @FXML
    private TableColumn<Flight, String> destinationAirportColumn;
    @FXML
    private TableColumn<Flight, Integer> availableSeatsColumn;
    @FXML
    private TableColumn<Flight, Double> fareColumn;
    @FXML
    private Button bookNowButton;
    @FXML
    private Button backButton;
    
    private boolean doubleClickInProgress = false;
    
    User currentUser;
    
    Flight selectedFlight;
    // Method to initialize the airport choices
    
    // Handle double-clicks on the TableView
    private void handleTableMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            if (!doubleClickInProgress) {
                doubleClickInProgress = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        doubleClickInProgress = false;
                    }
                }, 500); // Change the delay (in milliseconds) to your preference
                onBookNowButtonClicked();
            }
        }
    }
    
    // Initialize the controller
    @FXML
    private void initialize() {
    	flightTableView.setOnMouseClicked(event -> handleTableMouseClick(event));
    	
    	FlightReservationSQLiteDAO flightReservationDAO = new FlightReservationSQLiteDAO();
    	List<Flight> allFlights = null;
		try {
			allFlights = flightReservationDAO.retrieveAllFlights();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	showSearchResults(allFlights);

        // Retrieve available airports for the ComboBoxes
        List<String> availableAirports = null;
		try {
			availableAirports = flightReservationDAO.retrieveAvailableAirports();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
    	FlightReservationSQLiteDAO flightReservationDAO = new FlightReservationSQLiteDAO();
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
        bookNowButton.setDisable(!flightSelected);
        
        selectedFlight = SelectedFlight;
    }
    
    // Event handler for "Book Now" button
    @FXML
    private void onBookNowButtonClicked() {
        // Open the seat selection popup
        showSeatSelectionPopup(selectedFlight);
    }
    
    // Show the seat selection popup
    private void showSeatSelectionPopup(Flight flight) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("seatSelection.fxml"));
            Parent seatSelectionViewRoot = loader.load();

            seatSelectionController seatSelectionController = loader.getController();
            seatSelectionController.init(flight);

            Stage stage = new Stage();
            stage.setTitle("Seat Selection");
            stage.setScene(new Scene(seatSelectionViewRoot));
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the view fails
        }
    }
    
    // Event handler to go back to the home page
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/homePage/homePage.fxml");
    }
}
