package com.zebra.das.service;

import java.util.List;

import com.zebra.das.model.api.PrintHead;

public interface PrintHeadService {
	public List<PrintHead> findAll();

    public PrintHead find(String printHeadId);
}
