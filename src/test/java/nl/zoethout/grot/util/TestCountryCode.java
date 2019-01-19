package nl.zoethout.grot.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import nl.zoethout.grot.MyUnitTest;

@DisplayName("TestCountryCode")
public class TestCountryCode extends MyUnitTest {
	TestCountryCode(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@Nested
	@DisplayName("GetCode")
	class GetCode {
		GetCode(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("Zimbabwe")
		void test1(TestInfo inf) {
			print(inf.getDisplayName());
			assertTrue(CountryCode.getCode("Zimbabwe").equalsIgnoreCase("ZW"),
					() -> "Country \"Zimbabwe\" should have had code \"ZW\"...");
		}
	}

	@Nested
	@DisplayName("GetName")
	class GetName {
		GetName(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("Andorra")
		void test1(TestInfo inf) {
			print(inf.getDisplayName());
			assertTrue(CountryCode.getName("AD").equalsIgnoreCase("Andorra"),
					() -> "Code \"AD\" should have had country \"Andorra\"...");
		}
	}
}
