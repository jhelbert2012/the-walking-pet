package com.zebra.das.repository.impl;

import com.zebra.constant.model.CardTechnology;
import com.zebra.constant.model.Color;
import com.zebra.constant.model.Industry;
import com.zebra.constant.model.MaterialType;
import com.zebra.constant.model.PrintTechnology;
import com.zebra.constant.model.ProductType;
import com.zebra.constant.model.RibbonApplication;
import com.zebra.das.model.api.Material;
import com.zebra.das.repository.MaterialRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;

public class MaterialRepositoryImpl implements MaterialRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Material> searchBy(String[] printers, String printTech, String productType, String materialType, Double width, Double length, 
            String dmar, String ribbonApp, String cardTech, Double cardThick, String color, String industry) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT m FROM Material m LEFT JOIN FETCH m.printers LEFT JOIN FETCH m.highRibbons LEFT JOIN FETCH m.standardRibbons ");
        sb.append("WHERE 1 = 1 ");
        if (!StringUtils.isEmpty(printTech)) {
            sb.append(" AND m.printTechnology=:printTech");
        }
        if (!StringUtils.isEmpty(productType)) {
            sb.append(" AND m.productType=:productType");
        }
        if (!StringUtils.isEmpty(materialType)) {
            sb.append(" AND m.materialType=:materialType");
        }
        if (width != 0) {
            sb.append(" AND m.width=:width");
        }
        if (length != 0) {
            sb.append(" AND m.length=:length");
        }
        if (!StringUtils.isEmpty(dmar)) {
            sb.append(" AND m.DmarSku=:dmar");
        }
        if (!StringUtils.isEmpty(ribbonApp)) {
            sb.append(" AND (m.standardRibbons.ribbonApplication=:ribbonApp OR m.highRibbons.ribbonApplication=:ribbonApp)");
        }
        if (!StringUtils.isEmpty(cardTech)) {
            sb.append(" AND m.cardTechnology=:cardTech");
        }
        if (cardThick != 0) {
            sb.append(" AND m.cardThickness=:cardThick");
        }
        if (!StringUtils.isEmpty(color)) {
            sb.append(" AND m.color=:color");
        }
        if (!StringUtils.isEmpty(industry)) {
            sb.append(" AND m.industry=:industry");
        }

        Query query = em.createQuery(sb.toString(), Material.class);
        if (!StringUtils.isEmpty(printTech)) {
            query.setParameter("printTech", PrintTechnology.valueOf(printTech));
        }
        if (!StringUtils.isEmpty(productType)) {
            query.setParameter("productType", ProductType.valueOf(productType));
        }
        if (!StringUtils.isEmpty(materialType)) {
            query.setParameter("materialType", MaterialType.valueOf(materialType));
        }
        if (width != 0) {
            query.setParameter("width", width);
        }
        if (length != 0) {
            query.setParameter("length", length);
        }
        if (!StringUtils.isEmpty(dmar)) {
            query.setParameter("dmar", dmar);
        }
        if (!StringUtils.isEmpty(ribbonApp)) {
            query.setParameter("ribbonApp", RibbonApplication.valueOf(ribbonApp));
        }
        if (!StringUtils.isEmpty(cardTech)) {
            query.setParameter("cardTech", CardTechnology.valueOf(cardTech));
        }
        if (cardThick != 0) {
            query.setParameter("cardThick", cardThick);
        }
        if (!StringUtils.isEmpty(color)) {
            query.setParameter("color", Color.valueOf(color));
        }
        if (!StringUtils.isEmpty(industry)) {
            query.setParameter("industry", Industry.valueOf(industry));
        }
       return query.getResultList();
    }

}
