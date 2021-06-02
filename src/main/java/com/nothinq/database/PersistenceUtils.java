package com.nothinq.database;

import com.nothinq.database.model.User;
import com.nothinq.database.repository.impl.UserRepository;
import com.nothinq.database.repository.object.Page;
import com.nothinq.database.repository.object.PageRequest;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.util.List;

public class PersistenceUtils {

    EntityManagerFactory sessionFactory;

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    public PersistenceUtils() {
        this.sessionFactory = Persistence.createEntityManagerFactory( "default" );
    }

    private void close() {
        em.close();
    }

    public void insertTest() {
//        em = sessionFactory.createEntityManager();
//        em.getTransaction().begin();
//
//        for (int i = 0; i < 20; i++) {
//            User user = new User();
//            user.setName("User " + i);
//            em.persist(user);
//        }
//
//        em.getTransaction().commit();
//        close();
    }

    public void queryTest() {
//        em = sessionFactory.createEntityManager();
//       // em.getTransaction().begin();
//        List<User> users = em.createQuery("select u from User u", User.class).getResultList();
//        System.out.println("user result size: " + users.size());
////        User user = users.get(0);
////        user.setName("update");
////        em.flush();
////        em.getTransaction().commit();
//        close();
        EntityManager em1 = sessionFactory.createEntityManager();
        em1.getTransaction().begin();
        try {
            UserRepository userRepository = new UserRepository();
            User user = userRepository.findById(2);
            Page<User> users = userRepository.findAll(PageRequest.of(1, 5));

            user.setName("update temp 34555");
            userRepository.update(user);
            System.out.println(user);

//            if (user != null) {
//                throw new RuntimeException("error");
//            }

            em1.getTransaction().commit();
        } catch (Exception e) {
            em1.getTransaction().rollback();
        }
    }
}
