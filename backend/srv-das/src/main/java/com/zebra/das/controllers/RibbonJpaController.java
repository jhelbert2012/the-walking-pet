
package com.zebra.das.controllers;

import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.zebra.das.model.api.Printer;
import java.util.ArrayList;
import java.util.List;
import com.zebra.das.model.api.Material;
import com.zebra.das.model.api.Ribbon;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class RibbonJpaController implements Serializable {

    public RibbonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ribbon ribbon) throws PreexistingEntityException, Exception {
        if (ribbon.getPrinters() == null) {
            ribbon.setPrinters(new ArrayList<Printer>());
        }
        if (ribbon.getMaterialsHigh() == null) {
            ribbon.setMaterialsHigh(new ArrayList<Material>());
        }
        if (ribbon.getMaterialsStandard() == null) {
            ribbon.setMaterialsStandard(new ArrayList<Material>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Printer> attachedPrinters = new ArrayList<Printer>();
            for (Printer printersPrinterToAttach : ribbon.getPrinters()) {
                printersPrinterToAttach = em.getReference(printersPrinterToAttach.getClass(), printersPrinterToAttach.getId());
                attachedPrinters.add(printersPrinterToAttach);
            }
            ribbon.setPrinters(attachedPrinters);
            List<Material> attachedMaterialsHigh = new ArrayList<Material>();
            for (Material materialsHighMaterialToAttach : ribbon.getMaterialsHigh()) {
                materialsHighMaterialToAttach = em.getReference(materialsHighMaterialToAttach.getClass(), materialsHighMaterialToAttach.getId());
                attachedMaterialsHigh.add(materialsHighMaterialToAttach);
            }
            ribbon.setMaterialsHigh(attachedMaterialsHigh);
            List<Material> attachedMaterialsStandard = new ArrayList<Material>();
            for (Material materialsStandardMaterialToAttach : ribbon.getMaterialsStandard()) {
                materialsStandardMaterialToAttach = em.getReference(materialsStandardMaterialToAttach.getClass(), materialsStandardMaterialToAttach.getId());
                attachedMaterialsStandard.add(materialsStandardMaterialToAttach);
            }
            ribbon.setMaterialsStandard(attachedMaterialsStandard);
            em.persist(ribbon);
            for (Printer printersPrinter : ribbon.getPrinters()) {
                printersPrinter.getRibbons().add(ribbon);
                printersPrinter = em.merge(printersPrinter);
            }
            for (Material materialsHighMaterial : ribbon.getMaterialsHigh()) {
                materialsHighMaterial.getHighRibbons().add(ribbon);
                materialsHighMaterial = em.merge(materialsHighMaterial);
            }
            for (Material materialsStandardMaterial : ribbon.getMaterialsStandard()) {
                materialsStandardMaterial.getStandardRibbons().add(ribbon);
                materialsStandardMaterial = em.merge(materialsStandardMaterial);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRibbon(ribbon.getId()) != null) {
                throw new PreexistingEntityException("Ribbon " + ribbon + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ribbon ribbon) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ribbon persistentRibbon = em.find(Ribbon.class, ribbon.getId());
            List<Printer> printersOld = persistentRibbon.getPrinters();
            List<Printer> printersNew = ribbon.getPrinters();
            List<Material> materialsHighOld = persistentRibbon.getMaterialsHigh();
            List<Material> materialsHighNew = ribbon.getMaterialsHigh();
            List<Material> materialsStandardOld = persistentRibbon.getMaterialsStandard();
            List<Material> materialsStandardNew = ribbon.getMaterialsStandard();
            List<Printer> attachedPrintersNew = new ArrayList<Printer>();
            for (Printer printersNewPrinterToAttach : printersNew) {
                printersNewPrinterToAttach = em.getReference(printersNewPrinterToAttach.getClass(), printersNewPrinterToAttach.getId());
                attachedPrintersNew.add(printersNewPrinterToAttach);
            }
            printersNew = attachedPrintersNew;
            ribbon.setPrinters(printersNew);
            List<Material> attachedMaterialsHighNew = new ArrayList<Material>();
            for (Material materialsHighNewMaterialToAttach : materialsHighNew) {
                materialsHighNewMaterialToAttach = em.getReference(materialsHighNewMaterialToAttach.getClass(), materialsHighNewMaterialToAttach.getId());
                attachedMaterialsHighNew.add(materialsHighNewMaterialToAttach);
            }
            materialsHighNew = attachedMaterialsHighNew;
            ribbon.setMaterialsHigh(materialsHighNew);
            List<Material> attachedMaterialsStandardNew = new ArrayList<Material>();
            for (Material materialsStandardNewMaterialToAttach : materialsStandardNew) {
                materialsStandardNewMaterialToAttach = em.getReference(materialsStandardNewMaterialToAttach.getClass(), materialsStandardNewMaterialToAttach.getId());
                attachedMaterialsStandardNew.add(materialsStandardNewMaterialToAttach);
            }
            materialsStandardNew = attachedMaterialsStandardNew;
            ribbon.setMaterialsStandard(materialsStandardNew);
            ribbon = em.merge(ribbon);
            for (Printer printersOldPrinter : printersOld) {
                if (!printersNew.contains(printersOldPrinter)) {
                    printersOldPrinter.getRibbons().remove(ribbon);
                    printersOldPrinter = em.merge(printersOldPrinter);
                }
            }
            for (Printer printersNewPrinter : printersNew) {
                if (!printersOld.contains(printersNewPrinter)) {
                    printersNewPrinter.getRibbons().add(ribbon);
                    printersNewPrinter = em.merge(printersNewPrinter);
                }
            }
            for (Material materialsHighOldMaterial : materialsHighOld) {
                if (!materialsHighNew.contains(materialsHighOldMaterial)) {
                    materialsHighOldMaterial.getHighRibbons().remove(ribbon);
                    materialsHighOldMaterial = em.merge(materialsHighOldMaterial);
                }
            }
            for (Material materialsHighNewMaterial : materialsHighNew) {
                if (!materialsHighOld.contains(materialsHighNewMaterial)) {
                    materialsHighNewMaterial.getHighRibbons().add(ribbon);
                    materialsHighNewMaterial = em.merge(materialsHighNewMaterial);
                }
            }
            for (Material materialsStandardOldMaterial : materialsStandardOld) {
                if (!materialsStandardNew.contains(materialsStandardOldMaterial)) {
                    materialsStandardOldMaterial.getStandardRibbons().remove(ribbon);
                    materialsStandardOldMaterial = em.merge(materialsStandardOldMaterial);
                }
            }
            for (Material materialsStandardNewMaterial : materialsStandardNew) {
                if (!materialsStandardOld.contains(materialsStandardNewMaterial)) {
                    materialsStandardNewMaterial.getStandardRibbons().add(ribbon);
                    materialsStandardNewMaterial = em.merge(materialsStandardNewMaterial);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ribbon.getId();
                if (findRibbon(id) == null) {
                    throw new NonexistentEntityException("The ribbon with id " + id + " no longer exists.");
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
            Ribbon ribbon;
            try {
                ribbon = em.getReference(Ribbon.class, id);
                ribbon.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ribbon with id " + id + " no longer exists.", enfe);
            }
            List<Printer> printers = ribbon.getPrinters();
            for (Printer printersPrinter : printers) {
                printersPrinter.getRibbons().remove(ribbon);
                printersPrinter = em.merge(printersPrinter);
            }
            List<Material> materialsHigh = ribbon.getMaterialsHigh();
            for (Material materialsHighMaterial : materialsHigh) {
                materialsHighMaterial.getHighRibbons().remove(ribbon);
                materialsHighMaterial = em.merge(materialsHighMaterial);
            }
            List<Material> materialsStandard = ribbon.getMaterialsStandard();
            for (Material materialsStandardMaterial : materialsStandard) {
                materialsStandardMaterial.getStandardRibbons().remove(ribbon);
                materialsStandardMaterial = em.merge(materialsStandardMaterial);
            }
            em.remove(ribbon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ribbon> findRibbonEntities() {
        return findRibbonEntities(true, -1, -1);
    }

    public List<Ribbon> findRibbonEntities(int maxResults, int firstResult) {
        return findRibbonEntities(false, maxResults, firstResult);
    }

    private List<Ribbon> findRibbonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Ribbon as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Ribbon findRibbon(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ribbon.class, id);
        } finally {
            em.close();
        }
    }

    public int getRibbonCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Ribbon as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
