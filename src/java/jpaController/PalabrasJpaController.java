/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaController;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpaController.exceptions.NonexistentEntityException;
import jpaController.exceptions.RollbackFailureException;
import modelo.Palabras;

/**
 *
 * @author cosma_000
 */
public class PalabrasJpaController implements Serializable {

    public PalabrasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Palabras palabras) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(palabras);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Palabras palabras) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            palabras = em.merge(palabras);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = palabras.getIdpal();
                if (findPalabras(id) == null) {
                    throw new NonexistentEntityException("The palabras with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Palabras palabras;
            try {
                palabras = em.getReference(Palabras.class, id);
                palabras.getIdpal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palabras with id " + id + " no longer exists.", enfe);
            }
            em.remove(palabras);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Palabras> findPalabrasEntities() {
        return findPalabrasEntities(true, -1, -1);
    }

    public List<Palabras> findPalabrasEntities(int maxResults, int firstResult) {
        return findPalabrasEntities(false, maxResults, firstResult);
    }

    private List<Palabras> findPalabrasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Palabras.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Palabras findPalabras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palabras.class, id);
        } finally {
            em.close();
        }
    }
     public Palabras findPalabraNombre(String nombre) {
      EntityManager em = getEntityManager();
       Query q ;
        try {
            
           
            q =  em.createQuery("SELECT p FROM Palabras p WHERE p.palabra = :palabra ");
            q.setParameter("palabra", nombre);          
                 q.setMaxResults(1);
              if(q.getResultList().size()>0){
               return (Palabras)q.getSingleResult();
              }
            return null;
          
        } finally {
           
            em.close();
        }
    }
public List<Palabras> findPalabraTipo(String tipo) {
      EntityManager em = getEntityManager();
       Query q ;
        try {
            
           
            q =  em.createQuery("SELECT p FROM Palabras p WHERE (p.tipopal = :tipo)   ");
            
            q.setParameter("tipo", tipo);          
                
             
               return q.getResultList();
           
          
        } finally {
           
            em.close();
        }
    }
    public int getPalabrasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Palabras> rt = cq.from(Palabras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
