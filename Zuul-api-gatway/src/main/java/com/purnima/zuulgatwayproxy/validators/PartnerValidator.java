package com.purnima.zuulgatwayproxy.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.purnima.zuulgatwayproxy.dtos.ApplicationMainVO;

@Component
public class PartnerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ApplicationMainVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ApplicationMainVO objectToValidate = (ApplicationMainVO) target;
		
		objectToValidate.getRequiredFields().stream().forEach(fieldValue -> {
			ValidationUtils.rejectIfEmpty(errors, fieldValue, fieldValue + " Can't be empty or null");
			Object value = errors.getFieldValue(fieldValue);
			System.out.println(value instanceof Number);
			
			if ("email".equalsIgnoreCase(fieldValue)) {
				CustomizedValidators.rejectInvalidEmails(errors, fieldValue, "Email is not valid");
			}
			
			if(value instanceof Number) {
				CustomizedValidators.rejectIFNotNumerical(errors, fieldValue, "Only numeric values are allow", null, null);
			}
		}
		);
	}

}
