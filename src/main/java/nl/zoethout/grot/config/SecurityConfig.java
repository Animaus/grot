package nl.zoethout.grot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import nl.zoethout.grot.security.CustomAuthenticationFailureHandler;
import nl.zoethout.grot.security.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // P42-01
//@EnableJpaRepositories(basePackageClasses = UserRepository.class) // P42-01
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

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
				.antMatchers("/user/**").authenticated().anyRequest().permitAll(); //

		http.logout() //
				.logoutSuccessHandler(logoutSuccessHandler()) //
				.permitAll();

		http.formLogin() //
				.loginPage("/login") // custom login page
				.defaultSuccessUrl("/loginSuccess", true) // landing after a successful login
				.failureHandler(failureHandler()); // processing unsuccessful login
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.equals(encodedPassword);
			}
		};
	}

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}
}
