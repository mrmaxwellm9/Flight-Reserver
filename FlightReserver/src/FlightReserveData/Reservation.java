package FlightReserveData;

import java.util.UUID;

public class Reservation {
    private String reservationId;
    private User user;
    private Flight flight;
    int seatId;
    
    public Reservation() { }

    public Reservation(User user, Flight flight) {
        this.reservationId = generateRandomReservationId();
        this.user = user;
        this.flight = flight;
    }
    
    public Reservation(User user, Flight flight, int row, int col, int seatid) {
        this.reservationId = generateRandomReservationId();
        this.user = user;
        this.flight = flight;
        this.seatId = seatid;
    }

    public String getReservationId() {
        return reservationId;
    }

    public User getUser() {
        return user;
    }

    public Flight getFlight() {
        return flight;
    }
    
    public void setReservationId(String ReservationId) {
        reservationId = ReservationId;
    }

    public void setUser(User User) {
        user = User;
    }

    public void setFlight(Flight Flight) {
        flight = Flight;
    }
    
    public void setSeatId(int seatid) {
        seatId = seatid;
    }
    
    public int getSeatId() {
        return seatId;
    }

    // Generate a random reservation ID using UUID
    private String generateRandomReservationId() {
        return UUID.randomUUID().toString();
    }
}