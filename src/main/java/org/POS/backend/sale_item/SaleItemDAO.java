package org.POS.backend.sale_item;

import jakarta.persistence.NoResultException;
import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class SaleItemDAO {

    private SessionFactory sessionFactory;

    public SaleItemDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<SaleItem> getAllValidSaleItemsByIds(List<Integer> ids){
        List<SaleItem> saleItems = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            saleItems = session.createQuery("SELECT si FROM SaleItem si WHERE si.id IN : ids AND si.isReturned = FALSE", SaleItem.class)
                    .setParameter("ids", ids)
                    .getResultList();

            saleItems.forEach(s -> Hibernate.initialize(s.getProduct().getStocks()));
        }catch (Exception e){
            e.printStackTrace();
            throw new NoResultException("No sale item found");
        }
        return saleItems;
    }
}
