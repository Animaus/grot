package nl.zoethout.grot.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.service.UserService;

// TODO 26 - Users - fieldvalidation - 02 Validator added
// FIXME Can be made generic...!
public class UserValidator implements Validator {

	// Zie:
	// http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html

	private final Validator addressValidator;
	private UserService userService;

	public UserValidator(UserService userService, Validator addressValidator) {
		// Activering geneste POJO-validatie
		if (addressValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is " + "required and must not be null.");
		}
		if (!addressValidator.supports(Address.class)) {
			throw new IllegalArgumentException(
					"The supplied [Validator] must " + "support the validation of [Address] instances.");
		}
		this.addressValidator = addressValidator;
		this.userService = userService;
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