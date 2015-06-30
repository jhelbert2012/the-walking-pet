package com.zebra.rest.resource.v1;


import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.rest.resource.ZebraResource;
import com.zebra.rest.temp.PersistenceStorage;
import org.apache.log4j.Logger;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/admintasks/{adminTask}")
@Scope("prototype")
public class AdminTaskResource extends ZebraResource {

    private static final Logger log = Logger.getLogger(AdminTaskResource.class);
    private static final String REFRESH = "refresh";

    @Autowired
    private PersistenceStorage persistenceStorage;

    private String adminTask;

    @Override
    public String getName() {
        return "SyncJob resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a SyncJob";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        this.adminTask = (String) getRequest().getAttributes().get("adminTask");
    }

    @Post("json")
    public Representation postOperation() throws Exception {
        if (adminTask.equalsIgnoreCase(REFRESH)) {
            persistenceStorage.reloadStorage();
            setStatus(Status.SUCCESS_OK);

        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        JacksonRepresentation<String> syncJobRepresentation = new JacksonRepresentation<>(getStatus().getDescription());
        return syncJobRepresentation;
    }

}
