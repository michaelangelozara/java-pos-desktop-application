package org.POS.backend.cash_transaction;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CashTransactionDAO {

    private SessionFactory sessionFactory;

    public CashTransactionDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<CashTransaction> getAllValidCashTransaction(int number){
        List<CashTransaction> cashTransactions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            cashTransactions = session.createQuery("SELECT ct FROM CashTransaction ct", CashTransaction.class)
                    .setMaxResults(number)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return cashTransactions;
    }

    public List<CashTransaction> getAllValidCashTransactionByUserName(String name){
        List<CashTransaction> cashTransactions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            cashTransactions = session.createQuery("SELECT ct FROM CashTransaction ct JOIN FETCH ct.user u WHERE u.name LIKE :name AND u.isDeleted = FALSE", CashTransaction.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return cashTransactions;
    }

    public List<CashTransaction> getAllValidCashTransactionByRange(LocalDateTime start, LocalDateTime end){
        List<CashTransaction> cashTransactions = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            cashTransactions = session.createQuery("SELECT ct FROM CashTransaction ct JOIN FETCH ct.user u WHERE (ct.dateTime >= :start AND ct.dateTime <= :end) AND u.isDeleted = FALSE", CashTransaction.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return  cashTransactions;
    }
}
