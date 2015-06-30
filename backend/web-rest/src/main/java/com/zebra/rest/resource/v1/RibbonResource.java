package com.zebra.rest.resource.v1;

import com.zebra.das.service.RibbonService;

import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.model.api.Ribbon;
import com.zebra.rest.mapper.RibbonMapper;
import com.zebra.rest.resource.ZebraResource;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/ribbons/{ribbonId}")
@Scope("prototype")
public class RibbonResource extends ZebraResource {

    @Autowired
    private RibbonService ribbonService;

    @Autowired
    private RibbonMapper ribbonMapper;
    
    private String ribbonId;

    @Override
    public String getName() {
        return "Ribbon resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a Ribbon";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        this.ribbonId = (String) getRequest().getAttributes().get("ribbonId");
    }

    @Get("json")
    public Representation getOperation() {

        // business logic (throw Not Found exception if not found!)
        Ribbon found = ribbonMapper.map(ribbonService.find(ribbonId));
        // Prepare response
        setStatus(Status.SUCCESS_OK);
        setSelfLink(found);

        JacksonRepresentation<Ribbon> ribbonRepresentation = new JacksonRepresentation<Ribbon>(found);

        return ribbonRepresentation;

    }
}
