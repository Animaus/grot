package nl.zoethout.grot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@SpringBootApplication
@ComponentScan("nl.zoethout.grot")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
