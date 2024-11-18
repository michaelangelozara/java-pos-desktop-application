package org.POS.backend.user_log;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserLogDAO {

    private SessionFactory sessionFactory;

    public UserLogDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<UserLog> getAllValidUserLogsByUserId(int id, int number){
        List<UserLog> userLogs = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            userLogs = session.createQuery("SELECT ul FROM UserLog ul WHERE ul.user.id =: id", UserLog.class)
                    .setParameter("id", id)
                    .setMaxResults(number)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return userLogs;
    }

    public List<UserLog> getAllValidUserLogsByRangeAndUserId(LocalDate start, LocalDate end, int id){
        List<UserLog> userLogs = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            userLogs = session.createQuery("SELECT ul FROM UserLog ul WHERE (ul.date >= :start AND ul.date <= :end) AND ul.user.id =: id", UserLog.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .setParameter("id", id)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return userLogs;
    }

    public List<UserLog> getAllValidUserLogsByTransactionCodeAndUserId(String code, int id){
        List<UserLog> userLogs = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            userLogs = session.createQuery("SELECT ul FROM UserLog ul WHERE (ul.code LIKE :code OR ul.action LIKE : code) AND ul.user.id =: id", UserLog.class)
                    .setParameter("code", "%" + code + "%")
                    .setParameter("id", id)
                    .getResultList();

            userLogs.forEach(ul -> Hibernate.initialize(ul.getUser()));

        }catch (Exception e){
            e.printStackTrace();
        }
        return userLogs;
    }
}
