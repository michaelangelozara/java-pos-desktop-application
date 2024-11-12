package org.POS.backend.cash_transaction;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
