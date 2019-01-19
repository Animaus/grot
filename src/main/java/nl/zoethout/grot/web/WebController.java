package nl.zoethout.grot.web;

import java.io.IOException;
import java.util.function.BinaryOperator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserService;
import nl.zoethout.grot.util.AttributeProvider;
import nl.zoethout.grot.util.AttributeProviderImpl;

public abstract class WebController {
	protected static final String ADM = "ROLE_ADMIN";
	protected static final String USR = "ROLE_USER";
	protected final static BinaryOperator<String> merger = (key, val) -> {
		throw new IllegalStateException(String.format("Duplicate key \"%s\"", key));
	};
	
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

	protected String getAuthorPage(HttpServletRequest req, String pagename, String username, boolean isEdit) {
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
			if (checkRole(req, ADM)) {
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
		} else if (principal.hasRole(ADM)) {
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

	protected void editAuthorisation(UserService userService, HttpServletRequest req, User user) {
		Principal principal = provider(req).getSAPrincipal();
		if (principal != null && principal.hasRole(ADM)) {
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

	protected void setAuthorisation(UserService userService, User user) {
		Role role = userService.readRole(USR);
		user.getRoles().add(role);
		user.setEnabled(true);
	}
}
