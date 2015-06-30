package com.zebra.rest.resource.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.model.api.HateoasResource;
import com.zebra.model.exception.ValidationException;
import com.zebra.model.validation.ExternalValidationGroup;
import com.zebra.model.validation.InternalValidationGroup;

@Component("payloadValidator")
@Scope("singleton")
public class PayloadValidator {

	private Validator validator;

	public PayloadValidator() {
		super();
		ValidatorFactory validatorFactory = Validation
				.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	public void validate(HateoasResource payload, Class<?>... validationGroups)
			throws ValidationException {

		Set<ConstraintViolation<HateoasResource>> violations = validator
				.validate(payload, validationGroups);

		if (violations != null && violations.size() > 0) {
			throw new ValidationException(violations);
		}
	}

	public void validateExternal(HateoasResource payload)
			throws ValidationException {
		this.validate(payload, Default.class, ExternalValidationGroup.class);
	}

	public void validateInternal(HateoasResource payload)
			throws ValidationException {
		this.validate(payload, Default.class, InternalValidationGroup.class);
	}
}
