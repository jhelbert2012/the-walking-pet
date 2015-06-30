package com.zebra.model.exception;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.zebra.model.api.HateoasResource;

public final class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private Set<String> messages;

	public ValidationException() {
		messages = new HashSet<String>();
	}

	public ValidationException(String message) {
		messages = new HashSet<String>();
		messages.add(message);
	}

	public ValidationException(
			Set<ConstraintViolation<HateoasResource>> violations) {
		this();
		for (ConstraintViolation<HateoasResource> violation : violations) {
			StringBuffer msgBuf = new StringBuffer();

			msgBuf.append(violation.getPropertyPath());
			msgBuf.append(" : ");
			msgBuf.append(violation.getMessage());

			messages.add(msgBuf.toString());
		}
	}

	public Set<String> getMessages() {
		return messages;
	}

	@Override
	public String getMessage() {
		return messages.toString();
	}
}
