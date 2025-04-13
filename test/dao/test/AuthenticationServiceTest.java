package dao.test;

import static org.junit.Assert.*;

import org.junit.Test;
import exception.DatabaseConnectionException;
import exception.UserNotFoundException;
import dao.AuthenticationService;


public class AuthenticationServiceTest {

	@Test
	public void test() {
		try {
            AuthenticationService authService = new AuthenticationService();
            
            
            authService.authenticateUser("invalidUser", "invalidPass");
            
           
            fail("Expected UserNotFoundException for invalid credentials.");
        
        } catch (UserNotFoundException e) {
            
            assertTrue(e.getMessage().contains("Invalid username or password") ||
                       e.getMessage().contains("Error during authentication"));
        } catch (DatabaseConnectionException e) {
            
            fail("Database connection failed: " + e.getMessage());
        }
    
	}

}
