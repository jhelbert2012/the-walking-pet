
package com.zebra.das.service.impl;

import com.zebra.das.model.api.Printer;
import com.zebra.das.repository.PrinterRepository;
import com.zebra.das.service.PrinterService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrinterServiceImpl implements PrinterService{

    @Autowired
    private PrinterRepository repository;
    @Override
    public List<Printer> findAll() {
        return repository.findAllWithPrintHeads();
    }

    @Override
    public Printer find(String printerId) {
        return repository.findWithPrintHeads(printerId);
    }

}
