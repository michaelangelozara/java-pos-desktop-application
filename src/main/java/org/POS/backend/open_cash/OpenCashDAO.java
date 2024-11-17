package org.POS.backend.open_cash;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.user.User;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OpenCashDAO {

    private SessionFactory sessionFactory;

    public OpenCashDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(OpenCash openCash, User user, UserLog userLog){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            user.addOpenCash(openCash);
            session.persist(openCash);
            session.persist(userLog);
            transaction.commit();
        }catch (Exception e){
            // Ensure the transaction is active before attempting rollback
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (RuntimeException rollbackException) {
                    rollbackException.printStackTrace();
                    System.err.println("Rollback failed: " + rollbackException.getMessage());
                }
            }
            e.printStackTrace();
        }
    }
}
