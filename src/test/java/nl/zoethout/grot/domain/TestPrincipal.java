package nl.zoethout.grot.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import nl.zoethout.grot.MyUnitTest;

@DisplayName("TestPrincipal")
public class TestPrincipal extends MyUnitTest {
	private Principal p;

	private TestPrincipal(final TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	private void init(final User usr) {
		p = Principal.getUser(usr);
	}

	@SuppressWarnings("static-access")
	@AfterEach
	private void terminate() {
		p.terminate();
	}

	@Nested
	@DisplayName("HasRole")
	class HasRole {
		HasRole(final TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("ADMIN")
		void testAdmin(final TestInfo inf) {
			printLine(inf.getDisplayName());
			init(getAdmin());
			printLine(p.getUser());
			assertTrue(p.hasRole(ADM), () -> "User " + p.getUserName() + " should have role \"Admin\"...");
		}

		@Test
		@DisplayName("USER")
		void testUser(final TestInfo inf) {
			printLine(inf.getDisplayName());
			init(getUser());
			printLine(p.getUser());
			assertTrue(p.hasRole(USR), () -> "User " + p.getUserName() + " should have role \"User\"...");
		}

		@Test
		@DisplayName("DISABLED")
		void testDisabled(final TestInfo inf) {
			printLine(inf.getDisplayName());
			init(getDisabled());
			printLine(p.getUser());
			assertFalse(p.hasRole(""), () -> "User " + p.getUserName() + " should have no roles...");
		}

		@Test
		@DisplayName("GUEST")
		@Disabled("Disabled : GUEST")
		void testGuest(final TestInfo inf) {
			// What if user is not logged in?
		}
	}

}
