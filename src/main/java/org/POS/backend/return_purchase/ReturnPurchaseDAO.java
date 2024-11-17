package org.POS.backend.return_purchase;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.return_product.ReturnProduct;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ReturnPurchaseDAO {
    private SessionFactory sessionFactory;

    public ReturnPurchaseDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Purchase purchase, ReturnPurchase returnPurchase, List<PurchaseItem> purchaseItems){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.merge(purchase);

            for(var purchaseItem : purchaseItems){
                session.merge(purchaseItem);
            }

            session.merge(returnPurchase);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw new RuntimeException("Returning purchase error");
        }
    }

    public List<ReturnPurchase> getAlValidReturnProducts(int number){
        List<ReturnPurchase> returnPurchases = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){

            returnPurchases = session.createQuery("SELECT rp FROM ReturnPurchase rp", ReturnPurchase.class)
                    .setMaxResults(number)
                    .getResultList();

            for(var returnProduct : returnPurchases){
                Hibernate.initialize(returnProduct.getPurchaseItems());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return returnPurchases;
    }
}
