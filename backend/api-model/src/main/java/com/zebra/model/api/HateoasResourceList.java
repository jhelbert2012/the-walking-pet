package com.zebra.model.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.zebra.model.annotation.BusinessKey;

public abstract class HateoasResourceList<T extends HateoasResource> extends HateoasResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @BusinessKey
    @NotEmpty
    @Valid
    private List<T> resources;

    @BusinessKey
    @Valid
    private Map<String, List<Facet>> facets;

    @BusinessKey
    @NotNull
    private Long total;

    public HateoasResourceList() {
    }

    public HateoasResourceList(List<T> resources, Long total) {
        this(resources, null, total);
    }

    public HateoasResourceList(List<T> resources, Map<String, List<Facet>> facets, Long total) {
        super();
        this.resources = resources;
        this.facets = facets;
        this.total = total;
    }

    public List<T> getResources() {
        return resources;
    }

    public void setResources(List<T> resources) {
        this.resources = resources;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Map<String, List<Facet>> getFacets() {
        return facets;
    }

    public void setFacets(Map<String, List<Facet>> facets) {
        this.facets = facets;
    }

}
