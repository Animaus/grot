package nl.zoethout.grot.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.service.UserService;
import nl.zoethout.grot.util.SessionAttributes;

@DisplayName("TestUserControllerIntegrationTest")
@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {WebConfig.class})
@ContextConfiguration(locations = { "classpath:testContext.xml", "classpath:applicationContext.xml" })
@WebAppConfiguration
public class TestUserControllerIntegrationTest extends MyUnitTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;

	@Autowired
	private UserService userService;

	TestUserControllerIntegrationTest(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		if (mockMvc == null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		}
	}

	@Nested
	@DisplayName("Login")
	class Login {
		private static final String JSP = "/WEB-INF/jsp/logon.jsp";

		Login(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("rmLoginGet")
		void rmLoginGet(TestInfo inf) throws Exception {
			Principal usr = null;
			
			List<Role> roles = new ArrayList<Role>();
			addRole(roles, "admin", "Administrators");
			addRole(roles, "user", "Regular users");
			addRole(roles, "employee", "Employee");
			addRole(roles, "student", "Student");
			addRole(roles, "ROLE_USER", "Gebruiker");
			addRole(roles, "ROLE_ADMIN", "Beheerder");
			when(userService.readRoles()).thenReturn(roles);

			assertTrue(roles.size() == 6);
			assertNotNull(mockMvc);
			
			ResultActions ra = mockMvc.perform(get("/user/login"));
			ra.andExpect(status().isOk());
			ra.andExpect(forwardedUrl(JSP));
			ra.andExpect(request().sessionAttribute(SessionAttributes.USER, usr));
		}
	}
}
