package nl.zoethout.grot.samples;

public enum Roles {
	ADMIN("admin"), CANDIDATE("candidate");

	private Roles(String role) {
		this.value = role;
	}

	private final String value;

	public String value() {
		return value.toLowerCase();
	}

	public String toString() {
		return value.toLowerCase();
	}

}
