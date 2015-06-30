
package com.zebra.das.service.impl;

import com.zebra.das.model.api.Principal;
import com.zebra.das.repository.PrincipalRepository;
import com.zebra.das.service.PrincipalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrincipalServiceImpl implements PrincipalService{

    @Autowired
    private PrincipalRepository repository;
    @Override
    public List<Principal> findAll() {
        return repository.findAll();
    }

    @Override
    public Principal find(String principalId) {
        return repository.findOne(principalId);
    }

}
