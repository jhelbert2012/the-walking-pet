package com.zebra.rest.resource.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.constant.model.Application;
import com.zebra.constant.model.PartnerType;
import com.zebra.constant.model.Region;
import com.zebra.constant.model.VerticalMarket;
import com.zebra.constant.util.ConstantUtil;
import com.zebra.model.api.Facet;
import com.zebra.model.api.Partner;
import com.zebra.model.api.PartnerList;
import com.zebra.rest.resource.ZebraResource;
import com.zebra.rest.temp.PersistenceStorage;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/partners")
@Scope("prototype")
public class PartnersResource extends ZebraResource {
    
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_REGION = "region";
    private static final String PARAM_VERTICAL = "vertical";
    private static final String PARAM_APPLICATION = "application";
    private static final String PARAM_VALIDATED = "validated";
    private static final String PARAM_SHOWRESULTS = "showResults";

    private static final String SORTBY_VALUES_NAME = "name";
    private static final String[] SORTBY_VALUES = {SORTBY_VALUES_NAME};
    
    @Autowired
    private PersistenceStorage persistenceStorage;

    private String type;
    private String[] regions;
    private String[] verticals;
    private String[] applications;
    private String validated;
    private boolean showResults = true;

    @Override
    protected String[] getAcceptedOrderByValues() {
        return SORTBY_VALUES;
    }

    @Override
    public String getName() {
        return "Partners resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing the Partners";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        if (Method.GET.equals(getMethod())) {
            type = StringUtils.upperCase(getQueryValue(PARAM_TYPE));
            regions = getRequest().getResourceRef().getQueryAsForm().getValuesArray(PARAM_REGION, true);
            verticals = getRequest().getResourceRef().getQueryAsForm().getValuesArray(PARAM_VERTICAL, true);
            applications = getRequest().getResourceRef().getQueryAsForm().getValuesArray(PARAM_APPLICATION, true);
            if (Boolean.FALSE.toString().equalsIgnoreCase(getQueryValue(PARAM_SHOWRESULTS))) {
                showResults = false;
            }
            validated = StringUtils.upperCase(getQueryValue(PARAM_VALIDATED));

        }

    }

    @Get("json")
    public Representation getOperation() {

        List<Partner> results = persistenceStorage.getStorage().getPartners();
        results = filterResults(results);

        sortResults(results);

        PartnerList partners = new PartnerList(results, new Long(results.size()));

        // prepare response
        setResourceListElementSelfLink(partners);
        setSelfLink(partners);
        setFacets(partners);
        // setPaginationLinks(printers);

        if (!showResults) {
            partners.setResources(null);
        }

        JacksonRepresentation<PartnerList> partnersRepresentation = new JacksonRepresentation<PartnerList>(partners);

        return partnersRepresentation;
    }

    private void setFacets(PartnerList partnerList) {

        Map<String, List<Facet>> facets = new HashMap<String, List<Facet>>();

        if (StringUtils.isEmpty(type)) {
            List<Facet> tFacets = new ArrayList<Facet>();

            for (PartnerType t : PartnerType.values()) {
                int counter = 0;
                for (Partner partner : partnerList.getResources()) {
                    if (t.equals(partner.getType())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(t, Locale.ENGLISH));
                    facet.setValue(t.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_TYPE, t.name()).toString());
                    tFacets.add(facet);
                }
            }
            if (CollectionUtils.isNotEmpty(tFacets)) {
                facets.put(PARAM_TYPE, tFacets);
            }
        }

        List<Facet> rFacets = new ArrayList<Facet>();
        for (Region r : Region.values()) {
            int counter = 0;
            for (Partner partner : partnerList.getResources()) {
                if (partner.getRegions().contains(r)) {
                    counter++;
                }
            }

            if (counter > 0) {
                Facet facet = new Facet();
                facet.setCount(counter);
                facet.setLabel(ConstantUtil.value(r, Locale.ENGLISH));
                facet.setValue(r.name());
                facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_REGION, r.name()).toString());
                rFacets.add(facet);
            }
        }
        if (CollectionUtils.isNotEmpty(rFacets)) {
            facets.put(PARAM_REGION, rFacets);
        }

        List<Facet> vFacets = new ArrayList<Facet>();
        for (VerticalMarket v : VerticalMarket.values()) {
            int counter = 0;
            for (Partner partner : partnerList.getResources()) {
                if (partner.getVerticalMarkets().contains(v)) {
                    counter++;
                }
            }

            if (counter > 0) {
                Facet facet = new Facet();
                facet.setCount(counter);
                facet.setLabel(ConstantUtil.value(v, Locale.ENGLISH));
                facet.setValue(v.name());
                facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_VERTICAL, v.name()).toString());
                vFacets.add(facet);
            }
        }
        if (CollectionUtils.isNotEmpty(vFacets)) {
            facets.put(PARAM_VERTICAL, vFacets);
        }

        List<Facet> aFacets = new ArrayList<Facet>();
        for (Application a : Application.values()) {
            int counter = 0;
            for (Partner partner : partnerList.getResources()) {
                if (partner.getApplications().contains(a)) {
                    counter++;
                }
            }

            if (counter > 0) {
                Facet facet = new Facet();
                facet.setCount(counter);
                facet.setLabel(ConstantUtil.value(a, Locale.ENGLISH));
                facet.setValue(a.name());
                facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_APPLICATION, a.name()).toString());
                aFacets.add(facet);
            }
        }
        if (CollectionUtils.isNotEmpty(aFacets)) {
            facets.put(PARAM_APPLICATION, aFacets);
        }

        if (StringUtils.isEmpty(validated)) {
            List<Facet> valFacets = new ArrayList<Facet>();

            for (com.zebra.constant.model.Boolean v : com.zebra.constant.model.Boolean.values()) {
                int counter = 0;
                for (Partner partner : partnerList.getResources()) {
                    if (v.equals(partner.getValidated())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(v, Locale.ENGLISH));
                    facet.setValue(v.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_VALIDATED, v.name()).toString());
                    valFacets.add(facet);
                }
            }
            if (CollectionUtils.isNotEmpty(valFacets)) {
                facets.put(PARAM_VALIDATED, valFacets);
            }
        }

        sortFacets(facets);

        partnerList.setFacets(facets);
    }

    private void sortResults(List<Partner> results) {

        // defaulting if not provided
        sortBy = StringUtils.isBlank(sortBy) ? SORTBY_VALUES_NAME : sortBy;

        Comparator<Partner> comparator = null;
        if (StringUtils.equals(SORTBY_VALUES_NAME, sortBy)) {
            comparator = new NameComparator();
        }

        if (!sortOrder.isAscending()) {
            comparator = Collections.reverseOrder(comparator);
        }

        Collections.sort(results, comparator);

    }

    private List<Partner> filterResults(List<Partner> results) {

        if (StringUtils.isNotBlank(type)) {
            List<Partner> filteredResults = new ArrayList<Partner>();

            for (Partner partner : results) {
                if (partner.getType() != null && partner.getType().name().equalsIgnoreCase(type)) {
                    filteredResults.add(partner);
                }
            }

            results = filteredResults;
        }

        if (ArrayUtils.isNotEmpty(regions)) {
            List<Partner> filteredResults = new ArrayList<Partner>();

            for (Partner partner : results) {

                int hits = 0;
                for (String regionId : regions) {
                    if (partner.hasRegion(regionId)) {
                        hits++;
                    }
                }

                if (hits == regions.length) {
                    filteredResults.add(partner);
                }

            }
            results = filteredResults;
        }

        if (ArrayUtils.isNotEmpty(verticals)) {
            List<Partner> filteredResults = new ArrayList<Partner>();

            for (Partner partner : results) {

                int hits = 0;
                for (String verticalId : verticals) {
                    if (partner.hasVerticalMarket(verticalId)) {
                        hits++;
                    }
                }

                if (hits == verticals.length) {
                    filteredResults.add(partner);
                }

            }
            results = filteredResults;
        }

        if (ArrayUtils.isNotEmpty(applications)) {
            List<Partner> filteredResults = new ArrayList<Partner>();

            for (Partner partner : results) {

                int hits = 0;
                for (String applicationId : applications) {
                    if (partner.hasApplication(applicationId)) {
                        hits++;
                    }
                }

                if (hits == applications.length) {
                    filteredResults.add(partner);
                }

            }
            results = filteredResults;
        }

        if (StringUtils.isNotBlank(validated)) {
            List<Partner> filteredResults = new ArrayList<Partner>();

            for (Partner partner : results) {
                if (partner.getValidated() != null && partner.getValidated().name().equalsIgnoreCase(validated)) {
                    filteredResults.add(partner);
                }
            }

            results = filteredResults;
        }

        return results;
    }

    public class NameComparator implements Comparator<Partner> {

        @Override
        public int compare(Partner partner1, Partner partner2) {
            return partner1.getName().toLowerCase().compareTo(partner2.getName().toLowerCase());
        }

    }

}
