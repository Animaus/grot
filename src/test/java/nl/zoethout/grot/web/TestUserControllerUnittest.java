package nl.zoethout.grot.web;

import static nl.zoethout.grot.util.PageURL.LOGIN;
import static nl.zoethout.grot.util.PageURL.REDIRECT_HOME;
import static nl.zoethout.grot.util.PageURL.REDIRECT_USER;
import static nl.zoethout.grot.util.PageURL.USERS_UNKNOWN;
import static nl.zoethout.grot.util.PageURL.USERS_VERIFIED;
import static nl.zoethout.grot.util.PageURL.USER_UNKNOWN;
import static nl.zoethout.grot.util.PageURL.USER_VERIFIED;
import static nl.zoethout.grot.util.PageURL.USER_VERIFIED_WRITE;
import static nl.zoethout.grot.util.SessionAttributes.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.domain.Address;
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
	private MockMvc mockMvc;
	
	@Mock
	private UserServiceImpl userService;
	
	@InjectMocks
	private UserController userController;
	private String urlMethod;
	private String httpMethod;

	TestUserControllerUnittest(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		if (mockMvc == null) {
			// Initializes objects annotated with @Mock
			MockitoAnnotations.initMocks(this);
			// Initializes MockMvc without loading Spring configuration
			mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
			initUserService(userService);
		}
	}

	private void assertRun(User user, String userName, String mode) throws Exception {
		// Prepare web
		String page = getPage(mode);
		String url = getUrl(userName);
		req = new MockHttpServletRequest(httpMethod, url);
		login(user);
		// Print parameters
		printParameters(user, userName, 0, page);
		// Prepare action
		MockHttpServletRequestBuilder action = get(url).with(mockedRequest(req));
		ResultActions ra = mockMvc.perform(action);
		// Check status
		ra.andExpect(status().isOk());
		// Check page to access
		ra.andExpect(view().name(page));
		// Check user to be edited
		assertUser(userName, ra);
	}

	private void assertUser(String userName, ResultActions ra) {
		if (userName != null) {
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get("mutable");
			User fixed = attr.getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}
	}

	private String getUrl(String userName) {
		String url = "";
		if (userName != null) {
			url = URL_USER + "/" + userName + urlMethod;
		} else {
			url = URL_USER;
		}
		return url;
	}

	private String getPage(String mode) {
		String access = "";
		switch (mode) {
		case "sv": // singular verified
			access = USER_VERIFIED.part();
			break;
		case "svw": // singular verified write
			access = USER_VERIFIED_WRITE.part();
			break;
		case "su": // singular unknown
			access = USER_UNKNOWN.part();
			break;
		case "pv": // plural verified
			access = USERS_VERIFIED.part();
			break;
		case "pu": // plural unknown
			access = USERS_UNKNOWN.part();
			break;
		default:
			break;
		}
		return access;
	}

	private void printParameters(User user, String userName, int saving, String page) {
		if (user == null) {
			println("logon\t: " + user);
		} else {
			println("logon\t: " + user.getUserName());
		}
		println("access\t: " + userName);
		println("saving\t: " + saving);
		println("page\t: " + page);
	}

	private void setMutable(User edit) {
		req.setParameter("userId", "" + edit.getUserId());
		req.setParameter("userName", edit.getUserName());
		req.setParameter("firstName", edit.getFirstName());
		req.setParameter("lastName", edit.getLastName());
		req.setParameter("prefix", edit.getPrefix());
		req.setParameter("sex", edit.getSex());
		req.setParameter("password", edit.getPassword());
		req.setParameter("enabled", "" + edit.isEnabled());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		req.setParameter("dateBirth", dateFormat.format(edit.getDateBirth()));
		req.setParameter("dateRegistered", dateFormat.format(edit.getDateRegistered()));
	
		Address address = edit.getAddress();
	
		req.setParameter("address.streetName", address.getStreetName());
		req.setParameter("address.streetNumber", address.getStreetNumber());
		req.setParameter("address.zip", address.getZip());
		req.setParameter("address.city", address.getCity());
		req.setParameter("address.country", address.getCountry());
		req.setParameter("address.phone1", address.getPhone1());
		req.setParameter("address.phone2", address.getPhone2());
		req.setParameter("address.email1", address.getEmail1());
		req.setParameter("address.email2", address.getEmail2());
	
	}

	@Nested
	@DisplayName("Login")
	class Login {
		Login(TestInfo inf) {
			println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("rmLoginGet")
		void rmLoginGet(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			Principal usr = null;
			ResultActions ra = mockMvc.perform(get(URL_USER_LOGIN));
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(LOGIN.part()));
			ra.andExpect(request().sessionAttribute(USER, usr));
			// Print parameters
			printParameters(null, null, 0, LOGIN.part());
		}

		@Test
		@DisplayName("rmLoginPost_Admin_GoodPWD")
		void rmLoginPost_Admin_GoodPWD(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			User user = getAdmin();
			req = new MockHttpServletRequest("POST", URL_USER_LOGIN);
			req.setParameter("username", user.getUserName());
			req.setParameter("password", user.getPassword());
			MockHttpServletRequestBuilder action = post(URL_USER_LOGIN).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().is3xxRedirection());
			ra.andExpect(view().name(REDIRECT_HOME.part()));
			// Print parameters
			printParameters(user, null, 0, REDIRECT_HOME.part());
		}

		@Test
		@DisplayName("rmLoginPost_Admin_WrongPWD")
		void rmLoginPost_Admin_WrongPWD(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			User user = getAdmin();
			req = new MockHttpServletRequest("POST", URL_USER_LOGIN);
			req.setParameter("username", user.getUserName());
			req.setParameter("password", "WrongPWD");
			MockHttpServletRequestBuilder action = post(URL_USER_LOGIN).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(LOGIN.part()));
			// Print parameters
			printParameters(user, null, 0, LOGIN.part());
		}
	}

	@Nested
	@DisplayName("Users")
	class Usrs {
		Usrs(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "";
			httpMethod = "GET";
		}

		@Test
		@DisplayName("rmUsers_Admin")
		void rmUsers_Admin(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getAdmin(), null, "pv");
		}

		@Test
		@DisplayName("rmUsers_User")
		void rmUsers_User(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getUser(), null, "pu");
		}

		@Test
		@DisplayName("rmUsers_Guest")
		void rmUsers_Guest(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(null, null, "pu");
		}
	}

	@Nested
	@DisplayName("User")
	class Usr {
		Usr(TestInfo inf) {
			println("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "";
			httpMethod = "GET";
		}

		@Test
		@DisplayName("rmUser_Admin_front00")
		void rmUser_Admin_front00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getAdmin(), "front00", "sv");
		}

		@Test
		@DisplayName("rmUser_Admin_arc0j00")
		void rmUser_Admin_arc0j00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getAdmin(), "arc0j00", "sv");
		}

		@Test
		@DisplayName("rmUser_User_front00")
		void rmUser_User_front00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getUser(), "front00", "su");
		}

		@Test
		@DisplayName("rmUser_User_arc0j00")
		void rmUser_User_arc0j00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getUser(), "arc0j00", "sv");
		}

		@Test
		@DisplayName("rmUser_Guest_arc0j00")
		void rmUser_Guest_arc0j00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(null, "arc0j00", "su");
		}
	}

	@Nested
	@DisplayName("UserGet")
	class UsrGet {
		UsrGet(TestInfo inf) {
			println("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "/edit";
			httpMethod = "GET";
		}

		@Test
		@DisplayName("rmUserGet_Admin_front00")
		void rmUserGet_Admin_front00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getAdmin(), "front00", "svw");
		}

		@Test
		@DisplayName("rmUserGet_Admin_arc0j00")
		void rmUserGet_Admin_arc0j00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getAdmin(), "arc0j00", "svw");
		}

		@Test
		@DisplayName("rmUserGet_User_arc0j00")
		void rmUserGet_User_arc0j00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getUser(), "arc0j00", "svw");
		}

		@Test
		@DisplayName("rmUserGet_User_front00")
		void rmUserGet_User_front00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(getUser(), "front00", "su");
		}

		@Test
		@DisplayName("rmUserGet_Guest_arc0j00")
		void rmUserGet_Guest_arc0j00(TestInfo inf) throws Exception {
			println(inf.getDisplayName());
			assertRun(null, "arc0j00", "su");
		}
	}

	@Nested
	@DisplayName("UserPost")
	class UsrPost {
		UsrPost(TestInfo inf) {
			println("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "/save";
			httpMethod = "POST";
		}

		private void assertEdit(User user, String userName, int saving) throws Exception {
			// Prepare web
			String page = REDIRECT_USER.part() + userName;
			String url = getUrl(userName);
			req = new MockHttpServletRequest(httpMethod, url);
			login(user);
			// Print parameters
			printParameters(user, userName, saving, page);
			// Prepare user to be edited
			User edit = userService.readUser(userName);
			attr.setSAFixed(edit);
			setMutable(edit);
			// Prepare action
			MockHttpServletRequestBuilder action = post(url).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			// Check status
			ra.andExpect(status().isFound());
			// Check page to access
			ra.andExpect(view().name(page));
			ra.andExpect(model().attributeExists("mutable"));
			// Check saving
			ArgumentCaptor<User> saveUser = ArgumentCaptor.forClass(User.class);
			ArgumentCaptor<Address> saveAddress = ArgumentCaptor.forClass(Address.class);
			verify(userService, times(saving)).saveUser(saveUser.capture());
			verify(userService, times(saving)).saveAddress(saveAddress.capture());
		}

		@Test
		@DisplayName("rmUserPost_Admin_front00")
		void rmUserPost_Admin_front00(TestInfo inf) throws Exception {
			assertEdit(getAdmin(), "front00", 1);
		}

		@Test
		@DisplayName("rmUserPost_Admin_arc0j00")
		void rmUserPost_Admin_arc0j00(TestInfo inf) throws Exception {
			assertEdit(getAdmin(), "arc0j00", 1);
		}

		@Test
		@DisplayName("rmUserPost_User_front00")
		void rmUserPost_User_front00(TestInfo inf) throws Exception {
			assertEdit(getUser(), "front00", 0);
		}

		@Test
		@DisplayName("rmUserPost_User_arc0j00")
		void rmUserPost_User_arc0j00(TestInfo inf) throws Exception {
			assertEdit(getUser(), "arc0j00", 1);
		}

		@Test
		@DisplayName("rmUserPost_Guest_arc0j00")
		void rmUserPost_Guest_arc0j00(TestInfo inf) throws Exception {
			assertEdit(null, "arc0j00", 0);
		}
	}

	@Nested
	@DisplayName("UserPostError")
	class UsrPostError {
		UsrPostError(TestInfo inf) {
			println("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "/save";
			httpMethod = "POST";
		}

		private void assertEdit(User user, String userName, int saving, String page, ResultMatcher rm) throws Exception {
			// Prepare web
			String url = getUrl(userName);
			req = new MockHttpServletRequest(httpMethod, url);
			login(user);
			// Print parameters
			printParameters(user, userName, saving, page);
			// Prepare user to be edited
			User edit = userService.readUser(userName);
			attr.setSAFixed(edit);
			setMutable(edit);
			req.setParameter("dateBirth", "WrongDate");
			// Prepare action
			MockHttpServletRequestBuilder action = post(url).with(mockedRequest(req));
			ResultActions ra = mockMvc.perform(action);
			// Check status
			ra.andExpect(rm);
			// Check page to access
			ra.andExpect(view().name(page));
			ra.andExpect(model().attributeExists("mutable"));
			// Check saving
			ArgumentCaptor<User> saveUser = ArgumentCaptor.forClass(User.class);
			ArgumentCaptor<Address> saveAddress = ArgumentCaptor.forClass(Address.class);
			verify(userService, times(saving)).saveUser(saveUser.capture());
			verify(userService, times(saving)).saveAddress(saveAddress.capture());
		}

		@Test
		@DisplayName("rmUserPost_Admin_front00")
		void rmUserPost_Admin_front00(TestInfo inf) throws Exception {
			String userName = "front00";
			String page = "user_verified_write";
			assertEdit(getAdmin(), userName, 0, page, status().isOk());
		}

		@Test
		@DisplayName("rmUserPost_Admin_arc0j00")
		void rmUserPost_Admin_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String page = "user_verified_write";
			assertEdit(getAdmin(), userName, 0, page, status().isOk());
		}

		@Test
		@DisplayName("rmUserPost_User_front00")
		void rmUserPost_User_front00(TestInfo inf) throws Exception {
			String userName = "front00";
			String page = REDIRECT_USER.part() + userName;
			assertEdit(getUser(), userName, 0, page, status().isFound());
		}

		@Test
		@DisplayName("rmUserPost_User_arc0j00")
		void rmUserPost_User_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String page = "user_verified_write";
			assertEdit(getUser(), userName, 0, page, status().isOk());
		}

		@Test
		@DisplayName("rmUserPost_Guest_arc0j00")
		void rmUserPost_Guest_arc0j00(TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String page = REDIRECT_USER.part() + userName;
			assertEdit(null, userName, 0, page, status().isFound());
		}
	}
}
