package com.zebra.rest.mapper;

import com.zebra.model.api.SyncJob;
import com.zebra.rest.mapper.exception.MapperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncJobMapper extends Mapper<SyncJob, com.zebra.das.model.api.SyncJob> {

    @Autowired
    SyncDataMapper syncDataMapper;

    @Override
    public SyncJob map(com.zebra.das.model.api.SyncJob job) throws MapperException {
        SyncJob syncJob = new SyncJob();
        syncJob.setId(job.getId());
        syncJob.setDomain(job.getHostname());
        syncJob.setFormat(job.getFormat());
        syncJob.setPassword(job.getPassword());
        syncJob.setPort(job.getPort());
        syncJob.setProtocol(job.getProtocol());
        syncJob.setResource(job.getPath());
//        syncJob.setSyncDataList(syncDataMapper.map(job.getSyncDataList()));
        return syncJob;
    }

}
