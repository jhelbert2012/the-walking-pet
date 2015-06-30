package com.zebra.rest.mapper;

import com.zebra.model.api.PrintHead;
import com.zebra.model.api.Printer;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrinterMapper extends Mapper<Printer, com.zebra.das.model.api.Printer> {
    private Map<String, String> dmarData;
	@Autowired
	private PrintHeadMapper printHeadMapper;
	
    @Override
    public Printer map(com.zebra.das.model.api.Printer p) {
        Printer printer = new Printer();
        printer.setId(p.getId());
        printer.setImageLink(p.getImageLink());
        printer.setName(p.getName());
        printer.setPrintTechnology(p.getPrintTechnologies());
        printer.setPrinterType(p.getPrinterType());
        printer.setPrintHeads(new ArrayList<PrintHead>());
        printer.getPrintHeads().addAll(printHeadMapper.map(p.getPrintHeads()));
        return printer;
    }

    public void setDmarData(Map<String, String> dmarData) {
        this.dmarData = dmarData;
    }

}
