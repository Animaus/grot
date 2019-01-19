package nl.zoethout.grot.domain;

public final class Principal extends UserWrapper {

	// 1 - a private static variable to store the instance (usually final, but here it's not)
	// Store one instance (this is the singleton)
	private static Principal USER ; // Lazy initialization: create on first use

	// 2 - a private constructor (no callers can instantiate directly)
	private Principal(User user) {
		this.user = user;
	}

	// 3 - public static method for callers to get a reference to the instance
	public static Principal getUser(User user) {
		if (USER == null) {
			synchronized (Principal.class) {
				if (USER == null) {
					USER = new Principal(user);
				}
			}
		}
		return USER;
	}

	// Make sure there's no previous login
	public static void terminate() {
		USER = null;
	}
}
