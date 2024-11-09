package org.POS.backend.purchased_product;

import jakarta.transaction.Transactional;
import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class PurchaseProductDAO {

    private SessionFactory sessionFactory;

    public PurchaseProductDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(List<PurchaseProduct> purchaseProducts) {
        try (Session session = sessionFactory.getCurrentSession()) {
            for (PurchaseProduct purchaseProduct : purchaseProducts) {
                session.persist(purchaseProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save purchase products");
        }
    }

    public void delete(int purchaseProductId){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            PurchaseProduct purchaseProduct = session.createQuery("SELECT pp FROM PurchaseProduct pp WHERE pp.id = :purchaseProductId AND pp.isDelete = FALSE ", PurchaseProduct.class)
                            .setParameter("purchaseProductId", purchaseProductId)
                                    .getSingleResult();
            purchaseProduct.setDelete(true);
            purchaseProduct.setDeletedAt(LocalDate.now());
            session.merge(purchaseProduct);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(List<PurchaseProduct> purchaseProducts){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            for (PurchaseProduct purchaseProduct : purchaseProducts) {
                if(purchaseProduct.getId() != null){
                    session.merge(purchaseProduct);
                }else{
                    session.persist(purchaseProduct);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
