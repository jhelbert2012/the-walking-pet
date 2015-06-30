package com.zebra.das.repository;

import com.zebra.das.model.api.Ribbon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RibbonRepository extends JpaRepository<Ribbon, String> {
    @Query("SELECT DISTINCT r FROM Ribbon r LEFT JOIN FETCH r.printers ")
    List<Ribbon> findAllWithPrinters();

    @Query("SELECT r FROM Ribbon r LEFT JOIN FETCH r.printers WHERE r.id=:id")
    Ribbon findOneWithPrinters(@Param("id") String id);
}
