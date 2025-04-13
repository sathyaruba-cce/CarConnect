package dao.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dao.CustomerService;
import entity.Customer;
import exception.DatabaseConnectionException;
import exception.InvalidInputException;

import java.util.Date;

public class CustomerUpdateTest {

    private CustomerService customerService;

    @Before
    public void setUp() throws DatabaseConnectionException {
        customerService = new CustomerService();
    }

    @Test
    public void testUpdateCustomer() {
        try {
            
            int existingCustomerId = 1;
            Customer updatedCustomer = new Customer();
            updatedCustomer.setCustomerID(existingCustomerId);
            updatedCustomer.setFirstName("Johnny");
            updatedCustomer.setLastName("Dough");
            updatedCustomer.setEmail("johnny.dough@example.com");
            updatedCustomer.setPhoneNumber("0987654321");
            updatedCustomer.setAddress("5678 Oak St");
            updatedCustomer.setUsername("johnny123");
            updatedCustomer.setPassword("newpassword123");
            updatedCustomer.setRegistrationDate(new Date());
            boolean result = customerService.updateCustomer(updatedCustomer);
            assertTrue("Customer update failed", result);
            Customer retrieved = customerService.getCustomerById(existingCustomerId);
            assertEquals("Johnny", retrieved.getFirstName());
            assertEquals("Dough", retrieved.getLastName());
            assertEquals("johnny.dough@example.com", retrieved.getEmail());
            assertEquals("0987654321", retrieved.getPhoneNumber());
            assertEquals("5678 Oak St", retrieved.getAddress());
            assertEquals("johnny123", retrieved.getUsername());
            assertEquals("newpassword123", retrieved.getPassword());

        } catch (InvalidInputException | DatabaseConnectionException e) {
            fail("Exception during test: " + e.getMessage());
        }
    }
}
