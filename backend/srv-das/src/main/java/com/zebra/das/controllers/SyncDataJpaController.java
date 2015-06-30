
package com.zebra.das.controllers;

import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;
import com.zebra.das.model.api.SyncData;
import com.zebra.das.model.api.SyncDataPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.zebra.das.model.api.SyncJob;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SyncDataJpaController implements Serializable {

    public SyncDataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SyncData syncData) throws PreexistingEntityException, Exception {
        if (syncData.getSyncDataPK() == null) {
            syncData.setSyncDataPK(new SyncDataPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SyncJob syncJob = syncData.getSyncJob();
            if (syncJob != null) {
                syncJob = em.getReference(syncJob.getClass(), syncJob.getId());
                syncData.setSyncJob(syncJob);
            }
            em.persist(syncData);
            if (syncJob != null) {
                syncJob.getSyncDataList().add(syncData);
                syncJob = em.merge(syncJob);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSyncData(syncData.getSyncDataPK()) != null) {
                throw new PreexistingEntityException("SyncData " + syncData + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SyncData syncData) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SyncData persistentSyncData = em.find(SyncData.class, syncData.getSyncDataPK());
            SyncJob syncJobOld = persistentSyncData.getSyncJob();
            SyncJob syncJobNew = syncData.getSyncJob();
            if (syncJobNew != null) {
                syncJobNew = em.getReference(syncJobNew.getClass(), syncJobNew.getId());
                syncData.setSyncJob(syncJobNew);
            }
            syncData = em.merge(syncData);
            if (syncJobOld != null && !syncJobOld.equals(syncJobNew)) {
                syncJobOld.getSyncDataList().remove(syncData);
                syncJobOld = em.merge(syncJobOld);
            }
            if (syncJobNew != null && !syncJobNew.equals(syncJobOld)) {
                syncJobNew.getSyncDataList().add(syncData);
                syncJobNew = em.merge(syncJobNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SyncDataPK id = syncData.getSyncDataPK();
                if (findSyncData(id) == null) {
                    throw new NonexistentEntityException("The syncData with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SyncDataPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SyncData syncData;
            try {
                syncData = em.getReference(SyncData.class, id);
                syncData.getSyncDataPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The syncData with id " + id + " no longer exists.", enfe);
            }
            SyncJob syncJob = syncData.getSyncJob();
            if (syncJob != null) {
                syncJob.getSyncDataList().remove(syncData);
                syncJob = em.merge(syncJob);
            }
            em.remove(syncData);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SyncData> findSyncDataEntities() {
        return findSyncDataEntities(true, -1, -1);
    }

    public List<SyncData> findSyncDataEntities(int maxResults, int firstResult) {
        return findSyncDataEntities(false, maxResults, firstResult);
    }

    private List<SyncData> findSyncDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from SyncData as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SyncData findSyncData(SyncDataPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SyncData.class, id);
        } finally {
            em.close();
        }
    }

    public int getSyncDataCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from SyncData as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
