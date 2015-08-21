package com.twp.petcare.repository;

import com.twp.petcare.bean.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "owners", path = "owners")
public interface OwnerRepository extends MongoRepository<Owner, String> {

    List<Owner> findByUsername(@Param("username") String username);

    Page<Owner> findByDocumentTypeAndUsername(@Param("documentType") String documentType, @Param("userName") String username, Pageable pageable);

    Page<Owner> findByDocumentNumberAndUsernameAllIgnoreCase(@Param("documentNumber") String documentNumber, @Param("userName") String username, Pageable pageable);
}
