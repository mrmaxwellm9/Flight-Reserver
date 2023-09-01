# Flight Reserver

## Usage

To use download and extract the repository to a folder, then run the [Flight Reserver](https://github.com/mrmaxwellm9/Flight-Reserver/blob/main/Flight%20Reserver.lnk) shortcut. Alternatively, enter the [run_files](https://github.com/mrmaxwellm9/Flight-Reserver/tree/main/run_files) folder and run the [bat file](https://github.com/mrmaxwellm9/Flight-Reserver/blob/main/run_files/RunFlightReserver.bat) or [shell script file](https://github.com/mrmaxwellm9/Flight-Reserver/blob/main/run_files/RunFlightReserver.sh) depending on your system.

## Features

The flight reserver application allows users to create and log into user accounts, search for flights, book flights with a seat selection, edit their profile, edit their reserved flight seat, and cancel their reserved flights. The application also has an admin account which defaults to username "admin" and password "password" which allows the admin user to create and remove flights and airports for normal users to reserve. The admin account should not reserve flights itself.

### User Registration and Login

When you open the application you will see the following interface which allows you to be directed to the login or register page. ![66sS26m](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/29e6ac0b-fa41-4803-9bac-d19f6077b176) 

#### User Registration

When you click the register button you will be shown this interface ![yHRT8mr](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/d22a594d-16c1-4e88-9c01-36a186c0ebde) To create a user account you need to enter a first name, last name, and unique email and password combination. If the email and password combination is already being used the application will inform you and you will need to enter new information. On successful account registration, the user will be taken to the user's home page which looks like this for regular users ![De8L2VV](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/a41e3571-a5d3-4187-a244-9790a26dfbf7) and this for the admin account. ![48LQbaO](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/0ae52cfd-51f4-410e-9699-3a2272a920d9)

#### Logging in

When you click the login button you will be shown this interface![52tOpzz](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/b11192b3-e058-4b00-8933-9a03bbc2cb4d) where you will enter a matching account email and password. Upon successful login, you will be brought to the user's home page.

### Searching and Booking Flights

If you click the "Search and Book Flights" button on the user home screen you will be shown an interface like this. ![me0Qsi0](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/08073dd9-bea9-4477-bb38-e6017636a0a7) This is the default search flight screen with a couple of added example flights. You can filter the flights by selecting a departure airport, destination airport, and departure date in the top filters and then clicking search. When you find a flight you would like to book you can either select the flight by clicking once and then click the "Book Now" button in the bottom right or double-click the desired flight in the flight search table. In this example, if I were to book a seat on the flight from SFO to LAX I would double-click the Flight Numer 1 row to have this popup displayed ![hVEM958](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/d19f3727-dbb2-4a75-a1ba-242bbb1c9ed8) then from there I would check the seat I would like to reserve as shown here ![PYG33KM](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/e5220f2c-d8be-4c62-9179-1a47dff4cc3b) then I would click the bottom right book button which will bring up a confirmation popup and upon confirmation the seat is booked and will be grayed out for other users trying to reserve seats on the same flight.

### Viewing Reserved Flights

If you would like to change your seat or cancel a reservation, from the user home screen you would click the "View Reserved Flights" button to be shown the following interface (assuming the logged in user has a reservation) ![rzyEABt](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/850ba6d4-3131-4c2c-b5ce-57f22233dea3)
Then you can edit the reservation by selecting the reservation to edit by clicking the reservation row once and then clicking "Modify" on the bottom right or double-clicking the reservation row. When you do that something like this interface will show. ![Vn8Nh4L](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/f1270aa0-4867-428b-af04-7b847f532bf6)
To change the seat you check a different seat and then click "Modify", or if you want to cancel the reservation, click the "Cancel Reservation" button.

### Editing Profile

If you want to edit your profile, from the user home page click the "Edit Profile" button which will show the following interface. ![KT2E0nu](https://github.com/mrmaxwellm9/Flight-Reserver/assets/130167736/3919daeb-b271-4073-9cb8-0fff333aeb3b) You then will enter the information you want to be associated with the logged in account and click save. Similar to registering a user you also need a unique email and password pair. Then click save and the account's information will be updated if valid.

### Admin Controls

#### Adding Airports

#### Removing Airports

#### Adding Flights

#### Removing Flights
