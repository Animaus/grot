package nl.zoethout.grot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import nl.zoethout.grot.domain.Principal;

public class CustomLogoutSuccessHandler extends Handler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication)
			throws IOException, ServletException {
		Principal.terminate();
		req.getSession().invalidate();
		res.sendRedirect("/");
	}

}
