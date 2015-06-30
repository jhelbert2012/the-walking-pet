package com.zebra.das.repository;

import com.zebra.das.model.api.Principal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrincipalRepository extends JpaRepository<Principal, String> {

}
