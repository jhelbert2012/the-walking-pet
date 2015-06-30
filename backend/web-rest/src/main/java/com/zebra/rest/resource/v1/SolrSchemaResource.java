package com.zebra.rest.resource.v1;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.solr.service.SolrSearchService;
import com.zebra.rest.resource.ZebraResource;

@Component("/v1/solr/schema")
@Scope("prototype")
public class SolrSchemaResource extends ZebraResource {

    private static final String PARAM_LOCALE = "locale";

    @Autowired
    private SolrSearchService schemaService;

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

        this.locale = getQueryValue(PARAM_LOCALE);
    }

    @Get("json")
    public Representation getOperation() {
        JsonRepresentation materialRepresentation = null;
        try {

            String s = schemaService.getSchema(locale);
            materialRepresentation = new JsonRepresentation(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialRepresentation;
    }
}
