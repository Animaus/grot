package nl.zoethout.grot.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.zoethout.grot.AttributeProvider;
import nl.zoethout.grot.AttributeProviderImpl;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserService;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/user")
public class UserController extends WebController {

	private static final String DISPATCHER = "poc";
	private static final String DEFAULT_MESSAGE = "message";

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String rmLogin() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void rmLogout(HttpServletRequest req, HttpServletResponse res) {
		AttributeProvider attr = AttributeProviderImpl.getProvider(req);
		attr.setSAUser(null);
		req.getSession().invalidate();
		gotoURL(req, res, "/");
	}

	// TODO 16 - Pseudo security
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String rmCheck(Map<String, Object> model, HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		User usr = userService.loginUser(username, password);

		if (usr == null) {
			model.put(DEFAULT_MESSAGE, "Not found!");
		} else {
			model.put(DEFAULT_MESSAGE, username + " " + password);
			userService.setGenialUser(req, usr);
		}

		return "poc";
	}
}
