package com.zebra.model.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zebra.model.util.DomainEntityUtils;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(Object obj) {
		return DomainEntityUtils.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return DomainEntityUtils.hashCode(this);
	}

	@Override
	public String toString() {
		return DomainEntityUtils.toString(this);
	}

}
