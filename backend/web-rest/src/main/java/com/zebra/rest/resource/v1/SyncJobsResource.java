package com.zebra.rest.resource.v1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Method;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.das.service.SyncJobService;
import com.zebra.model.api.SyncJobList;
import com.zebra.model.api.SyncJob;
import com.zebra.rest.mapper.SyncJobMapper;
import com.zebra.rest.resource.ZebraResource;
import com.zebra.sync.service.SynchronizationRunnerService;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/syncjobs")
@Scope("prototype")
public class SyncJobsResource extends ZebraResource {

    @Autowired
    private SyncJobService syncJobService;
    
    @Autowired
    private SynchronizationRunnerService syncRunnerService;

    @Autowired
    private SyncJobMapper syncJobMapper;

    private static final String SORTBY_VALUES_ID = "id";
    private static final String[] SORTBY_VALUES = {SORTBY_VALUES_ID};

    @Override
    protected String[] getAcceptedOrderByValues() {
        return SORTBY_VALUES;
    }

    @Override
    public String getName() {
        return "SyncJobs resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing the SyncJobs";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        if (Method.GET.equals(getMethod())) {
//            dmar = StringUtils.upperCase(getQueryValue(PARAM_DMAR));
        }
        if (Method.POST.equals(getMethod())) {

        }

    }

    @Get("json")
    public Representation getOperation() {

        List<SyncJob> results = syncJobMapper.map(syncJobService.findAllWithData());

        results = filterResults(results);

        sortResults(results);

        SyncJobList syncJobs = new SyncJobList(results, new Long(results.size()));

        // prepare response
        setResourceListElementSelfLink(syncJobs);
        setSelfLink(syncJobs);
        // setPaginationLinks(printers);

        JacksonRepresentation<SyncJobList> syncJobsRepresentation = new JacksonRepresentation<>(syncJobs);

        return syncJobsRepresentation;
    }

    @Post("json")
    public Representation executeSync() {
        //execute all syncs
        syncRunnerService.synchronizeAll();
        
        String status = "OK";

        JacksonRepresentation<String> syncJobsRepresentation = new JacksonRepresentation<>(status);

        return syncJobsRepresentation;
    }

    private void sortResults(List<SyncJob> results) {

        // defaulting if not provided
        sortBy = StringUtils.isBlank(sortBy) ? SORTBY_VALUES_ID : sortBy;

        Comparator<SyncJob> comparator = null;
        if (StringUtils.equals(SORTBY_VALUES_ID, sortBy)) {
            comparator = new IdComparator();
        }

        if (!sortOrder.isAscending()) {
            comparator = Collections.reverseOrder(comparator);
        }

        Collections.sort(results, comparator);

    }

    private List<SyncJob> filterResults(List<SyncJob> results) {

        return results;
    }

    public class IdComparator implements Comparator<SyncJob> {

        @Override
        public int compare(SyncJob syncJob1, SyncJob syncJob2) {
            return syncJob1.getId().toLowerCase().compareTo(syncJob2.getId().toLowerCase());
        }

    }
}
