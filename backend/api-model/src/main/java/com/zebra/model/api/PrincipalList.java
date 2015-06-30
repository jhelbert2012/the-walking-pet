package com.zebra.model.api;

import java.util.List;

public class PrincipalList extends HateoasResourceList<Principal> {

	private static final long	serialVersionUID	= 1L;

	public PrincipalList() {
		super();
	}

	public PrincipalList(List<Principal> resources, Long total) {
		super(resources, total);
	}

}
