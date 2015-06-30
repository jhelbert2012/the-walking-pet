package com.zebra.rest.mapper;

import com.zebra.constant.model.Dmar;
import com.zebra.model.api.Material;
import com.zebra.model.api.Printer;
import com.zebra.model.api.Ribbon;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialMapper extends Mapper<Material, com.zebra.das.model.api.Material> {

    @Autowired
    private PrinterMapper printerMapper;
    @Autowired
    private RibbonMapper ribbonMapper;

    private Map<String, String> dmarData;

    @Override
    public Material map(com.zebra.das.model.api.Material m) {
        Material material = new Material();
        material.setId(m.getId());
        material.setAdditionalInfo(m.getAdditionalInfo());
        material.setAdhesive(m.getAdhesive());
        material.setAmountInFeetPerRoll(m.getAmountInFeetPerRoll());
        material.setAmountPerRoll(m.getAmountPerRoll());
        material.setCardTechnology(m.getCardTechnology());
        material.setCardThickness(m.getCardThickness());
        material.setCardsPerBox(m.getCardsPerBox());
        material.setColor(m.getColor());
        material.setDescription(m.getDescription());
        material.setDetailedSpecs(m.getDetailedSpecs());
        material.setDmarDescription(m.getDmarDescription());
        material.setDmarSku(m.getDmarSku());
        material.setHighRibbons(new ArrayList<Ribbon>());
        if (m.getHighRibbons() != null) {
            material.getHighRibbons().addAll(ribbonMapper.map(m.getHighRibbons()));
        }
        material.setIndustry(m.getIndustry());
        material.setLength(m.getLength());
        material.setMaterialType(m.getMaterialType());
        material.setName(m.getName());
        material.setPccSku(m.getPccId());
        material.setPerforated(m.getPerforated());
        material.setPreferred(m.getFeatured());
        material.setPrintTechnology(m.getPrintTechnology());
        material.setPrinters(new ArrayList<Printer>());
        if (m.getPrinters() != null) {
            material.getPrinters().addAll(printerMapper.map(m.getPrinters()));
        }
        material.setProductType(m.getProductType());
        material.setQuantityPerCarton(m.getQuantityPerCarton());
        material.setStandardRibbons(new ArrayList<Ribbon>());
        if (m.getStandardRibbons() != null) {
            material.getStandardRibbons().addAll(ribbonMapper.map(m.getStandardRibbons()));
        }
        material.setWidth(m.getWidth());
        for (Dmar dmarConstant : Dmar.values()) {
            String dmar = dmarData.get(dmarConstant + "_" + m.getId());
            material.getDmarSkus().put(dmarConstant, dmar);
        }
        material.setListPrice(m.getListPrice());
        material.setPartNumberDescription(m.getPartNumberDescription());
        return material;
    }

    public Map<String, String> getDmarData() {
        return dmarData;
    }

    public void setDmarData(Map<String, String> dmarData) {
        this.dmarData = dmarData;
    }

}
