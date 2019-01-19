package nl.zoethout.grot.web;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.zoethout.grot.dao.UserDao;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.repository.UserRepository;
import nl.zoethout.grot.security.PseudoSecurity;
import nl.zoethout.grot.security.PseudoSecurityExecute;
import nl.zoethout.grot.service.UserService;

@Controller // This class is a Controller
@RequestMapping(path = { "/poc", "/pocs" }) // URL's start with /demo (after Application path)
public class PocController {

	private static final String DEFAULT_MESSAGE = "message";
	private static final String DISPATCHER = "poc";
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private ResourceBundle bundle = ResourceBundle.getBundle("poc");

	@Autowired // Get the bean called userRepository which is auto-generated by Spring, we will
				// use it to handle the data
	private UserRepository userRepository;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "start", method = RequestMethod.GET)
	public String rmStart(Map<String, Object> model) {
		setAttributes();
		model.putAll(attributes);
		model.put(DEFAULT_MESSAGE, bundle.getString("MSG_START"));
		return DISPATCHER;
	}

	private void setAttributes() {
		setText("DESCRIPTION");
		setText("VAL_START");
		setText("VAL_DAO");
		setText("VAL_PROPERTIES");
		setText("VAL_REPOSITORY");
		setText("VAL_SAVEUSER");
		setText("VAL_LOGIN_VALID");
		setText("VAL_LOGIN_INVALID");
	}

	private void setText(String key) {
		attributes.put(key, bundle.getString(key));
	}

	@RequestMapping(value = "dao", method = RequestMethod.GET)
	public String rmDao(Model model, HttpServletRequest req) {
		setAttributes();
		model.addAllAttributes(attributes);
		List<String> listUserRoles = userDao.listUserRoles(1);
		model.addAttribute(DEFAULT_MESSAGE, bundle.getString("MSG_DAO") + listUserRoles);
		return DISPATCHER;
	}

	// inject via application.properties
	@Value("${msg.welcome:test}")
	private String message = "Hello World";

	@RequestMapping(value = "properties", method = RequestMethod.GET)
	public String rmProperties(Map<String, Object> model) {
		setAttributes();
		model.putAll(attributes);
		model.put(DEFAULT_MESSAGE, bundle.getString("MSG_PROPERTIES") + this.message);
		return DISPATCHER;
	}

	@RequestMapping(value = { "/repository", "/repo" }, method = RequestMethod.GET)
	public @ResponseBody Iterable<User> rmRepository() {
		// Returns a JSON or XML with the users (in the body, not a page)
		return userRepository.findAll();
	}

	@RequestMapping(value = "/saveUser")
	public @ResponseBody Iterable<User> rmSaveUser() {
		User user = new User();
		user.setName("Test");
		user.setEmail("test@domain.org");
		user.setPassword("123456");
		user.setEnabled(true);
		GregorianCalendar gregorianCalendar = new GregorianCalendar(2018, 0, 11);
		Date dateBirth = gregorianCalendar.getTime();
		user.setDateBirth(dateBirth);
		Date today = new Date(System.currentTimeMillis());
		user.setDateRegistered(today);
		userService.saveUser(user);
		// Returns a JSON or XML with the users (in the body, not a page)
		return userRepository.findAll();
	}

	@RequestMapping(value = "/loginValid")
	public String rmLoginValid(Map<String, Object> model) {
		setAttributes();
		model.putAll(attributes);
		User usr = userService.loginUser("Gerard", "123456");

		if (usr == null) {
			model.put(DEFAULT_MESSAGE, "Not found!");
		} else {
			model.put(DEFAULT_MESSAGE, usr.toString());
		}

		return DISPATCHER;
	}

	@RequestMapping(value = "loginInvalid")
	public String rmLoginInvalid(Map<String, Object> model) {
		setAttributes();
		model.putAll(attributes);
		User usr = userService.loginUser("Thierry", "123456");

		if (usr == null) {
			model.put(DEFAULT_MESSAGE, "Not found!");
		} else {
			model.put(DEFAULT_MESSAGE, usr.toString());
		}

		return DISPATCHER;
	}

	@RequestMapping(value = "denied")
	@PseudoSecurity(roles = { "admin", "user" })
	public String rmDenied(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res) {
		// For testing : simulate a login
		String[] roles = { "huey", "duey", "luey" };
		HttpSession session = req.getSession();
		session.setAttribute("roles", roles);

		// Redirect on execution denied
		PseudoSecurityExecute.run(req, res, this.getClass());

		// Set navigational links
		setAttributes();

		// To page
		model.putAll(attributes);
		return DISPATCHER;
	}

	@RequestMapping(value = "granted")
	@PseudoSecurity(roles = { "admin", "user" })
	public String rmGranted(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res) {
		// For testing : simulate a login
		String[] roles = { "user" };
		HttpSession session = req.getSession();
		session.setAttribute("roles", roles);
		
		// Redirect on execution denied
		PseudoSecurityExecute.run(req, res, this.getClass());
		
		// Set navigational links
		setAttributes();

		// Useful code...
		model.put(DEFAULT_MESSAGE,
				"Access Granted" + "<br>Context : " + req.getContextPath() + "<br>QueryString : " + req.getQueryString()
						+ "<br>RequestURI : " + req.getRequestURI() + "<br>RequestURL : " + req.getRequestURL()
						+ "<br>ServletPath : " + req.getServletPath() + "<br>ServerName : " + req.getServerName()
						+ "<br>ServerPort : " + req.getServerPort() + "<br># ");

		// To page
		model.putAll(attributes);
		return DISPATCHER;
	}
}
