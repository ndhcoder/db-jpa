package com.nothinq.database.repository;

import javax.persistence.EntityManager;

public interface JpaActionReturn<T> {

    T doInTransaction(EntityManager em);
}
