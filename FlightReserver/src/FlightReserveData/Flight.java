package FlightReserveData;

public class Flight {
	String flightNumber;
	String departureAirportCode;
	String destinationAirportCode;
	String departureDateTime;
	String arrivalDateTime;
	int availableSeats;
	int totalSeats;
	double fare;
	String seatInfo; // Only for use in populating reserved flights table
	
    // Constructor
    public Flight(String flightNumber, String departureAirportCode, String destinationAirportCode,
                  String departureDateTime, String arrivalDateTime, int availableSeats,
                  int totalSeats, double fare) {
        this.flightNumber = flightNumber;
        this.departureAirportCode = departureAirportCode;
        this.destinationAirportCode = destinationAirportCode;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.fare = fare;
    }
    
    //Empty Contructor
    public Flight() { }
    
	public void setFlightNumber(String FlightNumber) {
		this.flightNumber = FlightNumber;
	}
	
	public void setDepartureAirportCode(String DepartureAirportCode) {
		this.departureAirportCode = DepartureAirportCode;
	}
	
	public void setDestinationAirportCode(String DestinationAirportCode) {
		this.destinationAirportCode = DestinationAirportCode;
	}
	
	public void setDepartureDateTime(String DepartureDateTime) {
		this.departureDateTime = DepartureDateTime;
	}
	
	public void setArrivalDateTime(String ArrivalDateTime) {
		this.arrivalDateTime = ArrivalDateTime;
	}
	
	public void setAvailableSeats(int AvailableSeats) {
		this.availableSeats = AvailableSeats;
	}
	
	public void setTotalSeats(int TotalSeats) {
		this.totalSeats = TotalSeats;
	}
	
	public void setFare(double Fare) {
		this.fare = Fare;
	}
	
	public String getFlightNumber() {
		return flightNumber;
	}
	
	public String getDepartureAirportCode() {
		return departureAirportCode;
	}
	
	public String getDestinationAirportCode() {
		return destinationAirportCode;
	}
	
	public String getDepartureDateTime() {
		return departureDateTime;
	}
	
	public String getArrivalDateTime() {
		return arrivalDateTime;
	}
	
	public int getAvailableSeats(){
		return availableSeats;
	}
	
	public int getTotalSeats() {
		return totalSeats;
	}
	
	public double getFare() {
		return fare;
	}
	
    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    public String getSeatInfo() {
        return seatInfo;
    }
}