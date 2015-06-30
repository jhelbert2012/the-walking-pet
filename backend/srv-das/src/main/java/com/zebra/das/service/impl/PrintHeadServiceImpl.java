package com.zebra.das.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.das.model.api.PrintHead;
import com.zebra.das.repository.PrintHeadRepository;
import com.zebra.das.service.PrintHeadService;

@Service
public class PrintHeadServiceImpl implements PrintHeadService {

	@Autowired
    private PrintHeadRepository repository;
	@Override
	public List<PrintHead> findAll() {
		return repository.findAllWithPrinters();
	}

	@Override
	public PrintHead find(String printHeadId) {
		return repository.findOneWithPrinters(printHeadId);
	}

}
