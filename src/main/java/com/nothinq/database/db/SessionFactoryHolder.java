package com.nothinq.database.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SessionFactoryHolder {
    private static SessionFactoryHolder INSTANCE;

    private EntityManagerFactory entityManagerFactory;

    public static SessionFactoryHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionFactoryHolder();
        }

        return INSTANCE;
    }

    public SessionFactoryHolder() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory( "default" );
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
