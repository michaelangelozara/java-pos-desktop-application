package org.POS.backend.inventory_adjustment;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.product.Product;
import org.POS.backend.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdjustmentDAO {

    private SessionFactory sessionFactory;

    public InventoryAdjustmentDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(InventoryAdjustment inventoryAdjustment, Product product){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.persist(inventoryAdjustment);

            session.merge(product);

            transaction.commit();
        }catch (Exception e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(InventoryAdjustment inventoryAdjustment, User user, Product product){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.merge(user);
            session.merge(product);
            session.merge(inventoryAdjustment);

            transaction.commit();
        }catch (Exception e){
            System.err.println("Error in InventoryAdjustmentDAO.update: " + e.getMessage());
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw new RuntimeException("Error updating inventory adjustment", e); // Rethrow with context
        }
    }

    public List<InventoryAdjustment> getAllValidInventoryAdjustment(int number){
        List<InventoryAdjustment> inventoryAdjusts = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            inventoryAdjusts = session.createQuery("SELECT ia FROM InventoryAdjustment ia WHERE ia.isDeleted = FALSE", InventoryAdjustment.class)
                    .setMaxResults(number)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return inventoryAdjusts;
    }

    public InventoryAdjustment getValidInventoryAdjustmentById(int id){
        InventoryAdjustment inventoryAdjustment = null;
        try (Session session = sessionFactory.openSession()){

            inventoryAdjustment = session.createQuery("SELECT ia FROM InventoryAdjustment ia WHERE ia.id =: id AND ia.isDeleted = FALSE", InventoryAdjustment.class)
                    .setParameter("id", id)
                    .getSingleResult();

        }catch (Exception e){
            e.printStackTrace();
        }
        return inventoryAdjustment;
    }
}
