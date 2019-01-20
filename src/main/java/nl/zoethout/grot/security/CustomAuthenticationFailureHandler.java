package nl.zoethout.grot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler extends Handler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res,
			AuthenticationException exception) throws IOException, ServletException {
		String error = (String) req.getSession().getAttribute("LOGIN_ERR");
		provider(req).setSAUsername(req.getParameter("username"));
		provider(req).setSAError(error);
		res.sendRedirect("/login");
	}
}
