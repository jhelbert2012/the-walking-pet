package com.zebra.das.repository;

import com.zebra.das.model.api.SyncJob;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SyncJobRepository extends JpaRepository<SyncJob, String> {

    @Query("SELECT DISTINCT c FROM SyncJob c LEFT JOIN FETCH c.syncDataList")
    List<SyncJob> findAllWithData();

    @Query("SELECT c FROM SyncJob c LEFT JOIN FETCH c.syncDataList WHERE c.id=:id")
    SyncJob findOneWithData(@Param("id") String id);
}
