
package com.zebra.das.service.impl;

import com.zebra.das.model.api.Partner;
import com.zebra.das.repository.PartnerRepository;
import com.zebra.das.service.PartnerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerServiceImpl implements PartnerService{

    @Autowired
    private PartnerRepository repository;
    @Override
    public List<Partner> findAll() {
        return repository.findAll();
    }

    @Override
    public Partner find(String partnerId) {
        return repository.findOne(partnerId);
    }

}
