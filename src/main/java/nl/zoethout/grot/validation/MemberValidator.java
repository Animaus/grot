package nl.zoethout.grot.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import nl.zoethout.grot.domain.Member;

public class MemberValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Member member = (Member) target;

		if (member.getFirstName() == null || member.getFirstName().equals("")) {
			errors.rejectValue("firstName", "field.required");
		}

		// do "complex" validation here

	}

}