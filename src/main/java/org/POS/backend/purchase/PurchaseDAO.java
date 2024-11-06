package org.POS.backend.purchase;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.product.Product;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {

    private SessionFactory sessionFactory;

    public PurchaseDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Purchase purchase){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(purchase);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Purchase purchase){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(purchase);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(int purchaseId){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Purchase purchase = session.createQuery("SELECT p FROM Purchase p WHERE p.id = :purchaseId AND p.isDeleted = FALSE", Purchase.class)
                            .setParameter("purchaseId", purchaseId)
                                    .getSingleResult();
            purchase.setDeleted(true);
            purchase.setDeletedAt(LocalDate.now());
            session.merge(purchase);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Purchase getValidPurchaseById(int purchaseId){
        Purchase purchase = null;
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            purchase = session.createQuery("SELECT p FROM Purchase p WHERE p.id = : purchaseId AND p.isDeleted = FALSE ", Purchase.class)
                    .setParameter("purchaseId", purchaseId)
                    .getSingleResult();


            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return purchase;
    }

    public List<Purchase> getAllValidPurchases(){
        Transaction transaction = null;
        List<Purchase> purchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            purchases = session.createQuery("SELECT p FROM Purchase p WHERE p.isDeleted = FALSE ", Purchase.class)
                    .getResultList();

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
        }
        return purchases;
    }

    public List<Purchase> getAllValidPurchaseBySupplierId(int supplierId){
        List<Purchase> purchases = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            String hqlQuery = "SELECT p FROM Purchase p LEFT JOIN FETCH p.purchaseProducts WHERE p.person.id = :supplierId AND p.isDeleted = FALSE ";
            purchases = session.createQuery(hqlQuery, Purchase.class)
                            .setParameter("supplierId", supplierId)
                                    .getResultList();

            session.getTransaction().commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return purchases;
    }


}
