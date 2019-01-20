package nl.zoethout.grot.web;

import static nl.zoethout.grot.util.PageURL.REDIRECT_USER;
import static nl.zoethout.grot.util.PageURL.USERS_UNKNOWN;
import static nl.zoethout.grot.util.PageURL.USERS_VERIFIED;
import static nl.zoethout.grot.util.PageURL.USER_UNKNOWN;
import static nl.zoethout.grot.util.PageURL.USER_VERIFIED;
import static nl.zoethout.grot.util.PageURL.USER_VERIFIED_WRITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.config.WebConfig;
import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserServiceImpl;

@DisplayName("TestUserController")
//@ExtendWith(SpringExtension.class) // Enables loading WebApplicationContext
//@WebAppConfiguration // Will load the web application context
// Bootstrap the context that the test will use
//@ContextConfiguration(locations = { "classpath:mockBeans.xml", "classpath:applicationContext.xml" })
@ContextConfiguration(classes = { WebConfig.class })
//@ImportResource({"classpath:mockBeans.xml"})
public class TestUserController extends MyUnitTest {

	private static final String MUTABLE = "mutable";
	private static final String URL_USER = "/user";
	private MockMvc mockMvc;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private UserController userController;

	private String urlMethod;
	private String httpMethod;

	TestUserController(final TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		if (mockMvc == null) {
			// Initializes objects annotated with @Mock
			MockitoAnnotations.initMocks(this);
			// Initializes MockMvc without loading Spring configuration
			mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
			mockUserService(userService);
		}
	}

	private void assertRun(final User user, final String userName, final String mode) throws Exception {
		// Prepare web
		String page = getPage(mode);
		String url = getUrl(userName);
		MockHttpServletRequest req = new MockHttpServletRequest(httpMethod, url);
		mockLogin(req, user);
		// Print parameters
		printLine("url\t: " + url);
		printParameters(user, userName, 0, page);
		// Prepare action
		MockHttpServletRequestBuilder action = get(url).with(mockRequest(req));
		ResultActions ra = mockMvc.perform(action);
		// Check status
		ra.andExpect(status().isOk());
		// Check page to access
		ra.andExpect(view().name(page));
		// Check user to be edited
		assertUser(req, userName, ra);
	}

	private void assertUser(final MockHttpServletRequest req, final String userName, final ResultActions ra) {
		if (userName != null) {
			User mutable = (User) ra.andReturn().getModelAndView().getModel().get(MUTABLE);
			User fixed = provider(req).getSAFixed();
			assertEquals(userName, mutable.getUserName());
			assertEquals(userName, fixed.getUserName());
		}
	}

	private String getUrl(final String userName) {
		String url = "";
		if (userName != null) {
			url = URL_USER + "/" + userName + urlMethod;
		} else {
			url = URL_USER;
		}
		return url;
	}

	private String getPage(final String mode) {
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

	private void printParameters(final User user, final String userName, final int saving, final String page) {
		if (user == null) {
			printLine("logon\t: " + user);
		} else {
			printLine("logon\t: " + user.getUserName());
		}
		printLine("access\t: " + userName);
		printLine("saving\t: " + saving);
		printLine("page\t: " + page);
	}

	private void setMutable(final MockHttpServletRequest req, final User edit) {
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
	@DisplayName("Users")
	class Usrs {
		Usrs(final TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "";
			httpMethod = "GET";
		}

		@Test
		@DisplayName("rmUsers_Admin")
		void rmUsers_Admin(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getAdmin(), null, "pv");
		}

		@Test
		@DisplayName("rmUsers_User")
		void rmUsers_User(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getUser(), null, "pu");
		}

		@Test
		@DisplayName("rmUsers_Guest")
		void rmUsers_Guest(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(null, null, "pu");
		}

		@Test
		@DisplayName("rmHome2")
		void rmHome2(TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			User user = getAdmin();
			// Prepare web
			String page = "home";
			String url = "/home";
			MockHttpServletRequest req = new MockHttpServletRequest("GET", url);
			mockLogin(req, user);
			// Print parameters
			// printParameters(user, userName, 0, page);
			// Prepare action
			MockHttpServletRequestBuilder action = get(url).with(mockRequest(req));
			ResultActions ra = mockMvc.perform(action);
			// Check status
			ra.andExpect(status().isOk());
			// Check page to access
			ra.andExpect(view().name(page));
			// Check user to be edited
			// assertUser(req, userName, ra);
		}
		
		@Test
		@DisplayName("rmHome3")
		void rmHome3(TestInfo inf) throws Exception {
			mockMvc.perform(get("/")).andExpect(status().isOk());
		}

	}

	@Nested
	@DisplayName("User")
	class Usr {
		Usr(final TestInfo inf) {
			printLine("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "";
			httpMethod = "GET";
		}

		@Test
		@DisplayName("rmUser_Admin_front00")
		void rmUser_Admin_front00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getAdmin(), "front00", "sv");
		}

		@Test
		@DisplayName("rmUser_Admin_arc0j00")
		void rmUser_Admin_arc0j00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getAdmin(), "arc0j00", "sv");
		}

		@Test
		@DisplayName("rmUser_User_front00")
		void rmUser_User_front00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getUser(), "front00", "su");
		}

		@Test
		@DisplayName("rmUser_User_arc0j00")
		void rmUser_User_arc0j00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getUser(), "arc0j00", "sv");
		}

		@Test
		@DisplayName("rmUser_Guest_arc0j00")
		void rmUser_Guest_arc0j00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(null, "arc0j00", "su");
		}
	}

	@Nested
	@DisplayName("UserGet")
	class UsrGet {
		UsrGet(final TestInfo inf) {
			printLine("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "/edit";
			httpMethod = "GET";
		}

		@Test
		@DisplayName("rmUserGet_Admin_front00")
		void rmUserGet_Admin_front00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getAdmin(), "front00", "svw");
		}

		@Test
		@DisplayName("rmUserGet_Admin_arc0j00")
		void rmUserGet_Admin_arc0j00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getAdmin(), "arc0j00", "svw");
		}

		@Test
		@DisplayName("rmUserGet_User_arc0j00")
		void rmUserGet_User_arc0j00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getUser(), "arc0j00", "svw");
		}

		@Test
		@DisplayName("rmUserGet_User_front00")
		void rmUserGet_User_front00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(getUser(), "front00", "su");
		}

		@Test
		@DisplayName("rmUserGet_Guest_arc0j00")
		void rmUserGet_Guest_arc0j00(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			assertRun(null, "arc0j00", "su");
		}
	}

	@Nested
	@DisplayName("UserPost")
	class UsrPost {
		UsrPost(final TestInfo inf) {
			printLine("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "/save";
			httpMethod = "POST";
		}

		private void assertEdit(final User user, final String userName, final int saving) throws Exception {
			// Prepare web
			String page = REDIRECT_USER.part() + userName;
			String url = getUrl(userName);
			MockHttpServletRequest req = new MockHttpServletRequest(httpMethod, url);
			mockLogin(req, user);
			// Print parameters
			printParameters(user, userName, saving, page);
			// Prepare user to be edited
			User edit = userService.readUser(userName);
			provider(req).setSAFixed(edit);
			setMutable(req, edit);
			// Prepare action
			MockHttpServletRequestBuilder action = post(url).with(mockRequest(req));
			ResultActions ra = mockMvc.perform(action);
			// Check status
			ra.andExpect(status().isFound());
			// Check page to access
			ra.andExpect(view().name(page));
			ra.andExpect(model().attributeExists(MUTABLE));
			// Check saving
			ArgumentCaptor<User> saveUser = ArgumentCaptor.forClass(User.class);
			ArgumentCaptor<Address> saveAddress = ArgumentCaptor.forClass(Address.class);
			verify(userService, times(saving)).saveUser(saveUser.capture());
			verify(userService, times(saving)).saveAddress(saveAddress.capture());
		}

		@Test
		@DisplayName("rmUserPost_Admin_front00")
		void rmUserPost_Admin_front00(final TestInfo inf) throws Exception {
			assertEdit(getAdmin(), "front00", 1);
		}

		@Test
		@DisplayName("rmUserPost_Admin_arc0j00")
		void rmUserPost_Admin_arc0j00(final TestInfo inf) throws Exception {
			assertEdit(getAdmin(), "arc0j00", 1);
		}

		@Test
		@DisplayName("rmUserPost_User_front00")
		void rmUserPost_User_front00(final TestInfo inf) throws Exception {
			assertEdit(getUser(), "front00", 0);
		}

		@Test
		@DisplayName("rmUserPost_User_arc0j00")
		void rmUserPost_User_arc0j00(final TestInfo inf) throws Exception {
			assertEdit(getUser(), "arc0j00", 1);
		}

		@Test
		@DisplayName("rmUserPost_Guest_arc0j00")
		void rmUserPost_Guest_arc0j00(final TestInfo inf) throws Exception {
			assertEdit(null, "arc0j00", 0);
		}
	}

	@Nested
	@DisplayName("UserPostError")
	class UsrPostError {
		UsrPostError(final TestInfo inf) {
			printLine("- " + inf.getDisplayName());
			prefix = "\t";
			urlMethod = "/save";
			httpMethod = "POST";
		}

		private void assertEdit(final User user, final String userName, final int saving, final String page,
				final ResultMatcher rm) throws Exception {
			// Prepare web
			String url = getUrl(userName);
			MockHttpServletRequest req = new MockHttpServletRequest(httpMethod, url);
			mockLogin(req, user);
			// Print parameters
			printParameters(user, userName, saving, page);
			// Prepare user to be edited
			User edit = userService.readUser(userName);
			provider(req).setSAFixed(edit);
			setMutable(req, edit);
			req.setParameter("dateBirth", "WrongDate");
			// Prepare action
			MockHttpServletRequestBuilder action = post(url).with(mockRequest(req));
			ResultActions ra = mockMvc.perform(action);
			// Check status
			ra.andExpect(rm);
			// Check page to access
			ra.andExpect(view().name(page));
			ra.andExpect(model().attributeExists(MUTABLE));
			// Check saving
			ArgumentCaptor<User> saveUser = ArgumentCaptor.forClass(User.class);
			ArgumentCaptor<Address> saveAddress = ArgumentCaptor.forClass(Address.class);
			verify(userService, times(saving)).saveUser(saveUser.capture());
			verify(userService, times(saving)).saveAddress(saveAddress.capture());
		}

		@Test
		@DisplayName("rmUserPost_Admin_front00")
		void rmUserPost_Admin_front00(final TestInfo inf) throws Exception {
			String userName = "front00";
			String page = "user_verified_write";
			assertEdit(getAdmin(), userName, 0, page, status().isOk());
		}

		@Test
		@DisplayName("rmUserPost_Admin_arc0j00")
		void rmUserPost_Admin_arc0j00(final TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String page = "user_verified_write";
			assertEdit(getAdmin(), userName, 0, page, status().isOk());
		}

		@Test
		@DisplayName("rmUserPost_User_front00")
		void rmUserPost_User_front00(final TestInfo inf) throws Exception {
			String userName = "front00";
			String page = REDIRECT_USER.part() + userName;
			assertEdit(getUser(), userName, 0, page, status().isFound());
		}

		@Test
		@DisplayName("rmUserPost_User_arc0j00")
		void rmUserPost_User_arc0j00(final TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String page = "user_verified_write";
			assertEdit(getUser(), userName, 0, page, status().isOk());
		}

		@Test
		@DisplayName("rmUserPost_Guest_arc0j00")
		void rmUserPost_Guest_arc0j00(final TestInfo inf) throws Exception {
			String userName = "arc0j00";
			String page = REDIRECT_USER.part() + userName;
			assertEdit(null, userName, 0, page, status().isFound());
		}
	}
}
