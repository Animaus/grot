package nl.zoethout.grot.spring;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.AbstractList;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;
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

@DisplayName("TestSpringSnippets")
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

	class MyList extends AbstractList<String> {

		@Override
		public String get(final int index) {
			return null;
		}

		@Override
		public int size() {
			return 1;
		}

		@Override
		public void add(int index, String element) {
			// no-op
		}

		final public int finalMethod() {
			return 0;
		}
	}

//	@RunWith(MockitoJUnitRunner.class)
	@Nested
	@DisplayName("MockitoVoidMethods")
	public class MockitoVoidMethods {

		@Test
		public void whenAddCalledVerified() {
			MyList mockVoid = mock(MyList.class);
			mockVoid.add(0, "");
			verify(mockVoid, times(1)).add(0, "");
		}

		// @Test //(expected = Exception.class)
		// public void givenNull_addThrows() {
		// MyList mockVoid = mock(MyList.class);
		// doThrow().when(mockVoid).add(isA(Integer.class), isNull());
		// mockVoid.add(0, null);
		// }

		@Test
		public void whenAddCalledValueCaptured() {
			MyList mockVoid = mock(MyList.class);
			ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
			doNothing().when(mockVoid).add(any(Integer.class), valueCapture.capture());
			mockVoid.add(0, "captured");
			assertEquals("captured", valueCapture.getValue());
		}

		@Test
		public void whenAddCalledAnswered() {
			MyList mockVoid = mock(MyList.class);
			doAnswer((Answer<Void>) invocation -> {
				Object arg0 = invocation.getArgument(0);
				Object arg1 = invocation.getArgument(1);

				// do something with the arguments here
				assertEquals(3, arg0);
				assertEquals("answer me", arg1);

				return null;
			}).when(mockVoid).add(any(Integer.class), any(String.class));
			mockVoid.add(3, "answer me");
		}

		@Test
		public void whenAddCalledRealMethodCalled() {
			MyList mockVoid = mock(MyList.class);
			doCallRealMethod().when(mockVoid).add(any(Integer.class), any(String.class));
			mockVoid.add(1, "real");
			verify(mockVoid, times(1)).add(1, "real");
		}
	}

}
