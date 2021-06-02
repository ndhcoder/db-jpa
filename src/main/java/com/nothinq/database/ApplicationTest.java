package com.nothinq.database;

public class ApplicationTest {

    public static void main(String[] args) {
        PersistenceUtils persistenceUtils = new PersistenceUtils();
        persistenceUtils.insertTest();
        persistenceUtils.queryTest();
    }
}
