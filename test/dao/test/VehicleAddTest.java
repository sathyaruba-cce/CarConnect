package dao.test;

import static org.junit.Assert.*;

import org.junit.Test;


import dao.VehicleService;
import entity.Vehicle;
import exception.InvalidInputException;
import exception.DatabaseConnectionException;
public class VehicleAddTest {

	@Test
	public void test() {
		try {
            VehicleService vehicleService = new VehicleService();

            Vehicle vehicle = new Vehicle(0, "Swift", "Maruti", 2021, "White", "TN01AB1234", true, 1200.0);

            boolean result = vehicleService.addVehicle(vehicle);

            assertTrue("Vehicle should be added successfully", result);

        } catch (InvalidInputException e) {
            fail("Validation failed: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            fail("Database connection failed: " + e.getMessage());
        }
	}

}
