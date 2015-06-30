package com.zebra.model.api;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.PrinterType;
import com.zebra.model.annotation.BusinessKey;

import java.util.ArrayList;
import java.util.List;


public class Printer extends HateoasSingleResource {

    private static final long serialVersionUID = 1L;

    @BusinessKey
    @NotBlank
    @Length(min = 2, max = 50)
    private String name;

    @BusinessKey
    @NotEmpty
    private List<PrintTechnology> printTechnology= new ArrayList<>();

    @BusinessKey
    @Length(min = 2, max = 50)
    @URL
    private String imageLink;
    
    @BusinessKey
    private PrinterType printerType;
    
    @BusinessKey
    private List<PrintHead> printHeads= new ArrayList<>();

    public Printer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PrintTechnology> getPrintTechnology() {
        return printTechnology;
    }

    public void setPrintTechnology(List<PrintTechnology> printTechnology) {
        this.printTechnology = printTechnology;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
