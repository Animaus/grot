package nl.zoethout.grot.mytest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
// TODO 43 - MyTestController implements MyTest
public class MyTestController implements MyTest {
	private String strClass = this.getClass().getCanonicalName();

	@RequestMapping("/arc0j00")
	public void user(HttpServletRequest request, HttpServletResponse response) {
		testInfo(strClass, "user", "accessing arc0j00");
		System.out.println("\t(" + strClass + ") accessing arc0j00");
	}

	@RequestMapping("/front00")
	public void admin(HttpServletRequest request, HttpServletResponse response) {
		testInfo(strClass, "admin", "accessing front00");
		System.out.println("\t(" + strClass + ") accessing front00");
	}
}
