package com.zebra.das.repository;

import com.zebra.das.model.api.SyncData;
import com.zebra.das.model.api.SyncDataPK;
import com.zebra.das.model.api.SyncJob;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SyncDataRepository extends JpaRepository<SyncData, SyncDataPK> {

    @Query("SELECT s FROM SyncData s JOIN FETCH s.syncJob WHERE s.syncDataPK.localId = :localId")
    List<SyncData> findByLocalId(@Param("localId") String localId);

    @Query("SELECT s FROM SyncData s LEFT JOIN FETCH s.syncJob")
    List<SyncData> findAllWithJob();

    @Modifying
    @Query("DELETE FROM SyncData s WHERE s.syncJob = :syncJob")
    void deleteAllForJob(@Param("syncJob") SyncJob job);
}
