package nl.zoethout.grot.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import nl.zoethout.grot.MyUnitTest;

@DisplayName("TestCountryCode")
public class TestCountryCode extends MyUnitTest {
	TestCountryCode(TestInfo inf) {
		System.out.println(inf.getDisplayName());
		prefix = "\t";
	}

	@Test
	@DisplayName("getCode (Zimbabwe)")
	void getCode(TestInfo inf) {
		printLine("- " + inf.getDisplayName());
		assertTrue(CountryCode.getCode("Zimbabwe").equalsIgnoreCase("ZW"),
				() -> "Country \"Zimbabwe\" should have had code \"ZW\"...");
	}

	@Test
	@DisplayName("getName (Andorra)")
	void getName(TestInfo inf) {
		printLine("- " + inf.getDisplayName());
		assertTrue(CountryCode.getName("AD").equalsIgnoreCase("Andorra"),
				() -> "Code \"AD\" should have had country \"Andorra\"...");
	}
}
