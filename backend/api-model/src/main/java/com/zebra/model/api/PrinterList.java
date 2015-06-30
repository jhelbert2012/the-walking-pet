package com.zebra.model.api;

import java.util.List;

public class PrinterList extends HateoasResourceList<Printer> {

	private static final long serialVersionUID = 1L;

	public PrinterList() {
		super();
	}

	public PrinterList(List<Printer> resources, Long total) {
		super(resources, total);
	}

}
