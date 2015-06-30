package com.zebra.das.model.api;

import com.zebra.constant.model.Adhesive;
import com.zebra.constant.model.CardTechnology;
import com.zebra.constant.model.Color;
import com.zebra.constant.model.Industry;
import com.zebra.constant.model.MaterialType;
import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.ProductType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;

@Cacheable
@Entity
@Table(name = "material")
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m")})
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private String id;
    @Size(max = 255)
    private String name;
    @Lob
    @Column(length = 1000, columnDefinition = "text")
    private String description;
    @Size(max = 255)
    private String additionalInfo;
    @Size(max = 255)
    private String dmarDescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double width;
    private Double length;
    @Enumerated(EnumType.STRING)
    private PrintTechnology printTechnology;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;
    @Enumerated(EnumType.STRING)
    private Adhesive adhesive;
    private com.zebra.constant.model.Boolean perforated;
    private Integer amountInFeetPerRoll;
    private Integer amountPerRoll;
    private Integer quantityPerCarton;
    private Integer cardsPerBox;
    @Enumerated(EnumType.STRING)
    private CardTechnology cardTechnology;
    private Integer cardThickness;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    @Size(max = 255)
    private String detailedSpecs;
    private com.zebra.constant.model.Boolean featured;
    @Size(max = 255)
    private String dmarSku;
    @BatchSize(size = 5)
    @ManyToMany(mappedBy = "materials")
    private Set<Printer> printers;
    @BatchSize(size = 5)
    @ManyToMany
    @JoinTable(
            name = "material_highRibbons",
            joinColumns = {
                @JoinColumn(name = "materialId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "ribbonId", referencedColumnName = "id")})
    private Set<Ribbon> highRibbons;
    @BatchSize(size = 5)
    @ManyToMany
    @JoinTable(
            name = "material_standardRibbons",
            joinColumns = {
                @JoinColumn(name = "materialId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "ribbonId", referencedColumnName = "id")})
    private Set<Ribbon> standardRibbons;
    @Transient
    private String pccId;
    @Column(precision=6, scale=2)
    private Double listPrice;
    @Size(max = 255)
    private String partNumberDescription;

    public Material() {
    }

    public Material(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getDmarDescription() {
        return dmarDescription;
    }

    public void setDmarDescription(String dmarDescription) {
        this.dmarDescription = dmarDescription;
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

    public Integer getAmountInFeetPerRoll() {
        return amountInFeetPerRoll;
    }

    public void setAmountInFeetPerRoll(Integer amountInFeetPerRoll) {
        this.amountInFeetPerRoll = amountInFeetPerRoll;
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

    public String getDetailedSpecs() {
        return detailedSpecs;
    }

    public void setDetailedSpecs(String detailedSpecs) {
        this.detailedSpecs = detailedSpecs;
    }

    public com.zebra.constant.model.Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(com.zebra.constant.model.Boolean featured) {
        this.featured = featured;
    }

    public String getDmarSku() {
        return dmarSku;
    }

    public void setDmarSku(String dmarSku) {
        this.dmarSku = dmarSku;
    }

    public Set<Printer> getPrinters() {
        return printers;
    }

    public void setPrinters(Set<Printer> printers) {
        this.printers = printers;
    }

    public Set<Ribbon> getHighRibbons() {
        return highRibbons;
    }

    public void setHighRibbons(Set<Ribbon> highRibbons) {
        this.highRibbons = highRibbons;
    }

    public Set<Ribbon> getStandardRibbons() {
        return standardRibbons;
    }

    public void setStandardRibbons(Set<Ribbon> standardRibbons) {
        this.standardRibbons = standardRibbons;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.zebra.das.model.api.Material[ id=" + id + " ]";
    }

    public String getPccId() {
        return pccId;
    }

    public void setPccId(String pccId) {
        this.pccId = pccId;
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
