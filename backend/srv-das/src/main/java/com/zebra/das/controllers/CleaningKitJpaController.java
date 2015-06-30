
package com.zebra.das.controllers;

import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;
import com.zebra.das.model.api.CleaningKit;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.zebra.das.model.api.Printer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CleaningKitJpaController implements Serializable {

    public CleaningKitJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CleaningKit cleaningKit) throws PreexistingEntityException, Exception {
        if (cleaningKit.getPrinters() == null) {
            cleaningKit.setPrinters(new ArrayList<Printer>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Printer> attachedPrinters = new ArrayList<Printer>();
            for (Printer printersPrinterToAttach : cleaningKit.getPrinters()) {
                printersPrinterToAttach = em.getReference(printersPrinterToAttach.getClass(), printersPrinterToAttach.getId());
                attachedPrinters.add(printersPrinterToAttach);
            }
            cleaningKit.setPrinters(attachedPrinters);
            em.persist(cleaningKit);
            for (Printer printersPrinter : cleaningKit.getPrinters()) {
                printersPrinter.getCleaningKits().add(cleaningKit);
                printersPrinter = em.merge(printersPrinter);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCleaningKit(cleaningKit.getId()) != null) {
                throw new PreexistingEntityException("CleaningKit " + cleaningKit + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CleaningKit cleaningKit) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CleaningKit persistentCleaningKit = em.find(CleaningKit.class, cleaningKit.getId());
            List<Printer> printersOld = persistentCleaningKit.getPrinters();
            List<Printer> printersNew = cleaningKit.getPrinters();
            List<Printer> attachedPrintersNew = new ArrayList<Printer>();
            for (Printer printersNewPrinterToAttach : printersNew) {
                printersNewPrinterToAttach = em.getReference(printersNewPrinterToAttach.getClass(), printersNewPrinterToAttach.getId());
                attachedPrintersNew.add(printersNewPrinterToAttach);
            }
            printersNew = attachedPrintersNew;
            cleaningKit.setPrinters(printersNew);
            cleaningKit = em.merge(cleaningKit);
            for (Printer printersOldPrinter : printersOld) {
                if (!printersNew.contains(printersOldPrinter)) {
                    printersOldPrinter.getCleaningKits().remove(cleaningKit);
                    printersOldPrinter = em.merge(printersOldPrinter);
                }
            }
            for (Printer printersNewPrinter : printersNew) {
                if (!printersOld.contains(printersNewPrinter)) {
                    printersNewPrinter.getCleaningKits().add(cleaningKit);
                    printersNewPrinter = em.merge(printersNewPrinter);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cleaningKit.getId();
                if (findCleaningKit(id) == null) {
                    throw new NonexistentEntityException("The cleaningKit with id " + id + " no longer exists.");
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
            CleaningKit cleaningKit;
            try {
                cleaningKit = em.getReference(CleaningKit.class, id);
                cleaningKit.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cleaningKit with id " + id + " no longer exists.", enfe);
            }
            List<Printer> printers = cleaningKit.getPrinters();
            for (Printer printersPrinter : printers) {
                printersPrinter.getCleaningKits().remove(cleaningKit);
                printersPrinter = em.merge(printersPrinter);
            }
            em.remove(cleaningKit);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CleaningKit> findCleaningKitEntities() {
        return findCleaningKitEntities(true, -1, -1);
    }

    public List<CleaningKit> findCleaningKitEntities(int maxResults, int firstResult) {
        return findCleaningKitEntities(false, maxResults, firstResult);
    }

    private List<CleaningKit> findCleaningKitEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CleaningKit as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CleaningKit findCleaningKit(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CleaningKit.class, id);
        } finally {
            em.close();
        }
    }

    public int getCleaningKitCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from CleaningKit as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
