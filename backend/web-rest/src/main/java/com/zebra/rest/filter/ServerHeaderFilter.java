package com.zebra.rest.filter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

public class ServerHeaderFilter extends Filter {

	@Override
	protected void afterHandle(Request request, Response response) {
		response.getServerInfo().setAgent("Zebra API Web Server");
		super.afterHandle(request, response);
	}
}