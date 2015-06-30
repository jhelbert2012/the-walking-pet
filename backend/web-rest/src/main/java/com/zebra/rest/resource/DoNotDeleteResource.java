package com.zebra.rest.resource;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("/DO_NOT_DELETE.txt")
@Scope("prototype")
public class DoNotDeleteResource extends ZebraResource {

	@Override
	public String getName() {
		return "Do Not Delete resource";
	}

	@Override
	public String getDescription() {
		return "The resource used by the F5 load balancer to check if the service is up";
	}

	@Get()
	public Representation getOperation() {

		setStatus(Status.SUCCESS_OK);

		return new StringRepresentation("Zebra API service is up.");
	}
}
