package com.zebra.das.model.api;

import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.PrinterType;
import com.zebra.model.annotation.BusinessKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Cacheable
@Table(name = "printer")
@NamedQueries({
    @NamedQuery(name = "Printer.findAll", query = "SELECT p FROM Printer p")})
public class Printer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private String id;
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection()
    @CollectionTable(name = "printerPrintTechnologies", joinColumns = @JoinColumn(name = "printerId"))
    @Enumerated(EnumType.STRING)
    @OrderColumn
    private List<PrintTechnology> printTechnologies;
    @Size(max = 255)
    private String imageLink;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "printers_ribbons",
            joinColumns = {
                @JoinColumn(name = "printerId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "ribbonId", referencedColumnName = "id")})
    private List<Ribbon> ribbons;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "printers_materials",
            joinColumns = {
                @JoinColumn(name = "printerId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "materialId", referencedColumnName = "id")})
    private List<Material> materials;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "printers_cleaningKits",
            joinColumns = {
                @JoinColumn(name = "printerId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "cleaningKitId", referencedColumnName = "id")})
    private List<CleaningKit> cleaningKits = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private PrinterType printerType;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "printers_printHeads",
            joinColumns = {
                @JoinColumn(name = "printerId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "printHeadId", referencedColumnName = "id")})
    private List<PrintHead> printHeads = new ArrayList<>();

    public Printer() {
    }

    public Printer(String id) {
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

    public List<PrintTechnology> getPrintTechnologies() {
        return printTechnologies;
    }

    public void setPrintTechnologies(List<PrintTechnology> printTechnologies) {
        this.printTechnologies = printTechnologies;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<Ribbon> getRibbons() {
        return ribbons;
    }

    public void setRibbons(List<Ribbon> ribbons) {
        this.ribbons = ribbons;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<CleaningKit> getCleaningKits() {
        return cleaningKits;
    }

    public void setCleaningKits(List<CleaningKit> cleaningKits) {
        this.cleaningKits = cleaningKits;
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
        if (!(object instanceof Printer)) {
            return false;
        }
        Printer other = (Printer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.zebra.das.model.api.Printer[ id=" + id + " ]";
    }

	public PrinterType getPrinterType() {
		return printerType;
	}

	public void setPrinterType(PrinterType printerType) {
		this.printerType = printerType;
	}

	public List<PrintHead> getPrintHeads() {
		return printHeads;
	}

	public void setPrintHeads(List<PrintHead> printHeads) {
		this.printHeads = printHeads;
	}

}
