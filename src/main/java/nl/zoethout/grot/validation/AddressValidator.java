package nl.zoethout.grot.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import nl.zoethout.grot.domain.Address;

public class AddressValidator extends MainValidator {

	// Zie:
	// http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html

	public AddressValidator() {
		super();
	}

	public boolean supports(Class<?> clazz) {
		return Address.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		String strRequired = "field.required";

		Address address = (Address) target;

		List<String> required = new ArrayList<String>();
		required.add("streetName");
		required.add("streetNumber");
		required.add("zip");
		required.add("city");
		required.add("country");
		required.add("email1");
		required.add("phone1");

		for (String fieldName : required) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, strRequired);
		}

		// do "complex" validation here

		String streetName = address.getStreetName();
		if (streetName == null) {
			errors.rejectValue("streetName", strRequired);
		} else if (!isAlphaSpace(streetName)) {
			errors.rejectValue("streetName", "streetname.invalid");
		} else if (streetName.length() < 5 & streetName.length() > 0) {
			errors.rejectValue("streetName", "streetname.length");
		}

		String streetNumber = address.getStreetNumber();
		if (streetNumber == null) {
			errors.rejectValue("streetNumber", strRequired);
		} else if (!isAlphaNumeric(streetNumber)) {
			errors.rejectValue("streetNumber", "streetnumber.invalid");
		}

		String zip = address.getZip();
		if (zip == null) {
			errors.rejectValue("zip", strRequired);
		} else {
			String strError = isZip(zip);
			if (!strError.equals("")) {
				errors.rejectValue("zip", strError);
			}
		}

		String city = address.getCity();
		if (city == null) {
			errors.rejectValue("city", strRequired);
		} else if (!isAlphaSpace(city)) {
			errors.rejectValue("city", "city.invalid");
		} else if (city.length() < 5 & city.length() > 0) {
			errors.rejectValue("city", "city.length");
		}

		if (address.getCountry() == null) {
			errors.rejectValue("country", strRequired);
		}

	}

	private String isZip(String str) {
		String strError = "";
		if (str.length() == 0) {
			strError = "";
		} else if (!isAlphaNumericSpace(str)) {
			strError = "zip.invalid";
		} else if (str.length() != 7 & str.length() > 0) {
			strError = "zip.length";
		} else if (!str.substring(4, 5).equals(" ")) {
			strError = "zip.space";
		} else {
			String codePrefix = str.substring(0, 4);
			String codeSuffix = str.substring(5, 7);
			if (!StringUtils.isAlpha(codeSuffix)) {
				strError = "zip.letter";
			} else if (!StringUtils.isNumeric(codePrefix)) {
				strError = "zip.number";
			}
		}
		return strError;
	}

}