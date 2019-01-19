package nl.zoethout.grot.web;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller // When this is @RestController: no JSP but plain text displayed
public class Welcome {

	private ResourceBundle lnk = ResourceBundle.getBundle("link");
	private ResourceBundle txt = ResourceBundle.getBundle("text");
	private Map<String, Object> attributes = new HashMap<String, Object>();

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String welcome(Map<String, Object> model) {
		this.attributes = model;
		// attributes.put("message", this.message);
		setAttributes();
		setSiteText("WELCOME");
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		setAttributes();
		setSiteText("LOGIN_TXT");
		setSiteText("LOGIN_BTN");
		setSiteText("LOGIN_USR");
		setSiteText("LOGIN_PWD");
		setSiteText("LOGIN_ERR");
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		model.addAllObjects(attributes);
		return model;
	}

	private void setAttributes() {
		setSiteText("LOGIN_OFF");
		setSiteText("LOGIN_YES");
		setSiteText("LOGIN_OUT");
		setSiteLink("LNK_USR_HOME");
		setSiteLink("LNK_USR_LOGIN");
	}

	private void setSiteText(String key) {
		String value = txt.getString(key.toUpperCase());
		attributes.put(key, value);
	}

	private void setSiteLink(String key) {
		String value = lnk.getString(key.toUpperCase());
		attributes.put(key, value);
	}

}