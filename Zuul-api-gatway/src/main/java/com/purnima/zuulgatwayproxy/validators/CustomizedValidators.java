package com.purnima.zuulgatwayproxy.validators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
public class CustomizedValidators extends ValidationUtils {
	
	public static final Pattern EMAIL_VALIDATION_PATTERN= Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern NUMBER_VALIDATION_PATTERN= Pattern.compile("[0-9.]+");
	
	public static void rejectInvalidEmails(Errors errors,String field, String errorCode) {
		if(field!=null)
		rejectIfEmailNotValid(errors, field, errorCode, null,null);
	}
	
	
	public static void rejectIfEmailNotValid(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
       Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if(value!=null) {
		Matcher matcher = EMAIL_VALIDATION_PATTERN.matcher(value.toString());
		if (!matcher.find()) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
		}
	}
	
	
	public static void rejectIFNotNumerical(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if(value!=null) {
		Matcher matcher = NUMBER_VALIDATION_PATTERN.matcher(value.toString());
		if (!matcher.find()) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
		}
	}
}
