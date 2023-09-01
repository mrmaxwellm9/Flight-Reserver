# **Flight Reserver**

## **Usage**

To use download and extract the repository to a folder, then run the [Flight Reserver](https://github.com/mrmaxwellm9/Flight-Reserver/blob/main/Flight%20Reserver.lnk) shortcut. Alternatively, enter the [run_files](https://github.com/mrmaxwellm9/Flight-Reserver/tree/main/run_files) folder and run the [bat file](https://github.com/mrmaxwellm9/Flight-Reserver/blob/main/run_files/RunFlightReserver.bat) or [shell script file](https://github.com/mrmaxwellm9/Flight-Reserver/blob/main/run_files/RunFlightReserver.sh) depending on your system.

## **Features**

The flight reserver application allows users to create and log into user accounts, search for flights, book flights with a seat selection, edit their profile, edit their reserved flight seat, and cancel their reserved flights. The application also has an admin account which defaults to username "admin" and password "password" which allows the admin user to create and remove flights and airports for normal users to reserve. The admin account should not reserve flights itself.

### **User Registration and Login**

When you open the application you will see the following interface which allows you to be directed to the login or register page.
![66sS26m](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/29e6ac0b-fa41-4803-9bac-d19f6077b176)\

### **User Registration**

When you click the register button you will be shown this interface
![yHRT8mr](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/d22a594d-16c1-4e88-9c01-36a186c0ebde) \
To create a user account you need to enter a first name, last name, and unique email and password combination. If the email and password combination is already being used the application will inform you and you will need to enter new information. On successful account registration, the user will be taken to the user's home page which looks like this for regular users 
![De8L2VV](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/a41e3571-a5d3-4187-a244-9790a26dfbf7)\
and this for the admin account.
![48LQbaO](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/0ae52cfd-51f4-410e-9699-3a2272a920d9)\

### **Logging in**

When you click the login button you will be shown this interface
![52tOpzz](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/b11192b3-e058-4b00-8933-9a03bbc2cb4d)\
where you will enter a matching account email and password. Upon successful login, you will be brought to the user's home page.

## **Searching and Booking Flights**

If you click the "Search and Book Flights" button on the user home screen you will be shown an interface like this.
![me0Qsi0](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/08073dd9-bea9-4477-bb38-e6017636a0a7) <br/>
This is the default search flight screen with a couple of added example flights. You can filter the flights by selecting a departure airport, destination airport, and departure date in the top filters and then clicking search. When you find a flight you would like to book you can either select the flight by clicking once and then click the "Book Now" button in the bottom right or double-click the desired flight in the flight search table. In this example, if I were to book a seat on the flight from SFO to LAX I would double-click the Flight Numer 1 row to have this popup displayed
![hVEM958](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/d19f3727-dbb2-4a75-a1ba-242bbb1c9ed8) <br/>
then from there I would check the seat I would like to reserve as shown here
![PYG33KM](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/e5220f2c-d8be-4c62-9179-1a47dff4cc3b) <br/>
then I would click the bottom right book button which will bring up a confirmation popup and upon confirmation, the seat is booked and will be grayed out for other users trying to reserve seats on the same flight.

## **Viewing Reserved Flights**

If you would like to change your seat or cancel a reservation, from the user home screen you would click the "View Reserved Flights" button to be shown the following interface (assuming the logged in user has a reservation)
![rzyEABt](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/850ba6d4-3131-4c2c-b5ce-57f22233dea3)\
Then you can edit the reservation by selecting the reservation to edit by clicking the reservation row once and then clicking "Modify" on the bottom right or double-clicking the reservation row. When you do that something like this interface will show. 
![Vn8Nh4L](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/f1270aa0-4867-428b-af04-7b847f532bf6)\
To change the seat you check a different seat and then click "Modify", or if you want to cancel the reservation, click the "Cancel Reservation" button.

## **Editing Profile**

If you want to edit your profile, from the user home page click the "Edit Profile" button which will show the following interface.
![KT2E0nu](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/3919daeb-b271-4073-9cb8-0fff333aeb3b)\
You then will enter the information you want to be associated with the logged-in account and click save. Similar to registering a user you also need a unique email and password pair. Then click save and the account's information will be updated if valid.

## **Admin Controls**

If you are logged in with an admin account on the user home page you will also see an "Admin Controls" button which will show the following interface when clicked 
![ScxUk3z](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/8dce6957-82b5-4e81-898d-16737b6bf752)\

### **Adding Airports**

If you would like to add an airport to be used in flight creation, from the "Admin Controls" interface you will click the "Add Airport" button which will bring you to the following interface
![ZAdwbYp](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/9e84aeca-de86-4d4c-95fa-00de8d32be6b)\
where you will input the desired information and click "Add Airport"

### **Removing Airports**

To remove an Airport and all associated flights and reservations; from the "Admin Controls" interface click the "Remove Airport" button. This will display the following interface
![e9xn2Ft](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/b66f85bf-2b05-43db-ac1b-9eb3f7358a87)\
Here you will select an airport code from the dropdown menu and then click remove to remove the selected airport.

### **Adding Flights**

To add a flight, from the "Admin Controls" interface click the "Add Flight" button. This will show you the following interface
![aQ046z7](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/899ae9ef-8611-4be0-9237-2a4843fffc8f)\
To start making a flight first click the "Add Flight" button on the bottom right which will show this popup
![ig3eS5F](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/9e849467-fc60-4d56-9427-a2e6188d23ca)\
here enter the maximum row and column needed to display the flight. For example, if the plane has 8 isles and at most 4 seats per isle with a gap between the seat sets, you would enter "5 8" and click "OK". This will populate the add flight menu with a grid that you can fill in to set the seats. If filled in as previously described the interface could look like this
![mFxeUel](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/3fa96b23-9409-4f9f-9751-d95181300135)\
Then you just need to add the airport, date, and time for the destination and departure of the flight as well as a fare for all the seats. When everything is entered correctly click "Save Flight" on the bottom to add the flight the the search flight view. Then, when a user views the seat selection for this example it will look like so.
![gUDYX7x](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/c18009b2-7d90-4eb4-ad5f-47334f8f3b36)\

### **Removing Flights**

If you want to remove a flight and all its associated reservations, from the admin control interface click "Remove Flight" and you will see an interface like the following
![hI3HmEq](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/82bca721-068e-49ba-8b6e-45382221d0d2)
Then, you can select the flight to remove and click "Remove" or double click the flight to remove it.

