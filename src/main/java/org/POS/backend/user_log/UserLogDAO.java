package org.POS.backend.user_log;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserLogDAO {

    private SessionFactory sessionFactory;

    public UserLogDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void addUserLog(UserLog userLog){
        try (Session session = sessionFactory.openSession()){
            session.persist(userLog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
