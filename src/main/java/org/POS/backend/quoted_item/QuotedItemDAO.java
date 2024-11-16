package org.POS.backend.quoted_item;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuotedItemDAO {

    private SessionFactory sessionFactory;

    public QuotedItemDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<QuotedItem> getALlValidQuotedItemByIds(Set<Integer> quotedItemIds){
        List<QuotedItem> quotedItems = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            quotedItems = session.createQuery(
                            "SELECT qi FROM QuotedItem qi WHERE qi.id IN :quotedItemIds AND qi.isDeleted = FALSE",
                            QuotedItem.class)
                    .setParameter("quotedItemIds", quotedItemIds)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return quotedItems;
    }

    public void delete(int quotedItemId){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            QuotedItem quotedItem = session.createQuery(
                            "SELECT qi FROM QuotedItem qi WHERE qi.id = :quotedItemId AND qi.isDeleted = FALSE",
                            QuotedItem.class)
                    .setParameter("quotedItemId", quotedItemId)
                    .getSingleResult();

            quotedItem.setDeleted(true);
            quotedItem.setDeletedAt(LocalDate.now());
            session.merge(quotedItem);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
