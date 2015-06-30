package com.zebra.model.api;

import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.zebra.model.api.Printer;
import com.zebra.model.validation.ExternalValidationGroup;
import com.zebra.model.validation.InternalValidationGroup;
import com.zebra.test.BaseSpringTestCase;

public class PrinterValidationTest extends BaseSpringTestCase {

	@Resource
	public Validator validator;

	public void testExternalValidator() {

		Printer printer = new Printer();

		Set<ConstraintViolation<Printer>> constraintViolations = validator
				.validate(printer, Default.class, ExternalValidationGroup.class);

		assertEquals(2, constraintViolations.size());

		for (ConstraintViolation<Printer> violation : constraintViolations) {
			if ("name".equals(violation.getPropertyPath().toString())) {
				assertEquals("may not be empty", violation.getMessage());
			} else if ("printTechnology".equals(violation.getPropertyPath()
					.toString())) {
				assertEquals("may not be empty", violation.getMessage());
			} else {
				assertTrue(violation.getPropertyPath().toString() + " "
						+ "field not tested", false);
			}
		}
	}

	public void testInternalValidator() {

		Printer printer = new Printer();

		Set<ConstraintViolation<Printer>> constraintViolations = validator
				.validate(printer, InternalValidationGroup.class);

		assertEquals(1, constraintViolations.size());

		for (ConstraintViolation<Printer> violation : constraintViolations) {
			if ("id".equals(violation.getPropertyPath().toString())) {
				assertEquals("may not be empty", violation.getMessage());
			} else {
				assertTrue(violation.getPropertyPath().toString() + " "
						+ "field not tested", false);
			}
		}

	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
