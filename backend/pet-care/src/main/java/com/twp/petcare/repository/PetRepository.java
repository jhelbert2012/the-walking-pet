package com.twp.petcare.repository;

import com.twp.petcare.bean.Pet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "pets", path = "pets")
public interface PetRepository extends MongoRepository<Pet, String>, PetRepositoryCustom {

    List<Pet> findByName(@Param("name") String name);

    List<Pet> findBySpecie(@Param("specie") int specie);

    List<Pet> findByColourIgnoreCase(@Param("color") String color, Pageable pageable);

}
