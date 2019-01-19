package nl.zoethout.grot.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.zoethout.grot.MyUnitTest;

@DisplayName("TestHomeController")
@WebMvcTest(HomeController.class)
@ExtendWith(SpringExtension.class)
public class TestHomeController extends MyUnitTest {
	private MockMvc mockMvc;
	@Autowired
	WebApplicationContext wac;

	TestHomeController(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		if (mockMvc == null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		}
	}

	@Nested
	@DisplayName("GetPage")
	class GetPage {
		private static final String MSG = "GROT is een afkorting voor Generieke Re-enactment Organisatie Toepassing.";
		private static final String URL = "/WEB-INF/jsp/home.jsp";

		GetPage(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("homepage")
		void homepage(TestInfo inf) throws Exception {
			assertNotNull(mockMvc);
			String[] urls = { "/", "/home", "/welcome", "/index" };
			for (String url : urls) {
				print(url);
				ResultActions ra = mockMvc.perform(get(url));
				ra.andExpect(status().isOk());
				ra.andExpect(model().attribute("message", MSG));
				ra.andExpect(forwardedUrl(URL));
				checkAttributes(ra);
			}
		}

		/**
		 * Checks the contents of the sessionattributes
		 * 
		 * @param ra
		 * @throws Exception
		 */
		private void checkAttributes(ResultActions ra) throws Exception {
			ResourceBundle bundle = ResourceBundle.getBundle("text");
			ra.andExpect(request().sessionAttribute("WELCOME", bundle.getString("WELCOME")));
			ra.andExpect(request().sessionAttribute("LOGIN_MSG", bundle.getString("LOGIN_MSG")));
			ra.andExpect(request().sessionAttribute("LOGIN_NON", bundle.getString("LOGIN_NON")));
			ra.andExpect(request().sessionAttribute("LOGIN_YES", bundle.getString("LOGIN_YES")));
			ra.andExpect(request().sessionAttribute("LOGIN_OUT", bundle.getString("LOGIN_OUT")));
			ra.andExpect(request().sessionAttribute("LOGIN_ONN", bundle.getString("LOGIN_ONN")));
			ra.andExpect(request().sessionAttribute("LOGIN_USR", bundle.getString("LOGIN_USR")));
			ra.andExpect(request().sessionAttribute("LOGIN_PWD", bundle.getString("LOGIN_PWD")));
			ra.andExpect(request().sessionAttribute("LOGIN_ERR", bundle.getString("LOGIN_ERR")));
			bundle = ResourceBundle.getBundle("link");
			ra.andExpect(request().sessionAttribute("LNK_USR_HOME", bundle.getString("LNK_USR_HOME")));
			ra.andExpect(request().sessionAttribute("LNK_USR_LOGIN", bundle.getString("LNK_USR_LOGIN")));
			ra.andExpect(request().sessionAttribute("LNK_USR_MEMBERS", bundle.getString("LNK_USR_MEMBERS")));
		}
	}
}
