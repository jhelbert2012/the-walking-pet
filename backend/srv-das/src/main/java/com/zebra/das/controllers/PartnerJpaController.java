
package com.zebra.das.controllers;

import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;
import com.zebra.das.model.api.Partner;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

public class PartnerJpaController implements Serializable {

    public PartnerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partner partner) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(partner);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartner(partner.getId()) != null) {
                throw new PreexistingEntityException("Partner " + partner + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partner partner) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            partner = em.merge(partner);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = partner.getId();
                if (findPartner(id) == null) {
                    throw new NonexistentEntityException("The partner with id " + id + " no longer exists.");
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
            Partner partner;
            try {
                partner = em.getReference(Partner.class, id);
                partner.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partner with id " + id + " no longer exists.", enfe);
            }
            em.remove(partner);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partner> findPartnerEntities() {
        return findPartnerEntities(true, -1, -1);
    }

    public List<Partner> findPartnerEntities(int maxResults, int firstResult) {
        return findPartnerEntities(false, maxResults, firstResult);
    }

    private List<Partner> findPartnerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Partner as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Partner findPartner(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partner.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartnerCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Partner as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
