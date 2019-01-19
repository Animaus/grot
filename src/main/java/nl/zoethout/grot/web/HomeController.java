package nl.zoethout.grot.web;

import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // When this is @RestController: no JSP but plain text displayed
public class HomeController {

	private static final String DEFAULT_MESSAGE = "message";
	private static final String DISPATCHER = "home";

	@RequestMapping(value = { "/", "/home", "/welcome", "/index" }, method = RequestMethod.GET)
	public String rmWelcome(Map<String, Object> model, HttpServletRequest req) {
		setAttributes(req);
		model.put(DEFAULT_MESSAGE, req.getSession().getAttribute("WELCOME"));
		return DISPATCHER;
	}

	private void setAttributes(HttpServletRequest req) {
		HttpSession ses = req.getSession();
		ResourceBundle bundle = ResourceBundle.getBundle("text");
		setText(ses, bundle, "WELCOME");
		setText(ses, bundle, "LOGIN_TXT");
		setText(ses, bundle, "LOGIN_OFF");
		setText(ses, bundle, "LOGIN_YES");
		setText(ses, bundle, "LOGIN_OUT");
		setText(ses, bundle, "LOGIN_BTN");
		setText(ses, bundle, "LOGIN_USR");
		setText(ses, bundle, "LOGIN_PWD");
		setText(ses, bundle, "LOGIN_ERR");
		bundle = ResourceBundle.getBundle("link");
		setText(ses, bundle, "LNK_USR_HOME");
		setText(ses, bundle, "LNK_USR_LOGIN");
	}

	private void setText(HttpSession ses, ResourceBundle bundle, String key) {
		String value = bundle.getString(key);
		ses.setAttribute(key, value);
	}

}