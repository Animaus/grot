package bookstoread;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class NumericCalculatorTest {
	private static final String MY_FIRST_TEST = "My First Test";

	@Test
	@DisplayName(MY_FIRST_TEST)
	void myFirstTest(TestInfo testInfo) {
		NumericCalculator calculator = new NumericCalculator();
		assertEquals(2, calculator.add(1, 1), "1 + 1 = 2");
		assertEquals(MY_FIRST_TEST, testInfo.getDisplayName(), () -> "TestInfo is injected correctly");
	}
}
