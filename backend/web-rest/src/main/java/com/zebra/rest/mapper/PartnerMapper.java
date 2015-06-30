package com.zebra.rest.mapper;

import com.zebra.model.api.Partner;
import org.springframework.stereotype.Service;

@Service
public class PartnerMapper extends Mapper<Partner, com.zebra.das.model.api.Partner> {

    @Override
    public Partner map(com.zebra.das.model.api.Partner p) {
        Partner partner = new Partner();
        partner.setId(p.getId());
        partner.setApplications(p.getApplications());
        partner.setDescription(p.getDescription());
        partner.setLogo(p.getLogo());
        partner.setName(p.getName());
        partner.setRegions(p.getRegions());
        partner.setType(p.getType());
        partner.setUrl(p.getUrl());
        partner.setValidated(p.getValidated());
        partner.setVerticalMarkets(p.getVerticalMarkets());
        return partner;
    }

}
