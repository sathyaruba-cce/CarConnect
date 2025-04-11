package dao;

import exception.*;
import util.DBConnUtil;

import java.sql.*;

public class AuthenticationService {
    private Connection con;

    public AuthenticationService() throws DatabaseConnectionException {
        con = DBConnUtil.getDbConnection();  
    }

    public void authenticateUser(String username, String password) throws UserNotFoundException {
        String sql = "SELECT * FROM Customer WHERE Username = ? AND Password = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            
            if (rs.next()) {
                System.out.println("Login successful. Welcome, " + rs.getString("FirstName") + "!");
            } else {
                throw new UserNotFoundException("Invalid username or password.");
            }
        } catch (SQLException e) {
            throw new UserNotFoundException("Error during authentication: " + e.getMessage(), e);
        }
    }
}
