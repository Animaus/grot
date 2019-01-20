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
import org.springframework.mock.web.MockHttpServletResponse;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.zoethout.grot.config.SecurityConfig;
import nl.zoethout.grot.config.WebConfig;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.mytest.MyTestCases;
import nl.zoethout.grot.service.UserService;
import nl.zoethout.grot.service.UserServiceImpl;
import nl.zoethout.grot.testconfig.TestBeans;
import nl.zoethout.grot.testconfig.TestSecurityConfig;

//@DisplayName("TestLoginController")
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(LoginController.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = { SecurityConfig.class })

@DisplayName("TestLoginController")
public class TestLoginController extends MyTestCases {

	TestLoginController(final TestInfo inf) {
		System.out.println("\n| " + inf.getDisplayName());
	}

	@Nested
	@DisplayName("Login_01")
	// Enables loading WebApplicationContext
	@ExtendWith(SpringExtension.class)
	// Will load the web application context
	@WebAppConfiguration
	// Bootstrap the context that the test will use
	// @ContextConfiguration(locations = { "classpath:mockBeans.xml",
	// "classpath:applicationContext.xml" })
	@ContextConfiguration(classes = { WebConfig.class })
	@ImportResource({ "classpath:mockBeans.xml" })
	class Login_01 {

		@InjectMocks
		// TODO 43 - 02 - loginController (after mocking in testContext.xml)
		private LoginController loginController;
		@Mock
		// TODO 43 - 01 - userDetailsService (after mocking in testContext.xml)
		private UserServiceImpl userDetailsService;
		// @Autowired
		// private Filter springSecurityFilterChain;

		private MockMvc mockMvc;
		@Autowired
		WebApplicationContext wac;

		@BeforeEach
		// TODO 43 - 04 - setup 01
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

	@Nested
	@DisplayName("Login_02")
	@ContextConfiguration(classes = { SecurityConfig.class, TestBeans.class }) // , WebConfig.class})
	@WebAppConfiguration // Will load the web application context
	@ExtendWith(SpringExtension.class) // Enables loading WebApplicationContext
	class Login_02 {

		@Autowired
		private WebApplicationContext context;
		private MockMvc mockMvc;
		private String strClass = this.getClass().getCanonicalName();

		Login_02(final TestInfo inf) {
			System.out.println("| - " + inf.getDisplayName());
		}

		@BeforeEach
		// TODO 43 - 04 - setup 02
		void setup() {
			String strMethod = "setup";
			testInfo(strClass, strMethod, "");
			mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		}

		@Test
		// @DisplayName("securedURL_requiresLogin")
		@DisplayName("givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication")
		void givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication(final TestInfo inf)
				throws Exception {
			String strMethod = "givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication";
			testInfo(strClass, strMethod, "");

			ResultActions ra = mockMvc.perform(get("/user/arc0j00")); //
			ra.andExpect(status().is3xxRedirection()); //
			ra.andExpect(redirectedUrlPattern("**/login"));
		}

		@Test
		void givenCredentials_whenAccessSecuredResource_thenSuccess() throws Exception {
			String strMethod = "givenCredentials_whenAccessSecuredResource_thenSuccess";
			testInfo(strClass, strMethod, "");

			UserDetails userDetails = getUserDetails(getAdmin());

			ResultActions ra = mockMvc.perform( //
					get("/user/arc0j00").with(user(userDetails))//
			);

			testInfo(strClass, strMethod, status().toString());

			ra.andExpect(status().isOk());
		}

		@Test
		@DisplayName("get_login")
		void get_login(final TestInfo inf) throws Exception {
			String strMethod = "get_login";
			testInfo(strClass, strMethod, "");

			ResultActions ra = mockMvc.perform(get("/login")); //
			ra.andExpect(status().isFound()); //
//			ra.andExpect(redirectedUrlPattern("**/login"));
		}

	}

	@Nested
	@DisplayName("Login_03")
	@ContextConfiguration(classes = { SecurityConfig.class, TestBeans.class }) // , WebConfig.class })
//	@WebAppConfiguration // Will load the web application context
	@ExtendWith(SpringExtension.class) // Enables loading WebApplicationContext
//	@RunWith(SpringRunner.class)
//	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT);
	class Login_03 {

		private String strClass = this.getClass().getCanonicalName();

		@Mock
		private UserService userService;
		private MockMvc mockMvc;

//		@Autowired
//		private FilterChainProxy springSecurityFilterChain;

		Login_03(final TestInfo inf) {
			System.out.println("| - " + inf.getDisplayName());
		}

		@BeforeEach
		void setup() {
			String strMethod = "setup";
			testInfo(strClass, strMethod, "");
			// Initializes MockMvc with Spring configuration
			mockMvc = MockMvcBuilders //
					.standaloneSetup(new LoginController()) //
//					.apply(springSecurity()) //
//					.addFilters(springSecurityFilterChain) //
					.build();
		}

		@Test
		@DisplayName("login_exists")
		void login_exists() throws Exception {
			String strMethod = "login_exists";
			testInfo(strClass, strMethod, "");

			ResultActions ra = mockMvc.perform(get("/login")); //
			ra.andExpect(status().isOk()); //

			printResponse(strMethod, ra);
		}

		@Test
		// @DisplayName("securedURL_requiresLogin")
		@DisplayName("givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication")
		void givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication(final TestInfo inf)
				throws Exception {
			String strMethod = "givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication";
			testInfo(strClass, strMethod, "");

			// MvcResult result = mockMvc.perform(action) //
			// .andExpect(status().is3xxRedirection()) //
			// .andReturn();

			ResultActions ra = mockMvc.perform(get("/user/arc0j00")); //
			printResponse(strMethod, ra);

			// ra.andExpect(status().is3xxRedirection()); //
			ra.andExpect(redirectedUrlPattern("**/login"));

		}

		void printResponse(String strMethod, ResultActions ra) {
			MvcResult result = ra.andReturn();
			MockHttpServletResponse res = result.getResponse();
			String url = res.getRedirectedUrl();
			testInfo(strClass, strMethod, "\n\t\tRedirectedUrl\t= " + url);
			url = res.getForwardedUrl();
			testInfo(strClass, strMethod, "\n\t\tForwardedUrl\t= " + url);
			url = res.getIncludedUrl();
			testInfo(strClass, strMethod, "\n\t\tIncludedUrl\t= " + url);
			res.getIncludedUrls().stream().forEach(System.out::println);
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
	
		private UserDetails userDetails;
	
		private String strClass = this.getClass().getCanonicalName() ;
		public static final String URL_LOGIN_REDIR = "**/login";
		//	public static final String URL_SECURE = "/secured";
		public static final String URL_SECURE = "/user/arc0j00";
	
		RedirectionSecurityIntegrationTest(final TestInfo inf) {
			System.out.println("| - " + inf.getDisplayName());
		}
	
		@BeforeEach
		void setup() {
			String strMethod = "setup";
			testInfo(strClass, strMethod, "");
			mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
			userDetails = getUserDetails(getUser());
		}
	
		@Test
		public void givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication() throws Exception {
			String strMethod = "givenSecuredResource_whenAccessUnauthenticated_thenRequiresAuthentication";
			testInfo(strClass, strMethod, "");
	
			mvc.perform( //
					get(URL_SECURE) //
			) //
					.andExpect(status().is3xxRedirection()) //
					.andExpect(redirectedUrlPattern(URL_LOGIN_REDIR));
		}
	
		@Test
		public void givenCredentials_whenAccessSecuredResource_thenSuccess() throws Exception {
			String strMethod = "givenCredentials_whenAccessSecuredResource_thenSuccess";
			testInfo(strClass, strMethod, "");
	
			mvc.perform( //
					get(URL_SECURE).with(user(userDetails))//
			)//
					.andExpect(status().isOk());
		}
	
		@Test
		public void givenAccessSecuredResource_whenAuthenticated_thenRedirectedBack() throws Exception {
			String strMethod = "givenAccessSecuredResource_whenAuthenticated_thenRedirectedBack";
			testInfo(strClass, strMethod, "");
	
			MockHttpServletRequestBuilder action1 = get(URL_SECURE);
			ResultActions ra1 = mvc.perform(action1);
			ra1.andExpect(status().is3xxRedirection());
			MvcResult result1 = ra1.andReturn();
			MockHttpSession session = (MockHttpSession) result1.getRequest().getSession();
			String loginUrl = result1.getResponse().getRedirectedUrl();
	
			MockHttpServletRequestBuilder action2 = post(loginUrl);
			action2.param("username", userDetails.getUsername());
			action2.param("password", userDetails.getPassword());
			action2.session(session);
			action2.with(csrf());
	
			ResultActions ra2 = mvc.perform(action2);
	
			MvcResult result2 = ra2.andReturn();
			testInfo("", "", "RedirectedUrl = " + result2.getResponse().getRedirectedUrl());
	
			ra2 //
					.andExpect(status().is3xxRedirection()) //
					.andExpect(redirectedUrlPattern("**/user/**")) //
					.andReturn();
	
			MockHttpServletRequestBuilder action3 = action1.session(session);
			ResultActions ra3 = mvc.perform(action3);

			MvcResult result3 = ra3.andReturn();
			testInfo("", "", "RedirectedUrl = " + result3.getResponse().getRedirectedUrl());

			ra3.andExpect(status().isOk());
		}
	}

}
