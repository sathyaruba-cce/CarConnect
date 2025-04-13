package dao.test;
import org.junit.Test;

import dao.VehicleService;
import entity.Vehicle;
import exception.VehicleNotFoundException;
import exception.DatabaseConnectionException;
import static org.junit.Assert.*;



public class VehicleUpdateTest {

	@Test
	public void test() {
		try{
		VehicleService vehicleService = new VehicleService();

        
        Vehicle vehicle = new Vehicle(1, "Alto", "Maruti", 2023, "Red", "TN10AB4321", true, 1100.0);

        boolean result = vehicleService.updateVehicle(vehicle);

        assertTrue("Vehicle should be updated successfully", result);

    } catch (VehicleNotFoundException e) {
        fail("Vehicle not found for update: " + e.getMessage());
    } catch (DatabaseConnectionException e) {
        fail("Database connection failed: " + e.getMessage());
    }
	}

}
