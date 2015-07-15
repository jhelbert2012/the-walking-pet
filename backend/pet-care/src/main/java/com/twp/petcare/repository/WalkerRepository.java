package com.twp.petcare.repository;

import com.twp.petcare.bean.Walker;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "walkers", path = "walkers")
public interface WalkerRepository extends MongoRepository<Walker, String> {

    List<Walker> findByUsername(@Param("username") String username);
}
