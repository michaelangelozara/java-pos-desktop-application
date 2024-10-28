package org.POS.backend.user;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAO {

    private SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(User user){
       try(Session session = sessionFactory.openSession()){
           session.beginTransaction();

           session.persist(user);

           session.getTransaction().commit();
       } catch (Exception e){
           System.out.println("Error saving user: " + e.getMessage());
       }
    }

}
