package com.zebra.rest.resource.v1;

import com.zebra.das.service.PartnerService;

import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.model.api.Partner;
import com.zebra.rest.mapper.PartnerMapper;
import com.zebra.rest.resource.ZebraResource;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/partners/{partnerId}")
@Scope("prototype")
public class PartnerResource extends ZebraResource {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PartnerMapper partnerMapper;

    private String partnerId;

    @Override
    public String getName() {
        return "Partner resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a Partner";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        this.partnerId = (String) getRequest().getAttributes().get("partnerId");
    }

    @Get("json")
    public Representation getOperation() {

        // business logic (throw Not Found exception if not found!)
        Partner found = partnerMapper.map(partnerService.find(partnerId));

        // Prepare response
        setStatus(Status.SUCCESS_OK);
        setSelfLink(found);

        JacksonRepresentation<Partner> partnerRepresentation = new JacksonRepresentation<Partner>(found);

        return partnerRepresentation;

    }
}
