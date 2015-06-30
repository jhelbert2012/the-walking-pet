package com.zebra.rest.mapper;

import com.zebra.constant.model.Dmar;
import com.zebra.model.api.CleaningKit;
import com.zebra.model.api.Printer;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CleaningKitMapper extends Mapper<CleaningKit, com.zebra.das.model.api.CleaningKit> {

    @Autowired
    private PrinterMapper printerMapper;
    private Map<String, String> dmarData;

    @Override
    public CleaningKit map(com.zebra.das.model.api.CleaningKit c) {
        CleaningKit cleaningKit = new CleaningKit();
        cleaningKit.setId(c.getId());
        cleaningKit.setDescription(c.getDescription());
        cleaningKit.setDmarSku(c.getDmarSku());
        cleaningKit.setName(c.getName());
        cleaningKit.setPrinters(new ArrayList<Printer>());
        for (com.zebra.das.model.api.Printer p : c.getPrinters()) {
            Printer printer = printerMapper.map(p);
            cleaningKit.getPrinters().add(printer);
        }
        for (Dmar dmarConstant : Dmar.values()) {
            String dmar = dmarData.get(dmarConstant + "_" + c.getId());
            cleaningKit.getDmarSkus().put(dmarConstant, dmar);
        }
        return cleaningKit;
    }

    public void setDmarData(Map<String, String> dmarData) {
        this.dmarData = dmarData;
    }

}
