package com.twp.petcare.repository;

import com.twp.petcare.bean.Owner;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "owners", path = "owners")
public interface OwnerRepository extends MongoRepository<Owner, String> {

    List<Owner> findByUsername(@Param("username") String username);
}
