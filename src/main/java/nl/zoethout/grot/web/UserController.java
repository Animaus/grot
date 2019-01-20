package nl.zoethout.grot.web;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserService;
import nl.zoethout.grot.util.CountryCode;
import nl.zoethout.grot.validation.AddressValidator;
import nl.zoethout.grot.validation.UserValidator;

@Controller
@RequestMapping("/user")
public class UserController extends WebController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String rmLoginGet(HttpServletRequest req) {
		// Make sure there's no previous login
		Principal.terminate();
		provider(req).setSAPrincipal(null);
		return PAGE_LOGIN;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String rmLoginPost(Map<String, Object> model, HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User usr = userService.loginUser(username, password);
		if (usr == null) {
			model.put("username", username);
			model.put("error", req.getSession().getAttribute("LOGIN_ERR"));
			return PAGE_LOGIN;
		} else {
			userService.setPrincipal(req, usr);
			return REDIRECT_HOME;
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String rmLogout(HttpServletRequest req) {
		Principal.terminate();
		provider(req).setSAPrincipal(null);
		req.getSession().invalidate();
		return REDIRECT_HOME;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String rmUsers(Model model, HttpServletRequest req) {
		provider(req).setSAFixed(null);
		List<User> profiles = userService.listProfiles();
		provider(req).setSAProfiles(profiles);
		if (checkRole(req, ADMIN)) {
			return PAGE_USERS_VERIFIED;
		} else {
			return PAGE_USERS_UNKNOWN;
		}
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public String rmUser(Model model, HttpServletRequest req, @PathVariable(value = "username") String username) {
		// Chosen member - reading
		User user = userService.readUser(username);
		// Chosen member - session
		provider(req).setSAFixed(user);
		// Chosen member - model (P14-02)
		model.addAttribute("mutable", user);
		// Choosing which page to go
		String page = getAuthorPage(req, "user", username, false);
		// Return chosen page
		return page;
	}

	@RequestMapping(value = "/{username}/edit", method = RequestMethod.GET)
	public String rmUserGet(Model model, HttpServletRequest req, @PathVariable(value = "username") String username) {
		// Chosen member - reading
		User user = userService.readUser(username);
		// Chosen member - session
		provider(req).setSAFixed(user);
		// Chosen member - model (P19-02 / mutable user)
		model.addAttribute("mutable", user);
		// appropriate page
		String page = getAuthorPage(req, "user", username, true);
		return page;
	}

	/**
	 * Saving comes in pairs: one on the session, one on the model. Session contains
	 * original values (if available), model contains mutations. Username and userID
	 * are determined automatically and are never, ever on the model. On new user,
	 * session does not contain any value.
	 *
	 * @param model
	 * @param user
	 * @param bindingResult
	 * @param req
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/{username}/save", method = RequestMethod.POST)
	public String rmUserPost(Model model, @ModelAttribute("mutable") User user, BindingResult bindingResult,
			HttpServletRequest req, @PathVariable(value = "username") String username) {
		// Make sure fields not on model do have value
		User fixedUser = provider(req).getSAFixed();
		user.setUserId(fixedUser.getUserId());
		user.setUserName(fixedUser.getUserName());
		user.setRoles(fixedUser.getRoles());
		user.setDateRegistered(fixedUser.getDateRegistered());
		Address address = user.getAddress();
		address.setUserId(fixedUser.getUserId());
		address.setUserName(fixedUser.getUserName());
		if (!checkRole(req, ADMIN)) {
			user.setEnabled(fixedUser.isEnabled());
		}
		// Change case
		user.changeCase();
		address.changeCase();
		// instantiate validator
		AddressValidator addressValidator = new AddressValidator();
		UserValidator userValidator = new UserValidator(userService, addressValidator);
		// validate
		userValidator.validate(user, bindingResult);
		// appropriate page
		if (bindingResult.hasErrors()) {
			// logging on unexpected error
			logging(model, bindingResult, "");
			// route to appropriate page
			String page = getAuthorPage(req, "user", username, true);
			return page;
		} else {
			// Change authorisation
			editAuthorisation(userService, req, user);
			// save member
			userService.saveUser(user);
			userService.saveAddress(address);
			// route to appropriate page
			return "redirect:/user/" + username;
		}
	}

	private void logging(Model model, BindingResult bindingResult, String message) {
		if (message == null || message.equals("")) {
			message = "";
		}
		List<ObjectError> errors = bindingResult.getAllErrors();
		for (ObjectError error : errors) {
			if (error.getDefaultMessage() != null) {
				message += "<br><br><b>getCode</b> : " + error.getCode();
				message += "<br><b>getDefaultMessage</b> : " + error.getDefaultMessage();
				message += "<br><b>getObjectName</b> : " + error.getObjectName();
				message += "<br>" + error.toString();
			}
		}
		model.addAttribute("message", message);
	}

	@ModelAttribute("roles")
	public Map<String, String> maRoles(HttpServletRequest req) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		Principal principal = provider(req).getSAPrincipal();
		if (principal == null) {
			return null;
		} else if (principal.hasRole(ADMIN)) {
			for (Role role : userService.readRoles()) {
				result.put(role.getRoleName(), role.getRoleDesc());
			}
			return result;
		} else {
			return null;
		}
	}

	@ModelAttribute("countries")
	public CountryCode[] maCountries(HttpServletRequest req) {
		return CountryCode.values();
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		webDataBinder.registerCustomEditor(Role.class, new RoleEditor());
	}

	// https://www.logicbig.com/tutorials/spring-framework/spring-core/property-editors.html
	private class RoleEditor extends PropertyEditorSupport {
		@Override
		public void setAsText(String roleName) throws IllegalArgumentException {
			setValue(userService.readRole(roleName));
		}
	}
}
