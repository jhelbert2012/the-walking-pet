package com.zebra.das.model.api;

import com.zebra.constant.model.Dmar;
import com.zebra.constant.model.SyncFormat;
import com.zebra.constant.model.SyncProtocol;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Cacheable
@Table(name = "syncJob")
public class SyncJob implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String id;
    @Enumerated(EnumType.STRING)
    private Dmar dmar;
    @Size(max = 255)
    @Column(length = 255)
    private String hostname;
    @Column
    private Integer port;
    @Size(max = 45)
    @Column(length = 45)
    private String path;
    @Size(max = 255)
    @Column(length = 255)
    private String user;
    @Size(max = 255)
    @Column(length = 255)
    private String password;
    @Column
    private Integer localIdPosition;
    @Column
    private Integer remoteIdPosition;
    @Column
    private String localIdRESTAttribute;
    @Column
    private String remoteIdRESTAttribute;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "syncJob", fetch = FetchType.LAZY)
    private List<SyncData> syncDataList;
    @Enumerated(EnumType.STRING)
    private SyncProtocol protocol;
    @Enumerated(EnumType.STRING)
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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SyncJob)) {
            return false;
        }
        SyncJob other = (SyncJob) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.zebra.das.model.api.SyncJobs[ id=" + id + " ]";
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

    public Dmar getDmar() {
        return dmar;
    }

    public void setDmar(Dmar dmar) {
        this.dmar = dmar;
    }

    public Integer getLocalIdPosition() {
        return localIdPosition;
    }

    public void setLocalIdPosition(Integer localIdPosition) {
        this.localIdPosition = localIdPosition;
    }

    public Integer getRemoteIdPosition() {
        return remoteIdPosition;
    }

    public void setRemoteIdPosition(Integer remoteIdPosition) {
        this.remoteIdPosition = remoteIdPosition;
    }

    public String getLocalIdRESTAttribute() {
        return localIdRESTAttribute;
    }

    public void setLocalIdRESTAttribute(String localIdRESTAttribute) {
        this.localIdRESTAttribute = localIdRESTAttribute;
    }

    public String getRemoteIdRESTAttribute() {
        return remoteIdRESTAttribute;
    }

    public void setRemoteIdRESTAttribute(String remoteIdRESTAttribute) {
        this.remoteIdRESTAttribute = remoteIdRESTAttribute;
    }

}
