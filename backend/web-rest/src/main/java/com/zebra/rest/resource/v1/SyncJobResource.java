package com.zebra.rest.resource.v1;

import com.zebra.das.service.SyncJobService;

import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.model.api.SyncJob;
import com.zebra.rest.mapper.SyncJobMapper;
import com.zebra.rest.mapper.exception.MapperException;
import com.zebra.rest.resource.ZebraResource;
import com.zebra.rest.temp.PersistenceStorage;
import com.zebra.sync.service.SynchronizationRunnerService;
import org.apache.log4j.Logger;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/syncjobs/{syncJobId}")
@Scope("prototype")
public class SyncJobResource extends ZebraResource {

    private static final Logger log = Logger.getLogger(SyncJobResource.class);

    @Autowired
    private SyncJobService syncJobService;

    @Autowired
    private SyncJobMapper syncJobMapper;

    @Autowired
    private SynchronizationRunnerService syncRunnerService;
    
    @Autowired
    private PersistenceStorage persistenceStorage;

    private String syncJobId;

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

        this.syncJobId = (String) getRequest().getAttributes().get("syncJobId");
    }

    @Get("json")
    public Representation getOperation() {

        // business logic (throw Not Found exception if not found!)
        SyncJob found;
        try {
            found = syncJobMapper.map(syncJobService.find(syncJobId));
            setSelfLink(found);
            JacksonRepresentation<SyncJob> syncJobRepresentation = new JacksonRepresentation<>(found);
            setStatus(Status.SUCCESS_OK);
            return syncJobRepresentation;
        } catch (MapperException ex) {
            setStatus(Status.SERVER_ERROR_INTERNAL);
            log.error(ex);
        }
        return null;

    }

    @Post("json")
    public Representation postOperation() throws Exception {
        syncRunnerService.synchronize(syncJobId);
        persistenceStorage.reloadStorage();
        setStatus(Status.SUCCESS_OK);
        JacksonRepresentation<String> syncJobRepresentation = new JacksonRepresentation<>(getStatus().getDescription());
        return syncJobRepresentation;

    }
}
