package nl.zoethout.grot.util;

public enum PageURL {
	HOME("home"), //
	LOGIN("logon"),
	POC("poc"), //
	ERROR("error"), //
	TEST("test"), //
	REDIRECT_HOME("redirect:/"), //
	REDIRECT_REPOSITORY("redirect:repository"), //
	USERS_UNKNOWN("users_unknown"), // Show all users (when logged out)
	USERS_VERIFIED("users_verified"), // Show all users (when logged in)
	USER_UNKNOWN("user_unknown"), // Show single user (when logged out) - read only
	USER_VERIFIED("user_verified"), // Show single user (when logged in) - read only
	USER_VERIFIED_WRITE("user_verified_write"); // Show single user (when logged in) - write

	private final String part;

	private PageURL(final String part) {
		this.part = part;
	}

	public String part() {
		return part;
	}

	public static void main(String[] args) {
		System.out.println(PageURL.POC.part());
		System.out.println(PageURL.POC.toString());
	}

}
