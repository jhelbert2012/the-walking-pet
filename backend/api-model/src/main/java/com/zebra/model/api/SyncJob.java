package com.zebra.model.api;

import com.zebra.constant.model.SyncFormat;
import com.zebra.constant.model.SyncProtocol;
import com.zebra.model.annotation.BusinessKey;
import java.util.List;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

public class SyncJob extends HateoasSingleResource {

    @BusinessKey
    @NotBlank
    private String id;
    @Size(max = 255)
    @BusinessKey
    private String domain;
    @BusinessKey
    private Integer port;
    @Size(max = 45)
    @BusinessKey
    private String resource;
    @Size(max = 255)
    @BusinessKey
    private String user;
    @Size(max = 255)
    @BusinessKey
    private String password;
    @BusinessKey
    private List<SyncData> syncDataList;
    @BusinessKey
    private SyncProtocol protocol;
    @BusinessKey
    private SyncFormat format;

    public SyncJob() {
    }

    public SyncJob(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SyncData> getSyncDataList() {
        return syncDataList;
    }

    public void setSyncDataList(List<SyncData> syncDataList) {
        this.syncDataList = syncDataList;
    }

    public SyncProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(SyncProtocol protocol) {
        this.protocol = protocol;
    }

    public SyncFormat getFormat() {
        return format;
    }

    public void setFormat(SyncFormat format) {
        this.format = format;
    }

}
