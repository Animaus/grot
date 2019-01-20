package nl.zoethout.grot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import nl.zoethout.grot.repository.UserRepository;
import nl.zoethout.grot.security.CustomAuthenticationFailureHandler;
import nl.zoethout.grot.security.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	private final class hupseflups implements LogoutSuccessHandler {
//		@Override
//		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
//				Authentication authentication) throws IOException, ServletException {
//		}
//	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
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
