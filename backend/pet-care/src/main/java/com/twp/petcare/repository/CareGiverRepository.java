package com.twp.petcare.repository;

import com.twp.petcare.bean.CareGiver;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "caregivers", path = "caregivers")
public interface CareGiverRepository extends MongoRepository<CareGiver, String> {
    
    List<CareGiver> findByUsername(@Param("username") String username);
}
