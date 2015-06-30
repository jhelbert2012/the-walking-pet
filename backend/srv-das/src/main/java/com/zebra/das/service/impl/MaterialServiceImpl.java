package com.zebra.das.service.impl;

import com.zebra.das.model.api.Material;
import com.zebra.das.repository.MaterialRepository;
import com.zebra.das.service.MaterialService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Resource
    private MaterialRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Material> findAll() {
        return repository.findAllWithRibbons();
    }

    @Override
    public Material find(String materialId) {
        return repository.findOneWithRibbons(materialId);
    }

    @Override
    public List<Material> find(String[] printers, String printTech, String productType, String materialType, Double width, Double length, String dmar, String ribbonApp, String cardTech, Double cardThick, String color, String industry) {
//        repository.findAll();
        return repository.searchBy(printers, printTech, productType, materialType, width, length, dmar, ribbonApp, cardTech, cardThick, color, industry);
    }

}
