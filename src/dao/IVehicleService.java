package dao;
import java.util.HashMap;
import entity.Vehicle;
import exception.*;

public interface IVehicleService {
	Vehicle getVehicleById(int vehicleId) throws VehicleNotFoundException;
    HashMap<Integer, Vehicle> getAvailableVehicles();
    boolean addVehicle(Vehicle vehicle) throws InvalidInputException;
    boolean updateVehicle(Vehicle vehicle) throws VehicleNotFoundException;
    boolean removeVehicle(int vehicleId) throws VehicleNotFoundException;
}
