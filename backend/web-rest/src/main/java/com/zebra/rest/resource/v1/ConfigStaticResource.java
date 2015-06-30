package com.zebra.rest.resource.v1;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zebra.rest.resource.ZebraResource;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;

@Component("/v1/config/{staticFileId}")
@Scope("prototype")
public class ConfigStaticResource extends ZebraResource {

    private static final String STATIC_JSON_DIR = "/static/";
    private static final String STATIC_FILE_ID_PARAM = "staticFileId";
    private static final String DEFAULT_CONFIG_NOT_FOUND_MESSAGE = "The requested static resource does not exist";
    private static final String DEFAULT_MESSAGE_KEY = "message";
    private static final String JSON_EXTENSION = ".json";    
    private static final Logger log = Logger.getLogger(ConfigStaticResource.class);

    private String staticFileId;

    @Override
    public String getName() {
        return "StaticFile resource";
    }

    @Override
    public String getDescription() {
        return "The resource representing a StaticFile";
    }

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        this.staticFileId = (String) getRequest().getAttributes().get(STATIC_FILE_ID_PARAM);
    }

    @Get("json")
    public Representation getOperation() throws Exception {
        Map<String, String> defaultResponse = new HashMap<>();
        defaultResponse.put(DEFAULT_MESSAGE_KEY, DEFAULT_CONFIG_NOT_FOUND_MESSAGE);
        Representation response = new JacksonRepresentation(defaultResponse);
        if (!StringUtils.isEmpty(staticFileId)) {
            URL url = this.getClass().getResource(STATIC_JSON_DIR + staticFileId + JSON_EXTENSION);
            if (url != null) {
                byte[] encoded = Files.readAllBytes(Paths.get(url.toURI()));
                response = new JsonRepresentation(new String(encoded));
            }
            setStatus(Status.SUCCESS_OK);
        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }

        return response;
    }

}
