package nl.zoethout.grot.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserService;

// TODO JAVA 8 - Can be made generic...!
@SuppressWarnings("unused")
public class UserValidator implements Validator {

	// Zie:
	// http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html

	private final Validator addressValidator;
<<<<<<< HEAD
	private UserService userService;

	public UserValidator(UserService userService, Validator addressValidator) {
		if (addressValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is " + "required and must not be null.");
		}
		if (!addressValidator.supports(Address.class)) {
			throw new IllegalArgumentException(
					"The supplied [Validator] must " + "support the validation of [Address] instances.");
		}
		this.addressValidator = addressValidator;
		this.userService = userService;
=======

	public UserValidator(Validator addressValidator) {
		if (addressValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is required and must not be null.");
		}
		if (!addressValidator.supports(Address.class)) {
			throw new IllegalArgumentException(
					"The supplied [Validator] must support the validation of [Address] instances.");
		}
		this.addressValidator = addressValidator;
>>>>>>> develop/Grot.190119.1252
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		try {
			errors.pushNestedPath("address");
			ValidationUtils.invokeValidator(addressValidator, user.getAddress(), errors);
		} finally {
			errors.popNestedPath();
		}

		List<String> required = new ArrayList<String>();
		required.add("firstName");
		required.add("lastName");
		required.add("sex");
		required.add("dateBirth");

		for (String fieldName : required) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "field.required");
		}

		// do "complex" validation here

	}

}