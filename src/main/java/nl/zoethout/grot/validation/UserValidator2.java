package nl.zoethout.grot.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import nl.zoethout.grot.domain.User;

public class UserValidator2<T> implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		if (user.getFirstName() == null || user.getFirstName().equals("")) {
			errors.rejectValue("firstName", "field.required");
		}

		// do "complex" validation here

	}

}