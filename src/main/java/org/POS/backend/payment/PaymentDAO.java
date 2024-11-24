package org.POS.backend.payment;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private SessionFactory sessionFactory;

    public PaymentDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Payment> getAllCashPayment(){
        List<Payment> payments = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){

            payments = session.createQuery("SELECT p FROM Payment p WHERE p.transactionType = 'CASH'", Payment.class)
                    .setMaxResults(50)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return payments;
    }

    public List<Payment> getAllCashPaymentByRange(String query){
        List<Payment> payments = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){

            payments = session.createQuery("SELECT p FROM Payment p WHERE p.transactionType = 'CASH' AND p.sale.user.name =: query", Payment.class)
                    .setMaxResults(50)
                    .setParameter("query","%" + query + "%")
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return payments;
    }

    public List<Payment> getAllCashPaymentByRange(LocalDate start, LocalDate end){
        List<Payment> payments = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){

            payments = session.createQuery("SELECT p FROM Payment p WHERE p.transactionType = 'CASH' AND (p.date >= : start AND p.date <= : end)", Payment.class)
                    .setMaxResults(50)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return payments;
    }
}
