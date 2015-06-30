package com.zebra.das.repository;

import java.util.List;

import com.zebra.das.model.api.Printer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrinterRepository extends JpaRepository<Printer, String> {

	@Query("SELECT DISTINCT p FROM Printer p LEFT JOIN FETCH p.printHeads")
	List<Printer> findAllWithPrintHeads();

	@Query("SELECT p FROM Printer p LEFT JOIN FETCH p.printHeads WHERE p.id=:id")
	Printer findWithPrintHeads(@Param("id")String printerId);

}
