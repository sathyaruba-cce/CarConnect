package dao;

import java.sql.*;
import java.util.HashMap;

import entity.Vehicle;
import exception.DatabaseConnectionException;
import exception.VehicleNotFoundException;
import exception.InvalidInputException;

import util.DBConnUtil;

public class VehicleService implements IVehicleService {

    private Connection con;
    public  VehicleService() throws DatabaseConnectionException {
        super();
        con = DBConnUtil.getDbConnection();
    }

    @Override
    public Vehicle getVehicleById(int vehicleId) throws VehicleNotFoundException {
        Vehicle vehicle = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Vehicle WHERE VehicleID = ?");
            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                vehicle = new Vehicle(
                		rs.getInt("VehicleID"),
            	        rs.getString("Model"),
            	        rs.getString("Make"),
            	        rs.getInt("Year"),
            	        rs.getString("Color"),
            	        rs.getString("RegistrationNumber"),  
            	        rs.getBoolean("Availability"),
            	        rs.getDouble("DailyRate")
                );
            } else {
                throw new VehicleNotFoundException("Vehicle with ID " + vehicleId + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving vehicle by ID");
            e.printStackTrace();
        }
        return vehicle;
    }

    @Override
    public HashMap<Integer, Vehicle> getAvailableVehicles() {
        HashMap<Integer, Vehicle> availableVehicles = new HashMap<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicle WHERE Availability = true");
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                		rs.getInt("VehicleID"),
            	        rs.getString("Model"),
            	        rs.getString("Make"),
            	        rs.getInt("Year"),
            	        rs.getString("Color"),
            	        rs.getString("RegistrationNumber"),  
            	        rs.getBoolean("Availability"),
            	        rs.getDouble("DailyRate")
                );
                availableVehicles.put(vehicle.getVehicleID(), vehicle);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving available Vehicle");
            e.printStackTrace();
        }
        return availableVehicles;
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) throws InvalidInputException  {
        if (vehicle.getModel().isEmpty() || vehicle.getMake().isEmpty()) {
            throw new InvalidInputException("Model and Make cannot be empty.");
        }
        if (vehicle.getYear() <= 0) {
            throw new InvalidInputException("Year must be a positive number.");
        }
        if (vehicle.getDailyRate() <= 0) {
            throw new InvalidInputException("Daily Rate must be a positive number.");
        }

        boolean flag = false;
        try {
            PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO Vehicle (Model, Make, Year, Color, RegistrationNumber, Availability, DailyRate) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            pstmt.setString(1, vehicle.getModel());
            pstmt.setString(2, vehicle.getMake());
            pstmt.setInt(3, vehicle.getYear());
            pstmt.setString(4, vehicle.getColor());
            pstmt.setString(5, vehicle.getRegistrationNumber());
            pstmt.setBoolean(6, vehicle.isAvailability());
            pstmt.setDouble(7, vehicle.getDailyRate());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Error while adding vehicle");
            e.printStackTrace();
        }
        return flag;
    }


    @Override
    public boolean updateVehicle(Vehicle vehicle) throws VehicleNotFoundException {
        boolean updated = false;
        try {
            PreparedStatement pstmt = con.prepareStatement(
                "UPDATE Vehicle SET Make=?, Model=?, Year=?, Color=?, RegistrationNumber=?, Availability=?, DailyRate=? WHERE VehicleID=?"
            );
            pstmt.setString(1, vehicle.getMake());
            pstmt.setString(2, vehicle.getModel());
            pstmt.setInt(3, vehicle.getYear());
            pstmt.setString(4, vehicle.getColor());
            pstmt.setString(5, vehicle.getRegistrationNumber());
            pstmt.setBoolean(6, vehicle.isAvailability());
            pstmt.setDouble(7, vehicle.getDailyRate());
            pstmt.setInt(8, vehicle.getVehicleID());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                updated = true;
            } else {
                throw new VehicleNotFoundException("Vehicle with ID " + vehicle.getVehicleID() + " not found for update.");
            }
        } catch (SQLException e) {
            System.out.println("Error while updating vehicle");
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean removeVehicle(int vehicleId) throws VehicleNotFoundException {
        boolean deleted = false;
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Vehicle WHERE VehicleID=?");
            pstmt.setInt(1, vehicleId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                deleted = true;
            } else {
                throw new VehicleNotFoundException("Vehicle with ID " + vehicleId + " not found for deletion.");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting vehicle");
            e.printStackTrace();
        }
        return deleted;
    }
}
