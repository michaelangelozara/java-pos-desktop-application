package org.POS.backend.purchased_product;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PurchaseProductDAO {

    private SessionFactory sessionFactory;

    public PurchaseProductDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(List<PurchaseProduct> purchaseProducts) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(purchaseProducts);

            session.getTransaction().commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
