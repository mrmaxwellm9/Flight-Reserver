package FlightReserveData;

public class UserData {
	private static UserData instance;
    private User currentUser;

    private UserData() {
        // Private constructor to prevent direct instantiation.
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
