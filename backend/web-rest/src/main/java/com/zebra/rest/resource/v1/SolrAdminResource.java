package com.zebra.rest.resource.v1;

import com.zebra.solr.request.AdminSearchRequest;
import com.zebra.solr.service.SolrSearchService;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.rest.resource.ZebraResource;

@Component("/v1/solr/admin/{adminTask}")
@Scope("prototype")
public class SolrAdminResource extends ZebraResource {

    private static final String PARAM_LOCALE = "locale";
    private static final String CORES = "cores";

    private String adminTask;

    @Autowired
    private SolrSearchService adminService;

    private AdminSearchRequest request;

    String locale;

    @Override
    public String getName() {
        return "Solr Search resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a Solr Search Kit";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        this.adminTask = (String) getRequest().getAttributes().get("adminTask");

        this.locale = getQueryValue(PARAM_LOCALE);
    }

    @Get("json")
    public Representation getOperation() {
        JsonRepresentation materialRepresentation = null;
        if (adminTask.equalsIgnoreCase(CORES)) {
            try {
                request = new AdminSearchRequest();
                
                request.withWt(getQueryValue(AdminSearchRequest.PARAM_WT))
                        .withAction(getQueryValue(AdminSearchRequest.PARAM_ACTION));
                String s = adminService.getCores(request);
                materialRepresentation = new JsonRepresentation(s);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return materialRepresentation;
    }

}
