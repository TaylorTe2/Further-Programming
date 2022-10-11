
public class Vehicle {
	
	private String vehicleID;
	private String brand;
	private String model;
	private String type;
	private int yearMade;
	private int numSeats;
	private String color;
	
	//setting these variables to double to account for the possibility of future vehicles having a value such as $18.90 in the future. 
	private double rentalPerDay;
	private double insurancePerDay;
	private double serviceFee;
	private double discount;
	
	public Vehicle (String vehicleID, String brand, String model, String type, int yearMade, int numSeats, String color, double rentalPerDay, double insurancePerDay,
			double serviceFee, double discount) {
		
		this.vehicleID = vehicleID;
		this.brand = brand;
		this.model = model;
		this.type = type;
		this.yearMade = yearMade;
		this.numSeats = numSeats;
		this.color = color;
		this.rentalPerDay = rentalPerDay;
		this.insurancePerDay = insurancePerDay;
		this.serviceFee = serviceFee;
		this.discount = discount;
				
	}

	public String getVehicleID() {
		return vehicleID;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public String getType() {
		return type;
	}

	public int getYearMade() {
		return yearMade;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public String getColor() {
		return color;
	}

	public double getRentalPerDay() {
		return rentalPerDay;
	}

	public double getInsurancePerDay() {
		return insurancePerDay;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public double getDiscount() {
		return discount;
	}
	
	// Prints details of vehicle object to console.
	public void printDetails() {
		
		//vehicle information
		System.out.printf("| %-12s | %-12s | %-12s | %-7s | %-9s | %-9s | %-7s | %-14s | %-17s | %-11s | %-8s",
				this.vehicleID, this.brand, this.model, this.type, this.yearMade, this.numSeats, this.color, this.rentalPerDay, this.insurancePerDay, this.serviceFee, this.discount);
	}
	
	public void printUserFriendlyDetails() {
		System.out.println(this.vehicleID + " - " + this.brand + " " + this.model + " " + this.type + " with " + this.numSeats + " seats.");
	}
	
	

}
