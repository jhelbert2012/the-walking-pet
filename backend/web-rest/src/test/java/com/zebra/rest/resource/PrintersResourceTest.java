package com.zebra.rest.resource;

import java.io.IOException;

import javax.annotation.Resource;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ClientInfo;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.ext.spring.SpringBeanRouter;

import com.zebra.test.BaseSpringMockTestCase;

public class PrintersResourceTest extends BaseSpringMockTestCase {

	@Resource
	private SpringBeanRouter springBeanRouter;

	public void testGet() throws IOException {
		String resourceUri = "/agencies/1";
		Request request = new Request(Method.GET, resourceUri);
		request.setClientInfo(new ClientInfo(MediaType.APPLICATION_JSON));
		Response response = new Response(request);
		springBeanRouter.handle(request, response);

		//assertEquals(200, response.getStatus().getCode());
		//assertTrue(response.isEntityAvailable());

		//Representation entity = response.getEntity();
		//assertEquals(MediaType.APPLICATION_JSON, entity.getMediaType());

		//JacksonRepresentation<Agency> agenciesRepresentation = new JacksonRepresentation<Agency>(
		//		entity, Agency.class);

		//assertEquals(true, agenciesRepresentation.getObject() != null);

	}

	public void testPost() throws IOException {
	}

//	private Agency getMockAgency() {
//		Address address = new Address();
//		address.setAddressLine1("1145 N Museum Blvd");
//		address.setAddressLine2("apt 315");
//		address.setCity("Vernon Hills");
//		address.setProvince("IL");
//		address.setCountry("US");
//		address.setPostalCode("60061");
//		Agency agency = new Agency();
//		agency.setName("agency");
//		agency.setPhoneNumber("+13128237109");
//		agency.setAddress(address);
//		agency.setMbox("agency1@email.com");
//		return agency;
//	}

	@Override
	protected void configureMocks() {
	}

}
