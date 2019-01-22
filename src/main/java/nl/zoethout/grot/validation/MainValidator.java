package nl.zoethout.grot.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class MainValidator implements Validator {

	public abstract boolean supports(Class<?> arg0) ;

	public abstract void validate(Object arg0, Errors arg1) ;

<<<<<<< HEAD
	public boolean isAlphaNumericSpace(String strValue){
		return StringUtils.isAlphanumericSpace(strValue);
	}
	
	public boolean isAlphaNumeric(String strValue){
		return StringUtils.isAlphanumeric(strValue);
	}
	
	public boolean isAlphaSpace(String strValue){
=======
	protected boolean isAlphaNumericSpace(String strValue){
		return StringUtils.isAlphanumericSpace(strValue);
	}
	
	protected boolean isAlphaNumeric(String strValue){
		return StringUtils.isAlphanumeric(strValue);
	}
	
	protected boolean isAlphaSpace(String strValue){
>>>>>>> develop/Grot.190119.1252
		return StringUtils.isAlphaSpace(strValue);
	}
}
