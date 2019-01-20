package nl.zoethout.grot.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.mytest.MyTestCases;
import nl.zoethout.grot.service.UserServiceImpl;
import nl.zoethout.grot.testconfig.TestBeans;
import nl.zoethout.grot.testconfig.TestSecurityConfig;

@DisplayName("TestLoginController")
public class TestLoginController extends MyTestCases {

	TestLoginController(final TestInfo inf) {
		System.out.println("\n| " + inf.getDisplayName());
	}

	@Nested
	@DisplayName("LoginIntegration")
	@ContextConfiguration(classes = { TestSecurityConfig.class, TestBeans.class })
	@WebAppConfiguration // Will load the web application context
	@ExtendWith(SpringExtension.class) // Enables loading WebApplicationContext
	public class LoginIntegration {
		@Autowired
		private WebApplicationContext context;
		private MockMvc mvc;

		private String strClass = this.getClass().getSimpleName();

		LoginIntegration(final TestInfo inf) {
			System.out.println("| - " + inf.getDisplayName());
		}

		@BeforeEach
		void setup() {
			String strMethod = "setup";
			devInfo(strClass, strMethod, "");
			mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		}

		@Test
		@DisplayName("guest_formLogin")
		@Disabled
		void guest_formLogin(final TestInfo inf) throws Exception {
			// was: "login_exists";
			String strMethod = "guest_formLogin";
			devInfo(strClass, strMethod, "");

			// URL to test
			String url = "/login";
			// Prepare and perform action
			FormLoginRequestBuilder action = formLogin(url);
			ResultActions ra = mvc.perform(action);
			// Check status
			ra.andExpect(status().isFound());
		}

		@Test
		@DisplayName("guest_formLogin_loginUser")
		@Disabled
		void guest_formLogin_loginUser(final TestInfo inf) throws Exception {
			// was: "login_post_exists2";
			String strMethod = "guest_formLogin_loginUser";
			devInfo(strClass, strMethod, "");
		
			// URL to test
			String url = "/login";
			// User to test with
			User user = getUser();
			// Prepare and perform action
			FormLoginRequestBuilder action = formLogin(url);
			ResultActions ra = mvc.perform( //
					action //
							.user(user.getUserName()) //
							.password(user.getPassword()) //
			);
			// Check status
			ra.andExpect(status().isFound());
			// Check authentication
			ra.andExpect(authenticated().withUsername(user.getUserName()));
		}

		@Test
		@DisplayName("guest_formLogin_withMockUser")
		@WithMockUser(username = "arc0j00", password = "123456", roles = "USER")
		@Disabled
		void guest_formLogin_withMockUser(final TestInfo inf) throws Exception {
			String strMethod = "guest_formLogin_loginUser";
			devInfo(strClass, strMethod, "");

			// URL to test
			String url = "/login";
			// Prepare and perform action
			FormLoginRequestBuilder action = formLogin(url);
			ResultActions ra = mvc.perform( //
					action //
							.user("arc0j00") //
							.password("123456") //
			);
			// Check status
			ra.andExpect(status().isFound());
			// Check authentication
			ra.andExpect(authenticated().withUsername("arc0j00"));
		}

		@Test
		@DisplayName("guest_getLogin")
		void guest_getLogin() throws Exception {
			// was: "givenLogin_whenUnauthenticated_thenGotoLogin";
			String strMethod = "guest_getLogin";
			devInfo(strClass, strMethod, "");

			// URL to test
			String url = "/login";
			// Prepare and perform action
			MockHttpServletRequestBuilder action = get(url);
			ResultActions ra = mvc.perform(action);
			// Check status
			ra.andExpect(status().isOk()); //
		}

		@Test
		@DisplayName("guest_arc0j00_gotoLogin")
		void guest_arc0j00_gotoLogin() throws Exception {
			// was: "givenSecured_whenUnauthenticated_thenGotoLogin";
			String strMethod = "guest_arc0j00_gotoLogin";
			devInfo(strClass, strMethod, "");

			// URL to test
			String url = "/user/arc0j00";
			String redirect = "**/login";
			// Prepare and perform action
			MockHttpServletRequestBuilder action = get(url);
			ResultActions ra = mvc.perform(action);
			// Check status
			ra.andExpect(status().is3xxRedirection());
			// Check redirection
			ra.andExpect(redirectedUrlPattern(redirect));
		}

		@Test
		@DisplayName("guest_arc0j00_loginUser")
		void guest_arc0j00_loginUser() throws Exception {
			// was: givenSecured_whenLogin_thenRedirect
			String strMethod = "guest_arc0j00_loginUser";
			devInfo(strClass, strMethod, "");
		
			// URL to test
			String url = "/user/arc0j00";
			String redirect = "/login*";
			// User to test with
			User user = getUser();
			UserDetails userDetails = getUserDetails(user);
		
			// First action: get URL
			MockHttpServletRequestBuilder action1 = get(url);
			// Perform action 1
			ResultActions ra1 = mvc.perform(action1);
			// Check outcome of action 1
			ra1.andExpect(status().is3xxRedirection());
			// Get session of action 1
			MvcResult result1 = ra1.andReturn();
			MockHttpSession session1 = (MockHttpSession) result1.getRequest().getSession();
		
			// Get URI for action 2
			String loginFQPN = result1.getResponse().getRedirectedUrl();
			// Second action: post URI
			MockHttpServletRequestBuilder action2 = post(loginFQPN);
			// Set credentials for action 2
			action2.param("username", userDetails.getUsername());
			action2.param("password", userDetails.getPassword());
			// Set HTTP session to use for action 2
			action2.session(session1);
			// Perform action 2
			ResultActions ra2 = mvc.perform(action2);
			// Check outcome of action 2
			ra2.andExpect(status().is3xxRedirection());
			ra2.andExpect(redirectedUrlPattern(redirect));
		
			// Set HTTP session to use for action 3
			MockHttpServletRequestBuilder action3 = action1.session(session1);
			// Perform action 3
			ResultActions ra3 = mvc.perform(action3);
			// Check outcome of action 3
			ra3.andExpect(status().isOk());
		}

		@Test
		@DisplayName("user_arc0j00_success")
		void user_arc0j00_success() throws Exception {
			// was: "givenSecured_whenAuthenticated_thenSuccess";
			String strMethod = "user_arc0j00_success";
			devInfo(strClass, strMethod, "");

			// URL to test
			String url = "/user/arc0j00";
			// User to test with
			User user = getUser();
			UserDetails userDetails = getUserDetails(user);
			// Prepare and perform action
			MockHttpServletRequestBuilder action = get(url).with(user(userDetails));
			ResultActions ra = mvc.perform(action);
			// Check status
			ra.andExpect(status().isOk());
		}
	}

	@Nested
	@DisplayName("LoginUnit")
	@ContextConfiguration(classes = { TestSecurityConfig.class, TestBeans.class })
	public class LoginUnit {
		private String strClass = this.getClass().getSimpleName();

		private MockMvc mvc;

		@Mock
		private UserServiceImpl userService;

		@InjectMocks
		private LoginController loginController;

		LoginUnit(final TestInfo inf) {
			System.out.println("| - " + inf.getDisplayName());
		}

		@BeforeEach
		void setup() {
			String strMethod = "setup";
			devInfo(strClass, strMethod, "");
			if (mvc == null) {
				// Initializes objects annotated with @Mock
				MockitoAnnotations.initMocks(this);
				// Initializes MockMvc without loading full Spring configuration
				mvc = MockMvcBuilders.standaloneSetup(loginController).build();
				// Tells mocked service how to respond
				mockUserService(userService);
			}
		}

		/**
		 * Since Spring methods for testing login do not have a valid
		 * java.security.Principal, we have to mock it here.
		 * 
		 * @author Gerard Zoethout
		 * @throws Exception
		 */
		@Test
		@DisplayName("user_loginSuccess")
		void user_loginSuccess() throws Exception {
			String strMethod = "user_loginSuccess";
			devInfo(strClass, strMethod, "");

			// URL to test
			String url = "/loginSuccess";
			String redirect = "/*";
			// User to test with
			User user = getUser();
			// Mock principal
			Principal principal = Mockito.mock(Principal.class);
			// Tells mocked principal how to respond
			Mockito.when(principal.getName()).thenReturn(user.getUserName());
			// Prepare request
			MockHttpServletRequest req = new MockHttpServletRequest("GET", url);
			req.setUserPrincipal(principal);
			// Prepare and perform action
			MockHttpServletRequestBuilder action = get(url).with(mockRequest(req));
			ResultActions ra = mvc.perform(action);
			// Check status
			ra.andExpect(status().isFound());
			// Check redirection
			ra.andExpect(redirectedUrlPattern(redirect));
		}

	}

}
