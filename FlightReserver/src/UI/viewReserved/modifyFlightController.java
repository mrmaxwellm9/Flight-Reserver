package UI.viewReserved;

import java.util.ArrayList;
import java.util.List;
import FlightReserveData.UserData;
import FlightReserveData.Flight;
import FlightReserveData.FlightReservationSQLiteDAO;
import FlightReserveData.Reservation;
import FlightReserveData.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class modifyFlightController {
    @FXML
    private GridPane seatGrid;
    @FXML
    private Button modifyButton;
    
    User currentUser = UserData.getInstance().getCurrentUser();
	FlightReservationSQLiteDAO flightReservationSQLiteDAO;
    private Flight selectedFlight = null;
    private List<String> selectedSeats = new ArrayList<>();

    // Method to initialize the controller with a selected flight
    public void init(Flight flight) {
        selectedFlight = flight;
        loadSeats();
    }
    
    // Check if a seat at a given row and column is selected
    private boolean isSelectedSeat(int row, int col) {
        String seatInfo = selectedFlight.getSeatInfo();
        String seatToCheck = row + "-" + col;
        return seatInfo.equals(seatToCheck);
    }
    
    // Parse seat information into row and column coordinates
    private int[] parseSeatInfo(String seatInfo) {
        String[] seatCoords = seatInfo.split("-");
        int[] coords = new int[2];
        coords[0] = Integer.parseInt(seatCoords[0]);
        coords[1] = Integer.parseInt(seatCoords[1]);
        return coords;
    }
    
    // Load available seats for the selected flight and populate the seatGrid
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
                    	if (isSelectedSeat(finalRow, finalCol)) {
                            checkBox.setSelected(true);
                            checkBox.setDisable(false);
                        }
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
        if (checkBox.isSelected()) {
            selectedSeats.add(row + "-" + col); // Pass "row-col" format to parseSeatInfo
        } else {
            selectedSeats.remove(row + "-" + col);
        }
        modifyButton.setDisable(selectedSeats.size() != 1);
    }
    
    // Handle the "Modify" button click
    @FXML
    private void onModifyClicked() {
        if (selectedFlight != null && !selectedSeats.isEmpty()) {
        	String newSelectedSeat = selectedSeats.get(0); 
        	createReservation(selectedFlight, newSelectedSeat);
            String originalSelectedSeat = selectedFlight.getSeatInfo();         
            cancelReservation(selectedFlight, originalSelectedSeat);           
        }
        closePopup();
    }
    
    // Handle the "Cancel Reservation" button click
    @FXML
    private void onCancelReservation() {
        if (selectedFlight != null) {
            String selectedSeatInfo = selectedFlight.getSeatInfo();
            if (selectedSeatInfo != null && !selectedSeatInfo.isEmpty()) {
                cancelReservation(selectedFlight, selectedSeatInfo);
            }
        }
        closePopup();
    }
    
    // Close the popup
    @FXML
    private void closePopup() {
        Stage stage = (Stage) seatGrid.getScene().getWindow();
        stage.close();
    }
    
    // Cancel a reservation for a specific flight and seat
    private void cancelReservation(Flight flight, String seatInfo) {
        
        int[] seatCoords = parseSeatInfo(seatInfo);
        int seatId = FlightReservationSQLiteDAO.getSeatId(selectedFlight.getFlightNumber(), seatCoords[0], seatCoords[1], currentUser.getUserId());
        // Retrieve the reservation ID based on flight number and seat info
        int reservationId = FlightReservationSQLiteDAO.getReservationId(currentUser.getUserId(), flight.getFlightNumber(), seatId);

        // Remove the reservation from the database
        FlightReservationSQLiteDAO.removeReservation(reservationId);

        // Set seat info for the reservation to be cancelled
        FlightReservationSQLiteDAO.setSeatReservationId(flight.getFlightNumber(), seatCoords[0], seatCoords[1], currentUser.getUserId(), -1);
        FlightReservationSQLiteDAO.updateSeatUserId(flight.getFlightNumber(), seatCoords[0], seatCoords[1], 0);
    }

	// Create a new reservation for a specific flight and seat
    private void createReservation(Flight flight, String seatInfo) {
        int[] seatCoords = parseSeatInfo(seatInfo);
        // Create a new reservation and add it to the database
        int seatId = FlightReservationSQLiteDAO.getSeatId(flight.getFlightNumber(), seatCoords[0], seatCoords[1], 0);
        Reservation newReservation = new Reservation(currentUser, flight, seatCoords[0], seatCoords[1], seatId);
        FlightReservationSQLiteDAO.insertReservation(newReservation);
        FlightReservationSQLiteDAO.updateSeatUserId(flight.getFlightNumber(), seatCoords[0], seatCoords[1], currentUser.getUserId());

       
        // Update reservation ID for the new reservation
        int reservationId = FlightReservationSQLiteDAO.getReservationId(currentUser.getUserId(), flight.getFlightNumber(), seatId);
        FlightReservationSQLiteDAO.setSeatReservationId(flight.getFlightNumber(), seatCoords[0], seatCoords[1], currentUser.getUserId(), reservationId);
        
    }
}
