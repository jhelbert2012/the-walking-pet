package com.zebra.rest.resource.v1;

import java.util.ArrayList;
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

import com.zebra.constant.model.Dmar;
import com.zebra.constant.model.RibbonColor;
import com.zebra.constant.util.ConstantUtil;
import com.zebra.model.api.Facet;
import com.zebra.model.api.Printer;
import com.zebra.model.api.Ribbon;
import com.zebra.model.api.RibbonList;
import com.zebra.rest.resource.ZebraResource;
import com.zebra.rest.temp.PersistenceStorage;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/ribbons")
@Scope("prototype")
public class RibbonsResource extends ZebraResource {

    @Autowired
    private PersistenceStorage persistenceStorage;

    private static final String PARAM_PRINTER = "printer";
    private static final String PARAM_RIBBONCOLOR = "ribbonColor";
    private static final String PARAM_SHOWRESULTS = "showResults";
    private static final String PARAM_DMAR = "dmar";

    private String[] printers;
    private String ribbonColor;
    private String dmar;
    private boolean showResults = true;

    @Override
    public String getName() {
        return "Ribbons resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing the Ribbons";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        if (Method.GET.equals(getMethod())) {
            printers = getRequest().getResourceRef().getQueryAsForm().getValuesArray(PARAM_PRINTER, true);
            ribbonColor = getQueryValue(PARAM_RIBBONCOLOR);
            if (Boolean.FALSE.toString().equalsIgnoreCase(getQueryValue(PARAM_SHOWRESULTS))) {
                showResults = false;
            }
            dmar = StringUtils.upperCase(getQueryValue(PARAM_DMAR));
        }

    }

    @Get("json")
    public Representation getOperation() {

        List<Ribbon> results = persistenceStorage.getStorage().getRibbons();

        results = filterResults(results);

        RibbonList ribbons = new RibbonList(results, new Long(results.size()));

        // prepare response
        setResourceListElementSelfLink(ribbons);
        setSelfLink(ribbons);
        // setPaginationLinks(ribbons);
        setFacets(ribbons);
        if (!showResults) {
            ribbons.setResources(null);
        }

        JacksonRepresentation<RibbonList> ribbonsRepresentation = new JacksonRepresentation<>(ribbons);

        return ribbonsRepresentation;
    }

    private List<Ribbon> filterResults(List<Ribbon> results) {
        if (ArrayUtils.isNotEmpty(printers)) {
            List<Ribbon> filteredResults = new ArrayList<>();

            materialLoop:
            for (Ribbon ribbon : results) {
                for (String printerId : printers) {
                    if (ribbon.hasPrinter(printerId)) {
                        filteredResults.add(ribbon);
                        continue materialLoop;
                    }
                }
            }
            results = filteredResults;
        }
        if (StringUtils.isNotBlank(ribbonColor)) {
            List<Ribbon> filteredResults = new ArrayList<>();

            for (Ribbon ribbon : results) {
                if (ribbon.getRibbonColor() != null && ribbon.getRibbonColor().name().equalsIgnoreCase(ribbonColor)) {
                    filteredResults.add(ribbon);
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
                results = new ArrayList<>();
            } else {

                List<Ribbon> filteredResults = new ArrayList<>();

                for (Ribbon ribbon : results) {
                    String dmarRibbonSku = null;
                    if (ribbon.getDmarSkus() != null) {
                        dmarRibbonSku = ribbon.getDmarSkus().get(validatedDmar);
                    }
                    if (StringUtils.isNotBlank(dmarRibbonSku)) {
                        ribbon.setDmarSku(dmarRibbonSku);
                        filteredResults.add(ribbon);
                    }

                }

                results = filteredResults;
            }
        }

        return results;
    }

    private void setFacets(RibbonList materials) {

        Map<String, List<Facet>> facets = new HashMap<>();

        if (StringUtils.isEmpty(ribbonColor)) {
            List<Facet> rcFacets = new ArrayList<>();

            for (RibbonColor c : RibbonColor.values()) {
                int counter = 0;
                for (Ribbon ribbon : materials.getResources()) {
                    if (c.equals(ribbon.getRibbonColor())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(c, Locale.ENGLISH));
                    facet.setValue(c.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_RIBBONCOLOR, c.name())
                            .toString());
                    rcFacets.add(facet);
                }
            }

            if (CollectionUtils.isNotEmpty(rcFacets)) {
                facets.put(PARAM_RIBBONCOLOR, rcFacets);
            }
        }

        List<Facet> printerFacets = new ArrayList<>();
        for (Printer printer : persistenceStorage.getStorage().getPrinters()) {
            int counter = 0;
            for (Ribbon ribbon : materials.getResources()) {
                for (Printer ribbonPrinter : ribbon.getPrinters()) {
                    if (printer.getId().equals(ribbonPrinter.getId())) {
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

        materials.setFacets(facets);
    }

}
