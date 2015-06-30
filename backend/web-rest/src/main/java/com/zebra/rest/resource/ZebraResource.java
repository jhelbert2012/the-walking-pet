package com.zebra.rest.resource;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.wadl.MethodInfo;
import org.restlet.ext.wadl.WadlServerResource;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.zebra.constant.api.LinkType;
import com.zebra.context.ContextHolder;
import com.zebra.model.api.Facet;
import com.zebra.model.api.HateoasResource;
import com.zebra.model.api.HateoasResourceList;
import com.zebra.model.api.HateoasSingleResource;
import com.zebra.rest.resource.validator.PayloadValidator;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;

public abstract class ZebraResource extends WadlServerResource {

    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final SortOrder DEFAULT_SORTORDER = SortOrder.ASC;
    private static final String HTTP_PROTOCOL_URL = "http://";

    protected static final String PARAM_PAGENUMBER = "page";
    protected static final String PARAM_PAGESIZE = "pageSize";
    protected static final String PARAM_SORTBY = "sortBy";
    protected static final String PARAM_SORTORDER = "sortOrder";

    protected String sortBy;
    protected SortOrder sortOrder = DEFAULT_SORTORDER;
    protected Integer page = DEFAULT_PAGE_NUMBER;
    protected Integer pageSize = DEFAULT_PAGE_SIZE;

    @Resource
    protected PayloadValidator validator;

    public ZebraResource() {
    }

    public void setValidator(PayloadValidator validator) {
        this.validator = validator;
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        if (Method.GET.equals(getMethod())) {
            try {
                int pageParam = Integer.parseInt(getQueryValue(PARAM_PAGENUMBER));
                if (pageParam > 0) {
                    page = pageParam;
                }
            } catch (NumberFormatException e) {
            }
            try {
                int pageSizeParam = Integer.parseInt(getQueryValue(PARAM_PAGESIZE));
                if (pageSizeParam > 0) {
                    pageSize = pageSizeParam;
                }
            } catch (NumberFormatException e) {
            }

            String sortByParam = getQueryValue(PARAM_SORTBY);
            if (ArrayUtils.contains(getAcceptedOrderByValues(), sortByParam)) {
                sortBy = sortByParam;
            }

            try {
                String sortOrderQueryValue = getQueryValue(PARAM_SORTORDER);
                if (StringUtils.isNotBlank(sortOrderQueryValue)) {
                    SortOrder sortOrderParam = SortOrder.valueOf(sortOrderQueryValue);
                    if (sortOrderParam != null) {
                        sortOrder = sortOrderParam;
                    }
                }
            } catch (IllegalArgumentException e) {
            }
        }
    }

    protected String[] getAcceptedOrderByValues() {
        return null;
    }

    protected void setSelfLink(HateoasResource resource) {
        // TODO sanitize the URL just in the case of invalid parameters
        resource.links.put(LinkType.self, replaceProtocol(getReference().getIdentifier()));
    }

    protected void setPaginationLinks(HateoasResourceList<? extends HateoasResource> resourceList) {

        // TODO sanitize the URL just in the case of invalid parameters
        if (resourceList.getTotal() > page * pageSize) {
            Form query = getReference().getQueryAsForm();
            query.set(PARAM_PAGENUMBER, Integer.toString(page + 1));
            getReference().setQuery(query.getQueryString());
            resourceList.links.put(LinkType.nextPage, replaceProtocol(getReference().toString()));
        }
        if (page > 1) {
            Form query = getReference().getQueryAsForm();
            query.set(PARAM_PAGENUMBER, Integer.toString(page - 1));
            getReference().setQuery(query.getQueryString());
            resourceList.links.put(LinkType.previousPage, replaceProtocol(getReference().toString()));
        }
    }

    protected void setResourceListElementSelfLink(HateoasResourceList<? extends HateoasSingleResource> resourceList) {
        for (HateoasSingleResource resource : resourceList.getResources()) {
            resource.links.put(LinkType.self, buildSelfLink(resource, true));
        }
    }

    protected String buildSelfLink(HateoasSingleResource resource, boolean appendId) {
        if (appendId) {
            return replaceProtocol((getReference()).getBaseRef() + "/" + resource.getId());
        } else {
            return replaceProtocol((getReference()).getBaseRef().toString());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected <T extends HateoasSingleResource> T getRiapResource(T resource, String riapURI) throws IOException {

        ClientResource cr = new ClientResource(riapURI + resource.getId());
        Representation entity = cr.get();

        JacksonRepresentation<T> representation = new JacksonRepresentation(entity, resource.getClass());

        T riapResource = representation.getObject();

        // replace riap uri with http uri
        String hostIdentifier = getReference().getHostIdentifier();
        for (Entry<LinkType, String> entry : riapResource.links.entrySet()) {
            entry.setValue(StringUtils.replace(entry.getValue(), "riap://application", hostIdentifier));
        }

        return riapResource;
    }

    // SELF DOCUMENTATION VIA OPTIONS in WADL FORMAT
    @Override
    public abstract String getName();

    @Override
    public abstract String getDescription();

    @Override
    protected void describeGet(MethodInfo info) {
        super.describeGet(info);
        info.setDocumentation("[Generic Documentation] This method is a read-only operation, hence it is idempotent and safe.");
    }

    @Override
    protected void describeDelete(MethodInfo info) {
        super.describeDelete(info);
        info.setDocumentation("[Generic Documentation] This method is a delete operation, it is idempotent because the resource cannot be deleted twice and it is NOT safe since it changes the state on the server.");
    }

    @Override
    protected void describeOptions(MethodInfo info) {
        super.describeOptions(info);
        info.setDocumentation("[Generic Documentation] This method is not an operation. It returns the information about the methods that can be executed on the resource.");
    }

    @Override
    protected void describePost(MethodInfo info) {
        super.describePost(info);
        info.setDocumentation("[Generic Documentation] This method is a create operation, it is NOT idempotent since it will create a new resource each time is called and NOT safe since it changes the state on the server.");
    }

    @Override
    protected void describePut(MethodInfo info) {
        super.describePut(info);
        info.setDocumentation("[Generic Documentation] This method is an update operation, it is idempotent because the resource is overwriten each time is called and it is NOT safe since it changes the state on the server.");
    }

    public enum SortOrder {

        ASC,
        DESC;

        public boolean isAscending() {
            return this.equals(ASC);
        }
    }

    private static final FacetComparator facetComparator = new FacetComparator();

    public void sortFacets(Map<String, List<Facet>> facets) {
        for (Entry<String, List<Facet>> searchFacet : facets.entrySet()) {
            Collections.sort(searchFacet.getValue(), facetComparator);
        }
    }

    private static class FacetComparator implements Comparator<Facet> {

        @Override
        public int compare(Facet facet1, Facet facet2) {
            return facet1.getLabel().toLowerCase().compareTo(facet2.getLabel().toLowerCase());
        }

    }

    @Override
    public Reference getReference() {
        if (!StringUtils.isEmpty(ContextHolder.getProtocol())) {
            super.getReference().setProtocol(Protocol.valueOf(ContextHolder.getProtocol()));
        }
        return super.getReference();
    }

    private String replaceProtocol(String str) {
        if (str.contains(HTTP_PROTOCOL_URL) && !StringUtils.isEmpty(ContextHolder.getProtocol())) {
            return str.replace(HTTP_PROTOCOL_URL, ContextHolder.getProtocol() + "://");
        }
        return str;
    }

}
