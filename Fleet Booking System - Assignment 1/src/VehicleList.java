import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Iterator;

public class VehicleList {

	// using ArrayList as random access is required.
	ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	// get the directory for the fleet csv.
	String directory = System.getProperty("user.dir") + "/Fleet.csv";

	// read Fleet.csv and add all vehicles in the file to ArrayList vehicles.
	// for Part A however we will just do this manually.
//	public void addFleetToList() {
//		vehicles.add(new Vehicle("C001", "Toyota", "Yaris", "Sedan", 2012, 4, "Blue", 50.00, 15.00, 10, 10));
//		vehicles.add(new Vehicle("C002", "Toyota", "Corolla", "Hatch", 2020, 4, "White", 45.00, 20.00, 10, 10));
//		vehicles.add(new Vehicle("C003", "Toyota", "Kluger", "SUV", 2019, 7, "Grey", 70.00, 20.00, 20, 10));
//		vehicles.add(new Vehicle("C004", "Audi", "A3", "Sedan", 2015, 5, "Red", 65.00, 10.00, 20, 10));
//		vehicles.add(new Vehicle("C005", "Holden", "Cruze", "Hatch", 2020, 4, "Green", 70.00, 10.00, 10, 10));
//		vehicles.add(new Vehicle("C006", "BMW", "X5", "SUV", 2018, 7, "White", 100.00, 25.00, 20, 10));
//		vehicles.add(new Vehicle("C007", "BMW", "320i", "Sedan", 2021, 5, "Grey", 75.00, 10.00, 15, 0));
//		vehicles.add(new Vehicle("C008", "Ford", "Focus", "Sedan", 2014, 5, "Red", 45.00, 10.00, 10, 0));
//		vehicles.add(new Vehicle("C009", "Ford", "Puma", "SUV", 2015, 5, "Black", 70.00, 20.00, 15, 20));
//	}

	public void loadFromCSV() {

		ArrayList<String[]> records = new ArrayList<String[]>();
		try (BufferedReader br = new BufferedReader(new FileReader(directory))) {
			String dataRow;
			while ((dataRow = br.readLine()) != null) {
				String[] values = dataRow.split(",");
				records.add(values);
			}
		} catch (FileNotFoundException e) {
			System.out.println(
					"File BookingManagementSystembookings.csv couldn't be found in: " + System.getProperty("user.dir"));
		} catch (IOException e) {
			System.out.println("An error occured reading file.");
		}

		// create an object for each row of the fleet.csv file and add them to array object
		for (int i = 1; i < records.size(); i++) {

			String vehicleID = records.get(i)[0];
			String brand = records.get(i)[1];
			String model = records.get(i)[2];
			String type = records.get(i)[3];
			int yearMade = Integer.parseInt(records.get(i)[4]);
			int numSeats = Integer.parseInt(records.get(i)[5]);
			String color = records.get(i)[6];
			
			double rentalPerDay = Double.parseDouble(records.get(i)[7]);
			double insurancePerDay = Double.parseDouble(records.get(i)[8]);
			double serviceFee = Double.parseDouble(records.get(i)[9]);
			double discount = 0; 
			
			if (!records.get(i)[10].equalsIgnoreCase("N/A")) {
				discount = Double.parseDouble(records.get(i)[10]);
			}

			Vehicle newVehicle = new Vehicle(vehicleID, brand, model, type, yearMade, numSeats, color, rentalPerDay, insurancePerDay, serviceFee, discount);

			vehicles.add(newVehicle);

		}
	}

	public void returnVehicleTable() {

		// table headers
		tableHeaders();

		// Return details of every vehicle in list.
		for (int loopCount = 0; loopCount < vehicles.size(); loopCount++) {
			vehicles.get(loopCount).printDetails();
			System.out.println("");
		}

	}

	public void vehicleByBrand(String brand) {

		// use this integer to determine whether any vehicles by this brand were found
		// or not.
		int numVehiclesFound = 0;

		for (Vehicle vehicle : vehicles) {
			if (vehicle.getBrand().equalsIgnoreCase(brand)) {
				vehicle.printUserFriendlyDetails();
				System.out.println();
				numVehiclesFound++;
			}
		}

		if (numVehiclesFound == 0) {
			returnVehicleTable();
			System.out.println("We do not have any vehicles by this manufacturer. Above is a list of ALL vehicles");

		}
	}

	// Prints table headers to console. Have this as it's own method in case table
	// headers were to be printed in other interfaces
	public void tableHeaders() {
		System.out.printf("| %-12s | %-12s | %-12s | %-7s | %-9s | %-9s | %-7s | %-14s | %-17s | %-11s | %-8s",
				"Vehicle ID", "Brand", "Model", "Type", "Year Made", "Num Seats", "Color", "Rental Per Day",
				"Insurance Per Day", "Service Fee", "Discount");
		System.out.println();
		System.out.println();
	}

	// Returns a true or false value depending on if the user inputs a correct
	// vehicle ID or not. Mainly used for validation.
	public boolean checkIDInList(String IDToBook) {
		boolean vehicleInList = false;

		for (Vehicle vehicle : vehicles) {
			if (vehicle.getVehicleID().equalsIgnoreCase(IDToBook)) {
				vehicleInList = true;
			}
		}

		return vehicleInList;
	}

	public void returnVehicleBooking(String IDToBook, int differenceInDays) {

		Vehicle bookedVehicle = getVehicleByID(IDToBook);

		double totalRentalAmt;
		double discountedPrice;
		double totalInsuranceCost;
		double totalBookingCost;

		// change difference in days to 1 day if it is 0 (as a 3 hour booking will still
		// charge a whole day.)
		if (differenceInDays <= 0) {
			differenceInDays = 1;
		}

		// Calculate total rental amount
		totalRentalAmt = bookedVehicle.getRentalPerDay() * differenceInDays;
		// Calculate discounted price
		discountedPrice = totalRentalAmt;
		if (differenceInDays >= 7) {
			discountedPrice = totalRentalAmt * ((100 - bookedVehicle.getDiscount()) / 100);
		}

		// calculate total insurance cost
		totalInsuranceCost = bookedVehicle.getInsurancePerDay() * differenceInDays;
		// calculate total booking cost
		totalBookingCost = discountedPrice + totalInsuranceCost + bookedVehicle.getServiceFee();

		System.out.printf("%-35s %-50s %n", "Vehicle ID: ", IDToBook);
		System.out.printf("%-35s %-50s %n", "Number of Seats: ", bookedVehicle.getNumSeats());
		System.out.printf("%-35s %-50s %n", "Color: ", bookedVehicle.getColor());
		System.out.printf("%-35s %-50s %n", "Rental: ", totalRentalAmt);
		System.out.printf("%-35s %-50s %n", "Discounted Price: ", discountedPrice);
		System.out.printf("%-35s %-50s %n", "Insurance: ", totalInsuranceCost);
		System.out.printf("%-35s %-50s %n", "Service Fee: ", bookedVehicle.getServiceFee());
		System.out.printf("%-35s %-50s %n", "Total Booking Cost: ", totalBookingCost);

	}

	public Vehicle getVehicleByID(String IDToBook) {

		// It is fine to do this, as user input is checked in main to ensure that
		// The chosen vehicle exists in the ArrayList of vehicles.
		Vehicle bookedVehicle = null;

		for (Vehicle vehicle : vehicles) {
			if (vehicle.getVehicleID().equalsIgnoreCase(IDToBook)) {
				bookedVehicle = vehicle;
			}

		}

		return bookedVehicle;
	}

	public void returnReceipt(String IDToBook, int differenceInDays, Date startDate, Date endDate) {
		Vehicle bookedVehicle = getVehicleByID(IDToBook);

		String userFriendlyDetails = bookedVehicle.getBrand() + " " + bookedVehicle.getModel() + " "
				+ bookedVehicle.getType() + " with " + bookedVehicle.getNumSeats() + " seats.";

		double totalPayment = 0;

		if (differenceInDays >= 7) {
			totalPayment = (bookedVehicle.getRentalPerDay() * ((100 - bookedVehicle.getDiscount()) / 100)
					+ bookedVehicle.getInsurancePerDay()) * 7 + bookedVehicle.getServiceFee();
		} else {
			totalPayment = ((bookedVehicle.getRentalPerDay() + bookedVehicle.getInsurancePerDay()) * differenceInDays)
					+ bookedVehicle.getServiceFee();
		}

		System.out.printf("%-30s %-50s %n", "Your Vehicle: ", userFriendlyDetails);
		System.out.printf("%-30s %-50s %n", "Pick-Up Date: ", startDate);
		System.out.printf("%-30s %-50s %n", "Drop-Off Date: ", endDate);
		System.out.printf("%-30s %-50s %n", "Total Payment: ", totalPayment);

	}

	public void vehicleByNumSeats(int numSeatsNeeded) {

		// use this integer to determine whether any vehicles by this brand were found
		// or not.
		int numVehiclesFound = 0;

		for (Vehicle vehicle : vehicles) {
			if (vehicle.getNumSeats() >= numSeatsNeeded) {
				vehicle.printUserFriendlyDetails();
				System.out.println();
				numVehiclesFound++;
			}
		}

		if (numVehiclesFound == 0) {
			returnVehicleTable();
			System.out.println(
					"We do not have any vehicles with the required amount of seats. Above is a list of ALL vehicles");

		}

	}

	public void getAllTypes() {

		ArrayList<String> typeList = allVehicleTypes();

		System.out.println("The types we currently have available are: ");
		System.out.println();
		for (String type : typeList) {
			System.out.print(type + ", ");
		}

	}

	public ArrayList<String> allVehicleTypes() {
		ArrayList<String> typeList = new ArrayList<String>();

		for (Vehicle vehicle : vehicles) {
			if (!typeList.contains(vehicle.getType())) {
				typeList.add(vehicle.getType());
			}
		}

		return typeList;
	}

	public void vehicleByType(String typeToSearch) {

		// use this integer to determine whether any vehicles by this brand were found
		// or not.
		int numVehiclesFound = 0;

		for (Vehicle vehicle : vehicles) {
			if (vehicle.getType().equals(typeToSearch)) {
				vehicle.printUserFriendlyDetails();
				System.out.println();
				numVehiclesFound++;
			}
		}

		if (numVehiclesFound == 0) {
			returnVehicleTable();
			System.out.println(
					"We do not have any vehicles with the required amount of seats. Above is a list of ALL vehicles");

		}

	}

}
