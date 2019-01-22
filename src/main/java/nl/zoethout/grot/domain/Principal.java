package nl.zoethout.grot.domain;

public final class Principal extends UserWrapper {

	// 1 - a private static variable to store the instance (usually final, but here it's not)
	// Store one instance (this is the singleton)
	private static Principal USER ; // Lazy initialization: create on first use

	// 2 - a private constructor (no callers can instantiate directly)
<<<<<<< HEAD
	private Principal(User user) {
=======
	private Principal(final User user) {
>>>>>>> develop/Grot.190119.1252
		this.user = user;
	}

	// 3 - public static method for callers to get a reference to the instance
<<<<<<< HEAD
	public static Principal getUser(User user) {
=======
	public static Principal getUser(final User user) {
>>>>>>> develop/Grot.190119.1252
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
