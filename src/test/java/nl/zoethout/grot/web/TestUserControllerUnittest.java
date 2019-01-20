package nl.zoethout.grot.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.config.WebConfig;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.service.UserService;

@DisplayName("TestHomeController")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
public class TestUserControllerUnittest extends MyUnitTest {
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	WebApplicationContext wac;

	TestUserControllerUnittest(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		if (mockMvc == null) {
			mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		}
	}

	@Nested
	@DisplayName("Login")
	class Login {

		Login(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("rmLoginGet")
		void rmLoginGet(TestInfo inf) throws Exception {
			// Start - Does nothing
			List<Role> roles = new ArrayList<Role>();
			addRole(roles, "admin", "Administrators");
			addRole(roles, "user", "Regular users");
			addRole(roles, "employee", "Employee");
			addRole(roles, "student", "Student");
			addRole(roles, "ROLE_USER", "Gebruiker");
			addRole(roles, "ROLE_ADMIN", "Beheerder");
			assertTrue(roles.size() == 6);
	        when(userService.readRoles()).thenReturn(roles);
			// Final - Does nothing

			assertNotNull(mockMvc);
			ResultActions ra = mockMvc.perform(get("/user/login"));
			ra.andExpect(status().isOk());
			ra.andExpect(forwardedUrl("logon"));
		}
	}
}
