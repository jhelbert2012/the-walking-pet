package com.zebra.rest.temp;

import com.zebra.das.service.CleaningKitService;
import com.zebra.das.service.MaterialService;
import com.zebra.das.service.PartnerService;
import com.zebra.das.service.PrintHeadService;
import com.zebra.das.service.PrinterService;
import com.zebra.das.service.RibbonService;
import com.zebra.das.service.SyncDataService;
import com.zebra.model.api.CleaningKit;
import com.zebra.model.api.Material;
import com.zebra.model.api.Partner;
import com.zebra.model.api.PrintHead;
import com.zebra.model.api.Printer;
import com.zebra.model.api.Ribbon;
import com.zebra.rest.mapper.CleaningKitMapper;
import com.zebra.rest.mapper.MaterialMapper;
import com.zebra.rest.mapper.PartnerMapper;
import com.zebra.rest.mapper.PrintHeadMapper;
import com.zebra.rest.mapper.PrinterMapper;
import com.zebra.rest.mapper.RibbonMapper;
import com.zebra.rest.mapper.SyncDataMapper;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("PersistenceStorage")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
public class PersistenceStorageImpl implements PersistenceStorage {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PartnerMapper partnerMapper;

    @Autowired
    private RibbonService ribbonService;

    @Autowired
    private RibbonMapper ribbonMapper;

    @Autowired
    private CleaningKitService cleaningKitService;

    @Autowired
    private CleaningKitMapper cleaningKitMapper;

    @Autowired
    private PrinterService printerService;

    @Autowired
    private PrinterMapper printerMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private SyncDataMapper syncDataMapper;
    
    @Autowired
    private PrintHeadService printHeadService; 
    
    @Autowired
    private PrintHeadMapper printHeadMapper;

    private static Storage storage;

    @PostConstruct
    @Override
    public void createStorage() {

        if (storage == null) {
            reloadStorage();
        }

    }

    @Override
    public void reloadStorage() {
        Map<String, String> dmarData = syncDataMapper.map(syncDataService.findAll());
        printerMapper.setDmarData(dmarData);
        List<Printer> printers = printerMapper.map(printerService.findAll());
        cleaningKitMapper.setDmarData(dmarData);
        List<CleaningKit> cleaningKits = cleaningKitMapper.map(cleaningKitService.findAll());
        ribbonMapper.setDmarData(dmarData);
        List<Ribbon> ribbons = ribbonMapper.map(ribbonService.findAll());
        materialMapper.setDmarData(dmarData);
        List<Material> materials = materialMapper.map(materialService.findAll());
        List<Partner> partners = partnerMapper.map(partnerService.findAll());
        List<PrintHead> printHeads = printHeadMapper.map(printHeadService.findAll());
        if (storage == null) {
            storage = new Storage(printers, ribbons, materials, cleaningKits, partners, printHeads);
        } else {
            synchronized (storage) {
                storage = new Storage(printers, ribbons, materials, cleaningKits, partners, printHeads);
            }
        }
    }

    @Override
    public Storage getStorage() {
        return storage;
    }
}
