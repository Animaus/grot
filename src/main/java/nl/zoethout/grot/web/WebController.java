package nl.zoethout.grot.web;

import java.io.IOException;
<<<<<<< HEAD
=======
import java.util.function.BinaryOperator;
>>>>>>> develop/Grot.190119.1252

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserService;
import nl.zoethout.grot.util.AttributeProvider;
import nl.zoethout.grot.util.AttributeProviderImpl;

public abstract class WebController {
<<<<<<< HEAD

	// TODO JAVA 8 - Constanten naar enumeratie? Wegens geen "protected static final" en kunnen itereren...
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
=======
	protected String strClass = getClass().getSimpleName();
	protected static final String ADM = "ROLE_ADMIN";
	protected static final String USR = "ROLE_USER";
	
	protected final static BinaryOperator<String> merger = (key, val) -> {
		throw new IllegalStateException(String.format("Duplicate key \"%s\"", key));
	};

	protected void gotoURL(final HttpServletRequest req, final HttpServletResponse res, final String URL) {
>>>>>>> develop/Grot.190119.1252
		String context = req.getContextPath();
		try {
			res.sendRedirect(context + URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
	protected AttributeProvider provider(HttpServletRequest req) {
=======
	protected AttributeProvider provider(final HttpServletRequest req) {
>>>>>>> develop/Grot.190119.1252
		AttributeProvider provider = AttributeProviderImpl.getProvider(req);
		return provider;
	}

<<<<<<< HEAD
	protected String getAuthorPage(HttpServletRequest req,
			String pagename, String username, boolean isEdit) {
		
		pagename += "_";
		
=======
	protected String getAuthorPage(final HttpServletRequest req, final String pagename, final String username,
			final boolean isEdit) {
		String result = pagename;
		result += "_";
>>>>>>> develop/Grot.190119.1252
		String write = "";
		if (isEdit) {
			write = "_write";
		}
<<<<<<< HEAD
		
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
=======
		Principal principal = provider(req).getSAPrincipal();
		if (principal == null) {
			// Not logged on
			result += "unknown";
		} else {
			String principalName = principal.getUserName();
			if (checkRole(req, ADM)) {
				// Admin
				result += "verified" + write;
			} else if (principalName.equals(username)) {
				// Profile-owner
				result += "verified" + write;
			} else {
				// Neither admin nor profile-owner
				result += "unknown";
			}
		}
		return result;
	}

	protected boolean isAuthor(final HttpServletRequest req, final String username) {
>>>>>>> develop/Grot.190119.1252
		Principal principal = provider(req).getSAPrincipal();
		if (principal == null) {
			// Not logged on
			return false;
		} else if (principal.getUserName().equals(username)) {
			// Principal is owner
			return true;
<<<<<<< HEAD
		} else if(principal.hasRole(ADMIN)) {
=======
		} else if (principal.hasRole(ADM)) {
>>>>>>> develop/Grot.190119.1252
			// Principal is admin
			return true;
		} else {
			// Neither admin nor owner
			return false;
		}
	}

<<<<<<< HEAD
	protected boolean checkRole(HttpServletRequest req, String role) {
=======
	protected boolean checkRole(final HttpServletRequest req, final String role) {
>>>>>>> develop/Grot.190119.1252
		Principal principal = provider(req).getSAPrincipal();
		if (principal == null) {
			return false;
		} else {
			return principal.hasRole(role);
		}
	}

<<<<<<< HEAD
	protected void editAuthorisation(UserService userService, HttpServletRequest req, User user) {
		Principal principal = provider(req).getSAPrincipal();
		if (principal != null && principal.hasRole(ADMIN)) {
=======
	protected void editAuthorisation(final UserService userService, final HttpServletRequest req, final User user) {
		Principal principal = provider(req).getSAPrincipal();
		if (principal != null && principal.hasRole(ADM)) {
>>>>>>> develop/Grot.190119.1252
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

<<<<<<< HEAD
	protected void setAuthorisation(UserService userService, User user) {
		Role role = userService.readRole("ROLE_USER");
=======
	protected void setAuthorisation(final UserService userService, final User user) {
		Role role = userService.readRole(USR);
>>>>>>> develop/Grot.190119.1252
		user.getRoles().add(role);
		user.setEnabled(true);
	}

<<<<<<< HEAD
=======
	protected static void devInfo(String strClass, String strMethod, String strMessage) {
		System.out.println("TEST: (" + strClass + "-" + strMethod + ") " + strMessage);
	}
	
>>>>>>> develop/Grot.190119.1252
}
