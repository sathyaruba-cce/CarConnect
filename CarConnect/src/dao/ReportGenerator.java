package dao;

import java.sql.*;
import util.DBConnUtil;
import exception.DatabaseConnectionException;

public class ReportGenerator {

    private Connection con;

    public ReportGenerator() throws DatabaseConnectionException {
        con = DBConnUtil.getDbConnection();
    }

    
    public void generateReservationHistory() {
        String query = "SELECT r.ReservationID, c.FirstName, c.LastName, v.Model, v.Make, r.StartDate, r.EndDate, r.TotalCost, r.Status " +
                       "FROM Reservation r " +
                       "JOIN Customer c ON r.CustomerID = c.CustomerID " +
                       "JOIN Vehicle v ON r.VehicleID = v.VehicleID";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Reservation History Report:");
            while (rs.next()) {
                System.out.println("Reservation ID: " + rs.getInt("ReservationID"));
                System.out.println("Customer: " + rs.getString("FirstName") + " " + rs.getString("LastName"));
                System.out.println("Vehicle: " + rs.getString("Make") + " " + rs.getString("Model"));
                System.out.println("Start: " + rs.getTimestamp("StartDate"));
                System.out.println("End: " + rs.getTimestamp("EndDate"));
                System.out.println("Total Cost: ₹" + rs.getDouble("TotalCost"));
                System.out.println("Status: " + rs.getString("Status"));
                System.out.println("--------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void generateVehicleUtilization() {
        String query = "SELECT v.VehicleID, v.Make, v.Model, COUNT(r.ReservationID) AS TotalReservations " +
                       "FROM Vehicle v " +
                       "LEFT JOIN Reservation r ON v.VehicleID = r.VehicleID " +
                       "GROUP BY v.VehicleID";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Vehicle Utilization Report:");
            while (rs.next()) {
                System.out.println("Vehicle ID: " + rs.getInt("VehicleID"));
                System.out.println("Make & Model: " + rs.getString("Make") + " " + rs.getString("Model"));
                System.out.println("Total Reservations: " + rs.getInt("TotalReservations"));
                System.out.println("--------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void generateRevenueReport() {
        String query = "SELECT MONTH(StartDate) AS Month, SUM(TotalCost) AS MonthlyRevenue " +
                       "FROM Reservation " +
                       "GROUP BY MONTH(StartDate)";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Monthly Revenue Report:");
            while (rs.next()) {
                System.out.println("Month: " + rs.getInt("Month"));
                System.out.println("Revenue: ₹" + rs.getDouble("MonthlyRevenue"));
                System.out.println("--------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
