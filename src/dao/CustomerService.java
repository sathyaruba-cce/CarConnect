package dao;

import entity.Customer;
import exception.*;
import util.DBConnUtil;

import java.sql.*;
//import java.util.Date;

public class CustomerService implements ICustomerService {
	 private Connection con;

	    public CustomerService() throws DatabaseConnectionException {
	        super();
	        con = DBConnUtil.getDbConnection();
	    }

	    @Override
	    public Customer getCustomerById(int customerId) throws InvalidInputException {
	        String sql = "SELECT * FROM Customer WHERE CustomerID = ?";
	        Customer customer = null;

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setInt(1, customerId);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                customer = new Customer(
	                        rs.getInt("CustomerID"),
	                        rs.getString("FirstName"),
	                        rs.getString("LastName"),
	                        rs.getString("Email"),
	                        rs.getString("PhoneNumber"),
	                        rs.getString("Address"),
	                        rs.getString("Username"),
	                        rs.getString("Password"),
	                        rs.getDate("RegistrationDate")
	                );
	            } else {
	                throw new InvalidInputException("Customer with ID " + customerId + " not found.");
	            }
	        } catch (SQLException e) {
	            throw new InvalidInputException("Error fetching customer data: " + e.getMessage(), e);
	        }

	        return customer;
	    }

	    @Override
	    public Customer getCustomerByUsername(String username) throws AuthenticationException {
	        String sql = "SELECT * FROM Customer WHERE Username = ?";
	        Customer customer = null;

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, username);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                customer = new Customer(
	                        rs.getInt("CustomerID"),
	                        rs.getString("FirstName"),
	                        rs.getString("LastName"),
	                        rs.getString("Email"),
	                        rs.getString("PhoneNumber"),
	                        rs.getString("Address"),
	                        rs.getString("Username"),
	                        rs.getString("Password"),
	                        rs.getDate("RegistrationDate")
	                );
	            } else {
	                throw new AuthenticationException("Username not found.");
	            }
	        } catch (SQLException e) {
	            throw new AuthenticationException("Database error: " + e.getMessage(), e);
	        }

	        return customer;
	    }

	    @Override
	    public boolean registerCustomer(Customer customer) throws InvalidInputException, DatabaseConnectionException {
	        String sql = "INSERT INTO Customer (CustomerID, FirstName, LastName, Email, PhoneNumber, Address, Username, Password, RegistrationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setInt(1, customer.getCustomerID());
	            pstmt.setString(2, customer.getFirstName());
	            pstmt.setString(3, customer.getLastName());
	            pstmt.setString(4, customer.getEmail());
	            pstmt.setString(5, customer.getPhoneNumber());
	            pstmt.setString(6, customer.getAddress());
	            pstmt.setString(7, customer.getUsername());
	            pstmt.setString(8, customer.getPassword());
	            pstmt.setDate(9, new java.sql.Date(customer.getRegistrationDate().getTime()));

	            pstmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error inserting customer: " + e.getMessage(), e);
	        }
	    }

	    @Override
	    public boolean updateCustomer(Customer customer) throws InvalidInputException, DatabaseConnectionException {
	        String sql = "UPDATE Customer SET FirstName=?, LastName=?, Email=?, PhoneNumber=?, Address=?, Username=?, Password=? WHERE CustomerID=?";

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, customer.getFirstName());
	            pstmt.setString(2, customer.getLastName());
	            pstmt.setString(3, customer.getEmail());
	            pstmt.setString(4, customer.getPhoneNumber());
	            pstmt.setString(5, customer.getAddress());
	            pstmt.setString(6, customer.getUsername());
	            pstmt.setString(7, customer.getPassword());
	            pstmt.setInt(8, customer.getCustomerID());

	            pstmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error updating customer: " + e.getMessage(), e);
	        }
	    }

	    @Override
	    public boolean deleteCustomer(int customerId) throws InvalidInputException, DatabaseConnectionException {
	        String sql = "DELETE FROM Customer WHERE CustomerID = ?";

	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setInt(1, customerId);
	            int rowsAffected = pstmt.executeUpdate();

	            if (rowsAffected == 0) {
	                throw new InvalidInputException("Customer with ID " + customerId + " not found.");
	            }

	            return true;
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error deleting customer: " + e.getMessage(), e);
	        }
	    }
    
}
