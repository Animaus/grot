package nl.zoethout.grot.web;

import static nl.zoethout.grot.util.PageURL.LOGIN;
import static nl.zoethout.grot.util.PageURL.REDIRECT_HOME;
import static nl.zoethout.grot.util.SessionAttributes.USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.config.WebConfig;
import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserServiceImpl;

@DisplayName("TestUserController")
// Enables loading WebApplicationContext
@ExtendWith(SpringExtension.class)
// Will load the web application context
@WebAppConfiguration
// Bootstrap the context that the test will use
@ContextConfiguration(classes = { WebConfig.class })
@ImportResource({"classpath:mockBeans.xml"})
public class TestLoginController_old extends MyUnitTest {
	
	// TODO 43 - moved from nl.zoethout.grot.web.TestUserController.URL_USER_LOGIN
	private static final String URL_LOGIN = "/login";
	
	private MockMvc mockMvc;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private UserController userController;
	TestLoginController_old(final TestInfo inf) {
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

	@Nested
	@DisplayName("Login")
	// TODO 43 - moved from nl.zoethout.grot.web.TestUserController.Login
	class Login {
		Login(final TestInfo inf) {
			printLine("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("rmLoginGet")
		void rmLoginGet(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			Principal usr = null;
			ResultActions ra = mockMvc.perform(get(URL_LOGIN));
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(LOGIN.part()));
			ra.andExpect(request().sessionAttribute(USER, usr));
			// Print parameters
			printParameters(null, null, 0, LOGIN.part());
		}

		@Test
		@DisplayName("rmLoginPost_Admin_GoodPWD")
		void rmLoginPost_Admin_GoodPWD(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			User user = getAdmin();
			MockHttpServletRequest req = new MockHttpServletRequest("POST", URL_LOGIN);
			req.setParameter("username", user.getUserName());
			req.setParameter("password", user.getPassword());
			MockHttpServletRequestBuilder action = post(URL_LOGIN).with(mockRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().is3xxRedirection());
			ra.andExpect(view().name(REDIRECT_HOME.part()));
			// Print parameters
			printParameters(user, null, 0, REDIRECT_HOME.part());
		}

		@Test
		@DisplayName("rmLoginPost_Admin_WrongPWD")
		void rmLoginPost_Admin_WrongPWD(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			User user = getAdmin();
			MockHttpServletRequest req = new MockHttpServletRequest("POST", URL_LOGIN);
			req.setParameter("username", user.getUserName());
			req.setParameter("password", "WrongPWD");
			MockHttpServletRequestBuilder action = post(URL_LOGIN).with(mockRequest(req));
			ResultActions ra = mockMvc.perform(action);
			ra.andExpect(status().isOk());
			ra.andExpect(view().name(LOGIN.part()));
			// Print parameters
			printParameters(user, null, 0, LOGIN.part());
		}
	}
}
