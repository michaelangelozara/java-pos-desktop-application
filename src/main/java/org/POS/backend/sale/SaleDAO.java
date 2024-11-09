package org.POS.backend.sale;

import jakarta.transaction.Transactional;
import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.sale_item.SaleItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class SaleDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Transactional
    public void add(Sale sale, List<SaleItem> saleItems){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(sale);
            for(var saleItem : saleItems){
                session.persist(saleItem);
            }
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
