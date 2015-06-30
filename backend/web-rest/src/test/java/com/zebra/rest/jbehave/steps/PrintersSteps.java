package com.zebra.rest.jbehave.steps;

import java.io.IOException;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.stereotype.Component;

@Component
public class PrintersSteps {

	//private long agencyId;
	//private RouteList routeList;

	@Given("I visit agency (id:<agencyId>) website")
	public void visitWebsite(@Named("agencyId") long agencyId) {
	//	this.agencyId = agencyId;
	}

	@When("I search for all the available routes")
	public void search() throws IOException {
		//ClientResource resource = new ClientResource(
		//		"http://localhost:9191/agencies/" + agencyId + "/routes");
		//Representation representation = resource.get();
//		JacksonRepresentation<RouteList> listRepresentation = new JacksonRepresentation<RouteList>(
//				representation, RouteList.class);
//
//		routeList = listRepresentation.getObject();
	}		

	@Then("I should get all the available routes")
	public void check() {
	//	Assert.assertTrue("the route list is empty", routeList.getTotal() > 0);
	}

}
