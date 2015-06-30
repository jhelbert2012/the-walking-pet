package com.zebra.model.api;

import java.util.List;

public class CleaningKitList extends HateoasResourceList<CleaningKit> {

	private static final long	serialVersionUID	= 1L;

	public CleaningKitList() {
		super();
	}

	public CleaningKitList(List<CleaningKit> resources, Long total) {
		super(resources, total);
	}

}
