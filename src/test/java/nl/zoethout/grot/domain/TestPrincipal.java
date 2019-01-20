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

	private TestPrincipal(TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	private void init(User usr) {
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
		HasRole(TestInfo inf) {
			System.out.println("- " + inf.getDisplayName());
			prefix = "\t";
		}

		@Test
		@DisplayName("ADMIN")
		void testAdmin(TestInfo inf) {
			println(inf.getDisplayName());
			init(getAdmin());
			println(p.getUser());
			assertTrue(p.hasRole(ADM), () -> "User " + p.getUserName() + " should have role \"Admin\"...");
		}

		@Test
		@DisplayName("USER")
		void testUser(TestInfo inf) {
			println(inf.getDisplayName());
			init(getUser());
			println(p.getUser());
			assertTrue(p.hasRole(USR), () -> "User " + p.getUserName() + " should have role \"User\"...");
		}

		@Test
		@DisplayName("DISABLED")
		void testDisabled(TestInfo inf) {
			println(inf.getDisplayName());
			init(getDisabled());
			println(p.getUser());
			assertFalse(p.hasRole(""), () -> "User " + p.getUserName() + " should have no roles...");
		}

		@Test
		@DisplayName("GUEST")
		@Disabled("Disabled : GUEST")
		void testGuest(TestInfo inf) {
			// What if user is not logged in?
		}
	}

}
