package nl.zoethout.grot.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.context.annotation.ImportResource;
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

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.config.WebConfig;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserServiceImpl;

//@DisplayName("TestLoginController")
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(LoginController.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = { SecurityConfig.class })

@DisplayName("TestUserController")
//Enables loading WebApplicationContext
@ExtendWith(SpringExtension.class)
//Will load the web application context
@WebAppConfiguration
//Bootstrap the context that the test will use
//@ContextConfiguration(locations = { "classpath:mockBeans.xml", "classpath:applicationContext.xml" })
@ContextConfiguration(classes = { WebConfig.class })
@ImportResource({"classpath:mockBeans.xml"})
public class TestLoginController extends MyUnitTest {
	@Autowired
	WebApplicationContext wac;

//	@Autowired
//	private Filter springSecurityFilterChain;

	private MockMvc mockMvc;

	@Mock
	// TODO 43 - 01 - userDetailsService (after mocking in testContext.xml)
	private UserServiceImpl userDetailsService;

	@InjectMocks
	// TODO 43 - 02 - loginController (after mocking in testContext.xml)
	private LoginController loginController;

	@BeforeEach
	// TODO 43 - 04 - setup
	void setup() {
		if (mockMvc == null) {
			// Initializes objects annotated with @Mock
			MockitoAnnotations.initMocks(this);
			// Mocks userDetailsService
			mockUserDetailsService(userDetailsService);
			// Initializes MockMvc with Spring configuration
			mockMvc = MockMvcBuilders //
					.standaloneSetup(loginController) //
					// .webAppContextSetup(wac) //
					// .addFilters(springSecurityFilterChain) //
					// .apply(springSecurity()) //
					.build();
		}
	}

	@Nested
	@DisplayName("Login_new")
	class Login_new {

		@Test
		@DisplayName("login_exists")
		void login_exists(final TestInfo inf) throws Exception {
			FormLoginRequestBuilder action = formLogin("/login");
			ResultActions ra = mockMvc.perform( //
					action //
//							.user("user1") //
//							.password("user1Pass") //
			);
			ra.andExpect(status().isFound());
		}

		@Test
		@DisplayName("login_exists2")
		@WithMockUser(username = "user1", password = "user1Pass", roles = "USER")
		void login_exists2(final TestInfo inf) throws Exception {
			FormLoginRequestBuilder action = formLogin("/login");
			ResultActions ra = mockMvc.perform( //
					action //
							.user("user1") //
							.password("user1Pass") //
			);
			ra.andExpect(status().isFound());
			ra.andExpect(authenticated().withUsername("user1"));
		}

		@Test
		@DisplayName("login_post_exists2")
		void login_post_exists2(final TestInfo inf) throws Exception {
			// Prepare web
			User user = getAdmin();
			String url = "/login";
			// MockHttpServletRequest req = new MockHttpServletRequest("POST", url);
			// req.setParameter("username", user.getUserName());
			// req.setParameter("password", user.getPassword());
			// Prepare action
			// MockHttpServletRequestBuilder action = post(url).with(mockRequest(req));
			// ResultActions ra = mockMvc.perform(action);
			// // Check status
			// ra.andExpect(authenticated().withUsername(user.getUserName()));
			// ra.andExpect(status().isOk());
			// ra.andExpect(view().name(LOGIN.part()));

			FormLoginRequestBuilder action = formLogin(url);
			ResultActions ra = mockMvc.perform( //
					action //
							.user(user.getUserName()) //
							.password(user.getPassword()) //
			);
			ra.andExpect(status().isFound());
			ra.andExpect(authenticated().withUsername(user.getUserName()));

		}

		@Test
		public void givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication() throws Exception {
			// mockMvc.perform(get("/secured")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/login"));
			MockHttpServletRequestBuilder action = get("/user/arc0j00");
			mockMvc.perform(action) //
					.andExpect(status().is3xxRedirection()) //
					.andExpect(redirectedUrlPattern("**/login"));
		}

		@Test
		public void givenCredentials_whenAccessSecuredResource_thenSuccess() throws Exception {
			UserDetails userDetails = getUserDetails(getAdmin());
			MockHttpServletRequestBuilder action = get("/user/arc0j00").with(user(userDetails));
			mockMvc.perform(action) //
					.andExpect(status().isOk());
		}

		@Test
		public void givenAccessSecuredResource_whenAuthenticated_thenRedirectedBack() throws Exception {
			UserDetails userDetails = getUserDetails(getAdmin());
			MockHttpServletRequestBuilder action = get("/user/arc0j00");
			MvcResult result = mockMvc.perform(action) //
					.andExpect(status().is3xxRedirection()) //
					.andReturn();
//			MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
//			String loginUrl = result.getResponse().getRedirectedUrl();
//			mockMvc.perform( //
//					post(loginUrl) //
//							.param("username", userDetails.getUsername()) //
//							.param("password", userDetails.getPassword()) //
//							// .session(session).with(csrf()) //
//			) //
//					.andExpect(status().is3xxRedirection()) //
//					.andExpect(redirectedUrlPattern("**/secured")) //
//					.andReturn();
//			mockMvc.perform(action.session(session)).andExpect(status().isOk());
		}

	}

}
