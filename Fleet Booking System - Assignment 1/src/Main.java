import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		// create scanner and VehicleList Objects used for application.
		Scanner sc = new Scanner(System.in);
		VehicleList vehicleList = new VehicleList();
		vehicleList.loadFromCSV();

		// call the home screen.
		homeScreen(sc, vehicleList);

	}

	private static void homeScreen(Scanner sc, VehicleList vehicleList) {
		int myChoice = 0;

		System.out.println("Taylor Neal - s3873735@student.rmit.edu.au");
		// display the user home screen and wait for user input
		System.out.println("Welcome to Car Rental System!");
		System.out.println();
		System.out.println("> Select from Main Menu");
		System.out.println();
		System.out.println("[1] Search by Brand");
		System.out.println("[2] Search by Vehicle Type");
		System.out.println("[3] Search by Number of Seats");
		System.out.println("[4] View all vehicles");
		System.out.println("[5] Exit");

		// validate that the user has chosen a digit only from the list provided above.
		do {
			myChoice = getNextInt(sc);
			if (myChoice < 1 || myChoice > 5) {
				System.out.println("Please enter a whole number between 1 and 5 to confirm selection.");
			}
		} while (myChoice < 1 || myChoice > 5);

		// take user to relevant screen based on their selection.
		switch (myChoice) {
		case 1:
			searchByBrand(sc, vehicleList);
			break;
		case 2:
			searchByType(sc, vehicleList);
			break;
		case 3:
			searchByNumSeats(sc, vehicleList);
			break;
		case 4:
			displayAllVehicles(sc, vehicleList);
			break;
		case 5:
			System.exit(0);
			break;
		// default case should never be called as user input is validated using
		// do-while.
		default:
			System.out.println("Something went wrong.");
			System.exit(0);
			break;
		}
	}

	private static void displayAllVehicles(Scanner sc, VehicleList vehicleList) {
		int myChoice = -1;

		vehicleList.returnVehicleTable();

		System.out.println("[1] Go back");
		System.out.println("[0] Exit");

		// validate user input is between accepted range
		do {
			myChoice = getNextInt(sc);
			if (myChoice < 0 || myChoice > 1) {
				System.out.println("Please enter a whole number between 1 and 5 to confirm selection.");
			}
		} while (myChoice < 0 || myChoice > 1);

		// call relevant method based on user input.
		switch (myChoice) {
		case 1:
			homeScreen(sc, vehicleList);
			break;
		case 0:
			System.exit(0);
			break;
		// default case should never be called as user input is validated using
		// do-while.
		default:
			System.out.println("Something went wrong, terminating application");
			System.exit(0);
			break;
		}

	}

	private static void searchByNumSeats(Scanner sc, VehicleList vehicleList) {

		int numSeatsNeeded;

		System.out.println();
		System.out.println("Searching By Number of Seats...");
		System.out.println();
		System.out.println("How many seats are you looking for? ");
		System.out.println();

		numSeatsNeeded = getNextInt(sc);

		vehicleList.vehicleByNumSeats(numSeatsNeeded);

		afterSearchOptions(sc, vehicleList);

	}

	private static void searchByType(Scanner sc, VehicleList vehicleList) {
		String typeToSearch;

		System.out.println();
		System.out.println("Searching by Type... ");
		System.out.println();
		System.out.println("What type of vehicle are you looking for? ");

		vehicleList.getAllTypes();

		sc.nextLine();
		System.out.println();
		
		// This ensures that the user specified vehicle type exists in the current list of vehicles.
		do {
			System.out.println("Enter the type of vehicle from the list above.");
			typeToSearch = sc.nextLine();
		} while (!vehicleList.allVehicleTypes().contains(typeToSearch));
		
		vehicleList.vehicleByType(typeToSearch);
		
		afterSearchOptions(sc, vehicleList);

	}

	private static void searchByBrand(Scanner sc, VehicleList vehicleList) {

		String brandToSearch;
		int myChoice = -1;

		System.out.println();
		System.out.println("Searching by Brand Name: ");
		System.out.println();
		System.out.println("Which brand of car are you looking for? ");

		System.out.println();
		brandToSearch = sc.next();

		// consume trailing newline
		sc.nextLine();

		vehicleList.vehicleByBrand(brandToSearch);

		afterSearchOptions(sc, vehicleList);

	}

	private static void afterSearchOptions(Scanner sc, VehicleList vehicleList) {
		int myChoice;
		System.out.println("What would you like to do now? ");
		System.out.println("[1] Book Vehicle by ID");
		System.out.println("[2] Return Home");
		System.out.println("[0] Exit Program");

		// validate user input is between accepted range
		do {
			myChoice = getNextInt(sc);
			if (myChoice < 0 || myChoice > 2) {
				System.out.println("Please enter a whole number between 0 and 2 to confirm selection.");
			}
		} while (myChoice < 0 || myChoice > 2);

		// Use users input to direct to correct screen
		switch (myChoice) {
		case 0:
			System.exit(0);
			break;
		case 1:
			bookVehicleByID(sc, vehicleList);
			break;
		case 2:
			homeScreen(sc, vehicleList);
			break;
		default:
			System.out.println("Something went wrong. Closing Program");
			System.exit(0);
		}
	}

	private static void bookVehicleByID(Scanner sc, VehicleList vehicleList) {

		// declare variables for use of input validation later on
		String IDToBook;
		Date startDate;
		Date endDate;
		int differenceInDays;

		// Read vehicle ID from the user.
		System.out.println();
		System.out.println("Please enter the ID of the vehicle you wish to book. ");

		sc.nextLine();

		// If vehicle ID is not in list. Do not accept input and ask user to input again
		do {
			System.out.println("Please enter a valid ID (e.g C001): ");
			IDToBook = sc.nextLine();
		} while (!vehicleList.checkIDInList(IDToBook));

		// Ask user to enter a date, ensure that said date is correct and ensure that at
		// least 1 day has been chosen

		do {
			// ask user to input start date
			System.out.println("START DATE: ");
			startDate = askDate(sc);

			// ask user to input end date
			System.out.println("END DATE: ");
			endDate = askDate(sc);

			// Calculate the difference (in days between the start and end date.
			long differenceInTime = endDate.getTime() - startDate.getTime();
			differenceInDays = (int) (differenceInTime / (1000 * 60 * 60 * 24)) % 365;
			System.out.println("Total Days Booked is: " + differenceInDays);
		} while (differenceInDays < 0);

		confirmBooking(sc, IDToBook, differenceInDays, vehicleList, startDate, endDate);

	}

	// Confirm Booking Section
	private static void confirmBooking(Scanner sc, String IDToBook, int differenceInDays, VehicleList vehicleList,
			Date startDate, Date endDate) {
		// Variables that will store the users information
		String clientName = "";
		String clientEmail = "";
		int numPassengers;

		// Now that we have a vehicle and a date, we need to return the details back
		// to the user, and ask for confirmation
		System.out.println("> Booking Details");
		System.out.println("-----------------------------");
		vehicleList.returnVehicleBooking(IDToBook, differenceInDays);

		System.out.println("Would you like to reserve the above vehicle? (Y/N)");
		char selection = getYesNo(sc);

		System.out.println("You Selected: " + selection);

		if (selection == 'Y') {
			System.out.println("Please enter your name: ");
			clientName = sc.nextLine();
			clientEmail = askEmail(sc);

			System.out.println(
					"> Your vehicle has been booked. A receipt has been sent to your email and is provided below");
			System.out.println("We will be in touch soon to confirm your pick up date.");
			System.out.println("--------------------------------------------------------------");
			System.out.printf("%-30s %-50s %n", "Name: ", clientName);
			System.out.printf("%-30s %-50s %n", "Email", clientEmail);
			vehicleList.returnReceipt(IDToBook, differenceInDays, startDate, endDate);
		} else {
			homeScreen(sc, vehicleList);
		}
	}

	// Asks the user to input an email address. is validated using regular
	// expressions
	// inside of another method.
	private static String askEmail(Scanner sc) {
		String clientEmail = "";

		do {
			System.out.println("Please enter a valid email address: ");
			clientEmail = sc.nextLine();

		} while (!isValidEmail(clientEmail));

		return clientEmail;
	}

	// Uses Regular expression to validate user email in a simple manner.
	private static boolean isValidEmail(String emailInput) {
		boolean isValid = false;

		if (emailInput.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
			isValid = true;
		}
		return isValid;
	}

	// Convert a string to a date and return it into a variable when booking
	// vehicle.
	private static Date askDate(Scanner sc) {

		// declare and initialize variables
		String dateInput;
		Date givenDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// Ask user to enter a date, ensure that said date is correct.
		do {
			System.out.println("Enter the date of your vehicle reservation (dd/MM/yyyy): ");
			dateInput = sc.nextLine();
		} while (!isValidDate(dateInput));

		if (null != dateInput && dateInput.trim().length() > 0) {

			try {
				givenDate = sdf.parse(dateInput);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return givenDate;
	}

	// ensure that the date is valid using regular expression.
	private static boolean isValidDate(String dateInput) {

		if (dateInput.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
			return true;
		}
		return false;
	}

	// this method is used to ensure that the user only inputs digits where relevant
	// and not characters or strings.
	private static int getNextInt(Scanner sc) {
		while (!sc.hasNextInt()) {
			System.out.println("Please enter a whole number only");
			sc.next();
		}
		return sc.nextInt();
	}

	private static char getYesNo(Scanner sc) {
		boolean isValid = false;
		char yesNo;
		String decision;

		do {
			System.out.println("Please enter a valid character (Y / N)");
			decision = sc.nextLine();
			decision = decision.toUpperCase();
			yesNo = decision.charAt(0);

			if (yesNo == 'Y' || yesNo == 'N') {
				isValid = true;
			}

		} while (!isValid);

		return yesNo;
	}

}
