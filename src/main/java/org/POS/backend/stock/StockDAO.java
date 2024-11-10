package org.POS.backend.stock;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    private SessionFactory sessionFactory;

    public StockDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Stock stock){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(stock);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Stock> getAllValidStockByType(StockType type){
        List<Stock> stocks = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            stocks = session.createQuery("SELECT s FROM Stock s LEFT JOIN FETCH s.person WHERE s.type = :type", Stock.class)
                    .setParameter("type", type)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return stocks;
    }

    public List<Stock> getAllValidStockProductId(int productId){
        List<Stock> stocks = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            stocks = session.createQuery("SELECT s FROM Stock s JOIN FETCH s.product sp WHERE sp.id = :productId AND sp.isDeleted = FALSE ", Stock.class)
                    .setParameter("productId", productId)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return stocks;
    }
}
