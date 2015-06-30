package com.zebra.rest.resource.v1;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.rest.resource.ZebraResource;
import com.zebra.solr.service.CrawlService;
import org.apache.log4j.Logger;

@Component("/v1/solr/crawl/{collection}")
@Scope("prototype")
public class CrawlerResource extends ZebraResource {

    private static final String PARAM_SITEMAP = "sitemapUrl";
    private static final String PARAM_COLLECTION = "collection";
    private static final String PARAM_RESOURCE_TYPE = "resourceType";
    private static final Logger log = Logger.getLogger(CrawlerResource.class);

    @Autowired
    private CrawlService crawlService;

    private String collection;
    private String sitemap;
    private String resourceType;

    @Override
    public String getName() {
        return "Solr Search resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a Solr Search Crawler";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        this.collection = (String) getRequest().getAttributes().get(PARAM_COLLECTION);
        this.sitemap = getQueryValue(PARAM_SITEMAP);
        this.resourceType = getQueryValue(PARAM_RESOURCE_TYPE);
    }

    @Get("json")
    public Representation getOperation() {
        JsonRepresentation solrRepresentation = null;
        try {

            String result = crawlService.crawlFromSitemap(sitemap, collection, resourceType);
            solrRepresentation = new JsonRepresentation(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return solrRepresentation;
    }
}
