package org.POS.backend.return_purchase;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.product.Product;
import org.POS.backend.purchase.Purchase;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnPurchaseDAO {
    private SessionFactory sessionFactory;

    public ReturnPurchaseDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(
            Purchase purchase,
//            ReturnPurchase returnPurchase,
            List<PurchaseItem> purchaseItems,
            UserLog userLog,
            List<Product> products
    ){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.merge(purchase);

            for(var purchaseItem : purchaseItems){
                session.merge(purchaseItem);
            }

            for(var product : products){
                session.merge(product);
            }

            session.persist(userLog);
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

    public List<ReturnPurchase> getAllValidReturnPurchaseByRange(LocalDate start, LocalDate end){
        List<ReturnPurchase> returnPurchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            returnPurchases = session.createQuery("SELECT rp FROM ReturnPurchase rp JOIN FETCH rp.purchaseItems pi WHERE pi.returnedAt >= :start AND pi.returnedAt <= :end", ReturnPurchase.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return returnPurchases;
    }

    public List<ReturnPurchase> getAllValidReturnPurchaseByQuery(String name){
        List<ReturnPurchase> returnPurchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            returnPurchases = session.createQuery("SELECT rp FROM ReturnPurchase rp JOIN FETCH rp.purchase p WHERE (rp.code LIKE : name) OR (p.code LIKE :name) OR (p.person.name LIKE :name)", ReturnPurchase.class)
                    .setParameter("name","%" + name + "%")
                    .getResultList();

            returnPurchases.forEach(rp -> {
                Hibernate.initialize(rp.getPurchase());
                Hibernate.initialize(rp.getPurchase().getPerson());
                Hibernate.initialize(rp.getPurchaseItems());
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnPurchases;
    }

    public ReturnPurchase getValidReturnPurchaseById(int id){
        ReturnPurchase returnPurchase = null;
        try (Session session = sessionFactory.openSession()){

            returnPurchase = session.createQuery("Select rp FROM ReturnPurchase rp WHERE rp.id = :id",ReturnPurchase.class)
                    .setParameter("id", id)
                    .getSingleResult();

            Hibernate.initialize(returnPurchase.getPurchaseItems());
            Hibernate.initialize(returnPurchase.getPurchase());
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnPurchase;
    }
}
