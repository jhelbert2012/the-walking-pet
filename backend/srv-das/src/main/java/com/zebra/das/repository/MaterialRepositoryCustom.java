
package com.zebra.das.repository;

import com.zebra.das.model.api.Material;
import java.util.List;

public interface MaterialRepositoryCustom {
    List<Material> searchBy(String[] printers, String printTech, String productType, String materialType, Double width, Double length, String dmar, String ribbonApp, String cardTech, Double cardThick, String color, String industry);
}
