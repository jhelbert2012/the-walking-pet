package com.zebra.model.api;

import java.util.List;

public class RibbonList extends HateoasResourceList<Ribbon> {

	private static final long serialVersionUID = 1L;

	public RibbonList() {
		super();
	}

	public RibbonList(List<Ribbon> resources, Long total) {
		super(resources, total);
	}

}
