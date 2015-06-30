package com.zebra.rest.mapper;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.model.api.PrintHead;
import com.zebra.model.api.Printer;
import com.zebra.rest.mapper.Mapper;
import com.zebra.rest.mapper.PrinterMapper;

/**
 * 
 */

/**
 * @author ignaciogonzalez
 *
 */
@Service
public class PrintHeadMapper extends Mapper<PrintHead, com.zebra.das.model.api.PrintHead>{
    private Map<String, String> dmarData;
	
    @Autowired
	private PrinterMapper printerMapper;
	 
	@Override
	public PrintHead map(com.zebra.das.model.api.PrintHead p){
		PrintHead printHead = new PrintHead();
		printHead.setId(p.getId());
		printHead.setDpi(p.getDpi());
		printHead.setListPrice(p.getListPrice());
		printHead.setPartNumberDescription(p.getPartNumberDescription());
		return printHead;
	}
	
    public void setDmarData(Map<String, String> dmarData) {
        this.dmarData = dmarData;
    }

}
