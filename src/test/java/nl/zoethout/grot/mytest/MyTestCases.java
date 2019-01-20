package nl.zoethout.grot.mytest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserServiceImpl;
import nl.zoethout.grot.util.AttributeProvider;
import nl.zoethout.grot.util.AttributeProviderImpl;

public class MyTestCases implements MyTestUsers {
	protected static final String ADM = "ROLE_ADMIN";
	protected static final String USR = "ROLE_USER";
	protected String prefix = "";

	protected void printLine(final Object str) {
		System.out.println(prefix + str);
	}

	protected void printFeedback(final ResultActions ra) throws Exception {
		printLine("==============================");
		ra.andDo(print());
		printLine("==============================");
	}

	protected void addRole(List<Role> roles, final String roleDesc, final String roleName) {
		if (roles == null) {
			roles = new ArrayList<Role>();
		}
		Role role = new Role();
		role.setRoleId(roles.size() + 1);
		role.setRoleDesc(roleDesc);
		role.setRoleName(roleName);
		roles.add(role);
	}

	protected List<Role> getRoles() {
		List<Role> roles = new LinkedList<Role>();
		addRole(roles, "admin", "Administrators");
		addRole(roles, "user", "Regular users");
		addRole(roles, "employee", "Employee");
		addRole(roles, "student", "Student");
		addRole(roles, "ROLE_USER", "Gebruiker");
		addRole(roles, "ROLE_ADMIN", "Beheerder");
		return roles;
	}

	protected List<User> listProfiles() {
		List<User> profiles = new ArrayList<User>();
		profiles.add(getAdmin());
		profiles.add(getUser());
		profiles.add(getDisabled());
		return profiles;
	}

	/**
	 * Checks the contents of the sessionattributes
	 * 
	 * @param ra
	 * @throws Exception
	 */
	protected void assertAttributes(final ResultActions ra) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("text");
		ra.andExpect(request().sessionAttribute("WELCOME", bundle.getString("WELCOME")));
		ra.andExpect(request().sessionAttribute("LOGIN_MSG", bundle.getString("LOGIN_MSG")));
		ra.andExpect(request().sessionAttribute("LOGIN_NON", bundle.getString("LOGIN_NON")));
		ra.andExpect(request().sessionAttribute("LOGIN_YES", bundle.getString("LOGIN_YES")));
		ra.andExpect(request().sessionAttribute("LOGIN_OUT", bundle.getString("LOGIN_OUT")));
		ra.andExpect(request().sessionAttribute("LOGIN_ONN", bundle.getString("LOGIN_ONN")));
		ra.andExpect(request().sessionAttribute("LOGIN_USR", bundle.getString("LOGIN_USR")));
		ra.andExpect(request().sessionAttribute("LOGIN_PWD", bundle.getString("LOGIN_PWD")));
		ra.andExpect(request().sessionAttribute("LOGIN_ERR", bundle.getString("LOGIN_ERR")));
		bundle = ResourceBundle.getBundle("link");
		ra.andExpect(request().sessionAttribute("LNK_USR_HOME", bundle.getString("LNK_USR_HOME")));
		ra.andExpect(request().sessionAttribute("LNK_USR_LOGIN", bundle.getString("LNK_USR_LOGIN")));
		ra.andExpect(request().sessionAttribute("LNK_USR_MEMBERS", bundle.getString("LNK_USR_MEMBERS")));
	}
	
	// TODO 43 - 03a - mockUserDetailsService
	protected void mockUserDetailsService(final UserServiceImpl userDetailsService) {
		when(userDetailsService.loadUserByUsername("front00")).thenReturn(getUserDetails(getAdmin()));
		when(userDetailsService.loadUserByUsername("arc0j00")).thenReturn(getUserDetails(getAdmin()));
		when(userDetailsService.loadUserByUsername("hawks00")).thenReturn(getUserDetails(getAdmin()));
	}

	// TODO 43 - 03b - getUserDetails
	protected UserDetails getUserDetails(User user) {
		List<GrantedAuthority> authorities = getGrantedAuthorities(user);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				user.isEnabled(), true, true, true, authorities);
	}

	// TODO 43 - 03c - getGrantedAuthorities
	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<String> userRoles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		// TODO Lambda and streams
		for (String userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole));
		}
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(authorities);
		return result;
	}

	protected void mockUserService(final UserServiceImpl userService) {
		when(userService.readRoles()).thenReturn(getRoles());
		when(userService.listProfiles()).thenReturn(listProfiles());
		when(userService.loginUser("front00", "123456")).thenReturn(getAdmin());
		when(userService.loginUser("arc0j00", "123456")).thenReturn(getUser());
		when(userService.loginUser("hawks00", "123456")).thenReturn(getDisabled());
		when(userService.readUser("front00")).thenReturn(getAdmin());
		when(userService.readUser("arc0j00")).thenReturn(getUser());
		when(userService.readUser("hawks00")).thenReturn(getDisabled());
		// https://stackoverflow.com/questions/2276271/how-to-make-mock-to-void-methods-with-mockito
		// https://stackoverflow.com/questions/28836778/usages-of-dothrow-doanswer-donothing-and-doreturn-in-mockito
		// Code in line below found in "tutorials-master"
		doCallRealMethod().when(userService).setPrincipal(any(HttpServletRequest.class), any(User.class));
	}

	// https://stackoverflow.com/questions/30757044/autowired-httpservletrequest-in-spring-test-integration-tests
	protected RequestPostProcessor mockRequest(final MockHttpServletRequest mockHttpServletRequest) {
		return new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				return mockHttpServletRequest;
			}
		};
	}

	// See: nl.zoethout.grot.web.WebController.provider(HttpServletRequest)
	protected AttributeProvider provider(final MockHttpServletRequest req) {
		AttributeProvider provider = AttributeProviderImpl.getProvider(req);
		return provider;
	}

	protected void mockLogout(final MockHttpServletRequest req) throws Exception {
		Principal.terminate();
		provider(req).setSAPrincipal(null);
		req.getSession().invalidate();
	}

	/**
	 * @param req
	 * @param user For guest login user=null
	 * @throws Exception
	 */
	protected void mockLogin(final MockHttpServletRequest req, final User user) throws Exception {
		mockLogout(req);
		if (user != null) {
			Principal principal = Principal.getUser(user);
			provider(req).setSAPrincipal(principal);
		}
	}

}
