package com.zebra.rest.resource.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.zebra.das.service.MaterialService;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.model.api.Material;
import com.zebra.model.api.Printer;
import com.zebra.rest.mapper.MaterialMapper;
import com.zebra.rest.resource.ZebraResource;

import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/materials/{materialId}")
@Scope("prototype")
public class MaterialResource extends ZebraResource {

	private static final String PARAM_PRINTHEAD = "printhead";
	
	private static final String SORTBY_VALUES_ID = "id";
	private static final String SORTBY_VALUES_NAME = "name";
	private static final String[] SORTBY_VALUES = {SORTBY_VALUES_ID, SORTBY_VALUES_NAME};
    private boolean printhead = false;


    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialMapper materialMapper;

    private String materialId;

    @Override
    public String getName() {
        return "Material resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a Material";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        this.materialId = (String) getRequest().getAttributes().get("materialId");
        
        if (Method.GET.equals(getMethod())) {
            if (Boolean.TRUE.toString().equalsIgnoreCase(getQueryValue(PARAM_PRINTHEAD))) {
                printhead = true;
            }
        }
    }

    @Get("json")
    public Representation getOperation() {

        // business logic (throw Not Found exception if not found!)
        Material found = materialMapper.map(materialService.find(materialId));
//        found.setSyncData(syncDataService.getSyncRemoteID(materialId));

        if (printhead) {
			showOnlyPrintersWithPrintheads(found);
			List<Printer> printers = found.getPrinters();
			sortResults(printers);
		}
		// Prepare response
        setStatus(Status.SUCCESS_OK);
        setSelfLink(found);

        JacksonRepresentation<Material> materialRepresentation = new JacksonRepresentation<Material>(found);

        return materialRepresentation;

    }

	private void showOnlyPrintersWithPrintheads(Material found) {
		List<Printer> releatedPrints = new ArrayList<Printer>();
		if(found.getPrinters() != null) {
			List<Printer> printers = found.getPrinters();
			for (Printer printer : printers) {
				if(printer.getPrintHeads() != null && !printer.getPrintHeads().isEmpty()){
					releatedPrints.add(printer);
				}
			}
		}
		found.setPrinters(releatedPrints);
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
