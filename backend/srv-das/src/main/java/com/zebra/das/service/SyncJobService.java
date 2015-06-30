package com.zebra.das.service;

import com.zebra.das.model.api.SyncJob;
import java.util.List;

public interface SyncJobService {

    public List<SyncJob> findAll();

    public List<SyncJob> findAllWithData();

    public SyncJob find(String syncJobId);

    public SyncJob save(SyncJob s);

}
