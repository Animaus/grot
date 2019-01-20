package nl.zoethout.grot.web;

import static nl.zoethout.grot.util.PageURL.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserService;

@Controller
public class LoginController extends WebController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(final HttpServletRequest req) {
		Principal.terminate();
		provider(req).setSAPrincipal(null);
		devInfo(strClass, "login", "Principal terminated and nullified...");
		return LOGIN.part();
	}

	@RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
	public String loginSuccess(final HttpServletRequest req) {
		java.security.Principal principal = req.getUserPrincipal();
		String username = principal.getName();
		User usr = userService.readUser(username);
		userService.setPrincipal(req, usr);
		devInfo(strClass, "loginSuccess", "User read from UserPrincipal...");
		return REDIRECT_HOME.part();
	}

}
