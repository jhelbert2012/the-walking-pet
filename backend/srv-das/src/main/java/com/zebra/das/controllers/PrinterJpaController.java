
package com.zebra.das.controllers;

import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;

import java.io.Serializable;

import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

import com.zebra.das.model.api.Ribbon;

import java.util.ArrayList;
import java.util.List;

import com.zebra.das.model.api.Material;
import com.zebra.das.model.api.CleaningKit;
import com.zebra.das.model.api.PrintHead;
import com.zebra.das.model.api.Printer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PrinterJpaController implements Serializable {

    public PrinterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Printer printer) throws PreexistingEntityException, Exception {
        if (printer.getRibbons() == null) {
            printer.setRibbons(new ArrayList<Ribbon>());
        }
        if (printer.getMaterials() == null) {
            printer.setMaterials(new ArrayList<Material>());
        }
        if (printer.getCleaningKits() == null) {
            printer.setCleaningKits(new ArrayList<CleaningKit>());
        }
        if(printer.getPrintHeads() == null){
        	printer.setPrintHeads(new ArrayList<PrintHead>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ribbon> attachedRibbons = new ArrayList<Ribbon>();
            for (Ribbon ribbonsRibbonToAttach : printer.getRibbons()) {
                ribbonsRibbonToAttach = em.getReference(ribbonsRibbonToAttach.getClass(), ribbonsRibbonToAttach.getId());
                attachedRibbons.add(ribbonsRibbonToAttach);
            }
            printer.setRibbons(attachedRibbons);
            List<Material> attachedMaterials = new ArrayList<Material>();
            for (Material materialsMaterialToAttach : printer.getMaterials()) {
                materialsMaterialToAttach = em.getReference(materialsMaterialToAttach.getClass(), materialsMaterialToAttach.getId());
                attachedMaterials.add(materialsMaterialToAttach);
            }
            printer.setMaterials(attachedMaterials);
            List<CleaningKit> attachedCleaningKits = new ArrayList<CleaningKit>();
            for (CleaningKit cleaningKitsCleaningKitToAttach : printer.getCleaningKits()) {
                cleaningKitsCleaningKitToAttach = em.getReference(cleaningKitsCleaningKitToAttach.getClass(), cleaningKitsCleaningKitToAttach.getId());
                attachedCleaningKits.add(cleaningKitsCleaningKitToAttach);
            }
            printer.setCleaningKits(attachedCleaningKits);
            em.persist(printer);
            for (Ribbon ribbonsRibbon : printer.getRibbons()) {
                ribbonsRibbon.getPrinters().add(printer);
                ribbonsRibbon = em.merge(ribbonsRibbon);
            }
            for (Material materialsMaterial : printer.getMaterials()) {
                materialsMaterial.getPrinters().add(printer);
                materialsMaterial = em.merge(materialsMaterial);
            }
            for (CleaningKit cleaningKitsCleaningKit : printer.getCleaningKits()) {
                cleaningKitsCleaningKit.getPrinters().add(printer);
                cleaningKitsCleaningKit = em.merge(cleaningKitsCleaningKit);
            }
            for (PrintHead printsHeadsPrintHead : printer.getPrintHeads()){
            	printsHeadsPrintHead.getPrinters().add(printer);
            	printsHeadsPrintHead = em.merge(printsHeadsPrintHead);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrinter(printer.getId()) != null) {
                throw new PreexistingEntityException("Printer " + printer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Printer printer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Printer persistentPrinter = em.find(Printer.class, printer.getId());
            List<Ribbon> ribbonsOld = persistentPrinter.getRibbons();
            List<Ribbon> ribbonsNew = printer.getRibbons();
            List<Material> materialsOld = persistentPrinter.getMaterials();
            List<Material> materialsNew = printer.getMaterials();
            List<CleaningKit> cleaningKitsOld = persistentPrinter.getCleaningKits();
            List<CleaningKit> cleaningKitsNew = printer.getCleaningKits();
            List<PrintHead> printHeadsOld = persistentPrinter.getPrintHeads();
            List<PrintHead> printHeadsNew = printer.getPrintHeads();
            List<Ribbon> attachedRibbonsNew = new ArrayList<Ribbon>();
            for (Ribbon ribbonsNewRibbonToAttach : ribbonsNew) {
                ribbonsNewRibbonToAttach = em.getReference(ribbonsNewRibbonToAttach.getClass(), ribbonsNewRibbonToAttach.getId());
                attachedRibbonsNew.add(ribbonsNewRibbonToAttach);
            }
            ribbonsNew = attachedRibbonsNew;
            printer.setRibbons(ribbonsNew);
            List<Material> attachedMaterialsNew = new ArrayList<Material>();
            for (Material materialsNewMaterialToAttach : materialsNew) {
                materialsNewMaterialToAttach = em.getReference(materialsNewMaterialToAttach.getClass(), materialsNewMaterialToAttach.getId());
                attachedMaterialsNew.add(materialsNewMaterialToAttach);
            }
            materialsNew = attachedMaterialsNew;
            printer.setMaterials(materialsNew);
            List<CleaningKit> attachedCleaningKitsNew = new ArrayList<CleaningKit>();
            for (CleaningKit cleaningKitsNewCleaningKitToAttach : cleaningKitsNew) {
                cleaningKitsNewCleaningKitToAttach = em.getReference(cleaningKitsNewCleaningKitToAttach.getClass(), cleaningKitsNewCleaningKitToAttach.getId());
                attachedCleaningKitsNew.add(cleaningKitsNewCleaningKitToAttach);
            }
            cleaningKitsNew = attachedCleaningKitsNew;
            printer.setCleaningKits(cleaningKitsNew);
            List<PrintHead> attachedPrintHeadNew = new ArrayList<PrintHead>();
            for (PrintHead printHeadsNewPrintHeadToAttach : attachedPrintHeadNew) {
            	printHeadsNewPrintHeadToAttach = em.getReference(printHeadsNewPrintHeadToAttach.getClass(), printHeadsNewPrintHeadToAttach.getId());
            	attachedPrintHeadNew.add(printHeadsNewPrintHeadToAttach);
			}
            printHeadsNew = attachedPrintHeadNew;
            printer.setPrintHeads(printHeadsNew);
            printer = em.merge(printer);
            for (Ribbon ribbonsOldRibbon : ribbonsOld) {
                if (!ribbonsNew.contains(ribbonsOldRibbon)) {
                    ribbonsOldRibbon.getPrinters().remove(printer);
                    ribbonsOldRibbon = em.merge(ribbonsOldRibbon);
                }
            }
            for (Ribbon ribbonsNewRibbon : ribbonsNew) {
                if (!ribbonsOld.contains(ribbonsNewRibbon)) {
                    ribbonsNewRibbon.getPrinters().add(printer);
                    ribbonsNewRibbon = em.merge(ribbonsNewRibbon);
                }
            }
            for (Material materialsOldMaterial : materialsOld) {
                if (!materialsNew.contains(materialsOldMaterial)) {
                    materialsOldMaterial.getPrinters().remove(printer);
                    materialsOldMaterial = em.merge(materialsOldMaterial);
                }
            }
            for (Material materialsNewMaterial : materialsNew) {
                if (!materialsOld.contains(materialsNewMaterial)) {
                    materialsNewMaterial.getPrinters().add(printer);
                    materialsNewMaterial = em.merge(materialsNewMaterial);
                }
            }
            for (CleaningKit cleaningKitsOldCleaningKit : cleaningKitsOld) {
                if (!cleaningKitsNew.contains(cleaningKitsOldCleaningKit)) {
                    cleaningKitsOldCleaningKit.getPrinters().remove(printer);
                    cleaningKitsOldCleaningKit = em.merge(cleaningKitsOldCleaningKit);
                }
            }
            for (CleaningKit cleaningKitsNewCleaningKit : cleaningKitsNew) {
                if (!cleaningKitsOld.contains(cleaningKitsNewCleaningKit)) {
                    cleaningKitsNewCleaningKit.getPrinters().add(printer);
                    cleaningKitsNewCleaningKit = em.merge(cleaningKitsNewCleaningKit);
                }
            }
            for (PrintHead printHeadsOldPrintHead : printHeadsOld) {
				if(!printHeadsOld.contains(printHeadsOldPrintHead)){
				   printHeadsOldPrintHead.getPrinters().add(printer);
				   printHeadsOldPrintHead = em.merge(printHeadsOldPrintHead);
				}
			}
            for (PrintHead printHeadsNewPrintHead : printHeadsNew) {
				if(!printHeadsOld.contains(printHeadsNewPrintHead)){
				   printHeadsNewPrintHead.getPrinters().add(printer);
				   printHeadsNewPrintHead = em.merge(printHeadsNewPrintHead);
				}
			}
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = printer.getId();
                if (findPrinter(id) == null) {
                    throw new NonexistentEntityException("The printer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Printer printer;
            try {
                printer = em.getReference(Printer.class, id);
                printer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The printer with id " + id + " no longer exists.", enfe);
            }
            List<Ribbon> ribbons = printer.getRibbons();
            for (Ribbon ribbonsRibbon : ribbons) {
                ribbonsRibbon.getPrinters().remove(printer);
                ribbonsRibbon = em.merge(ribbonsRibbon);
            }
            List<Material> materials = printer.getMaterials();
            for (Material materialsMaterial : materials) {
                materialsMaterial.getPrinters().remove(printer);
                materialsMaterial = em.merge(materialsMaterial);
            }
            List<CleaningKit> cleaningKits = printer.getCleaningKits();
            for (CleaningKit cleaningKitsCleaningKit : cleaningKits) {
                cleaningKitsCleaningKit.getPrinters().remove(printer);
                cleaningKitsCleaningKit = em.merge(cleaningKitsCleaningKit);
            }
            List<PrintHead> printHead = printer.getPrintHeads();
            for (PrintHead printHeadsPrintHead : printHead) {
				printHeadsPrintHead.getPrinters().remove(printer);
				printHeadsPrintHead = em.merge(printHeadsPrintHead);
			}
            em.remove(printer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Printer> findPrinterEntities() {
        return findPrinterEntities(true, -1, -1);
    }

    public List<Printer> findPrinterEntities(int maxResults, int firstResult) {
        return findPrinterEntities(false, maxResults, firstResult);
    }

    private List<Printer> findPrinterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Printer as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Printer findPrinter(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Printer.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrinterCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Printer as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
