package com.zebra.das.service.impl;

import com.zebra.das.model.api.SyncData;
import com.zebra.das.model.api.SyncDataPK;
import com.zebra.das.model.api.SyncJob;
import com.zebra.das.repository.SyncDataRepository;
import com.zebra.das.service.SyncDataService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SyncDataServiceImpl implements SyncDataService {

    @Resource
    private SyncDataRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<SyncData> findAll() {
        return repository.findAllWithJob();
    }

    @Override
    public SyncData find(SyncDataPK syncDataId) {
        return repository.findOne(syncDataId);
    }

    @Override
    public SyncData save(SyncData s) {
        return repository.save(s);
    }

    @Override
    public List<SyncData> save(Iterable<SyncData> iterable) {
        return repository.save(iterable);
    }

    @Override
    public List<SyncData> findByLocalId(String localId) {
        return repository.findByLocalId(localId);
    }

    @Transactional
    @Override
    public void deleteAllForJob(SyncJob id) {
        repository.deleteAllForJob(id);
    }

}
