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
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.zoethout.grot.config.WebConfig;
import nl.zoethout.grot.mytest.MyTestCases;

@DisplayName("TestHomeController")
@ContextConfiguration(classes = { WebConfig.class })
//TODO 46 - This is a workaround...!
public class TestHomeController extends MyTestCases {

	private MockMvc mockMvc;

	@InjectMocks
	private HomeController homeController;

	@BeforeEach
	void setup() {
		if (mockMvc == null) {
			// Initializes objects annotated with @Mock
			MockitoAnnotations.initMocks(this);
			// Initializes MockMvc without loading Spring configuration
			mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
		}
	}

	TestHomeController(final TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@Nested
	@DisplayName("Home")
	class Home {
		private static final String MSG = "GROT is een afkorting voor Generieke Re-enactment Organisatie Toepassing.";
		private static final String VIEW_NAME = "jspHome";

		Home(final TestInfo inf) {
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
