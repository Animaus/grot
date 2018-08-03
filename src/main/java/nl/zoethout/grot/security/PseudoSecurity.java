package nl.zoethout.grot.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
https://keyholesoftware.com/2014/07/14/creating-java-annotations/
https://www.mkyong.com/java/java-custom-annotations-example/
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PseudoSecurity {
	public String[] roles();
}
