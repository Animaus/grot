package nl.zoethout.grot.web;

<<<<<<< HEAD
=======
import static nl.zoethout.grot.util.PageURL.ERROR;

>>>>>>> develop/Grot.190119.1252
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.zoethout.grot.util.HttpError;

@Controller
public class CustomErrorController extends WebController implements ErrorController {

	private static final String PATH = "/error";

	@RequestMapping(PATH)
	public String handleError(Map<String, Object> model, HttpServletRequest req) {
		
		// Timestamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ (z)");
		model.put("timestamp", sdf.format(System.currentTimeMillis()));
		
		// HTTP statuscode
		Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
		model.put("status", statusCode);
		
		// HTTP status message
		model.put("message", HttpError.getMessage(statusCode));
		
		// URI
		String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}	
		model.put("requestUri", requestUri);
		
		// Referring page
		String referrer = req.getHeader("referer"); // Yes, with the legendary misspelling...
		model.put("referrer", referrer);
		
		// Exception
		Exception exception = (Exception) req.getAttribute("javax.servlet.error.exception");
		if (exception == null) {
			model.put("exception", "N/A");
		} else {
<<<<<<< HEAD
//			exception.getCause().getStackTrace();
=======
>>>>>>> develop/Grot.190119.1252
			model.put("exception", exception.getMessage());
			String stackTrace = "";
			for (StackTraceElement ste : exception.getCause().getStackTrace()) {
				stackTrace += ste.toString() + "<br>";
			}
			model.put("trace", "<pre>" + stackTrace + "</pre>");
<<<<<<< HEAD

//			model.put("exception", exception.getMessage());
//			String stackTrace = "";
//			for (StackTraceElement ste : exception.getStackTrace()) {
//				stackTrace += ste.toString() + "<br>";
//			}
//			model.put("trace", "<pre>" + stackTrace + "</pre>");
		}
		
		// To page
		return PAGE_ERROR;
=======
		}
		
		// To page
		return ERROR.part();
>>>>>>> develop/Grot.190119.1252
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
