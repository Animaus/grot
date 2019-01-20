package nl.zoethout.grot.spring;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nl.zoethout.grot.MyUnitTest;
import nl.zoethout.grot.web.HomeController;

@DisplayName("TestHomeController")
@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
public class TestSpringSnippets extends MyUnitTest {
	private MockMvc mockMvc;
	@Autowired
	WebApplicationContext wac;

	TestSpringSnippets(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		if (mockMvc == null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		}
	}

	@Nested
	@DisplayName("Spring")
	class Spring {
		Spring(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		public void shouldReturnDefaultMessage() throws Exception {
			ResultActions ra = mockMvc.perform(get("/"));
			ra.andDo(print());
			ra.andExpect(status().isOk());
			ra.andExpect(content().string(containsString("Hello World")));
		}

		/**
		 * Verify Test Configuration
		 * https://www.baeldung.com/integration-testing-in-spring
		 */
		@Test
		public void givenWac_whenServletContext_thenItProvidesGreetController() {
			ServletContext servletContext = wac.getServletContext();
			assertNotNull(servletContext);
			assertTrue(servletContext instanceof MockServletContext);
			assertNotNull(wac.getBean("homeController"));
		}

		/**
		 * Verify View Name https://www.baeldung.com/integration-testing-in-spring
		 * https://www.baeldung.com/integration-testing-in-spring
		 */
		@Test
		public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
			ResultActions ra = mockMvc.perform(get("/home"));
			ra.andDo(print());
			ra.andExpect(view().name("home"));
		}
	}
}
