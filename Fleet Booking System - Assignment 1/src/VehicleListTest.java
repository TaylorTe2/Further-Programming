import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class VehicleListTest {
	
	@Test
	void testcheckIDInList() {
		VehicleList vehicleList = new VehicleList();
		vehicleList.loadFromCSV();
		
		boolean test = vehicleList.checkIDInList("C001");
		
		assertEquals(true, test);
	}
	
	@Test
	void testcheckIDNotInList() {
		VehicleList vehicleList = new VehicleList();
		vehicleList.loadFromCSV();
		
		boolean test = vehicleList.checkIDInList("should be false");
		
		assertEquals(false, test);
	}
	
	@Test
	void testGetVehicleByID() {
		VehicleList vehicleList = new VehicleList();
		vehicleList.loadFromCSV();
		
		Vehicle test = vehicleList.getVehicleByID("C001");
		
		assertEquals(vehicleList.vehicles.get(0), test);
	}

	
}
