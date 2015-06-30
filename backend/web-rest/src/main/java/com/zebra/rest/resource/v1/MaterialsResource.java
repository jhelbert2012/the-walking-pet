package com.zebra.rest.resource.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.constant.model.CardTechnology;
import com.zebra.constant.model.Color;
import com.zebra.constant.model.Dmar;
import com.zebra.constant.model.Industry;
import com.zebra.constant.model.MaterialType;
import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.ProductType;
import com.zebra.constant.model.RibbonApplication;
import com.zebra.constant.util.ConstantUtil;
import com.zebra.model.api.Facet;
import com.zebra.model.api.Material;
import com.zebra.model.api.MaterialList;
import com.zebra.model.api.Printer;
import com.zebra.model.api.Ribbon;
import com.zebra.rest.resource.ZebraResource;
import com.zebra.rest.temp.PersistenceStorage;

import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/materials")
@Scope("prototype")
public class MaterialsResource extends ZebraResource {

    @Autowired
    private PersistenceStorage persistenceStorage;

    private static final String PARAM_PRINTER = "printer";
    private static final String PARAM_PRINTTECH = "printTech";
    private static final String PARAM_PRODUCTTYPE = "productType";
    private static final String PARAM_MATERIALTYPE = "materialType";
    private static final String PARAM_SHOWRESULTS = "showResults";
    private static final String PARAM_WIDTH = "width";
    private static final String PARAM_LENGTH = "length";
    private static final String PARAM_DMAR = "dmar";
    private static final String PARAM_RIBBONAPP = "ribbonApp";
    private static final String PARAM_CARDTECH = "cardTech";
    private static final String PARAM_CARDTHICKNESS = "cardThick";
    private static final String PARAM_COLOR = "color";
    private static final String PARAM_INDUSTRY = "industry";
    private static final String SORTBY_VALUES_NAME = "name";
    private static final String[] SORTBY_VALUES = {SORTBY_VALUES_NAME};
	private static final String PARAM_PRINTHEAD = "printhead";

    private String[] printers;
    private String printTech;
    private String productType;
    private String materialType;
    private boolean showResults = true;
    private Double width;
    private Double length;
    private String dmar;
    private String ribbonApp;
    private String cardTech;
    private Double cardThick;
    private String color;
    private String industry;
    private boolean printhead = false;

    public MaterialsResource() {
        //desc by default
        sortOrder = SortOrder.DESC;
    }

    @Override
    protected String[] getAcceptedOrderByValues() {
        return SORTBY_VALUES;
    }

    @Override
    public String getName() {
        return "Materials resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing the Materials";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        if (Method.GET.equals(getMethod())) {
            printers = getRequest().getResourceRef().getQueryAsForm().getValuesArray(PARAM_PRINTER, true);
            printTech = getQueryValue(PARAM_PRINTTECH);
            productType = getQueryValue(PARAM_PRODUCTTYPE);
            materialType = getQueryValue(PARAM_MATERIALTYPE);
            if (Boolean.FALSE.toString().equalsIgnoreCase(getQueryValue(PARAM_SHOWRESULTS))) {
                showResults = false;
            }
            width = NumberUtils.toDouble(getQueryValue(PARAM_WIDTH), 0d);
            length = NumberUtils.toDouble(getQueryValue(PARAM_LENGTH), 0d);
            dmar = StringUtils.upperCase(getQueryValue(PARAM_DMAR));
            ribbonApp = getQueryValue(PARAM_RIBBONAPP);
            cardTech = getQueryValue(PARAM_CARDTECH);
            cardThick = NumberUtils.toDouble(getQueryValue(PARAM_CARDTHICKNESS), 0d);
            color = getQueryValue(PARAM_COLOR);
            industry = getQueryValue(PARAM_INDUSTRY);
            if (Boolean.TRUE.toString().equalsIgnoreCase(getQueryValue(PARAM_PRINTHEAD))) {
                printhead = true;
            }
        }

    }

    @Get("json")
    public Representation getOperation() {

//        List<Material> results = materialMapper.map(materialService.find(printers, printTech, productType, materialType, width, length, dmar, ribbonApp, cardTech, cardThick, color, industry));
        List<Material> results = filterResults(persistenceStorage.getStorage().getMaterials());

        if (printhead) {
			showOnlyPrintersWithPrintheads(results);
		}
        
        sortResults(results);

        MaterialList materialList = new MaterialList(results, new Long(results.size()));

        // prepare response
        setResourceListElementSelfLink(materialList);
        setSelfLink(materialList);
        // setPaginationLinks(materials);
        setFacets(materialList);
        if (!showResults) {
            materialList.setResources(null);
        }

        JacksonRepresentation<MaterialList> materialsRepresentation = new JacksonRepresentation<>(materialList);

        return materialsRepresentation;
    }

    private void sortResults(List<Material> results) {

        // defaulting if not provided
        sortBy = StringUtils.isBlank(sortBy) ? SORTBY_VALUES_NAME : sortBy;

        Comparator<Material> comparator = null;
        if (StringUtils.equals(SORTBY_VALUES_NAME, sortBy)) {
            comparator = new NameComparator();
        }

        if (!sortOrder.isAscending()) {
            comparator = Collections.reverseOrder(comparator);
        }

        Collections.sort(results, comparator);

    }

    private List<Material> filterResults(List<Material> results) {

        if (ArrayUtils.isNotEmpty(printers)) {
            // Remove the ribbons that are not applicable for the printers
            // selected
            for (Material material1 : results) {
                if (material1.getStandardRibbons() != null) {
                    List<Ribbon> filteredRibbons = new ArrayList<>();

                    for (Ribbon ribbon : material1.getStandardRibbons()) {
                        for (String printerId : printers) {
                            if (ribbon.hasPrinter(printerId)) {
                                filteredRibbons.add(ribbon);
                            }
                        }
                    }

                    if (!filteredRibbons.isEmpty()) {
                        material1.setStandardRibbons(filteredRibbons);
                    } else {
                        material1.setStandardRibbons(null);
                    }
                }
                if (material1.getHighRibbons() != null) {
                    List<Ribbon> filteredRibbons = new ArrayList<>();

                    for (Ribbon ribbon : material1.getHighRibbons()) {
                        for (String printerId : printers) {
                            if (ribbon.hasPrinter(printerId)) {
                                filteredRibbons.add(ribbon);
                            }
                        }
                    }

                    if (!filteredRibbons.isEmpty()) {
                        material1.setHighRibbons(filteredRibbons);
                    } else {
                        material1.setHighRibbons(null);
                    }
                }
            }

            List<Material> filteredResults = new ArrayList<>();

            materialLoop:
            for (Material material2 : results) {
                for (String printerId : printers) {
                    if (material2.hasPrinter(printerId)) {
                        filteredResults.add(material2);
                        continue materialLoop;
                    }
                }
            }
            results = filteredResults;
        }
        if (StringUtils.isNotBlank(printTech)) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getPrintTechnology() != null
                        && material.getPrintTechnology().name().equalsIgnoreCase(printTech)) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }
        if (StringUtils.isNotBlank(productType)) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getProductType() != null && material.getProductType().name().equalsIgnoreCase(productType)) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }
        if (StringUtils.isNotBlank(materialType)) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getMaterialType() != null
                        && material.getMaterialType().name().equalsIgnoreCase(materialType)) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }
        if (width != 0d) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getWidth() != null && material.getWidth().equals(width)) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }
        if (length != 0d) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getLength() != null && material.getLength().equals(length)) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }

        if (StringUtils.isNotBlank(ribbonApp)) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                boolean isPresent = false;

                if (CollectionUtils.isNotEmpty(material.getStandardRibbons())) {
                    ribbon:
                    for (Ribbon ribbon : material.getStandardRibbons()) {
                        if (ribbon.getRibbonApplication() != null
                                && ribbon.getRibbonApplication().name().equalsIgnoreCase(ribbonApp)) {
                            isPresent = true;
                            break ribbon;
                        }
                    }

                }
                if (!isPresent && CollectionUtils.isNotEmpty(material.getHighRibbons())) {
                    ribbon:
                    for (Ribbon ribbon : material.getHighRibbons()) {
                        if (ribbon.getRibbonApplication() != null
                                && ribbon.getRibbonApplication().name().equalsIgnoreCase(ribbonApp)) {
                            isPresent = true;
                            break ribbon;
                        }
                    }

                }
                if (isPresent) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }

        if (StringUtils.isNotBlank(cardTech)) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getCardTechnology() != null
                        && material.getCardTechnology().name().equalsIgnoreCase(cardTech)) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }
        if (cardThick != 0d) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getCardThickness() != null && material.getCardThickness().equals(cardThick.intValue())) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }

        if (StringUtils.isNotBlank(color)) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getColor() != null && material.getColor().name().equalsIgnoreCase(color)) {
                    filteredResults.add(material);
                }
            }
            results = filteredResults;
        }

        if (StringUtils.isNotBlank(industry)) {
            List<Material> filteredResults = new ArrayList<>();

            for (Material material : results) {
                if (material.getIndustry() != null && material.getIndustry().name().equalsIgnoreCase(industry)) {
                    filteredResults.add(material);
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
                List<Material> filteredResults = new ArrayList<>();

                for (Material material : results) {
                    String dmarMaterialSku = null;
                    if (material.getDmarSkus() != null) {
                        dmarMaterialSku = material.getDmarSkus().get(validatedDmar);
                    }

                    if (StringUtils.isNotBlank(dmarMaterialSku)) {

                        if (CollectionUtils.isNotEmpty(material.getStandardRibbons())) {
                            List<Ribbon> filteredRibbons = new ArrayList<>();

                            for (Ribbon ribbon : material.getStandardRibbons()) {
                                String dmarRibbonSku = null;
                                if (ribbon.getDmarSkus() != null) {
                                    dmarRibbonSku = ribbon.getDmarSkus().get(validatedDmar);
                                }

                                if (StringUtils.isNotBlank(dmarRibbonSku)) {
                                    ribbon.setDmarSku(dmarRibbonSku);
                                    filteredRibbons.add(ribbon);
                                }
                            }
                            if (!filteredRibbons.isEmpty()) {
                                material.setStandardRibbons(filteredRibbons);
                            } else {
                                material.setStandardRibbons(null);
                            }

                        }

                        if (CollectionUtils.isNotEmpty(material.getHighRibbons())) {
                            List<Ribbon> filteredRibbons = new ArrayList<>();

                            for (Ribbon ribbon : material.getHighRibbons()) {
                                String dmarRibbonSku = null;
                                if (ribbon.getDmarSkus() != null) {
                                    dmarRibbonSku = ribbon.getDmarSkus().get(validatedDmar);
                                }

                                if (StringUtils.isNotBlank(dmarRibbonSku)) {
                                    ribbon.setDmarSku(dmarRibbonSku);
                                    filteredRibbons.add(ribbon);
                                }
                            }
                            if (!filteredRibbons.isEmpty()) {
                                material.setHighRibbons(filteredRibbons);
                            } else {
                                material.setHighRibbons(null);
                            }

                        }

                        material.setDmarSku(dmarMaterialSku);
                        filteredResults.add(material);
                    }
                }
                results = filteredResults;
            }
        }

        return results;
    }

    private void setFacets(MaterialList materials) {

        Map<String, List<Facet>> facets = new HashMap<String, List<Facet>>();

        if (StringUtils.isEmpty(printTech)) {
            List<Facet> ptFacets = new ArrayList<Facet>();

            for (PrintTechnology pt : PrintTechnology.values()) {
                int counter = 0;
                for (Material material : materials.getResources()) {
                    if (pt.equals(material.getPrintTechnology())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(pt, Locale.ENGLISH));
                    facet.setValue(pt.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_PRINTTECH, pt.name())
                            .toString());
                    ptFacets.add(facet);
                }
            }
            if (CollectionUtils.isNotEmpty(ptFacets)) {
                facets.put(PARAM_PRINTTECH, ptFacets);
            }
        }

        if (StringUtils.isEmpty(productType)) {
            List<Facet> ptFacets = new ArrayList<Facet>();

            for (ProductType pt : ProductType.values()) {
                int counter = 0;
                for (Material material : materials.getResources()) {
                    if (pt.equals(material.getProductType())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(pt, Locale.ENGLISH));
                    facet.setValue(pt.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_PRODUCTTYPE, pt.name())
                            .toString());
                    ptFacets.add(facet);
                }
            }
            if (CollectionUtils.isNotEmpty(ptFacets)) {
                facets.put(PARAM_PRODUCTTYPE, ptFacets);
            }
        }

        if (StringUtils.isEmpty(materialType)) {
            List<Facet> mtFacets = new ArrayList<Facet>();

            for (MaterialType mt : MaterialType.values()) {
                int counter = 0;
                for (Material material : materials.getResources()) {
                    if (mt.equals(material.getMaterialType())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(mt, Locale.ENGLISH));
                    facet.setValue(mt.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_MATERIALTYPE, mt.name())
                            .toString());
                    mtFacets.add(facet);
                }
            }
            if (CollectionUtils.isNotEmpty(mtFacets)) {
                facets.put(PARAM_MATERIALTYPE, mtFacets);
            }
        }

        if (width == 0d) {
            Map<Double, Integer> counters = new HashMap<Double, Integer>();
            for (Material material : materials.getResources()) {
                if (counters.containsKey(material.getWidth())) {
                    counters.put(material.getWidth(), counters.get(material.getWidth()) + 1);
                } else {
                    counters.put(material.getWidth(), 1);
                }
            }

            List<Facet> wFacets = new ArrayList<Facet>();

            for (Entry<Double, Integer> entry : counters.entrySet()) {
                if (entry.getKey() != null) {
                    Facet facet = new Facet();
                    facet.setCount(entry.getValue());
                    facet.setLabel(entry.getKey().toString());
                    facet.setValue(entry.getKey().toString());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_WIDTH,
                            entry.getKey().toString()).toString());
                    wFacets.add(facet);
                }
            }

            if (CollectionUtils.isNotEmpty(wFacets)) {
                facets.put(PARAM_WIDTH, wFacets);
            }
        }

        if (length == 0d) {
            Map<Double, Integer> counters = new HashMap<Double, Integer>();
            for (Material material : materials.getResources()) {
                if (counters.containsKey(material.getLength())) {
                    counters.put(material.getLength(), counters.get(material.getLength()) + 1);
                } else {
                    counters.put(material.getLength(), 1);
                }
            }

            List<Facet> lFacets = new ArrayList<Facet>();

            for (Entry<Double, Integer> entry : counters.entrySet()) {
                if (entry.getKey() != null) {
                    Facet facet = new Facet();
                    facet.setCount(entry.getValue());
                    facet.setLabel(entry.getKey().toString());
                    facet.setValue(entry.getKey().toString());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_LENGTH,
                            entry.getKey().toString()).toString());
                    lFacets.add(facet);
                }
            }

            if (CollectionUtils.isNotEmpty(lFacets)) {
                facets.put(PARAM_LENGTH, lFacets);
            }
        }

        if (StringUtils.isEmpty(ribbonApp)) {
            List<Facet> rtFacets = new ArrayList<Facet>();

            for (RibbonApplication ra : RibbonApplication.values()) {
                int counter = 0;

                for (Material material : materials.getResources()) {
                    boolean isPresent = false;

                    if (CollectionUtils.isNotEmpty(material.getStandardRibbons())) {
                        ribbon:
                        for (Ribbon ribbon : material.getStandardRibbons()) {
                            if (ra.equals(ribbon.getRibbonApplication())) {
                                isPresent = true;
                                break ribbon;
                            }
                        }
                    }
                    if (!isPresent && CollectionUtils.isNotEmpty(material.getHighRibbons())) {
                        ribbon:
                        for (Ribbon ribbon : material.getHighRibbons()) {
                            if (ra.equals(ribbon.getRibbonApplication())) {
                                isPresent = true;
                                break ribbon;
                            }
                        }
                    }

                    if (isPresent) {
                        counter++;
                    }
                }
                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(ra, Locale.ENGLISH));
                    facet.setValue(ra.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_RIBBONAPP, ra.name())
                            .toString());
                    rtFacets.add(facet);
                }
            }
            if (CollectionUtils.isNotEmpty(rtFacets)) {
                facets.put(PARAM_RIBBONAPP, rtFacets);
            }
        }

        if (StringUtils.isEmpty(cardTech)) {
            List<Facet> ctFacets = new ArrayList<Facet>();

            for (CardTechnology ct : CardTechnology.values()) {
                int counter = 0;
                for (Material material : materials.getResources()) {
                    if (ct.equals(material.getCardTechnology())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(ct, Locale.ENGLISH));
                    facet.setValue(ct.name());
                    facet
                            .setLink(new Reference(getReference()).addQueryParameter(PARAM_CARDTECH, ct.name()).toString());
                    ctFacets.add(facet);
                }
            }

            if (CollectionUtils.isNotEmpty(ctFacets)) {
                facets.put(PARAM_CARDTECH, ctFacets);
            }
        }

        if (cardThick == 0d) {
            Map<Integer, Integer> counters = new HashMap<Integer, Integer>();
            for (Material material : materials.getResources()) {
                if (counters.containsKey(material.getCardThickness())) {
                    counters.put(material.getCardThickness(), counters.get(material.getCardThickness()) + 1);
                } else {
                    counters.put(material.getCardThickness(), 1);
                }
            }

            List<Facet> wFacets = new ArrayList<Facet>();

            for (Entry<Integer, Integer> entry : counters.entrySet()) {
                if (entry.getKey() != null) {
                    Facet facet = new Facet();
                    facet.setCount(entry.getValue());
                    facet.setLabel(entry.getKey().toString());
                    facet.setValue(entry.getKey().toString());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_CARDTHICKNESS,
                            entry.getKey().toString()).toString());
                    wFacets.add(facet);
                }
            }
            if (CollectionUtils.isNotEmpty(wFacets)) {
                facets.put(PARAM_CARDTHICKNESS, wFacets);
            }
        }

        if (StringUtils.isEmpty(color)) {
            List<Facet> cFacets = new ArrayList<Facet>();

            for (Color c : Color.values()) {
                int counter = 0;
                for (Material material : materials.getResources()) {
                    if (c.equals(material.getColor())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(c, Locale.ENGLISH));
                    facet.setValue(c.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_COLOR, c.name()).toString());
                    cFacets.add(facet);
                }
            }

            if (CollectionUtils.isNotEmpty(cFacets)) {
                facets.put(PARAM_COLOR, cFacets);
            }
        }

        if (StringUtils.isEmpty(industry)) {
            List<Facet> iFacets = new ArrayList<Facet>();

            for (Industry i : Industry.values()) {
                int counter = 0;
                for (Material material : materials.getResources()) {
                    if (i.equals(material.getIndustry())) {
                        counter++;
                    }
                }

                if (counter > 0) {
                    Facet facet = new Facet();
                    facet.setCount(counter);
                    facet.setLabel(ConstantUtil.value(i, Locale.ENGLISH));
                    facet.setValue(i.name());
                    facet.setLink(new Reference(getReference()).addQueryParameter(PARAM_INDUSTRY, i.name()).toString());
                    iFacets.add(facet);
                }
            }

            if (CollectionUtils.isNotEmpty(iFacets)) {
                facets.put(PARAM_INDUSTRY, iFacets);
            }
        }

        List<Facet> printerFacets = new ArrayList<Facet>();
        for (Printer printer : persistenceStorage.getStorage().getPrinters()) {
            int counter = 0;
            for (Material material : materials.getResources()) {
                for (Printer matPrinter : material.getPrinters()) {
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

        materials.setFacets(facets);
    }

    public class NameComparator implements Comparator<Material> {

        @Override
        public int compare(Material material1, Material material2) {
            return material1.getName().toLowerCase().compareTo(material2.getName().toLowerCase());
        }

    }

	private void showOnlyPrintersWithPrintheads(List<Material> materials) {
		
		for (Material material: materials) {
			List<Printer> releatedPrints = new ArrayList<Printer>();
			if (material.getPrinters() != null) {
				List<Printer> printers = material.getPrinters();
				for (Printer printer : printers) {
					if (printer.getPrintHeads() != null
							&& !printer.getPrintHeads().isEmpty()) {
						releatedPrints.add(printer);
					}
				}
			}
			material.setPrinters(releatedPrints);
		}
	}

}
