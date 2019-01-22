package nl.zoethout.grot.util;

import static nl.zoethout.grot.util.DatetimeHelper.getDayNumber;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import nl.zoethout.grot.mytest.MyTestCases;

@DisplayName("TestDatetimeHelper")
public class TestDatetimeHelper extends MyTestCases {
	TestDatetimeHelper(TestInfo inf) {
		printLine(inf.getDisplayName());
	}

	@Nested
	@DisplayName("GetKey")
	class GetKey {
		GetKey(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("2016-01-01")
		void test1(TestInfo inf) {
			printLine(inf.getDisplayName());
			LocalDateTime dtLocal = LocalDateTime.of(2016, 01, 01, 0, 0, 0);
			String key = DatetimeHelper.getKey(dtLocal);
			printLine("Key=" + key);
			assertTrue(key.equals("2016-53"), () -> "Key \"" + key + "\" should have been \"2016-53\"...");
		}

		@Test
		@DisplayName("2016-01-06")
		void test2(TestInfo inf) {
			printLine(inf.getDisplayName());
			LocalDateTime dtLocal = LocalDateTime.of(2016, 01, 06, 0, 0, 0);
			String key = DatetimeHelper.getKey(dtLocal);
			printLine("Key=" + key);
			assertTrue(key.equals("2016-01"), () -> "Key \"" + key + "\" should have been \"2016-01\"...");
		}

		@Test
		@DisplayName("1965-03-18")
		void test3(TestInfo inf) {
			printLine(inf.getDisplayName());
			LocalDateTime dtLocal = LocalDateTime.of(1965, 03, 18, 0, 0, 0);
			String key = DatetimeHelper.getKey(dtLocal);
			printLine("Key=" + key);
			assertTrue(key.equals("1965-11"), () -> "Key \"" + key + "\" should have been \"1965-11\"...");
		}

	}

	@Nested
	@DisplayName("GetDayNumber")
	public class GetDayNumber {
		GetDayNumber(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("2016-01-04")
		void test1(TestInfo inf) {
			printLine(inf.getDisplayName());
			LocalDateTime dtLocal = LocalDateTime.of(2016, 01, 04, 0, 0, 0);
			int key = getDayNumber(dtLocal);
			printLine("Daynumber=" + key);
			assertTrue(key == 1, () -> "Key \"" + key + "\" should have been \"1\"...");
		}

		@Test
		@DisplayName("2016-01-10")
		void test2(TestInfo inf) {
			printLine(inf.getDisplayName());
			LocalDateTime dtLocal = LocalDateTime.of(2016, 01, 10, 0, 0, 0);
			int key = getDayNumber(dtLocal);
			printLine("Daynumber=" + key);
			assertTrue(key == 7, () -> "Key \"" + key + "\" should have been \"7\"...");
		}

		@Test
		@DisplayName("1965-03-18")
		void test3(TestInfo inf) {
			printLine(inf.getDisplayName());
			LocalDateTime dtLocal = LocalDateTime.of(1965, 03, 18, 0, 0, 0);
			int key = getDayNumber(dtLocal);
			printLine("Daynumber=" + key);
			assertTrue(key == 4, () -> "Key \"" + key + "\" should have been \"4\"...");
		}
	}

}
