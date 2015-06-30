package com.zebra.rest.mapper;

import com.zebra.constant.model.Dmar;
import com.zebra.model.api.Printer;
import com.zebra.model.api.Ribbon;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RibbonMapper extends Mapper<Ribbon, com.zebra.das.model.api.Ribbon> {

    @Autowired
    private PrinterMapper printerMapper;

    private Map<String, String> dmarData;

    @Override
    public Ribbon map(com.zebra.das.model.api.Ribbon p) {
        Ribbon ribbon = new Ribbon();
        ribbon.setId(p.getId());
        ribbon.setDescription(p.getDescription());
        ribbon.setDetailedSpecs(p.getDetailedSpecs());
        ribbon.setDmarSku(p.getDmarSku());
        ribbon.setImagesPerRoll(p.getImagesPerRoll());
        ribbon.setLength(p.getLength());
        ribbon.setName(p.getName());
        ribbon.setPrintTechnology(p.getPrintTechnology());
        ribbon.setPrinters(new ArrayList<Printer>());
        ribbon.getPrinters().addAll(printerMapper.map(p.getPrinters()));
        ribbon.setRibbonApplication(p.getRibbonApplication());
        ribbon.setRibbonColor(p.getRibbonColor());
        ribbon.setWidth(p.getWidth());
        for (Dmar dmarConstant : Dmar.values()) {
            String dmar = dmarData.get(dmarConstant + "_" + p.getId());
            ribbon.getDmarSkus().put(dmarConstant, dmar);
        }
        ribbon.setListPrice(p.getListPrice());
        ribbon.setPartNumberDescription(p.getPartNumberDescription());
        return ribbon;
    }

    public Map<String, String> getDmarData() {
        return dmarData;
    }

    public void setDmarData(Map<String, String> dmarData) {
        this.dmarData = dmarData;
    }

}
