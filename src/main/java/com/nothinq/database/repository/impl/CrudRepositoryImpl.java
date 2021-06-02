package com.nothinq.database.repository.impl;

import com.google.common.reflect.TypeToken;
import com.nothinq.database.repository.CrudRepository;
import com.nothinq.database.repository.JpaAction;
import com.nothinq.database.repository.JpaActionReturn;
import com.nothinq.database.db.SessionFactoryHolder;
import com.nothinq.database.repository.object.Page;
import com.nothinq.database.repository.object.PageImpl;
import com.nothinq.database.repository.object.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CrudRepositoryImpl<T, K> implements CrudRepository<T, K> {
    TypeToken<T> type = new TypeToken<T>(getClass()) {};
    EntityManagerFactory sessionFactory = SessionFactoryHolder.getInstance().getEntityManagerFactory();

    @Override
    public T save(T entity) {
        doInJpa(sessionFactory, em -> {
            em.persist(entity);
        });

        return entity;
    }

    @Override
    public T update(T entity) {
        return doInJpa(sessionFactory, em -> {
            return em.merge(entity);
        });
    }

    @Override
    public void update(List<T> entities) {
        doInJpa(sessionFactory, em -> {
            for (int i = 0; i < entities.size(); i++) {
                if (i % 20 == 0) {
                    em.flush();
                    em.clear();
                }

                entities.set(i, em.merge(entities.get(i)));
            }
        });
    }

    public void save(List<T> entities) {
        doInJpa(sessionFactory, em -> {
            for (int i = 0; i < entities.size(); i++) {
                if (i % 20 == 0) {
                    em.flush();
                    em.clear();
                }

                em.persist(entities.get(i));
            }
        });
    }

    @Override
    public T findById(K id) {
        EntityManager em = sessionFactory.createEntityManager();
        T result = (T) em.createQuery("SELECT e FROM " + type.getType().getTypeName() + " e WHERE id = :id", type.getRawType())
                .setParameter("id", id)
                .getSingleResult();

        return result;
    }

    @Override
    public List<T> findAll() {
        EntityManager em = sessionFactory.createEntityManager();
        List<T> results = (List<T>) em.createQuery("SELECT e FROM " + type.getType().getTypeName() + " e", type.getRawType())
                .getResultList();

        em.clear();
        em.close();
        return results;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        EntityManager em = sessionFactory.createEntityManager();
        String query = "SELECT e FROM " + type.getType().getTypeName() + " e";
        String countQuery = "SELECT COUNT(e.id) FROM " + type.getType().getTypeName() + " e";

        List<T> results = (List<T>) em.createQuery(query, type.getRawType())
                .setFirstResult(pageable.getPage() * pageable.getSize())
                .setMaxResults(pageable.getSize())
                .getResultList();

        Long totalElement = em.createQuery(countQuery, Long.class).getSingleResult();
        em.clear();
        em.close();

        return new PageImpl<>(results, totalElement, pageable);
    }

    @Override
    public void deleteById(K id) {
        doInJpa(sessionFactory, em -> {
                em.createQuery("DELETE e FROM " + type.getType().getTypeName() + " e WHERE id = :id").setParameter("id", id).executeUpdate();
        });
    }

    @Override
    public void delete(T entity) {
        doInJpa(sessionFactory, em -> {
            em.remove(entity);
        });
    }

    protected void doInJpa(EntityManagerFactory entityManagerFactory, JpaAction action) {
        EntityTransaction txn = null;
        EntityManager em = null;

        try {
            em = entityManagerFactory.createEntityManager();
            txn = em.getTransaction();
            txn.begin();
            action.doInTransaction(em);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (txn != null && txn.isActive()) txn.rollback();
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    protected T doInJpa(EntityManagerFactory entityManagerFactory, JpaActionReturn<T> action) {
        EntityTransaction txn = null;
        EntityManager em = null;

        try {
            em = entityManagerFactory.createEntityManager();
            txn = em.getTransaction();
            txn.begin();
            T result = action.doInTransaction(em);
            em.getTransaction().commit();
            return result;
        } catch (Exception e) {
            if (txn != null && txn.isActive()) txn.rollback();
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
