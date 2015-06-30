/**
 * 
 */
package com.zebra.das.model.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author ignaciogonzalez
 *
 */
@Entity
@Cacheable
@Table(name = "printHead")
@NamedQueries({
    @NamedQuery(name = "PrintHead.findAll", query = "SELECT p FROM PrintHead p")})
public class PrintHead implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private String id;
    @Size(max = 255)
    private String dpi;
    @Column(precision=6, scale=2)
    private Double listPrice;
    @ManyToMany(mappedBy = "printHeads",fetch = FetchType.EAGER)
    private List<Printer> printers;
    @Size(max = 255)
    private String partNumberDescription;
    
    public PrintHead(){
    	
    }
    
	public String getId() {
		return id;
	}
	
	public String getDpi() {
		return dpi;
	}
	
	public Double getListPrice() {
		return listPrice;
	}
	
	public List<Printer> getPrinters() {
		return printers;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}
	
	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}
	
	public void setPrinters(List<Printer> printers) {
		this.printers = printers;
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
	        if (!(object instanceof PrintHead)) {
	            return false;
	        }
	        PrintHead other = (PrintHead) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }
	    
	    @Override
	    public String toString() {
	        return "com.zebra.das.model.api.PrintHead[ id=" + id + " ]";
	    }

		public String getPartNumberDescription() {
			return partNumberDescription;
		}

		public void setPartNumberDescription(String partNumberDescription) {
			this.partNumberDescription = partNumberDescription;
		}

}
