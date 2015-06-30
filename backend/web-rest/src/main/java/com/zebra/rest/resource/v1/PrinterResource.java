package com.zebra.rest.resource.v1;

import com.zebra.das.service.PrinterService;
import com.zebra.das.service.SyncDataService;

import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.model.api.Printer;
import com.zebra.rest.mapper.PrinterMapper;
import com.zebra.rest.resource.ZebraResource;
import org.springframework.beans.factory.annotation.Autowired;

@Component("/v1/printers/{printerId}")
@Scope("prototype")
public class PrinterResource extends ZebraResource {

    @Autowired
    private PrinterService printerService;

    @Autowired
    private PrinterMapper printerMapper;
    
    private String printerId;

    @Override
    public String getName() {
        return "Printer resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a Printer";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        this.printerId = (String) getRequest().getAttributes().get("printerId");
    }

    @Get("json")
    public Representation getOperation() {

        // business logic (throw Not Found exception if not found!)
        Printer found = printerMapper.map(printerService.find(printerId));

        setStatus(Status.SUCCESS_OK);
        setSelfLink(found);

        JacksonRepresentation<Printer> printerRepresentation = new JacksonRepresentation<>(found);

        return printerRepresentation;

    }
}
