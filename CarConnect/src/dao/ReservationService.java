package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Reservation;
import exception.DatabaseConnectionException;
import exception.ReservationException;
import util.DBConnUtil;

public class ReservationService implements IReservationService {
    private Connection con;

    public ReservationService()  throws DatabaseConnectionException {
        super();
        con = DBConnUtil.getDbConnection();
    }

    @Override
    public Reservation getReservationById(int reservationId) throws ReservationException {
        Reservation reservation = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Reservation WHERE ReservationID = ?");
            pstmt.setInt(1, reservationId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reservation = new Reservation(
                        rs.getInt("ReservationID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("VehicleID"),
                        rs.getTimestamp("StartDate"),
                        rs.getTimestamp("EndDate"),
                        rs.getDouble("TotalCost"),
                        rs.getString("Status")
                );
            } else {
                throw new ReservationException("Reservation with ID " + reservationId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ReservationException("Error retrieving reservation by ID.");
        }
        return reservation;
    }

    @Override
    public List<Reservation> getReservationsByCustomerId(int customerId) throws ReservationException {
        List<Reservation> reservations = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Reservation WHERE CustomerID = ?");
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("ReservationID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("VehicleID"),
                        rs.getTimestamp("StartDate"),
                        rs.getTimestamp("EndDate"),
                        rs.getDouble("TotalCost"),
                        rs.getString("Status")
                );
                reservations.add(reservation);
            }
            if (reservations.isEmpty()) {
                throw new ReservationException("No reservations found for customer ID " + customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ReservationException("Error retrieving reservations for customer ID " + customerId);
        }
        return reservations;
    }

    @Override
    public boolean createReservation(Reservation reservationData) throws ReservationException {
        try {
            
            PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM Reservation WHERE VehicleID = ? AND Status = 'Reserved' AND StartDate <= ? AND EndDate >= ?");
            checkStmt.setInt(1, reservationData.getVehicleID());
            checkStmt.setTimestamp(2, new java.sql.Timestamp(reservationData.getStartDate().getTime()));
            checkStmt.setTimestamp(3, new java.sql.Timestamp(reservationData.getEndDate().getTime()));

            ResultSet checkResult = checkStmt.executeQuery();

            if (checkResult.next()) {
                throw new ReservationException("Vehicle is already reserved for the selected period.");
            }

            
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Reservation (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, reservationData.getCustomerID());
            pstmt.setInt(2, reservationData.getVehicleID());
            pstmt.setTimestamp(3, new java.sql.Timestamp(reservationData.getStartDate().getTime()));
            pstmt.setTimestamp(4, new java.sql.Timestamp(reservationData.getEndDate().getTime()));

            
            pstmt.setDouble(5, reservationData.getTotalCost());
            pstmt.setString(6, reservationData.getStatus());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ReservationException("Error creating reservation.");
        }
    }

    @Override
    public boolean updateReservation(Reservation reservationData) throws ReservationException {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE Reservation SET StartDate = ?, EndDate = ?, TotalCost = ?, Status = ? WHERE ReservationID = ?");
            pstmt.setTimestamp(1, new java.sql.Timestamp(reservationData.getStartDate().getTime()));  // Convert StartDate to Timestamp
            pstmt.setTimestamp(2, new java.sql.Timestamp(reservationData.getEndDate().getTime()));   
            pstmt.setDouble(3, reservationData.getTotalCost());
            pstmt.setString(4, reservationData.getStatus());
            pstmt.setInt(5, reservationData.getReservationID());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ReservationException("Error updating reservation.");
        }
    }

    @Override
    public boolean cancelReservation(int reservationId) throws ReservationException {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE Reservation SET Status = 'Cancelled' WHERE ReservationID = ?");
            pstmt.setInt(1, reservationId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ReservationException("Error cancelling reservation.");
        }
    }
}
