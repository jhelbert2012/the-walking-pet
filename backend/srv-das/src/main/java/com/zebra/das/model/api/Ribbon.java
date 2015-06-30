package com.zebra.das.model.api;

import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.RibbonApplication;
import com.zebra.constant.model.RibbonColor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Cacheable
@Table(name = "ribbon")
@NamedQueries({
    @NamedQuery(name = "Ribbon.findAll", query = "SELECT r FROM Ribbon r")})
public class Ribbon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private String id;
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double width;
    private Double length;
    @Enumerated(EnumType.STRING)
    private PrintTechnology printTechnology;
    @Enumerated(EnumType.STRING)
    private RibbonApplication ribbonApplication;
    private Integer imagesPerRoll;
    @Enumerated(EnumType.STRING)
    private RibbonColor ribbonColor;
    @Size(max = 255)
    private String detailedSpecs;
    @Size(max = 45)
    private String dmarSku;
    @ManyToMany(mappedBy = "ribbons",fetch = FetchType.EAGER)
    private List<Printer> printers;
    @ManyToMany(mappedBy = "highRibbons")
    private List<Material> materialsHigh;
    @ManyToMany(mappedBy = "standardRibbons")
    private List<Material> materialsStandard;
    @Column(precision=6, scale=2)
    private Double listPrice;
    @Size(max = 255)
    private String partNumberDescription;

    public Ribbon() {
    }

    public Ribbon(String id) {
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

    public String getDmarSku() {
        return dmarSku;
    }

    public void setDmarSku(String dmarSku) {
        this.dmarSku = dmarSku;
    }

    public List<Printer> getPrinters() {
        return printers;
    }

    public void setPrinters(List<Printer> printers) {
        this.printers = printers;
    }

    public List<Material> getMaterialsHigh() {
        return materialsHigh;
    }

    public void setMaterialsHigh(List<Material> materialsHigh) {
        this.materialsHigh = materialsHigh;
    }

    public List<Material> getMaterialsStandard() {
        return materialsStandard;
    }

    public void setMaterialsStandard(List<Material> materialsStandard) {
        this.materialsStandard = materialsStandard;
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
        if (!(object instanceof Ribbon)) {
            return false;
        }
        Ribbon other = (Ribbon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.zebra.das.model.api.Ribbon[ id=" + id + " ]";
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
