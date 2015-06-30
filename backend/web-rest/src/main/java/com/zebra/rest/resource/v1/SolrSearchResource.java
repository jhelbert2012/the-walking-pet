package com.zebra.rest.resource.v1;

import org.apache.log4j.Logger;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.rest.resource.ZebraResource;
import com.zebra.solr.request.SolrSearchRequest;
import com.zebra.solr.service.SolrSearchService;

@Component("/v1/solr/{locale}/select")
@Scope("prototype")
public class SolrSearchResource extends ZebraResource {

    private static final String PARAM_LOCALE = "locale";
    private static final Logger log = Logger.getLogger(SolrSearchResource.class);

    @Autowired
    private SolrSearchService searchService;

    private String locale;
    
    private SolrSearchRequest request;

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
        request = new SolrSearchRequest();

        request.withFacet(getQueryValue(SolrSearchRequest.PARAM_FACET))
                .withFacetLimit(getQueryValue(SolrSearchRequest.PARAM_FACET_LIMIT))
                .withFacetMinCount(getQueryValue(SolrSearchRequest.PARAM_FACET_MIN_COUNT))
                .withFacetSort(getQueryValue(SolrSearchRequest.PARAM_FACET_SORT))
                .withFacetPrefix(getQueryValue(SolrSearchRequest.PARAM_FACET_PREFIX))
                .withHl(getQueryValue(SolrSearchRequest.PARAM_HL))
                .withHlFragSize(getQueryValue(SolrSearchRequest.PARAM_HL_FRAGSIZE))
                .withHlSimplePost(getQueryValue(SolrSearchRequest.PARAM_HL_SIMPLE_POST))
                .withHlSimplePre(getQueryValue(SolrSearchRequest.PARAM_HL_SIMPLE_PRE))
                .withHlSnippets(getQueryValue(SolrSearchRequest.PARAM_HL_SNIPPETS))
                .withJsonNl(getQueryValue(SolrSearchRequest.PARAM_JSON_NL))
                .withJsonWr(getQueryValue(SolrSearchRequest.PARAM_JSON_WR))
                .withQ(getQueryValue(SolrSearchRequest.PARAM_Q))
                .withRows(getQueryValue(SolrSearchRequest.PARAM_ROWS))
                .withSort(getQueryValue(SolrSearchRequest.PARAM_SORT))
                .withWt(getQueryValue(SolrSearchRequest.PARAM_WT))
                .with_(getQueryValue(SolrSearchRequest.PARAM__))
                .withFacetField(getQueryValue(SolrSearchRequest.PARAM_FACET_FIELD));

        this.locale = (String) getRequest().getAttributes().get(PARAM_LOCALE);
    }

    @Get("json")
    public Representation getOperation() {
        JsonRepresentation solrRepresentation = null;
        try {

            String s = searchService.getResults(locale, request);
            solrRepresentation = new JsonRepresentation(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return solrRepresentation;
    }
}
