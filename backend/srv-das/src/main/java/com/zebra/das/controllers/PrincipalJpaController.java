
package com.zebra.das.controllers;

import com.zebra.das.controllers.exceptions.IllegalOrphanException;
import com.zebra.das.controllers.exceptions.NonexistentEntityException;
import com.zebra.das.controllers.exceptions.PreexistingEntityException;
import com.zebra.das.model.api.Principal;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.zebra.das.model.api.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PrincipalJpaController implements Serializable {

    public PrincipalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Principal principal) throws PreexistingEntityException, Exception {
        if (principal.getUsers() == null) {
            principal.setUsers(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<User> attachedUsers = new ArrayList<User>();
            for (User usersUserToAttach : principal.getUsers()) {
                usersUserToAttach = em.getReference(usersUserToAttach.getClass(), usersUserToAttach.getUsername());
                attachedUsers.add(usersUserToAttach);
            }
            principal.setUsers(attachedUsers);
            em.persist(principal);
            for (User usersUser : principal.getUsers()) {
                Principal oldPrincipalOfUsersUser = usersUser.getPrincipal();
                usersUser.setPrincipal(principal);
                usersUser = em.merge(usersUser);
                if (oldPrincipalOfUsersUser != null) {
                    oldPrincipalOfUsersUser.getUsers().remove(usersUser);
                    oldPrincipalOfUsersUser = em.merge(oldPrincipalOfUsersUser);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrincipal(principal.getName()) != null) {
                throw new PreexistingEntityException("Principal " + principal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Principal principal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Principal persistentPrincipal = em.find(Principal.class, principal.getName());
            List<User> usersOld = persistentPrincipal.getUsers();
            List<User> usersNew = principal.getUsers();
            List<String> illegalOrphanMessages = null;
            for (User usersOldUser : usersOld) {
                if (!usersNew.contains(usersOldUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain User " + usersOldUser + " since its principal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<User> attachedUsersNew = new ArrayList<User>();
            for (User usersNewUserToAttach : usersNew) {
                usersNewUserToAttach = em.getReference(usersNewUserToAttach.getClass(), usersNewUserToAttach.getUsername());
                attachedUsersNew.add(usersNewUserToAttach);
            }
            usersNew = attachedUsersNew;
            principal.setUsers(usersNew);
            principal = em.merge(principal);
            for (User usersNewUser : usersNew) {
                if (!usersOld.contains(usersNewUser)) {
                    Principal oldPrincipalOfUsersNewUser = usersNewUser.getPrincipal();
                    usersNewUser.setPrincipal(principal);
                    usersNewUser = em.merge(usersNewUser);
                    if (oldPrincipalOfUsersNewUser != null && !oldPrincipalOfUsersNewUser.equals(principal)) {
                        oldPrincipalOfUsersNewUser.getUsers().remove(usersNewUser);
                        oldPrincipalOfUsersNewUser = em.merge(oldPrincipalOfUsersNewUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = principal.getName();
                if (findPrincipal(id) == null) {
                    throw new NonexistentEntityException("The principal with id " + id + " no longer exists.");
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
            Principal principal;
            try {
                principal = em.getReference(Principal.class, id);
                principal.getName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The principal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<User> usersOrphanCheck = principal.getUsers();
            for (User usersOrphanCheckUser : usersOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Principal (" + principal + ") cannot be destroyed since the User " + usersOrphanCheckUser + " in its users field has a non-nullable principal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(principal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Principal> findPrincipalEntities() {
        return findPrincipalEntities(true, -1, -1);
    }

    public List<Principal> findPrincipalEntities(int maxResults, int firstResult) {
        return findPrincipalEntities(false, maxResults, firstResult);
    }

    private List<Principal> findPrincipalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Principal as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Principal findPrincipal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Principal.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrincipalCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Principal as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
