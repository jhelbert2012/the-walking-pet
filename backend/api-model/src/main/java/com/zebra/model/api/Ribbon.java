package com.zebra.model.api;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zebra.constant.model.Dmar;
import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.RibbonApplication;
import com.zebra.constant.model.RibbonColor;
import com.zebra.model.annotation.BusinessKey;

import java.util.ArrayList;
import java.util.List;

public class Ribbon extends HateoasSingleResource {

    private static final long serialVersionUID = 1L;

    @BusinessKey
    @NotBlank
    @Length(min = 2, max = 50)
    private String name;

    @BusinessKey
    @NotBlank
    @Length(min = 2, max = 300)
    private String description;

    @BusinessKey
    private Double width;

    @BusinessKey
    private Double length;

    @BusinessKey
    private PrintTechnology printTechnology;

    @BusinessKey
    @NotEmpty
    private List<Printer> printers= new ArrayList<>();

    @BusinessKey
    private RibbonApplication ribbonApplication;

    @BusinessKey
    private Integer imagesPerRoll;

    @BusinessKey
    private RibbonColor ribbonColor;

    @BusinessKey
    @Length(min = 2, max = 200)
    @URL
    private String detailedSpecs;

    @BusinessKey
    private String dmarSku;

    @BusinessKey
    @JsonIgnore
    private Map<Dmar, String> dmarSkus;

    @BusinessKey
    private Double listPrice;

	@BusinessKey
    private String partNumberDescription;

    public Ribbon() {
        dmarSkus = new HashMap<Dmar, String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public PrintTechnology getPrintTechnology() {
        return printTechnology;
    }

    public void setPrintTechnology(PrintTechnology printTechnology) {
        this.printTechnology = printTechnology;
    }

    public List<Printer> getPrinters() {
        return printers;
    }

    public void setPrinters(List<Printer> printers) {
        this.printers = printers;
    }

    public boolean hasPrinter(String printerId) {
        for (Printer printer : printers) {
            if (printer.getId().equalsIgnoreCase(printerId)) {
                return true;
            }
        }
        return false;
    }

    public RibbonApplication getRibbonApplication() {
        return ribbonApplication;
    }

    public void setRibbonApplication(RibbonApplication ribbonApplication) {
        this.ribbonApplication = ribbonApplication;
    }

    public Integer getImagesPerRoll() {
        return imagesPerRoll;
    }

    public void setImagesPerRoll(Integer imagesPerRoll) {
        this.imagesPerRoll = imagesPerRoll;
    }

    public String getDmarSku() {
        return dmarSku;
    }

    public void setDmarSku(String dmarSku) {
        this.dmarSku = dmarSku;
    }

    @JsonIgnore
    public Map<Dmar, String> getDmarSkus() {
        return dmarSkus;
    }

    @JsonIgnore
    public void setDmarSkus(Map<Dmar, String> dmarSkus) {
        this.dmarSkus = dmarSkus;
    }

    public RibbonColor getRibbonColor() {
        return ribbonColor;
    }

    public void setRibbonColor(RibbonColor ribbonColor) {
        this.ribbonColor = ribbonColor;
    }

    public String getDetailedSpecs() {
        return detailedSpecs;
    }

    public void setDetailedSpecs(String detailedSpecs) {
        this.detailedSpecs = detailedSpecs;
    }

	public Double getListPrice() {
		return listPrice;
	}

	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}

	public String getPartNumberDescription() {
		return partNumberDescription;
	}

	public void setPartNumberDescription(String partNumberDescription) {
		this.partNumberDescription = partNumberDescription;
	}

}
