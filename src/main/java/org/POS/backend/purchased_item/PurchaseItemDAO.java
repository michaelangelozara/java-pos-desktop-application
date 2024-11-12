package org.POS.backend.purchased_item;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PurchaseItemDAO {

    private SessionFactory sessionFactory;

    public PurchaseItemDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<PurchaseItem> getAllValidPurchaseItemByPurchaseItemIds(Set<Integer> ids){
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            purchaseItems = session.createQuery("SELECT pi FROM PurchaseItem pi WHERE pi.id IN (:ids) AND pi.isDelete = FALSE", PurchaseItem.class)
                    .setParameter("ids", ids)
                    .getResultList();

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return purchaseItems;
    }
}
