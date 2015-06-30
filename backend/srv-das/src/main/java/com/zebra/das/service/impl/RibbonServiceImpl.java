
package com.zebra.das.service.impl;

import com.zebra.das.model.api.Ribbon;
import com.zebra.das.repository.RibbonRepository;
import com.zebra.das.service.RibbonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RibbonServiceImpl implements RibbonService{

    @Autowired
    private RibbonRepository repository;
    @Override
    public List<Ribbon> findAll() {
        return repository.findAllWithPrinters();
    }

    @Override
    public Ribbon find(String ribbonId) {
        return repository.findOneWithPrinters(ribbonId);
    }

}
