
package com.zebra.das.service;

import com.zebra.das.model.api.Printer;
import java.util.List;

public interface PrinterService {
    public List<Printer> findAll();

    public Printer find(String printerId);

}
