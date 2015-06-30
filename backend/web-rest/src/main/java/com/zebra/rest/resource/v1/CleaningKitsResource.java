package com.zebra.rest.resource.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.zebra.constant.model.Dmar;
import com.zebra.das.service.SyncDataService;
import com.zebra.model.api.CleaningKit;
import com.zebra.model.api.CleaningKitList;
import com.zebra.model.api.Facet;
import com.zebra.model.api.Printer;
import com.zebra.rest.resource.ZebraResource;
import com.zebra.rest.temp.PersistenceStorage;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/cleaningkits")
@Scope("prototype")
public class CleaningKitsResource extends ZebraResource {

    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private PersistenceStorage persistenceStorage;

    private static final String PARAM_PRINTER = "printer";

    private static final String PARAM_SHOWRESULTS = "showResults";
    private static final String PARAM_DMAR = "dmar";

    private static final String[] ORDERBY_VALUES = {};

    private String[] printers;
    private String dmar;
    private boolean showResults = true;

    @Override
    protected String[] getAcceptedOrderByValues() {
        return ORDERBY_VALUES;
    }

    @Override
    public String getName() {
        return "Cleaning Kits resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing the Cleaning Kits";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        if (Method.GET.equals(getMethod())) {
            printers = getRequest().getResourceRef().getQueryAsForm().getValuesArray(PARAM_PRINTER, true);
            dmar = StringUtils.upperCase(getQueryValue(PARAM_DMAR));
            if (Boolean.FALSE.toString().equalsIgnoreCase(getQueryValue(PARAM_SHOWRESULTS))) {
                showResults = false;
            }
        }

    }

    @Get("json")
    public Representation getOperation() {

        List<CleaningKit> results = persistenceStorage.getStorage().getCleaningKits();

        results = filterResults(results);

        CleaningKitList cleaningKits = new CleaningKitList(results, new Long(results.size()));

        // prepare response
        setResourceListElementSelfLink(cleaningKits);
        setSelfLink(cleaningKits);
        // setPaginationLinks(accessories);
        setFacets(cleaningKits);
        if (!showResults) {
            cleaningKits.setResources(null);
        }

        JacksonRepresentation<CleaningKitList> cleaningKitsRepresentation = new JacksonRepresentation<CleaningKitList>(
                cleaningKits);

        return cleaningKitsRepresentation;
    }

    private List<CleaningKit> filterResults(List<CleaningKit> results) {
        if (ArrayUtils.isNotEmpty(printers)) {
            List<CleaningKit> filteredResults = new ArrayList<CleaningKit>();

            cleaningKitLoop:
            for (CleaningKit cleaningKit : results) {
                for (String printerId : printers) {
                    if (cleaningKit.hasPrinter(printerId)) {
                        filteredResults.add(cleaningKit);
                        continue cleaningKitLoop;
                    }
                }
            }
            results = filteredResults;
        }

        if (StringUtils.isNotBlank(dmar)) {
            Dmar validatedDmar = null;
            try {
                validatedDmar = Dmar.valueOf(dmar);
            } catch (Exception e) {
            }

            if (validatedDmar == null) {
                results = new ArrayList<CleaningKit>();
            } else {
                List<CleaningKit> filteredResults = new ArrayList<CleaningKit>();

                for (CleaningKit accessory : results) {
                    String dmarSupplySku = null;
                    if (accessory.getDmarSkus() != null) {
                        dmarSupplySku = accessory.getDmarSkus().get(validatedDmar);
                    }

                    if (StringUtils.isNotBlank(dmarSupplySku)) {
                        accessory.setDmarSku(dmarSupplySku);
                        filteredResults.add(accessory);
                    }
                }
                results = filteredResults;
            }
        }

        return results;
    }

    private void setFacets(CleaningKitList cleaningKits) {

        Map<String, List<Facet>> facets = new HashMap<String, List<Facet>>();

        List<Facet> printerFacets = new ArrayList<Facet>();

        for (Printer printer : persistenceStorage.getStorage().getPrinters()) {

            int counter = 0;
            for (CleaningKit cleaningKit : cleaningKits.getResources()) {
                for (Printer matPrinter : cleaningKit.getPrinters()) {
                    if (printer.getId().equals(matPrinter.getId())) {
                        counter++;
                        break;
                    }
                }
            }
            if (counter > 0) {
                Facet facet = new Facet();
                facet.setCount(counter);
                facet.setLabel(printer.getName());
                facet.setValue(printer.getId());
                facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_PRINTER, printer.getId())
                        .toString());

                // only add if not already filtered
                if (!ArrayUtils.contains(printers, printer.getId())) {
                    printerFacets.add(facet);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(printerFacets)) {
            facets.put(PARAM_PRINTER, printerFacets);
        }

        sortFacets(facets);

        cleaningKits.setFacets(facets);
    }

}
