package nl.zoethout.grot.web;

import static nl.zoethout.grot.util.SessionAttributes.USER;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserServiceImpl;

@DisplayName("TestUserControllerUnittest")
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
//@ContextConfiguration(classes = { WebConfig.class })
@ContextConfiguration(locations = { "classpath:testContext.xml", "classpath:applicationContext.xml" })
public class TestUserControllerUnittest extends MyUnitTest {
	private MockMvc mockMvc;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private UserController userController;

	TestUserControllerUnittest(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		// Initializes objects annotated with @Mock
		MockitoAnnotations.initMocks(this);
		if (mockMvc == null) {
			// Initializes MockMvc without loading Spring configuration
			mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
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
			Principal usr = null;
			ResultActions ra = mockMvc.perform(get(URL_LOGIN));
			// ra.andDo(print());
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(VIEW_NAME));
			ra.andExpect(request().sessionAttribute(USER, usr));
		}

		/**
		 * The idea here is to define the mock request with all necessary parameters
		 * before it is executed. We want to describe behaviour for the mock service
		 * first. This behaviour includes the mock request.
		 * 
		 * @param inf
		 * @throws Exception
		 */
		@Test
		@DisplayName("rmLoginPostExperimental")
		@Disabled
		void rmLoginPostExperimental(TestInfo inf) throws Exception {
			User user = getAdmin();
			// Define the mock request
			MockHttpServletRequest req = new MockHttpServletRequest("POST", URL_LOGIN);
			req.addParameter("username", user.getUserName());
			req.addParameter("password", user.getPassword());
			// https://stackoverflow.com/questions/2276271/how-to-make-mock-to-void-methods-with-mockito
			// Alas, this does not set a sessionattribute as we want.
			Mockito.doCallRealMethod().when(userService).setPrincipal(req, user);
			// Define the action and execute it
			MockHttpServletRequestBuilder action = post(URL_LOGIN).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			// Test results
			// ra.andDo(print());
			ra.andExpect(view().name(URL_REDIRECT));
			// Unfortunately this can not be...
			// ra.andExpect(request().sessionAttribute(USER, user));
		}

		@Test
		@DisplayName("rmLoginPost_Admin")
		@Disabled
		void rmLoginPost_Admin(TestInfo inf) throws Exception {
			User user = getAdmin();
			MockHttpServletRequestBuilder action = post(URL_LOGIN);
			action.param("username", user.getUserName());
			action.param("password", user.getPassword());
			ResultActions ra = mockMvc.perform(action);
			// ra.andDo(print());
			ra.andExpect(status().is3xxRedirection());
			ra.andExpect(view().name(URL_REDIRECT));
		}

		@Test
		@DisplayName("rmLoginPost_Admin_WrongPWD")
		@Disabled
		void rmLoginPost_Admin_WrongPWD(TestInfo inf) throws Exception {
			User user = getAdmin();
			MockHttpServletRequestBuilder action = post(URL_LOGIN);
			action.param("username", user.getUserName());
			action.param("password", "WrongPassword");
			ResultActions ra = mockMvc.perform(action);
			// ra.andDo(print());
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(VIEW_NAME));
		}

	}

	// https://stackoverflow.com/questions/30757044/autowired-httpservletrequest-in-spring-test-integration-tests
	private static RequestPostProcessor mockedRequest(final MockHttpServletRequest mockHttpServletRequest) {
		return new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				return mockHttpServletRequest;
			}
		};
	}
}
