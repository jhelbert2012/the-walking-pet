
package com.zebra.model.api;

import com.zebra.model.annotation.BusinessKey;
import javax.validation.constraints.Size;

public class SyncData extends HateoasSingleResource{
    @BusinessKey
    @Size(min = 1, max = 45)
    private String syncJobId;
    @BusinessKey
    @Size(min = 1, max = 45)
    private String localId;
    @BusinessKey
    @Size(min = 1, max = 45)
    private String remoteId;

    public SyncData() {
    }

    public String getSyncJob() {
        return syncJobId;
    }

    public void setSyncJob(String syncJob) {
        this.syncJobId = syncJob;
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

}
