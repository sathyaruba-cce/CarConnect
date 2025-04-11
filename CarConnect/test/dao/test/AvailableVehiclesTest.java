package dao.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import dao.VehicleService;
import entity.Vehicle;
import exception.DatabaseConnectionException;

public class AvailableVehiclesTest {

    private VehicleService vehicleService;

    @Before
    public void setUp() throws DatabaseConnectionException {
        vehicleService = new VehicleService(); 
    }

    @Test
    public void testGetAvailableVehicles() {
        HashMap<Integer, Vehicle> availableVehicles = vehicleService.getAvailableVehicles();

        
        assertNotNull("Returned map should not be null", availableVehicles);

        
        for (Vehicle v : availableVehicles.values()) {
            assertTrue("Vehicle should be marked available", v.isAvailability());
            assertNotNull("Model should not be null", v.getModel());
            assertTrue("VehicleID should be positive", v.getVehicleID() > 0);
        }
    }
}
