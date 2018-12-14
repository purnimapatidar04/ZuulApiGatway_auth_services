package com.purnima.zuulgatwayproxy.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.purnima.zuulgatwayproxy.dtos.ApplicationMainVO;

public class PartnerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ApplicationMainVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ApplicationMainVO objectToValidate = (ApplicationMainVO) target;
		objectToValidate.getRequiredFields().stream().forEach(fieldValue -> 
			ValidationUtils.rejectIfEmpty(errors, fieldValue, fieldValue + "Can't be empty or null")
		);
	}

}
