package com.zebra.rest.resource;

import org.restlet.data.MediaType;
import org.restlet.ext.wadl.WadlServerResource;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("/")
@Scope("prototype")
public class RootResource extends WadlServerResource {

	@Override
	public String getName() {
		return getApplication().getName() + " root";
	}

	@Override
	public String getDescription() {
		return "The root resource of the application";
	}

	@Override
	protected void doInit() throws ResourceException {
		setAutoDescribing(false);
	}

	@Get("plain/text")
	public StringRepresentation getText() {
		StringRepresentation textRepresentation = new StringRepresentation(
				"Welcome to " + getApplication().getName() + " !");
		textRepresentation.setMediaType(MediaType.TEXT_PLAIN);
		return textRepresentation;
	}
}
