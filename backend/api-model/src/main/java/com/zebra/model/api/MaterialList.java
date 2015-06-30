package com.zebra.model.api;

import java.util.List;

public class MaterialList extends HateoasResourceList<Material> {

	private static final long serialVersionUID = 1L;

	public MaterialList() {
		super();
	}

	public MaterialList(List<Material> resources, Long total) {
		super(resources, total);
	}

}
