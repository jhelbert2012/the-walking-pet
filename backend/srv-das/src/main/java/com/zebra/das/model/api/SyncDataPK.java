
package com.zebra.das.model.api;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class SyncDataPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "jobId", nullable = false, length = 45)
    private String jobId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "localId", nullable = false, length = 45)
    private String localId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "remoteId", nullable = false, length = 45)
    private String remoteId;

    public SyncDataPK() {
    }

    public SyncDataPK(String jobId, String localId, String remoteId) {
        this.jobId = jobId;
        this.localId = localId;
        this.remoteId = remoteId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobId != null ? jobId.hashCode() : 0);
        hash += (localId != null ? localId.hashCode() : 0);
        hash += (remoteId != null ? remoteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SyncDataPK)) {
            return false;
        }
        SyncDataPK other = (SyncDataPK) object;
        if ((this.jobId == null && other.jobId != null) || (this.jobId != null && !this.jobId.equals(other.jobId))) {
            return false;
        }
        if ((this.localId == null && other.localId != null) || (this.localId != null && !this.localId.equals(other.localId))) {
            return false;
        }
        if ((this.remoteId == null && other.remoteId != null) || (this.remoteId != null && !this.remoteId.equals(other.remoteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.zebra.das.model.api.SyncDataPK[ jobId=" + jobId + ", localId=" + localId + ", remoteId=" + remoteId + " ]";
    }

}
