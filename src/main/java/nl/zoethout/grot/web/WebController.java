package nl.zoethout.grot.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserService;
import nl.zoethout.grot.util.AttributeProvider;
import nl.zoethout.grot.util.AttributeProviderImpl;

public abstract class WebController {

	// TODO 99 - Constanten naar enumeratie? Wegens geen "protected static final" en kunnen itereren...
	protected static final String PAGE_HOME = "home";
	protected static final String PAGE_LOGIN = "login";	
	protected static final String PAGE_POC = "poc";
	protected static final String PAGE_ERROR = "error";
	protected static final String PAGE_TEST = "test";
	protected static final String REDIRECT_HOME = "redirect:/";
	protected static final String REDIRECT_REPOSITORY = "redirect:repository";
	
	protected static final String PAGE_USERS_UNKNOWN = "users_unknown"; // Show all users (when logged out)
	protected static final String PAGE_USERS_VERIFIED = "users_verified"; // Show all users (when logged in)
	protected static final String PAGE_USER_UNKNOWN = "user_unknown"; // Show single user (when logged out) - read only
	protected static final String PAGE_USER_VERIFIED = "user_verified"; // Show single user (when logged in) - read only
	protected static final String PAGE_USER_VERIFIED_WRITE = "user_verified_write"; // Show single user (when logged in) - write
	
	protected static final String ADMIN = "ROLE_ADMIN";
	protected static final String USER = "ROLE_USER";

	protected void gotoURL(HttpServletRequest req, HttpServletResponse res, String URL) {
		String context = req.getContextPath();
		try {
			res.sendRedirect(context + URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected AttributeProvider provider(HttpServletRequest req) {
		AttributeProvider provider = AttributeProviderImpl.getProvider(req);
		return provider;
	}

	protected String getAuthorPage(HttpServletRequest req,
			String pagename, String username, boolean isEdit) {
		
		pagename += "_";
		
		String write = "";
		if (isEdit) {
			write = "_write";
		}
		
		Principal principal = provider(req).getSAPrincipal();
		
		if (principal == null) {
			// Not logged on
			pagename += "unknown";
		} else {
			String principalName = principal.getUserName();
			if (checkRole(req, "ROLE_ADMIN")) {
				// Admin
				pagename += "verified" + write;
			} else if (principalName.equals(username)) {
				// Profile-owner
				pagename += "verified" + write;
			} else {
				// Neither admin nor profile-owner
				pagename += "unknown";
			}
		}
		
		return pagename;
	}

	protected boolean isAuthor(HttpServletRequest req, String username) {
		Principal principal = provider(req).getSAPrincipal();
		if (principal == null) {
			// Not logged on
			return false;
		} else if (principal.getUserName().equals(username)) {
			// Principal is owner
			return true;
		} else if(principal.hasRole(ADMIN)) {
			// Principal is admin
			return true;
		} else {
			// Neither admin nor owner
			return false;
		}
	}

	protected boolean checkRole(HttpServletRequest req, String role) {
		Principal principal = provider(req).getSAPrincipal();
		if (principal == null) {
			return false;
		} else {
			return principal.hasRole(role);
		}
	}

	// TODO 25 - Users - save member with values
	protected void editAuthorisation(UserService userService, HttpServletRequest req, User user) {
		Principal principal = provider(req).getSAPrincipal();
		if (principal != null && principal.hasRole(ADMIN)) {
			// Change allowed
			String[] roleNames = req.getParameterValues("roles");
			if (roleNames == null) {
				// New member
				setAuthorisation(userService, user);
			} else {
				// Remove old roles
				user.getRoles().clear();
				// Add new roles
				for (String roleName : roleNames) {
					user.getRoles().add(userService.readRole(roleName));
				}
			}
		}
	}

	// TODO 25 - Users - save member with values
	protected void setAuthorisation(UserService userService, User user) {
		// New user
		Role role = userService.readRole("ROLE_USER");
		user.getRoles().add(role);
		user.setEnabled(true);
	}

}
