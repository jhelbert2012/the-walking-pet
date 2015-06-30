/**
 * 
 */
package com.zebra.model.api;

import java.util.List;

/**
 * @author ignaciogonzalez
 *
 */
public class PrintHeadList extends HateoasResourceList<PrintHead> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PrintHeadList(){
		super();
	}
	
	public PrintHeadList(List<PrintHead> resources, Long total) {
		super(resources, total);
	}

}
