package nl.zoethout.grot.other;

/*
 * Example 2-6. src/main/java/com/example/BackendDTO.java
 */
public class DataTransfer {
	private String greeting;
	private long time;
	private String ip;

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
