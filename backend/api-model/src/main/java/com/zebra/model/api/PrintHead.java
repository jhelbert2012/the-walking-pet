/**
 * 
 */
package com.zebra.model.api;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.zebra.model.annotation.BusinessKey;

/**
 * @author ignaciogonzalez
 *
 */
public class PrintHead extends HateoasSingleResource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@BusinessKey
	private String dpi;
	
	@BusinessKey
	private Double listPrice;
	
	@BusinessKey
    private List<Printer> printers= new ArrayList<>();

	@BusinessKey
    private String partNumberDescription;

	public String getDpi() {
		return dpi;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
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

	public String getPartNumberDescription() {
		return partNumberDescription;
	}

	public void setPartNumberDescription(String partNumberDescription) {
		this.partNumberDescription = partNumberDescription;
	}
}
