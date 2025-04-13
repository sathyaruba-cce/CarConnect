package dao;

import entity.Admin;
import exception.AdminNotFoundException;
import exception.InvalidInputException;
import exception.DatabaseConnectionException;
import util.DBConnUtil;

import java.sql.*;

public class AdminService implements IAdminService {
    private Connection con;

    public AdminService() throws DatabaseConnectionException {
        con = DBConnUtil.getDbConnection();
    }

    @Override
    public Admin getAdminById(int adminId) throws AdminNotFoundException {
        String sql = "SELECT * FROM Admin WHERE AdminID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, adminId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractAdminFromResultSet(rs);
            } else {
                throw new AdminNotFoundException("Admin with ID " + adminId + " not found.");
            }
        } catch (SQLException e) {
            throw new AdminNotFoundException("Error fetching admin by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public Admin getAdminByUsername(String username) throws AdminNotFoundException {
        String sql = "SELECT * FROM Admin WHERE Username = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractAdminFromResultSet(rs);
            } else {
                throw new AdminNotFoundException("Admin with username '" + username + "' not found.");
            }
        } catch (SQLException e) {
            throw new AdminNotFoundException("Error fetching admin by username: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean registerAdmin(Admin adminData) throws InvalidInputException {
        String sql = "INSERT INTO Admin (AdminID, FirstName, LastName, Email, PhoneNumber, Username, Password, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, adminData.getAdminID());
            pstmt.setString(2, adminData.getFirstName());
            pstmt.setString(3, adminData.getLastName());
            pstmt.setString(4, adminData.getEmail());
            pstmt.setString(5, adminData.getPhoneNumber());
            pstmt.setString(6, adminData.getUsername());
            pstmt.setString(7, adminData.getPassword());
            pstmt.setString(8, adminData.getRole());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new InvalidInputException("Error registering admin: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateAdmin(Admin adminData) throws AdminNotFoundException {
        String sql = "UPDATE Admin SET FirstName=?, LastName=?, Email=?, PhoneNumber=?, Username=?, Password=?, Role=? WHERE AdminID=?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, adminData.getFirstName());
            pstmt.setString(2, adminData.getLastName());
            pstmt.setString(3, adminData.getEmail());
            pstmt.setString(4, adminData.getPhoneNumber());
            pstmt.setString(5, adminData.getUsername());
            pstmt.setString(6, adminData.getPassword());
            pstmt.setString(7, adminData.getRole());
            pstmt.setInt(8, adminData.getAdminID());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new AdminNotFoundException("Admin with ID " + adminData.getAdminID() + " not found for update.");
            }
            return true;
        } catch (SQLException e) {
            throw new AdminNotFoundException("Error updating admin: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteAdmin(int adminId) throws AdminNotFoundException {
        String sql = "DELETE FROM Admin WHERE AdminID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, adminId);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new AdminNotFoundException("Admin with ID " + adminId + " not found for deletion.");
            }
            return true;
        } catch (SQLException e) {
            throw new AdminNotFoundException("Error deleting admin: " + e.getMessage(), e);
        }
    }

    
    private Admin extractAdminFromResultSet(ResultSet rs) throws SQLException {
        return new Admin(
                rs.getInt("AdminID"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Email"),
                rs.getString("PhoneNumber"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("Role"),
                rs.getDate("JoinDate")
        );
    }
}
