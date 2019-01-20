package nl.zoethout.grot.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.mytest.MyTestCases;
import nl.zoethout.grot.service.UserServiceImpl;

public class TestUserValidator extends MyTestCases {
	@Mock
	private UserServiceImpl userService;
	private AddressValidator addressValidator;
	private UserValidator userValidator;

	private User user;

	private Address address;

	private String errorCode;

	TestUserValidator(final TestInfo inf) {
		System.out.println(inf.getDisplayName());
	}

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this); // Initializes objects annotated with @Mock
		mockUserService(userService);
		addressValidator = new AddressValidator();
		userValidator = new UserValidator(addressValidator);
	}

	@Test
	@DisplayName("All_Valid")
	void All_Valid(final TestInfo inf) throws Exception {
		printLine("- " + inf.getDisplayName());
		User user = getAdmin();
		Errors errors = new BeanPropertyBindingResult(user, "user");
		userValidator.validate(user, errors);
		assertFalse(errors.hasErrors());
		assertNull(errors.getFieldError("firstName"));
	}

	private void checkRequired(String fieldName) {
		Errors errors = new BeanPropertyBindingResult(user, "user");
		userValidator.validate(user, errors);
		assertTrue(errors.hasErrors());
		FieldError fieldError = errors.getFieldError(fieldName);
		assertNotNull(fieldError);
		assertEquals(errorCode, fieldError.getCode());
	}

	@Nested
	@DisplayName("User")
	class CheckUser {
		CheckUser(final TestInfo inf) {
			printLine("- " + inf.getDisplayName());
			prefix = "\t";
			errorCode = "field.required";
			user = getAdmin();
		}

		@Test
		@DisplayName("User_FirstName_Required")
		void User_FirstName_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			user.setFirstName("");
			checkRequired("firstName");
		}

		@Test
		@DisplayName("User_LastName_Required")
		void User_LastName_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			user.setLastName("");
			checkRequired("lastName");
		}

		@Test
		@DisplayName("User_DateBirth_Required")
		void User_DateBirth_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			user.setDateBirth(null);
			checkRequired("dateBirth");
		}

		@Test
		@DisplayName("User_Sex_Required")
		void User_Sex_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			user.setSex("");
			checkRequired("sex");
		}
	}

	@Nested
	@DisplayName("Address")
	class CheckAddress {
		CheckAddress(final TestInfo inf) {
			printLine("- " + inf.getDisplayName());
			prefix = "\t";
			user = getAdmin();
			address = user.getAddress();
		}

		@Test
		@DisplayName("Address_StreetName_Required")
		void Address_StreetName_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "field.required";
			address.setStreetName("");
			checkRequired("address.streetName");
		}

		@Test
		@DisplayName("Address_StreetName_Invalid")
		void Address_StreetName_Invalid(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "streetname.invalid";
			address.setStreetName("!");
			checkRequired("address.streetName");
		}

		@Test
		@DisplayName("Address_StreetName_Length")
		void Address_StreetName_Length(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "streetname.length";
			address.setStreetName("foo");
			checkRequired("address.streetName");
		}

		@Test
		@DisplayName("Address_StreetNumber_Required")
		void Address_StreetNumber_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "field.required";
			address.setStreetNumber("");
			checkRequired("address.streetNumber");
		}

		@Test
		@DisplayName("Address_StreetNumber_Invalid")
		void Address_StreetNumber_Invalid(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "streetnumber.invalid";
			address.setStreetNumber("!");
			checkRequired("address.streetNumber");
		}

		@Test
		@DisplayName("Address_Zip_Required")
		void Address_Zip_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "field.required";
			address.setZip("");
			checkRequired("address.zip");
		}

		@Test
		@DisplayName("Address_Zip_Invalid")
		void Address_Zip_Invalid(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "zip.invalid";
			address.setZip("!");
			checkRequired("address.zip");
		}

		@Test
		@DisplayName("Address_Zip_Length")
		void Address_Zip_Length(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "zip.length";
			address.setZip("999 XX");
			checkRequired("address.zip");
		}

		@Test
		@DisplayName("Address_Zip_Space")
		void Address_Zip_Space(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "zip.space";
			address.setZip("99 99XX");
			checkRequired("address.zip");
		}

		@Test
		@DisplayName("Address_Zip_Letter")
		void Address_Zip_Letter(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "zip.letter";
			address.setZip("9999 12");
			checkRequired("address.zip");
		}

		@Test
		@DisplayName("Address_Zip_Number")
		void Address_Zip_Number(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "zip.number";
			address.setZip("ABCD XX");
			checkRequired("address.zip");
		}

		@Test
		@DisplayName("Address_City_Required")
		void Address_City_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "field.required";
			address.setCity("");
			checkRequired("address.city");
		}

		@Test
		@DisplayName("Address_City_Invalid")
		void Address_City_Invalid(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "city.invalid";
			address.setCity("!");
			checkRequired("address.city");
		}

		@Test
		@DisplayName("Address_City_Length")
		void Address_City_Length(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "city.length";
			address.setCity("foo");
			checkRequired("address.city");
		}

		@Test
		@DisplayName("Address_Country_Required")
		void Address_Country_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "field.required";
			address.setCountry("");
			checkRequired("address.country");
		}

		@Test
		@DisplayName("Address_Email1_Required")
		void Address_Email1_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "field.required";
			address.setEmail1("");
			checkRequired("address.email1");
		}

		@Test
		@DisplayName("Address_Phone1_Required")
		void Address_Phone1_Required(final TestInfo inf) throws Exception {
			printLine(inf.getDisplayName());
			errorCode = "field.required";
			address.setPhone1("");
			checkRequired("address.phone1");
		}
	}

}
