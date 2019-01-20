package nl.zoethout.grot.testconfig;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.mytest.MyTestUsers;
import nl.zoethout.grot.security.CustomAuthenticationFailureHandler;
import nl.zoethout.grot.security.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig extends WebSecurityConfigurerAdapter implements MyTestUsers {
	private String strClass = this.getClass().getCanonicalName();

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**");
		web.ignoring().antMatchers("/img/**");
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/bootstrap/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		// Significant order – specific first, then general
		http.authorizeRequests() //
				.antMatchers("/").permitAll() //
				.antMatchers("/login*").permitAll() //
				.antMatchers("/user").permitAll() //
				.antMatchers("/user/**").authenticated() //
				.anyRequest().permitAll(); //

		http.logout() //
				.logoutSuccessHandler(logoutSuccessHandler()) //
				.permitAll();

//		http.formLogin() //
//				.loginPage("/login") // custom login page
//				.defaultSuccessUrl("/loginSuccess", true) // landing after a successful login
//				.failureHandler(failureHandler()); // processing unsuccessful login

		http.formLogin() //
				.successHandler(new SavedRequestAwareAuthenticationSuccessHandler());
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		testInfo(strClass, "configure(AuthenticationManagerBuilder)", "");
		User adm = getAdmin();
		User usr = getUser();
		User dis = getDisabled();

		List<String> admRoles = adm.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
		List<String> usrRoles = usr.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());

		// TODO 43 - RedirectionSecurityIntegrationTest auth.inMemoryAuthentication
		auth.inMemoryAuthentication() //
				.withUser(adm.getUserName()).password(adm.getPassword())
				.roles(getRolename(admRoles.get(0)), getRolename(admRoles.get(1))) //
				.and() //
				.withUser(usr.getUserName()).password(usr.getPassword()).roles(getRolename(usrRoles.get(0))) //
				.and() //
				.withUser(dis.getUserName()).password(dis.getPassword()).roles(getRolename(usrRoles.get(0)));
	}

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	private static String getRolename(String rolename) {
		if (rolename.indexOf("ROLE_") == 0) {
			return rolename.substring(5, rolename.length());
		}
		return rolename;
	}
}
