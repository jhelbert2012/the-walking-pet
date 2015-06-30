/**
 * 
 */
package com.zebra.das.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zebra.das.model.api.PrintHead;

/**
 * @author ignaciogonzalez
 *
 */
public interface PrintHeadRepository extends JpaRepository<PrintHead, String> {

	@Query("SELECT DISTINCT r FROM PrintHead r LEFT JOIN FETCH r.printers ")
    List<PrintHead> findAllWithPrinters();

    @Query("SELECT p FROM PrintHead p LEFT JOIN FETCH p.printers pr WHERE p.id=:id")
    PrintHead findOneWithPrinters(@Param("id") String id);
}
