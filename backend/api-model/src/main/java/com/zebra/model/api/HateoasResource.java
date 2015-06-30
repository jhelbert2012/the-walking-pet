package com.zebra.model.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.zebra.constant.api.LinkType;
import com.zebra.model.annotation.BusinessKey;
import com.zebra.model.annotation.BusinessKeyMethod;

public abstract class HateoasResource extends AbstractResource implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@BusinessKey(include = BusinessKeyMethod.TO_STRING)
	public Map<LinkType, String> links = new HashMap<>();

}
