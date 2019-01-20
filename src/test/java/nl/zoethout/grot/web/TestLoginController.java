package nl.zoethout.grot.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import nl.zoethout.grot.config.WebConfig;
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
	@DisplayName("Login_new")
	// Enables loading WebApplicationContext
	@ExtendWith(SpringExtension.class)
	// Will load the web application context
	@WebAppConfiguration
	// Bootstrap the context that the test will use
	// @ContextConfiguration(locations = { "classpath:mockBeans.xml",
	// "classpath:applicationContext.xml" })
	@ContextConfiguration(classes = { WebConfig.class })
	@ImportResource({ "classpath:mockBeans.xml" })
	class Login_new {

		@Autowired
		WebApplicationContext wac;

		private MockMvc mockMvc;
		@InjectMocks
		// TODO 43 - 02 - loginController (after mocking in testContext.xml)
		private LoginController loginController;

		@Mock
		// TODO 43 - 01 - userDetailsService (after mocking in testContext.xml)
		private UserServiceImpl userDetailsService;

		Login_new(final TestInfo inf) {
			System.out.println("| - " + inf.getDisplayName());
		}

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

	}

	@Nested
	@DisplayName("RedirectionSecurityIntegrationTest")
	@ContextConfiguration(classes = { TestSecurityConfig.class, TestBeans.class })
	@WebAppConfiguration // Will load the web application context
	@ExtendWith(SpringExtension.class) // Enables loading WebApplicationContext
	// TODO 43 - RedirectionSecurityIntegrationTest
	public class RedirectionSecurityIntegrationTest {
		@Autowired
		private WebApplicationContext context;
		private MockMvc mvc;

		private String strClass = this.getClass().getCanonicalName();

		RedirectionSecurityIntegrationTest(final TestInfo inf) {
			System.out.println("| - " + inf.getDisplayName());
		}

		@BeforeEach
		void setup() {
			String strMethod = "setup";
			testInfo(strClass, strMethod, "");
			mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		}

		@Test
		public void givenLogin_whenUnauthenticated_thenGotoLogin() throws Exception {
			String strMethod = "givenLogin_whenUnauthenticated_thenGotoLogin";
			testInfo(strClass, strMethod, "");

			String url = "/login";

			MockHttpServletRequestBuilder action = get(url);
			ResultActions ra = mvc.perform(action);

			ra.andExpect(status().isOk()); //
		}

		@Test
		public void givenSecured_whenUnauthenticated_thenGotoLogin() throws Exception {
			String strMethod = "givenSecured_whenUnauthenticated_thenGotoLogin";
			testInfo(strClass, strMethod, "");

			String url = "/user/arc0j00";
			String redirect = "**/login";

			MockHttpServletRequestBuilder action = get(url);
			ResultActions ra = mvc.perform(action);

			ra.andExpect(status().is3xxRedirection());
			ra.andExpect(redirectedUrlPattern(redirect));
		}

		@Test
		public void givenSecured_whenAuthenticated_thenSuccess() throws Exception {
			String strMethod = "givenSecured_whenAuthenticated_thenSuccess";
			testInfo(strClass, strMethod, "");

			UserDetails userDetails = getUserDetails(getUser());
			String url = "/user/arc0j00";

			MockHttpServletRequestBuilder action = get(url).with(user(userDetails));
			ResultActions ra = mvc.perform(action);

			ra.andExpect(status().isOk());
		}

		@Test
		public void givenSecured_whenLogin_thenRedirect() throws Exception {
			String strMethod = "givenSecured_whenLogin_thenRedirect";
			testInfo(strClass, strMethod, "");

			UserDetails userDetails = getUserDetails(getUser());

			String url = "/user/arc0j00";
			String redirect = "**/user/**";

			MockHttpServletRequestBuilder action1 = get(url);
			ResultActions ra1 = mvc.perform(action1);

			ra1.andExpect(status().is3xxRedirection());

			MvcResult result1 = ra1.andReturn();
			MockHttpSession session = (MockHttpSession) result1.getRequest().getSession();
			String loginUrl = result1.getResponse().getRedirectedUrl();

			MockHttpServletRequestBuilder action2 = post(loginUrl);
			action2.param("username", userDetails.getUsername());
			action2.param("password", userDetails.getPassword());
			action2.session(session);

			ResultActions ra2 = mvc.perform(action2);
			ra2.andExpect(status().is3xxRedirection());
			ra2.andExpect(redirectedUrlPattern(redirect));

			MockHttpServletRequestBuilder action3 = action1.session(session);
			ResultActions ra3 = mvc.perform(action3);
			ra3.andExpect(status().isOk());
		}
	}

}
