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
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.zoethout.grot.mytest.MyTestCases;
import nl.zoethout.grot.testconfig.TestBeans;
import nl.zoethout.grot.testconfig.TestSecurityConfig;

@DisplayName("TestHomeController")
public class TestHomeController extends MyTestCases {

	private static final String MSG = "GROT is een afkorting voor Generieke Re-enactment Organisatie Toepassing.";
	private static final String VIEW_NAME = "jspHome";

	TestHomeController(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@Nested
	@DisplayName("HomeIntegration")
	@ExtendWith(SpringExtension.class) // Enables loading WebApplicationContext
	@ContextConfiguration(classes = { TestSecurityConfig.class, TestBeans.class })
	@WebAppConfiguration // Will load the web application context
	class HomeIntegration {
		private MockMvc mockMvc;

		@Autowired
		private WebApplicationContext wac;

		HomeIntegration(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@BeforeEach
		void setup() {
			if (mockMvc == null) {
				mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
			}
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

	@Nested
	@DisplayName("HomeUnit")
	@ContextConfiguration(classes = { TestSecurityConfig.class, TestBeans.class })
	@WebAppConfiguration // Will load the web application context
	class HomeUnit {
		private MockMvc mockMvc;

		@InjectMocks
		private HomeController homeController;

		HomeUnit(final TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@BeforeEach
		void setup() {
			if (mockMvc == null) {
				// Initializes objects annotated with @Mock
				MockitoAnnotations.initMocks(this);
				// Initializes MockMvc without loading Spring configuration
				mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
			}
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
