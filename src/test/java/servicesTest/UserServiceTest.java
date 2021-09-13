package servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.trms.beans.User;
import com.trms.beans.UserType;
import com.trms.data.UserDAO;
import com.trms.services.UserService;

public class UserServiceTest {
	private UserService uservice;
	private static User u;
	private UserDAO ud;
	
	
	@BeforeAll
	public static void setUpClass() {
		u = new User();
		u.setUsername("Test");
		u.setSupervisorUsername("mark");
		u.setDepartment("HR");
		u.setType(UserType.EMPLOYEE);
		u.setAvailableReimbursement(1000.0);
		
	}
	
	  @Test 
	  public void testIsSupervisorTrue() { 
	  
		  String username = "mark";
	  
		  assertEquals(username, u.getSupervisorUsername(), "Asserting they are the supervisor");
	  
	  }
	  
	  @Test 
	  public void testIsTypeFalse() { 
		  UserType type = UserType.EMPLOYEE;
	  
		  assertNotEquals(type, u.getType(), "Asserting they are not the supervisor"); 
	  }
	  
	  @Test 
	  public void testIsDepheadTrue() { 
		  String dept = "HR";
	  
		  assertEquals(dept, u.getDepartment(),
		  "Asserting they are the dephead"); 
	  }
	  
	  @Test 
	  public void testIsSupervisorFalse() { 
		  String username = "Jen";
	  
		  assertNotEquals(username, u.getSupervisorUsername(),"Asserting he is not the supervisor"); 
	  }
	  
	  @Test
	  public void testIsBencoTrue() {
		  
		  assertNotEquals(UserType.BENCO, u.getType(), "Asserting User2 is a Benco");
	  }
	  
	  public void testReimbursement() {
		  
		  assertEquals(1000.0, u.getAwardedReimbursement(),"Asserting of available reimbursement is 1000"); 
	  }
}
