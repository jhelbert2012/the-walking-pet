
package com.zebra.das.service;

import com.zebra.das.model.api.Principal;
import java.util.List;

public interface PrincipalService {
    public List<Principal> findAll();

    public Principal find(String principalId);
}
