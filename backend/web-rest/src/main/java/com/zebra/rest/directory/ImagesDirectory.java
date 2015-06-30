package com.zebra.rest.directory;

import org.restlet.Context;
import org.restlet.resource.Directory;

public class ImagesDirectory extends Directory {

	public ImagesDirectory(Context context) {
		super(context, "clap://class/images"); 
	}


}
