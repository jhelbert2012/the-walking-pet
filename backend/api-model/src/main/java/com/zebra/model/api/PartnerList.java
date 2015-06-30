package com.zebra.model.api;

import java.util.List;

public class PartnerList extends HateoasResourceList<Partner> {

	private static final long serialVersionUID = 1L;

	public PartnerList() {
		super();
	}

	public PartnerList(List<Partner> resources, Long total) {
		super(resources, total);
	}

}
