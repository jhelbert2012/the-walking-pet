package com.zebra.das.service.impl;

import com.zebra.das.model.api.CleaningKit;
import com.zebra.das.repository.CleaningKitRepository;
import com.zebra.das.service.CleaningKitService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CleaningKitServiceImpl implements CleaningKitService {

    @Resource
    private CleaningKitRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<CleaningKit> findAll() {
        return repository.findAllWithPrinters();
    }

    @Transactional(readOnly = true)
    @Override
    public CleaningKit find(String cleaningkitId) {
        return repository.findOneWithPrinters(cleaningkitId);
    }

}
