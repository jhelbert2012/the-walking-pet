package com.zebra.model.api;

public class Facet extends AbstractResource {

	private static final long	serialVersionUID	= 1L;

	private String				label;

	private String				value;
	
	private Integer				count;

	private String				link;

	public Facet() {
		
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
