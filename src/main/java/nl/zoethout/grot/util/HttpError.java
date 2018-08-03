package nl.zoethout.grot.util;

import java.util.HashMap;
import java.util.Map;

public enum HttpError {

	// 4xx Client errors

	Err400("Bad Request"),
	Err401("Unauthorized"),
	Err402("Payment Required"),
	Err403("Forbidden"),
	Err404("Not Found"),
	Err405("Method Not Allowed"),
	Err406("Not Acceptable"),
	Err407("Proxy Authentication Required"),
	Err408("Request Timeout"),
	Err409("Conflict"),
	Err410("Gone"),
	Err411("Length Required"),
	Err412("Precondition Failed"),
	Err413("Payload Too Large"),
	Err414("URI Too Long"),
	Err415("Unsupported Media Type"),
	Err416("Range Not Satisfiable"),
	Err417("Expectation Failed"),
	Err418("I'm a teapot"),
	Err421("Misdirected Request"),
	Err422("Unprocessable Entity"),
	Err423("Locked"),
	Err424("Failed Dependency"),
	Err426("Upgrade Required"),
	Err428("Precondition Required"),
	Err429("Too Many Requests"),
	Err431("Request Header Fields Too Large"),
	Err451("Unavailable For Legal Reasons"),

	// 5xx Server errors

	Err500("Internal Server Error"),
	Err501("Not Implemented"),
	Err502("Bad Gateway"),
	Err503("Service Unavailable"),
	Err504("Gateway Timeout"),
	Err505("HTTP Version Not Supported"),
	Err506("Variant Also Negotiates"),
	Err507("Insufficient Storage"),
	Err508("Loop Detected"),
	Err510("Not Extended"),
	Err511("Network Authentication Required");

    private final String message;
    
	private static final Map<String, HttpError> messageMap = new HashMap<String, HttpError>();
	private static final Map<String, String> codeMap = new HashMap<String, String>();
    
    static {
        for (final HttpError cc : values()) {
            messageMap.put(cc.getName(), cc);
            codeMap.put(cc.toString(), cc.getName());
        }
    }
    
    private HttpError(final String name) {
		this.message = name;
	}

    public String getName() {
		return message;
	}
    
    public static String getMessage(int code) {
    	return codeMap.get("Err" + code);
	}
    
    public static String getCode(String name) {
    	return messageMap.get(name).toString();
    }

	public static void main(String[] args) {
		System.out.println(HttpError.getCode("I'm a teapot"));
		System.out.println(HttpError.getMessage(500));
	}

}
