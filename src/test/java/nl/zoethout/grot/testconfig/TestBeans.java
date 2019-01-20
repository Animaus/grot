package nl.zoethout.grot.testconfig;

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import nl.zoethout.grot.dao.UserDao;
import nl.zoethout.grot.service.UserService;

@Configuration
// TODO 43 - RedirectionSecurityIntegrationTest @ComponentScan
@ComponentScan(basePackages = { "nl.zoethout.grot.mytest" })
@SuppressWarnings("deprecation")
public class TestBeans {

	@Bean({ "passwordEncoder" })
	public PasswordEncoder delegatingPasswordEncoder() {
		PasswordEncoder defaultEncoder = NoOpPasswordEncoder.getInstance(); // not considered secure
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("noop", defaultEncoder);
		encoders.put("sha256", new StandardPasswordEncoder()); // not considered secure
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		// Defining bean
		DelegatingPasswordEncoder bean = new DelegatingPasswordEncoder("noop", encoders);
		bean.setDefaultPasswordEncoderForMatches(defaultEncoder);
		return bean;
	}

	@Bean({ "userService" })
	public UserService userService() {
		return Mockito.mock(UserService.class);
	}

	@Bean({ "userDao" })
	public UserDao userDao() {
		return Mockito.mock(UserDao.class);
	}

	@Bean({ "viewResolver" })
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/jsp/");
		bean.setSuffix(".jsp");
		return bean;
	}

	@Bean({ "messageSource" })
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

//	@Bean({ "messageSource" })
//	public MessageSource messageSource() {
//		ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
//		bean.setBasename("messages");
//		return bean;
//	}

//	@Bean({ "viewResolver" })
//	public ViewResolver viewResolver() {
//		final InternalResourceViewResolver bean = new InternalResourceViewResolver();
//		bean.setViewClass(JstlView.class);
//		bean.setPrefix("/WEB-INF/jsp/");
//		bean.setSuffix(".jsp");
//		return bean;
//	}
}
