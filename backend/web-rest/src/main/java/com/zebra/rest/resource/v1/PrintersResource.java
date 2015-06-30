package com.zebra.rest.resource.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Method;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.constant.model.Dmar;
import com.zebra.model.api.Material;
import com.zebra.model.api.PrintHead;
import com.zebra.model.api.Printer;
import com.zebra.model.api.PrinterList;
import com.zebra.rest.resource.ZebraResource;
import com.zebra.rest.temp.PersistenceStorage;

import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/printers")
@Scope("prototype")
public class PrintersResource extends ZebraResource {

    @Autowired
    private PersistenceStorage persistenceStorage;
    
    private static final String PARAM_DMAR = "dmar";
    private static final String PARAM_PRINTHEAD = "printhead";

    private static final String SORTBY_VALUES_ID = "id";
    private static final String SORTBY_VALUES_NAME = "name";
    private static final String[] SORTBY_VALUES = {SORTBY_VALUES_ID, SORTBY_VALUES_NAME};

    private String dmar;
    private boolean printhead = false;

    @Override
    protected String[] getAcceptedOrderByValues() {
        return SORTBY_VALUES;
    }

    @Override
    public String getName() {
        return "Printers resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing the Printers";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        if (Method.GET.equals(getMethod())) {
            dmar = StringUtils.upperCase(getQueryValue(PARAM_DMAR));
            if (Boolean.TRUE.toString().equalsIgnoreCase(getQueryValue(PARAM_PRINTHEAD))) {
                printhead = true;
            }
        }

    }

    @Get("json")
    public Representation getOperation() {

        List<Printer> results = persistenceStorage.getStorage().getPrinters();

        results = filterResults(results);

        sortResults(results);

        PrinterList printers = new PrinterList(results, new Long(results.size()));

        // prepare response
        setResourceListElementSelfLink(printers);
        setSelfLink(printers);
        // setPaginationLinks(printers);

        JacksonRepresentation<PrinterList> printersRepresentation = new JacksonRepresentation<PrinterList>(printers);

        return printersRepresentation;
    }

    private void sortResults(List<Printer> results) {

        // defaulting if not provided
        sortBy = StringUtils.isBlank(sortBy) ? SORTBY_VALUES_NAME : sortBy;

        Comparator<Printer> comparator = null;
        if (StringUtils.equals(SORTBY_VALUES_ID, sortBy)) {
            comparator = new IdComparator();
        } else if (StringUtils.equals(SORTBY_VALUES_NAME, sortBy)) {
            comparator = new NameComparator();
        }

        if (!sortOrder.isAscending()) {
            comparator = Collections.reverseOrder(comparator);
        }

        Collections.sort(results, comparator);

    }

    private List<Printer> filterResults(List<Printer> results) {

        if (StringUtils.isNotBlank(dmar)) {
            Dmar validatedDmar = null;
            try {
                validatedDmar = Dmar.valueOf(dmar);
            } catch (Exception e) {
            }

            if (validatedDmar == null) {
                results = new ArrayList<Printer>();
            } else {
                // Build a list of Printers based on the materials offered by
                // the dmar

                List<Material> filteredSupplyList = new ArrayList<Material>();
                for (Material supply : persistenceStorage.getStorage().getMaterials()) {
                    String dmarSupplySku = null;
                    if (supply.getDmarSkus() != null) {
                        dmarSupplySku = supply.getDmarSkus().get(validatedDmar);
                    }
                    if (StringUtils.isNotBlank(dmarSupplySku)) {
                        filteredSupplyList.add(supply);
                    }
                }

                Set<Printer> printers = new HashSet<Printer>();
                for (Material supply : filteredSupplyList) {
                    printers.addAll(supply.getPrinters());
                }

                results = new ArrayList<Printer>(printers);
            }
        }

        List<Printer> printersFiltered = new ArrayList<Printer>();
        if (!printhead) {
        	for (Printer printer : results) {
        		if (StringUtils.isNotBlank(printer.getImageLink())) {
        			printersFiltered.add(printer);
        		}
			}
		} else {
			for (Printer printer : results) {
				if(!printer.getPrintHeads().isEmpty()){
					printersFiltered.add(printer);
				}
			}
		}
        results = printersFiltered;
        
		return results;
    }

    public class IdComparator implements Comparator<Printer> {

        @Override
        public int compare(Printer printer1, Printer printer2) {
            return printer1.getId().toLowerCase().compareTo(printer2.getId().toLowerCase());
        }

    }

    public class NameComparator implements Comparator<Printer> {

        @Override
        public int compare(Printer printer1, Printer printer2) {
            return printer1.getName().toLowerCase().compareTo(printer2.getName().toLowerCase());
        }

    }

}
