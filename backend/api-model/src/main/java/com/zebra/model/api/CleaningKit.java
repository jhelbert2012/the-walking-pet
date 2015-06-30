package com.zebra.model.api;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zebra.constant.model.Dmar;
import com.zebra.model.annotation.BusinessKey;
import java.util.ArrayList;
import java.util.List;

public class CleaningKit extends HateoasSingleResource {

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
    @NotEmpty
    private List<Printer> printers= new ArrayList<>();

    @BusinessKey
    private String dmarSku;

    @BusinessKey
    @JsonIgnore
    private Map<Dmar, String> dmarSkus;
    
    @BusinessKey    
    private String pccSku;

    public CleaningKit() {
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

    public void setPccSku(String pccSku) {
        this.pccSku = pccSku;
    }

    public String getPccSku() {
        return pccSku;
    }

}
