package com.purnima.zuulgatwayproxy.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.purnima.zuulgatwayproxy.dtos.ApplicationMainVO;
import com.purnima.zuulgatwayproxy.enhancevalidators.CustomizedValidators;

@Component
public class PartnerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ApplicationMainVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		@SuppressWarnings("rawtypes")
		ApplicationMainVO objectToValidate = (ApplicationMainVO) target;
		objectToValidate.getViewObject().getAllRequiredFields().stream().forEach(fieldValue -> {
			ValidationUtils.rejectIfEmpty(errors, fieldValue, fieldValue + "Can't be empty or null");
			if (fieldValue.equalsIgnoreCase("email")) {
				CustomizedValidators.rejectInvalidEmails(errors, fieldValue, "Email is not valid");
			}
		});

	}

}
