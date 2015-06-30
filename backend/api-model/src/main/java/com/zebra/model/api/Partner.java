package com.zebra.model.api;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.zebra.constant.model.Application;
import com.zebra.constant.model.PartnerType;
import com.zebra.constant.model.Region;
import com.zebra.constant.model.VerticalMarket;
import com.zebra.model.annotation.BusinessKey;
import java.util.ArrayList;
import java.util.List;

public class Partner extends HateoasSingleResource {

    private static final long serialVersionUID = 1L;

    @BusinessKey
    @NotNull
    private PartnerType type;

    @BusinessKey
    @NotBlank
    @Length(min = 2, max = 50)
    private String name;

    @BusinessKey
    @NotBlank
    @Length(min = 2, max = 500)
    private String description;

    @BusinessKey
    @Length(min = 2, max = 200)
    @URL
    private String url;

    @BusinessKey
    @Length(min = 2, max = 200)
    @URL
    private String logo;

    @BusinessKey
    @NotEmpty
    private List<Region> regions = new ArrayList<>();

    @BusinessKey
    @NotEmpty
    private List<VerticalMarket> verticalMarkets= new ArrayList<>();

    @BusinessKey
    @NotEmpty
    private List<Application> applications= new ArrayList<>();

    @BusinessKey
    private com.zebra.constant.model.Boolean validated;

    public Partner() {
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

    public PartnerType getType() {
        return type;
    }

    public void setType(PartnerType type) {
        this.type = type;
    }

    public boolean hasRegion(String regionId) {
        if (regions != null) {
            for (Region region : regions) {
                if (region.name().equalsIgnoreCase(regionId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasVerticalMarket(String verticalId) {
        if (verticalMarkets != null) {
            for (VerticalMarket vertical : verticalMarkets) {
                if (vertical.name().equalsIgnoreCase(verticalId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasApplication(String applicationId) {
        if (applications != null) {
            for (Application application : applications) {
                if (application.name().equalsIgnoreCase(applicationId)) {
                    return true;
                }
            }
        }

        return false;
    }

}
