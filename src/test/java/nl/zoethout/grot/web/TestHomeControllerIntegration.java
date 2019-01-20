package nl.zoethout.grot.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
// TODO 46 - This does not work...!
public class TestHomeControllerIntegration extends MyUnitTest {

	private MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext wac;

	TestHomeControllerIntegration(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		if (mockMvc == null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		}
	}

	@Nested
	@DisplayName("Home")
	class Home {
		private static final String MSG = "GROT is een afkorting voor Generieke Re-enactment Organisatie Toepassing.";
		private static final String VIEW_NAME = "home";

		Home(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("rmHome")
		void rmHome(TestInfo inf) throws Exception {
			String[] urls = { "/", "/home", "/welcome", "/index" };
			for (String url : urls) {
				printLine(url);
				ResultActions ra = mockMvc.perform(get(url));
				ra.andExpect(status().isOk());
				ra.andExpect(model().attribute("message", MSG));
				ra.andExpect(view().name(VIEW_NAME));
				assertAttributes(ra);
			}
		}
	}
}
