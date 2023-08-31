module FlightReservations {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires transitive javafx.base;
	
    opens UI;
    opens FlightReserveData;

	opens UI.userRegistration to javafx.fxml;
	opens UI.flightSearch to javafx.fxml;
	opens UI.loginPage to javafx.fxml;
	opens UI.homePage to javafx.fxml;
	opens UI.viewReserved to javafx.fxml;
	opens UI.editProfile to javafx.fxml;
	opens UI.adminControls to javafx.fxml;

	exports FlightReserveData to UI.homePage;
	exports UI.flightSearch;
	exports UI.userRegistration;
	exports UI.loginPage;
	exports UI.viewReserved;
	exports UI.editProfile;
	exports UI.adminControls;
	exports UI.homePage to javafx.fxml;
}
