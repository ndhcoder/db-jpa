package com.nothinq.database.repository;

import javax.persistence.EntityManager;

public interface JpaAction {
    void doInTransaction(EntityManager em);
}
