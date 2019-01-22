package nl.zoethout.grot.web;

import static nl.zoethout.grot.util.PageURL.*;

import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.zoethout.grot.util.AttributeProvider;
import nl.zoethout.grot.util.AttributeProviderImpl;

@Controller // When this is @RestController: no JSP but plain text displayed
public class HomeController extends WebController {

	private static final String DEFAULT_MESSAGE = "message";

	@RequestMapping(value = { "/", "/home", "/welcome", "/index" }, method = RequestMethod.GET)
	public String rmHome(Map<String, Object> model, final HttpServletRequest req) {
		setAttributes(req);
		AttributeProvider attr = AttributeProviderImpl.getProvider(req);
		attr.setSAError(null);
		model.put(DEFAULT_MESSAGE, req.getSession().getAttribute("WELCOME"));
		return HOME.part();
	}

	private void setAttributes(final HttpServletRequest req) {
		HttpSession ses = req.getSession();
		ResourceBundle bundle = ResourceBundle.getBundle("text");
		setText(ses, bundle, "WELCOME");
		setText(ses, bundle, "LOGIN_MSG");
		setText(ses, bundle, "LOGIN_NON");
		setText(ses, bundle, "LOGIN_YES");
		setText(ses, bundle, "LOGIN_OUT");
		setText(ses, bundle, "LOGIN_ONN");
		setText(ses, bundle, "LOGIN_USR");
		setText(ses, bundle, "LOGIN_PWD");
		setText(ses, bundle, "LOGIN_ERR");
		bundle = ResourceBundle.getBundle("link");
		setText(ses, bundle, "LNK_USR_HOME");
		setText(ses, bundle, "LNK_USR_LOGIN");
		setText(ses, bundle, "LNK_USR_MEMBERS");
	}

	private void setText(HttpSession ses, final ResourceBundle bundle, final String key) {
		String value = bundle.getString(key);
		ses.setAttribute(key, value);
	}

}