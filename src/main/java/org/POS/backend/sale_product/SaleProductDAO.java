package org.POS.backend.sale_product;

import jakarta.persistence.NoResultException;
import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class SaleProductDAO {

    private SessionFactory sessionFactory;

    public SaleProductDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<SaleProduct> getAllValidSaleItemsByIds(List<Integer> ids){
        List<SaleProduct> saleProducts = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            saleProducts = session.createQuery("SELECT si FROM SaleProduct si WHERE si.id IN : ids", SaleProduct.class)
                    .setParameter("ids", ids)
                    .getResultList();

            saleProducts.forEach(s -> {
                Hibernate.initialize(s.getProduct().getStocks());
                Hibernate.initialize(s.getProduct().getSaleProducts());
            });
        }catch (Exception e){
            e.printStackTrace();
            throw new NoResultException("No sale item found");
        }
        return saleProducts;
    }
}
