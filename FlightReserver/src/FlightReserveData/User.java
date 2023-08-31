package FlightReserveData;

public class User {
	int userId;
	String firstName;
	String lastName;
	String email;
	String password;
	
	public User() {

	}
	
	public User(String FirstName, String LastName, String Email, String Password) {
		firstName = FirstName;
		lastName = LastName;
		email = Email;
		password = Password;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUserId(int newUserId) {
		userId = newUserId;
	}
	
	public void setFirstName(String newFirstName) {
		firstName = newFirstName;
	}
	
	public void setLastName(String newLastName) {
		lastName = newLastName;
	}
	
	public void setEmail(String newEmail) {
		email = newEmail;
	}
	
	public void setPassword(String newPassword) {
		password = newPassword;
	}
	
}
