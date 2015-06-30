
package com.zebra.das.service;

import com.zebra.das.model.api.Material;

import java.util.List;

public interface MaterialService {
    public List<Material> findAll();

    public Material find(String materialId);

    public List<Material> find(String[] printers, String printTech, String productType, String materialType, Double width, Double length, String dmar, String ribbonApp, String cardTech, Double cardThick, String color, String industry);

}
