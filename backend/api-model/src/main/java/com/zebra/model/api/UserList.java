package com.zebra.model.api;

import java.util.List;

public class UserList extends HateoasResourceList<User> {

	private static final long serialVersionUID = 1L;

	public UserList() {
		super();
	}

	public UserList(List<User> resources, Long total) {
		super(resources, total);
	}

}
