package nl.zoethout.grot.web;

import static nl.zoethout.grot.util.SessionAttributes.USER;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserServiceImpl;

@DisplayName("TestUserControllerIntegrationTest")
// Enables loading WebApplicationContext
@ExtendWith(SpringExtension.class)
// Will load the web application context
@WebAppConfiguration
// Bootstrap the context that the test will use
// @ContextConfiguration(classes = {WebConfig.class})
@ContextConfiguration(locations = { "classpath:testContext.xml", "classpath:applicationContext.xml" })
public class TestUserControllerIntegrationTest extends MyUnitTest {

	private MockMvc mockMvc;

	@Mock
	private UserServiceImpl userService;

	@Autowired
	WebApplicationContext wac;

	TestUserControllerIntegrationTest(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		// Initializes objects annotated with @Mock
		if (mockMvc == null) {
			// Initializes MockMvc and loads Spring configuration
			mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		}
		when(userService.readRoles()).thenReturn(getRoles());
		when(userService.loginUser("front00", "123456")).thenReturn(getAdmin());
		when(userService.loginUser("arc0j00", "123456")).thenReturn(getUser());
		when(userService.loginUser("hawks00", "123456")).thenReturn(getDisabled());
	}

	@Nested
	@DisplayName("Login")
	class Login {
		private static final String URL_REDIRECT = "redirect:/";
		private static final String URL_LOGIN = "/user/login";
		private static final String VIEW_NAME = "logon";
		Login(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("rmLoginGet")
		@Disabled
		void rmLoginGet(TestInfo inf) throws Exception {
			Principal principal = Principal.getUser(null);
			MockHttpServletRequestBuilder action = get(URL_LOGIN);
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(VIEW_NAME));
			ra.andExpect(request().sessionAttribute(USER, principal));
		}

		/**
		 * This FAILS because the mocked service does not get injected into the controller.
		 * @param inf
		 * @throws Exception
		 */
		@Test
		@DisplayName("rmLoginPost_Admin_FailsNoInjection")
		@Disabled
		void rmLoginPost_Admin_FailsNoInjection(TestInfo inf) throws Exception {
			User user = getAdmin();
			MockHttpServletRequestBuilder action = post(URL_LOGIN);
			action.param("username", user.getUserName());
			action.param("password", user.getPassword());
			ResultActions ra = mockMvc.perform(action);
			// ra.andDo(print());
			// ra.andExpect(status().is3xxRedirection());
			ra.andExpect(view().name(URL_REDIRECT));
		}
	}
}
