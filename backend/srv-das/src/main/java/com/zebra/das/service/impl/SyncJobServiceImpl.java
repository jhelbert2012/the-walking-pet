package com.zebra.das.service.impl;

import com.zebra.das.model.api.SyncJob;
import com.zebra.das.repository.SyncJobRepository;
import com.zebra.das.service.SyncJobService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncJobServiceImpl implements SyncJobService {

    @Autowired
    private SyncJobRepository repository;

    @Override
    public List<SyncJob> findAll() {
        return repository.findAll();
    }

    @Override
    public List<SyncJob> findAllWithData() {
        return repository.findAllWithData();
    }

    @Override
    public SyncJob find(String syncJobId) {
        return repository.findOneWithData(syncJobId);
    }

    @Override
    public SyncJob save(SyncJob s) {
        return repository.save(s);
    }

}
