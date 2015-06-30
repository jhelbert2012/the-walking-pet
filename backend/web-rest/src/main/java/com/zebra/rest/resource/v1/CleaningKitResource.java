package com.zebra.rest.resource.v1;

import com.zebra.das.service.CleaningKitService;
import com.zebra.das.service.SyncDataService;

import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.model.api.CleaningKit;
import com.zebra.rest.mapper.CleaningKitMapper;
import com.zebra.rest.resource.ZebraResource;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/cleaningkits/{cleaningkitId}")
@Scope("prototype")
public class CleaningKitResource extends ZebraResource {

    @Autowired
    private CleaningKitService cleaningKitService;

    @Autowired
    private CleaningKitMapper cleaningKitMapper;

    @Autowired
    private SyncDataService syncDataService;

    private String cleaningkitId;

    @Override
    public String getName() {
        return "Cleaning Kit resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a Cleaning Kit";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        this.cleaningkitId = (String) getRequest().getAttributes().get("cleaningkitId");
    }

    @Get("json")
    public Representation getOperation() {

        // business logic (throw Not Found exception if not found!)
        CleaningKit found = cleaningKitMapper.map(cleaningKitService.find(cleaningkitId));

        // Prepare response
        setStatus(Status.SUCCESS_OK);
        setSelfLink(found);

        JacksonRepresentation<CleaningKit> cleaningKitRepresentation = new JacksonRepresentation<>(found);

        return cleaningKitRepresentation;

    }
}
