
package com.zebra.das.service;

import com.zebra.das.model.api.SyncData;
import com.zebra.das.model.api.SyncDataPK;
import com.zebra.das.model.api.SyncJob;
import java.util.List;

public interface SyncDataService {
    public List<SyncData> findAll();

    public SyncData find(SyncDataPK syncDataId);

    public SyncData save(SyncData s);

    public List<SyncData> save(Iterable<SyncData> iterable);

    public List<SyncData> findByLocalId(String localId);

    public void deleteAllForJob(SyncJob id);
    
}
