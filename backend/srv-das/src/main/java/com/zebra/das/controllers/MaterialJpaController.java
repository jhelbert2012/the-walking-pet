
package com.zebra.das.controllers;

import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;
import com.zebra.das.model.api.Material;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.zebra.das.model.api.Printer;
import java.util.HashSet;
import java.util.Set;
import com.zebra.das.model.api.Ribbon;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MaterialJpaController implements Serializable {

    public MaterialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Material material) throws PreexistingEntityException, Exception {
        if (material.getPrinters() == null) {
            material.setPrinters(new HashSet<Printer>());
        }
        if (material.getHighRibbons() == null) {
            material.setHighRibbons(new HashSet<Ribbon>());
        }
        if (material.getStandardRibbons() == null) {
            material.setStandardRibbons(new HashSet<Ribbon>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Printer> attachedPrinters = new HashSet<Printer>();
            for (Printer printersPrinterToAttach : material.getPrinters()) {
                printersPrinterToAttach = em.getReference(printersPrinterToAttach.getClass(), printersPrinterToAttach.getId());
                attachedPrinters.add(printersPrinterToAttach);
            }
            material.setPrinters(attachedPrinters);
            Set<Ribbon> attachedHighRibbons = new HashSet<Ribbon>();
            for (Ribbon highRibbonsRibbonToAttach : material.getHighRibbons()) {
                highRibbonsRibbonToAttach = em.getReference(highRibbonsRibbonToAttach.getClass(), highRibbonsRibbonToAttach.getId());
                attachedHighRibbons.add(highRibbonsRibbonToAttach);
            }
            material.setHighRibbons(attachedHighRibbons);
            Set<Ribbon> attachedStandardRibbons = new HashSet<Ribbon>();
            for (Ribbon standardRibbonsRibbonToAttach : material.getStandardRibbons()) {
                standardRibbonsRibbonToAttach = em.getReference(standardRibbonsRibbonToAttach.getClass(), standardRibbonsRibbonToAttach.getId());
                attachedStandardRibbons.add(standardRibbonsRibbonToAttach);
            }
            material.setStandardRibbons(attachedStandardRibbons);
            em.persist(material);
            for (Printer printersPrinter : material.getPrinters()) {
                printersPrinter.getMaterials().add(material);
                printersPrinter = em.merge(printersPrinter);
            }
            for (Ribbon highRibbonsRibbon : material.getHighRibbons()) {
                highRibbonsRibbon.getMaterialsHigh().add(material);
                highRibbonsRibbon = em.merge(highRibbonsRibbon);
            }
            for (Ribbon standardRibbonsRibbon : material.getStandardRibbons()) {
                standardRibbonsRibbon.getMaterialsHigh().add(material);
                standardRibbonsRibbon = em.merge(standardRibbonsRibbon);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMaterial(material.getId()) != null) {
                throw new PreexistingEntityException("Material " + material + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Material material) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Material persistentMaterial = em.find(Material.class, material.getId());
            Set<Printer> printersOld = persistentMaterial.getPrinters();
            Set<Printer> printersNew = material.getPrinters();
            Set<Ribbon> highRibbonsOld = persistentMaterial.getHighRibbons();
            Set<Ribbon> highRibbonsNew = material.getHighRibbons();
            Set<Ribbon> standardRibbonsOld = persistentMaterial.getStandardRibbons();
            Set<Ribbon> standardRibbonsNew = material.getStandardRibbons();
            Set<Printer> attachedPrintersNew = new HashSet<Printer>();
            for (Printer printersNewPrinterToAttach : printersNew) {
                printersNewPrinterToAttach = em.getReference(printersNewPrinterToAttach.getClass(), printersNewPrinterToAttach.getId());
                attachedPrintersNew.add(printersNewPrinterToAttach);
            }
            printersNew = attachedPrintersNew;
            material.setPrinters(printersNew);
            Set<Ribbon> attachedHighRibbonsNew = new HashSet<Ribbon>();
            for (Ribbon highRibbonsNewRibbonToAttach : highRibbonsNew) {
                highRibbonsNewRibbonToAttach = em.getReference(highRibbonsNewRibbonToAttach.getClass(), highRibbonsNewRibbonToAttach.getId());
                attachedHighRibbonsNew.add(highRibbonsNewRibbonToAttach);
            }
            highRibbonsNew = attachedHighRibbonsNew;
            material.setHighRibbons(highRibbonsNew);
            Set<Ribbon> attachedStandardRibbonsNew = new HashSet<Ribbon>();
            for (Ribbon standardRibbonsNewRibbonToAttach : standardRibbonsNew) {
                standardRibbonsNewRibbonToAttach = em.getReference(standardRibbonsNewRibbonToAttach.getClass(), standardRibbonsNewRibbonToAttach.getId());
                attachedStandardRibbonsNew.add(standardRibbonsNewRibbonToAttach);
            }
            standardRibbonsNew = attachedStandardRibbonsNew;
            material.setStandardRibbons(standardRibbonsNew);
            material = em.merge(material);
            for (Printer printersOldPrinter : printersOld) {
                if (!printersNew.contains(printersOldPrinter)) {
                    printersOldPrinter.getMaterials().remove(material);
                    printersOldPrinter = em.merge(printersOldPrinter);
                }
            }
            for (Printer printersNewPrinter : printersNew) {
                if (!printersOld.contains(printersNewPrinter)) {
                    printersNewPrinter.getMaterials().add(material);
                    printersNewPrinter = em.merge(printersNewPrinter);
                }
            }
            for (Ribbon highRibbonsOldRibbon : highRibbonsOld) {
                if (!highRibbonsNew.contains(highRibbonsOldRibbon)) {
                    highRibbonsOldRibbon.getMaterialsHigh().remove(material);
                    highRibbonsOldRibbon = em.merge(highRibbonsOldRibbon);
                }
            }
            for (Ribbon highRibbonsNewRibbon : highRibbonsNew) {
                if (!highRibbonsOld.contains(highRibbonsNewRibbon)) {
                    highRibbonsNewRibbon.getMaterialsHigh().add(material);
                    highRibbonsNewRibbon = em.merge(highRibbonsNewRibbon);
                }
            }
            for (Ribbon standardRibbonsOldRibbon : standardRibbonsOld) {
                if (!standardRibbonsNew.contains(standardRibbonsOldRibbon)) {
                    standardRibbonsOldRibbon.getMaterialsHigh().remove(material);
                    standardRibbonsOldRibbon = em.merge(standardRibbonsOldRibbon);
                }
            }
            for (Ribbon standardRibbonsNewRibbon : standardRibbonsNew) {
                if (!standardRibbonsOld.contains(standardRibbonsNewRibbon)) {
                    standardRibbonsNewRibbon.getMaterialsHigh().add(material);
                    standardRibbonsNewRibbon = em.merge(standardRibbonsNewRibbon);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = material.getId();
                if (findMaterial(id) == null) {
                    throw new NonexistentEntityException("The material with id " + id + " no longer exists.");
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
            Material material;
            try {
                material = em.getReference(Material.class, id);
                material.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The material with id " + id + " no longer exists.", enfe);
            }
            Set<Printer> printers = material.getPrinters();
            for (Printer printersPrinter : printers) {
                printersPrinter.getMaterials().remove(material);
                printersPrinter = em.merge(printersPrinter);
            }
            Set<Ribbon> highRibbons = material.getHighRibbons();
            for (Ribbon highRibbonsRibbon : highRibbons) {
                highRibbonsRibbon.getMaterialsHigh().remove(material);
                highRibbonsRibbon = em.merge(highRibbonsRibbon);
            }
            Set<Ribbon> standardRibbons = material.getStandardRibbons();
            for (Ribbon standardRibbonsRibbon : standardRibbons) {
                standardRibbonsRibbon.getMaterialsHigh().remove(material);
                standardRibbonsRibbon = em.merge(standardRibbonsRibbon);
            }
            em.remove(material);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Material> findMaterialEntities() {
        return findMaterialEntities(true, -1, -1);
    }

    public List<Material> findMaterialEntities(int maxResults, int firstResult) {
        return findMaterialEntities(false, maxResults, firstResult);
    }

    private List<Material> findMaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Material as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Material findMaterial(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Material.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaterialCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Material as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
