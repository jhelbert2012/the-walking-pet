package com.twp.petcare.repository;

import com.twp.petcare.bean.Pet;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pets", path = "pets")
public interface PetRepository extends MongoRepository<Pet, String> {

    List<Pet> findByName(@Param("name") String name);
    List<Pet> findBySpecie(@Param("specie") String specie);

}
