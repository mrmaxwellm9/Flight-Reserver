package UI.viewReserved;
import FlightReserveData.Flight;
import FlightReserveData.Reservation;
import FlightReserveData.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.UserData;
import UI.PageFunctions;

public class viewReservedController implements Initializable {
    @FXML
    private TableView<Flight> flightTableView;
    @FXML
    private TableColumn<Flight, String> flightNumberColumn;
    @FXML
    private TableColumn<Flight, String> departureAirportColumn;
    @FXML
    private TableColumn<Flight, String> destinationAirportColumn;
    @FXML
    private TableColumn<Flight, String> departureTimeColumn;
    @FXML
    private TableColumn<Flight, String> arrivalTimeColumn;
    @FXML
    private TableColumn<Flight, String> seatColumn;
    @FXML
    private TableColumn<Flight, Double> fareColumn;
    @FXML
    private Button modifyButton;
    
	User currentUser;
    private boolean doubleClickInProgress = false;
	Flight selectedFlight;
	
	public viewReservedController() { }
	
	// Method to set the current user for the controller
    public void setCurrentUser(User user) {
    	this.currentUser = user;
    }
    
    // Handle double-click event on the table view
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
                onModifyClicked();
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	flightTableView.setOnMouseClicked(event -> handleTableMouseClick(event));
        flightTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onFlightSelected();
        });
    	// Initialize the user
    	setCurrentUser(UserData.getInstance().getCurrentUser());
        // Initialize the TableView columns
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        departureAirportColumn.setCellValueFactory(new PropertyValueFactory<>("departureAirportCode")); 
        destinationAirportColumn.setCellValueFactory(new PropertyValueFactory<>("destinationAirportCode"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureDateTime"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDateTime"));
        fareColumn.setCellValueFactory(new PropertyValueFactory<>("fare"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seatInfo")); 
        // Load user reservations into the TableView
        loadUserReservations();
    }
    
    
    // Handle selection of a flight in the table view
    @FXML
    private void onFlightSelected() {
        Flight SelectedFlight = flightTableView.getSelectionModel().getSelectedItem();

        // Enable the "Book Now" button only if a flight is selected
        boolean flightSelected = SelectedFlight != null;
        modifyButton.setDisable(!flightSelected);
        
        selectedFlight = SelectedFlight;
    }
    
    // Load user reservations into the table view
    @FXML
    private void loadUserReservations() {
        FlightReservationSQLiteDAO flightReservationDAO = new FlightReservationSQLiteDAO();
        
        try {
            List<Reservation> userReservations = flightReservationDAO.retrieveUserReservations(currentUser.getUserId());
            
            // Convert the list of reservations to a list of flights
            List<Flight> reservedFlights = new ArrayList<>();
            for (Reservation reservation : userReservations) {
            	Flight flight = reservation.getFlight(); // Retrieve the Flight object from Reservation
              
                // Get departure airport name
                String departureAirportCode = flight.getDepartureAirportCode();
                String departureAirportName = flightReservationDAO.getAirportNameByCode(departureAirportCode);
                flight.setDepartureAirportCode(departureAirportCode + " - " + departureAirportName);
                
                // Get destination airport name
                String destinationAirportCode = flight.getDestinationAirportCode();
                String destinationAirportName = flightReservationDAO.getAirportNameByCode(destinationAirportCode);
                flight.setDestinationAirportCode(destinationAirportCode + " - " + destinationAirportName);
                
                
                int[] seatCoords = FlightReservationSQLiteDAO.getFlightSeatRowAndColumn(reservation.getReservationId(), flight.getFlightNumber());
                String seatInfo = seatCoords[0] + "-" + seatCoords[1];
                flight.setSeatInfo(seatInfo); 
                flightTableView.getItems().add(flight);
                
                reservedFlights.add(flight);
            }
            
            // Create an ObservableList from the reserved flights list
            ObservableList<Flight> observableReservedFlights = FXCollections.observableArrayList(reservedFlights);
            // Set the items of the flightTableView
            flightTableView.setItems(observableReservedFlights);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Handle the "Modify" button click
    @FXML
    private void onModifyClicked() {
        try {    	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyFlight.fxml"));
            Parent modifyFlightViewRoot = loader.load();

            modifyFlightController modifyFlightController = loader.getController();
            modifyFlightController.init(selectedFlight);

            Stage stage = new Stage();
            stage.setTitle("Modify Flight");
            stage.setScene(new Scene(modifyFlightViewRoot));
            stage.setOnHidden(event -> refreshView());
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if loading the view fails
        }
    }

    // Handle the "Back" button click
    @FXML
    private void onBack(ActionEvent event) throws IOException {
        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        PageFunctions.openFxml(window, "/UI/homePage/homePage.fxml");
    }
    
    // Refresh the view by clearing and reloading data
    @FXML
    private void refreshView() {
        flightTableView.getItems().clear();
        loadUserReservations();
    }
}
