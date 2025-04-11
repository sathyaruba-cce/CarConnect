package dao;
import entity.Customer;
import exception.*;
public interface ICustomerService {
	 Customer getCustomerById(int customerId) throws InvalidInputException;

	    Customer getCustomerByUsername(String username) throws AuthenticationException;

	    boolean registerCustomer(Customer customer) throws InvalidInputException, DatabaseConnectionException;

	    boolean updateCustomer(Customer customer) throws InvalidInputException, DatabaseConnectionException;

	    boolean deleteCustomer(int customerId) throws InvalidInputException, DatabaseConnectionException;

}
