package com.zebra.das.repository;

import com.zebra.das.model.api.CleaningKit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CleaningKitRepository extends JpaRepository<CleaningKit, String> {

    @Query("SELECT DISTINCT c FROM CleaningKit c LEFT JOIN FETCH c.printers")
    List<CleaningKit> findAllWithPrinters();

    @Query("SELECT c FROM CleaningKit c LEFT JOIN FETCH c.printers WHERE c.id=:id")
    CleaningKit findOneWithPrinters(@Param("id") String id);
}
