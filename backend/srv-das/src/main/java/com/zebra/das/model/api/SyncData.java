
package com.zebra.das.model.api;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name = "syncData")
public class SyncData implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SyncDataPK syncDataPK;
    @JoinColumn(name = "jobId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SyncJob syncJob;

    public SyncData() {
    }

    public SyncData(SyncDataPK syncDataPK) {
        this.syncDataPK = syncDataPK;
    }

    public SyncData(String jobId, String localId, String remoteId) {
        this.syncDataPK = new SyncDataPK(jobId, localId, remoteId);
    }

    public SyncDataPK getSyncDataPK() {
        return syncDataPK;
    }

    public void setSyncDataPK(SyncDataPK syncDataPK) {
        this.syncDataPK = syncDataPK;
    }

    public SyncJob getSyncJob() {
        return syncJob;
    }

    public void setSyncJob(SyncJob syncJob) {
        this.syncJob = syncJob;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (syncDataPK != null ? syncDataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SyncData)) {
            return false;
        }
        SyncData other = (SyncData) object;
        if ((this.syncDataPK == null && other.syncDataPK != null) || (this.syncDataPK != null && !this.syncDataPK.equals(other.syncDataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.zebra.das.model.api.SyncData[ syncDataPK=" + syncDataPK + " ]";
    }

}
