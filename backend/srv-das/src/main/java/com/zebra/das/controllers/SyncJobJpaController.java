package com.zebra.das.controllers;

import com.zebra.constant.model.Dmar;
import com.zebra.das.controllers.exceptions.IllegalOrphanException;
import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.zebra.das.model.api.SyncData;
import com.zebra.das.model.api.SyncJob;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SyncJobJpaController implements Serializable {

    public SyncJobJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SyncJob syncJob) throws PreexistingEntityException, Exception {
        if (syncJob.getSyncDataList() == null) {
            syncJob.setSyncDataList(new ArrayList<SyncData>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SyncData> attachedSyncDataList = new ArrayList<SyncData>();
            for (SyncData syncDataListSyncDataToAttach : syncJob.getSyncDataList()) {
                syncDataListSyncDataToAttach = em.getReference(syncDataListSyncDataToAttach.getClass(), syncDataListSyncDataToAttach.getSyncDataPK());
                attachedSyncDataList.add(syncDataListSyncDataToAttach);
            }
            syncJob.setSyncDataList(attachedSyncDataList);
            em.persist(syncJob);
            for (SyncData syncDataListSyncData : syncJob.getSyncDataList()) {
                SyncJob oldSyncJobOfSyncDataListSyncData = syncDataListSyncData.getSyncJob();
                syncDataListSyncData.setSyncJob(syncJob);
                syncDataListSyncData = em.merge(syncDataListSyncData);
                if (oldSyncJobOfSyncDataListSyncData != null) {
                    oldSyncJobOfSyncDataListSyncData.getSyncDataList().remove(syncDataListSyncData);
                    oldSyncJobOfSyncDataListSyncData = em.merge(oldSyncJobOfSyncDataListSyncData);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSyncJob(syncJob.getId()) != null) {
                throw new PreexistingEntityException("SyncJob " + syncJob + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SyncJob syncJob) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SyncJob persistentSyncJob = em.find(SyncJob.class, syncJob.getId());
            List<SyncData> syncDataListOld = persistentSyncJob.getSyncDataList();
            List<SyncData> syncDataListNew = syncJob.getSyncDataList();
            List<String> illegalOrphanMessages = null;
            for (SyncData syncDataListOldSyncData : syncDataListOld) {
                if (!syncDataListNew.contains(syncDataListOldSyncData)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SyncData " + syncDataListOldSyncData + " since its syncJob field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SyncData> attachedSyncDataListNew = new ArrayList<SyncData>();
            for (SyncData syncDataListNewSyncDataToAttach : syncDataListNew) {
                syncDataListNewSyncDataToAttach = em.getReference(syncDataListNewSyncDataToAttach.getClass(), syncDataListNewSyncDataToAttach.getSyncDataPK());
                attachedSyncDataListNew.add(syncDataListNewSyncDataToAttach);
            }
            syncDataListNew = attachedSyncDataListNew;
            syncJob.setSyncDataList(syncDataListNew);
            syncJob = em.merge(syncJob);
            for (SyncData syncDataListNewSyncData : syncDataListNew) {
                if (!syncDataListOld.contains(syncDataListNewSyncData)) {
                    SyncJob oldSyncJobOfSyncDataListNewSyncData = syncDataListNewSyncData.getSyncJob();
                    syncDataListNewSyncData.setSyncJob(syncJob);
                    syncDataListNewSyncData = em.merge(syncDataListNewSyncData);
                    if (oldSyncJobOfSyncDataListNewSyncData != null && !oldSyncJobOfSyncDataListNewSyncData.equals(syncJob)) {
                        oldSyncJobOfSyncDataListNewSyncData.getSyncDataList().remove(syncDataListNewSyncData);
                        oldSyncJobOfSyncDataListNewSyncData = em.merge(oldSyncJobOfSyncDataListNewSyncData);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = syncJob.getId();
                if (findSyncJob(id) == null) {
                    throw new NonexistentEntityException("The syncJob with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SyncJob syncJob;
            try {
                syncJob = em.getReference(SyncJob.class, id);
                syncJob.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The syncJob with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SyncData> syncDataListOrphanCheck = syncJob.getSyncDataList();
            for (SyncData syncDataListOrphanCheckSyncData : syncDataListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SyncJob (" + syncJob + ") cannot be destroyed since the SyncData " + syncDataListOrphanCheckSyncData + " in its syncDataList field has a non-nullable syncJob field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(syncJob);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SyncJob> findSyncJobEntities() {
        return findSyncJobEntities(true, -1, -1);
    }

    public List<SyncJob> findSyncJobEntities(int maxResults, int firstResult) {
        return findSyncJobEntities(false, maxResults, firstResult);
    }

    private List<SyncJob> findSyncJobEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from SyncJob as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SyncJob findSyncJob(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SyncJob.class, id);
        } finally {
            em.close();
        }
    }

    public SyncJob findSyncJobByDmar(Dmar dmar) {
        EntityManager em = getEntityManager();
        try {
            return (SyncJob) em.createQuery("FROM SyncJob j WHERE j.dmar = :dmar").setParameter("dmar", dmar).getSingleResult();
        } finally {
            em.close();
        }
    }

    public int getSyncJobCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from SyncJob as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
