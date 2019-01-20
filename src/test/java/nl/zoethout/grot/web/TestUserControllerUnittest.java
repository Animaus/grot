package nl.zoethout.grot.web;

import static nl.zoethout.grot.util.PageURL.LOGIN;
import static nl.zoethout.grot.util.PageURL.USERS_UNKNOWN;
import static nl.zoethout.grot.util.PageURL.USERS_VERIFIED;
import static nl.zoethout.grot.util.PageURL.USER_UNKNOWN;
import static nl.zoethout.grot.util.PageURL.USER_VERIFIED;
import static nl.zoethout.grot.util.PageURL.USER_VERIFIED_WRITE;
import static nl.zoethout.grot.util.SessionAttributes.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserServiceImpl;

@DisplayName("TestUserControllerUnittest")
// Enables loading WebApplicationContext
@ExtendWith(SpringExtension.class)
// Will load the web application context
@WebAppConfiguration
// Bootstrap the context that the test will use
// @ContextConfiguration(classes = { WebConfig.class })
@ContextConfiguration(locations = { "classpath:testContext.xml", "classpath:applicationContext.xml" })
public class TestUserControllerUnittest extends MyUnitTest {

	private static final String URL_USER = "/user";
	private static final String URL_USER_LOGIN = "/user/login";
	private static final String URL_REDIRECT = "redirect:/";

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
			initUserService(userService);
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
		@Disabled
		void rmLoginGet(TestInfo inf) throws Exception {
			Principal usr = null;
			ResultActions ra = mockMvc.perform(get(URL_USER_LOGIN));
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(LOGIN.part()));
			ra.andExpect(request().sessionAttribute(USER, usr));
		}

		@Test
		@DisplayName("rmLoginPost_Admin_GoodPWD")
		@Disabled
		void rmLoginPost_Admin_GoodPWD(TestInfo inf) throws Exception {
			User user = getAdmin();
			req = new MockHttpServletRequest("POST", URL_USER_LOGIN);
			req.setParameter("username", user.getUserName());
			req.setParameter("password", user.getPassword());
			MockHttpServletRequestBuilder action = post(URL_USER_LOGIN).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().is3xxRedirection());
			ra.andExpect(view().name(URL_REDIRECT));
		}

		@Test
		@DisplayName("rmLoginPost_Admin_WrongPWD")
		@Disabled
		void rmLoginPost_Admin_WrongPWD(TestInfo inf) throws Exception {
			User user = getAdmin();
			req = new MockHttpServletRequest("POST", URL_USER_LOGIN);
			req.setParameter("username", user.getUserName());
			req.setParameter("password", "WrongPWD");
			MockHttpServletRequestBuilder action = post(URL_USER_LOGIN).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(LOGIN.part()));
		}
	}

	@Nested
	@DisplayName("Users")
	class Users {
		Users(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("rmUsers_Admin")
		@Disabled
		void rmUsers_Admin(TestInfo inf) throws Exception {
			req = new MockHttpServletRequest("GET", URL_USER);
			login(getAdmin());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USERS_VERIFIED.part()));
		}

		@Test
		@DisplayName("rmUsers_User")
		@Disabled
		void rmUsers_User(TestInfo inf) throws Exception {
			req = new MockHttpServletRequest("GET", URL_USER);
			login(getUser());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USERS_UNKNOWN.part()));
		}

		@Test
		@DisplayName("rmUsers_Guest")
		@Disabled
		void rmUsers_Guest(TestInfo inf) throws Exception {
			req = new MockHttpServletRequest("GET", URL_USER);
			logout();
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USERS_UNKNOWN.part()));
		}

		@Test
		@DisplayName("rmUser_Admin_front00")
		@Disabled
		void rmUser_Admin_front00(TestInfo inf) throws Exception {
			String userName = "front00";
			String URL = URL_USER + "/" + userName;
			req = new MockHttpServletRequest("GET", URL);
			login(getAdmin());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_VERIFIED.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}

		@Test
		@DisplayName("rmUser_Admin_arc0j00")
		@Disabled
		void rmUser_Admin_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String URL = URL_USER + "/" + userName;
			req = new MockHttpServletRequest("GET", URL);
			login(getAdmin());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_VERIFIED.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}

		@Test
		@DisplayName("rmUser_User_front00")
		@Disabled
		void rmUser_User_front00(TestInfo inf) throws Exception {
			String userName = "front00";
			String URL = URL_USER + "/" + userName;
			req = new MockHttpServletRequest("GET", URL);
			login(getUser());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_UNKNOWN.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}

		@Test
		@DisplayName("rmUser_User_arc0j00")
		@Disabled
		void rmUser_User_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String URL = URL_USER + "/" + userName;
			req = new MockHttpServletRequest("GET", URL);
			login(getUser());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_VERIFIED.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}
		
		@Test
		@DisplayName("rmUser_Guest_arc0j00")
		@Disabled
		void rmUser_Guest_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String URL = URL_USER + "/" + userName;
			req = new MockHttpServletRequest("GET", URL);
			logout();
			setProvider();
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_UNKNOWN.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}
		
		@Test
		@DisplayName("rmUserGet_Admin_front00")
		@Disabled
		void rmUserGet_Admin_front00(TestInfo inf) throws Exception {
			String userName = "front00";
			String URL = URL_USER + "/" + userName + "/edit";
			req = new MockHttpServletRequest("GET", URL);
			login(getAdmin());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_VERIFIED_WRITE.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}

		@Test
		@DisplayName("rmUserGet_Admin_arc0j00")
		@Disabled
		void rmUserGet_Admin_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String URL = URL_USER + "/" + userName + "/edit";
			req = new MockHttpServletRequest("GET", URL);
			login(getAdmin());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_VERIFIED_WRITE.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}

		@Test
		@DisplayName("rmUserGet_User_front00")
		@Disabled
		void rmUserGet_User_front00(TestInfo inf) throws Exception {
			String userName = "front00";
			String URL = URL_USER + "/" + userName + "/edit";
			req = new MockHttpServletRequest("GET", URL);
			login(getUser());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_UNKNOWN.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}

		@Test
		@DisplayName("rmUserGet_User_arc0j00")
		@Disabled
		void rmUserGet_User_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String URL = URL_USER + "/" + userName + "/edit";
			req = new MockHttpServletRequest("GET", URL);
			login(getUser());
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_VERIFIED_WRITE.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}
		
		@Test
		@DisplayName("rmUserGet_Guest_arc0j00")
		@Disabled
		void rmUserGet_Guest_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String URL = URL_USER + "/" + userName + "/edit";
			req = new MockHttpServletRequest("GET", URL);
			logout();
			setProvider();
			MockHttpServletRequestBuilder action = get(URL_USER).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(USER_UNKNOWN.part()));
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}

	}

}
