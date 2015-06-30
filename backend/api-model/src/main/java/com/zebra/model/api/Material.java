package com.zebra.model.api;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zebra.constant.model.Adhesive;
import com.zebra.constant.model.CardTechnology;
import com.zebra.constant.model.Color;
import com.zebra.constant.model.Dmar;
import com.zebra.constant.model.Industry;
import com.zebra.constant.model.MaterialType;
import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.ProductType;
import com.zebra.model.annotation.BusinessKey;

import java.util.ArrayList;
import java.util.List;

public class Material extends HateoasSingleResource {

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
    private String additionalInfo;

    @BusinessKey
    private String dmarDescription;

    @BusinessKey
    private Double width;

    @BusinessKey
    private Double length;

    @BusinessKey
    @NotEmpty
    private List<Printer> printers = new ArrayList<>();

    @BusinessKey
    private List<Ribbon> standardRibbons = new ArrayList<>();

    @BusinessKey
    private List<Ribbon> highRibbons = new ArrayList<>();

    @BusinessKey
    private PrintTechnology printTechnology;

    @BusinessKey
    @NotNull
    private ProductType productType;

    @BusinessKey
    private MaterialType materialType;

    @BusinessKey
    private Adhesive adhesive;

    @BusinessKey
    private com.zebra.constant.model.Boolean perforated;

    @BusinessKey
    private Integer amountInFeetPerRoll;

    @BusinessKey
    private Integer amountPerRoll;

    @BusinessKey
    private Integer quantityPerCarton;

    @BusinessKey
    private Integer cardsPerBox;

    @BusinessKey
    private CardTechnology cardTechnology;

    @BusinessKey
    private Integer cardThickness;

    @BusinessKey
    private Color color;

    @BusinessKey
    private Industry industry;

    @BusinessKey
    @Length(min = 2, max = 200)
    @URL
    private String detailedSpecs;

    @BusinessKey
    private com.zebra.constant.model.Boolean preferred;

    @BusinessKey
    private String dmarSku;

    @BusinessKey
    @JsonIgnore
    private Map<Dmar, String> dmarSkus;
    private String pccSku;

	@BusinessKey
	private Double listPrice;

	@BusinessKey
    private String partNumberDescription;

    public Material() {
        dmarSkus = new HashMap<>();

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

    public List<Ribbon> getStandardRibbons() {
        return standardRibbons;
    }

    public void setStandardRibbons(List<Ribbon> standardRibbons) {
        this.standardRibbons = standardRibbons;
    }

    public List<Ribbon> getHighRibbons() {
        return highRibbons;
    }

    public void setHighRibbons(List<Ribbon> highRibbons) {
        this.highRibbons = highRibbons;
    }

    public PrintTechnology getPrintTechnology() {
        return printTechnology;
    }

    public void setPrintTechnology(PrintTechnology printTechnology) {
        this.printTechnology = printTechnology;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public Adhesive getAdhesive() {
        return adhesive;
    }

    public void setAdhesive(Adhesive adhesive) {
        this.adhesive = adhesive;
    }

    public com.zebra.constant.model.Boolean getPerforated() {
        return perforated;
    }

    public void setPerforated(com.zebra.constant.model.Boolean perforated) {
        this.perforated = perforated;
    }

    public Integer getAmountPerRoll() {
        return amountPerRoll;
    }

    public void setAmountPerRoll(Integer amountPerRoll) {
        this.amountPerRoll = amountPerRoll;
    }

    public Integer getQuantityPerCarton() {
        return quantityPerCarton;
    }

    public void setQuantityPerCarton(Integer quantityPerCarton) {
        this.quantityPerCarton = quantityPerCarton;
    }

    public Integer getCardsPerBox() {
        return cardsPerBox;
    }

    public void setCardsPerBox(Integer cardsPerBox) {
        this.cardsPerBox = cardsPerBox;
    }

    public CardTechnology getCardTechnology() {
        return cardTechnology;
    }

    public void setCardTechnology(CardTechnology cardTechnology) {
        this.cardTechnology = cardTechnology;
    }

    public Integer getCardThickness() {
        return cardThickness;
    }

    public void setCardThickness(Integer cardThickness) {
        this.cardThickness = cardThickness;
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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public String getDmarDescription() {
        return dmarDescription;
    }

    public void setDmarDescription(String dmarDescription) {
        this.dmarDescription = dmarDescription;
    }

    public Integer getAmountInFeetPerRoll() {
        return amountInFeetPerRoll;
    }

    public void setAmountInFeetPerRoll(Integer amountInFeetPerRoll) {
        this.amountInFeetPerRoll = amountInFeetPerRoll;
    }

    public String getDetailedSpecs() {
        return detailedSpecs;
    }

    public void setDetailedSpecs(String detailedSpecs) {
        this.detailedSpecs = detailedSpecs;
    }

    public com.zebra.constant.model.Boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(com.zebra.constant.model.Boolean preferred) {
        this.preferred = preferred;
    }

    public void setPccSku(String pccSku) {
        this.pccSku = pccSku;
    }

    public String getPccSku() {
        return pccSku;
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
