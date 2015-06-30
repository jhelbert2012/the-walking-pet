package com.zebra.das.repository;

import com.zebra.das.model.api.Material;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaterialRepository extends JpaRepository<Material, String>, MaterialRepositoryCustom{
    @Query("SELECT DISTINCT m FROM Material m LEFT JOIN FETCH m.printers LEFT JOIN FETCH m.highRibbons LEFT JOIN FETCH m.standardRibbons")
    List<Material> findAllWithRibbons();

    @Query("SELECT m FROM Material m LEFT JOIN FETCH m.printers LEFT JOIN FETCH m.highRibbons LEFT JOIN FETCH m.standardRibbons WHERE m.id=:id")
    Material findOneWithRibbons(@Param("id") String id);

    @Query("SELECT DISTINCT m FROM Material m INNER JOIN FETCH m.printers p LEFT JOIN FETCH m.highRibbons hr LEFT JOIN FETCH m.standardRibbons sr WHERE p.id in (SELECT DISTINCT pr.id FROM Printer pr INNER JOIN pr.printHeads )")
	List<Material> findAllWithPrintHeads();

	@Query("SELECT m FROM Material m LEFT JOIN FETCH m.printers p LEFT JOIN FETCH m.highRibbons hr LEFT JOIN FETCH m.standardRibbons sr WHERE m.id=:id AND p.id in (SELECT DISTINCT pr.id FROM Printer pr INNER JOIN pr.printHeads )")
    Material findOneWithPrintHead(@Param("id") String id);

//    @Query("SELECT m FROM Material m WHERE m.printTech = :printerTech"
//            + " OR m.productType = " 
//            + "LEFT JOIN FETCH m.printers LEFT JOIN FETCH m.highRibbons LEFT JOIN FETCH m.standardRibbons WHERE m.id=:id")
//    public List<Material> find(String[] printers, String printTech, String productType, String materialType, Double width, Double length, String dmar, String ribbonApp, String cardTech, Double cardThick, String color, String industry);
}
