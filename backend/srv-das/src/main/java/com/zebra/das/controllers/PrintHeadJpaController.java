/**
 * 
 */
package com.zebra.das.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;
import com.zebra.das.model.api.PrintHead;
import com.zebra.das.model.api.Printer;
import com.zebra.das.model.api.Ribbon;

/**
 * @author ignaciogonzalez
 *
 */
public class PrintHeadJpaController implements Serializable {
	
	private EntityManagerFactory emf = null;
	
	public PrintHeadJpaController(EntityManagerFactory emf){
		this.emf = emf;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public void create(PrintHead printHead) throws PreexistingEntityException, Exception{
		EntityManager em = null;
		if(printHead.getPrinters() == null) {
		   printHead.setPrinters(new ArrayList<Printer>());
		}
		try{
			em = getEntityManager();
			em.getTransaction().begin();
			List<Printer> attachedPrinters = new ArrayList<Printer>();
			for (Printer printersPrinterToAttach : printHead.getPrinters()) {
				printersPrinterToAttach = em.getReference(printersPrinterToAttach.getClass(), printersPrinterToAttach.getId());
                attachedPrinters.add(printersPrinterToAttach);
			}
			printHead.setPrinters(attachedPrinters);
			em.persist(printHead);
			for (Printer printersPrinter : printHead.getPrinters()) {
                printersPrinter.getPrintHeads().add(printHead);
                printersPrinter = em.merge(printersPrinter);
            }
			em.getTransaction().commit();
		} catch (Exception ex) {
            if (findPrintHead(printHead.getId()) != null) {
                throw new PreexistingEntityException("PrintHead " + printHead + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
		
	}
	
	public void edit(PrintHead printHead) throws PreexistingEntityException, Exception{
		EntityManager em = null;
		try{
		   em = getEntityManager();
           em.getTransaction().begin();
           PrintHead persistentPrintHead = em.find(PrintHead.class, printHead.getId());
           List<Printer> printersOld = persistentPrintHead.getPrinters();
           List<Printer> printersNew = printHead.getPrinters();
           List<Printer> attachedPrintersNew = new ArrayList<Printer>();
           for (Printer printersNewPrinterToAttach : printersNew) {
               printersNewPrinterToAttach = em.getReference(printersNewPrinterToAttach.getClass(), printersNewPrinterToAttach.getId());
               attachedPrintersNew.add(printersNewPrinterToAttach);
           }
           printersNew = attachedPrintersNew;
           printHead.setPrinters(printersNew);
           printHead = em.merge(printHead);
           for (Printer printersOldPrinter : printersOld) {
               if (!printersNew.contains(printersOldPrinter)) {
                   printersOldPrinter.getPrintHeads().remove(printHead);
                   printersOldPrinter = em.merge(printersOldPrinter);
               }
           }
           for (Printer printersNewPrinter : printersNew) {
               if (!printersOld.contains(printersNewPrinter)) {
                   printersNewPrinter.getPrintHeads().add(printHead);
                   printersNewPrinter = em.merge(printersNewPrinter);
               }
           }
           
          em.getTransaction().commit();
		} catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = printHead.getId();
                if (findPrintHead(id) == null) {
                    throw new NonexistentEntityException("The printHead with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
	}
	
	public List<PrintHead> findPrintHeadEntities() {
	    return findPrintHeadEntities(true, -1, -1);
	}

	public List<PrintHead> findPrintHeadEntities(int maxResults, int firstResult) {
	    return findPrintHeadEntities(false, maxResults, firstResult);
	}

	private List<PrintHead> findPrintHeadEntities(boolean all, int maxResults, int firstResult) {
	    EntityManager em = getEntityManager();
	    try {
	        Query q = em.createQuery("select object(o) from PrintHead as o");
	        if (!all) {
	            q.setMaxResults(maxResults);
	            q.setFirstResult(firstResult);
	        }
	        return q.getResultList();
	    } finally {
	        em.close();
	    }
    }
	
    public PrintHead findPrintHead(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrintHead.class, id);
        } finally {
            em.close();
        }
    }
    
    public int getPrintHeadCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PrintHead as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
	
}
