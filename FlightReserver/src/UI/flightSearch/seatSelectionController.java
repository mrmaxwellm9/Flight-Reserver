package UI.flightSearch;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import FlightReserveData.Flight;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.Reservation;
import FlightReserveData.User;
import FlightReserveData.UserData;
import java.util.ArrayList;
import java.util.List;


//[Row, Col] 
public class seatSelectionController {
    @FXML
    private GridPane seatGrid;
    @FXML
    private Button bookButton;
    
	FlightReservationSQLiteDAO flightReservationSQLiteDAO;
    private Flight selectedFlight = null;
    private List<String> selectedSeats = new ArrayList<>();

    // Method to initialize the controller with a selected flight
    public void init(Flight flight) {
        selectedFlight = flight;
        loadSeats();
    }
    
    // Load available seats for the selected flight
    private void loadSeats() {
        List<int[]> availableSeatCoords = FlightReservationSQLiteDAO.getAvailableSeats(selectedFlight.getFlightNumber());
        CheckBox defaultGetter = new CheckBox();
        double defaultWidth = defaultGetter.getPrefWidth();
        double defaultHeight = defaultGetter.getPrefHeight();
        seatGrid.setHgap(0);
        seatGrid.setVgap(0);
        ColumnConstraints colConstraints = new ColumnConstraints(defaultWidth);
        seatGrid.getColumnConstraints().add(colConstraints);
        RowConstraints rowConstraints = new RowConstraints(defaultHeight);
        seatGrid.getRowConstraints().add(rowConstraints);
        
        int maxRow = availableSeatCoords.stream().mapToInt(coords -> coords[0]).max().orElse(0);
        int maxCol = availableSeatCoords.stream().mapToInt(coords -> coords[1]).max().orElse(0);

        for (int row = 0; row <= maxRow; row++) {
            for (int col = 0; col <= maxCol; col++) {
                int finalRow = row;
                int finalCol = col;

                boolean isAvailable = availableSeatCoords.stream()
                        .anyMatch(coords -> coords[0] == finalRow && coords[1] == finalCol);
                boolean seatBooked = availableSeatCoords.stream()
                        .anyMatch(coords -> coords[0] == finalRow && coords[1] == finalCol && coords[2] != 0);
                
                if (isAvailable) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setAlignment(Pos.CENTER);
                    checkBox.setContentDisplay(ContentDisplay.CENTER);
                    checkBox.setMinSize(defaultWidth, defaultHeight);
                    checkBox.setOnAction(event -> handleSeatSelection(checkBox, finalRow, finalCol));
                    if (seatBooked) {
                    	checkBox.setSelected(true);
                    	checkBox.setDisable(true);
                    }

                    seatGrid.add(checkBox, col, row);
                } else {
                	Rectangle placeholder = new Rectangle(22, 22, Color.TRANSPARENT);
                    
                    seatGrid.add(placeholder, col, row);
                }
            }
        }
        return;
    }
    
    // Handle the selection of a seat checkbox
    private void handleSeatSelection(CheckBox checkBox, int row, int col) {
        String seat = "Row " + row + ", Col " + col;
        if (checkBox.isSelected()) {
            selectedSeats.add(seat);
        } else {
            selectedSeats.remove(seat);
        }
        bookButton.setDisable(selectedSeats.size() != 1);
    }
    
    // Parse seat coordinates from the selected seat string
    private int[] parseSeatCoords(String seat) {
        String[] parts = seat.split(" ");
        int row = Integer.parseInt(String.valueOf(parts[1].charAt(0)));
        int col = Integer.parseInt(parts[3]);
        return new int[]{row, col};
    }
        
    // Handle the "Book" button click
    @FXML
    private void onBookButtonClicked() {
    	 Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
    	 confirmationAlert.setTitle("Confirm Reservation");
    	 confirmationAlert.setHeaderText("Confirm Reservation");
    	 confirmationAlert.setContentText("Are you sure you want to make the reservation?");
    	    
    	 ButtonType confirmButton = new ButtonType("Confirm");
    	 ButtonType cancelButton = new ButtonType("Cancel");
    	 confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);
    	 
    	 confirmationAlert.showAndWait().ifPresent(result -> {
    	        if (result == confirmButton) {
    	            // User clicked Confirm button
    	            makeReservation();
    	            closePopup();
    	        }
    	    }); 	    
   }
    
    // Make a reservation for the selected seat
    private void makeReservation() {
        if (selectedSeats.size() == 1) {
            String selectedSeat = selectedSeats.get(0);
            int[] seatCoords = parseSeatCoords(selectedSeat);

            User currentUser = UserData.getInstance().getCurrentUser(); 
            
            // Create a reservation
            Reservation reservation = new Reservation();
            reservation.setUser(currentUser);
            reservation.setFlight(selectedFlight);
           

            // Setting the selected seat to the user in the seat table
            FlightReservationSQLiteDAO.updateSeatUserId(selectedFlight.getFlightNumber(), seatCoords[0], seatCoords[1], currentUser.getUserId());
            // Setting the reservation's seat id to the selected seat's id using the flight number and userId
            int seatId = FlightReservationSQLiteDAO.getSeatId(selectedFlight.getFlightNumber(), seatCoords[0], seatCoords[1], currentUser.getUserId());
            reservation.setSeatId(seatId);
            FlightReservationSQLiteDAO.insertReservation(reservation);
            //get the auto incremented reservation id
            int reservationId = FlightReservationSQLiteDAO.getReservationId(currentUser.getUserId(), selectedFlight.getFlightNumber(), seatId);
            //add the reservation id to the reserved seat
            FlightReservationSQLiteDAO.setSeatReservationId(selectedFlight.getFlightNumber(), seatCoords[0], seatCoords[1], currentUser.getUserId(), reservationId);
            
            closePopup();
        }    
    }

    // Close the popup
    @FXML
    private void closePopup() {
        // Close the popup
        Stage stage = (Stage) seatGrid.getScene().getWindow();
        stage.close();
    }

    // Method to retrieve the list of selected seats
    public List<String> getSelectedSeats() {
        return selectedSeats;
    }
}
