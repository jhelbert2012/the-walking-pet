package com.zebra.das.model.api;

import com.zebra.constant.model.Application;
import com.zebra.constant.model.PartnerType;
import com.zebra.constant.model.Region;
import com.zebra.constant.model.VerticalMarket;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Cacheable
@Table(name = "partner")
@NamedQueries({
    @NamedQuery(name = "Partner.findAll", query = "SELECT p FROM Partner p")})
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private String id;
    @Enumerated(EnumType.STRING)
    private PartnerType type;
    @Size(max = 255)
    private String name;
    @Lob
    @Column(length = 1000, columnDefinition = "text")
    private String description;
    @Size(max = 255)
    private String url;
    @Size(max = 255)
    private String logo;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Region> regions;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VerticalMarket> verticalMarkets;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Application> applications;
    private com.zebra.constant.model.Boolean validated;

    public Partner() {
    }

    public Partner(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PartnerType getType() {
        return type;
    }

    public void setType(PartnerType type) {
        this.type = type;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<VerticalMarket> getVerticalMarkets() {
        return verticalMarkets;
    }

    public void setVerticalMarkets(List<VerticalMarket> verticalMarkets) {
        this.verticalMarkets = verticalMarkets;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public com.zebra.constant.model.Boolean getValidated() {
        return validated;
    }

    public void setValidated(com.zebra.constant.model.Boolean validated) {
        this.validated = validated;
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
        if (!(object instanceof Partner)) {
            return false;
        }
        Partner other = (Partner) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.zebra.das.model.api.Partner[ id=" + id + " ]";
    }

}
