package nl.zoethout.grot.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class WebController {

	public void gotoURL(HttpServletRequest req, HttpServletResponse res, String URL) {
		String context = req.getContextPath();
		try {
			res.sendRedirect(context + URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
