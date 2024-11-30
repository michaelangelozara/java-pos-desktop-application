package org.POS.backend.stock;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    private SessionFactory sessionFactory;

    public StockDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Stock stock) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(stock);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Stock> getAllValidStocksByProductId(StockType type, int productId) {
        List<Stock> stocks = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            stocks = session.createQuery("SELECT s FROM Stock s JOIN FETCH s.product sp WHERE s.type =: type AND (sp.id = :productId AND sp.isDeleted = FALSE) ", Stock.class)
                    .setParameter("productId", productId)
                    .setParameter("type", type)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public List<Stock> getAllValidStocks() {
        List<Stock> stocks = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            stocks = session.createQuery("SELECT s FROM Stock s", Stock.class)
                    .setMaxResults(50)
                    .getResultList();
            stocks.forEach(s -> {
                Hibernate.initialize(s.getProduct());
                Hibernate.initialize(s.getProduct().getProductAttributes());
                s.getProduct().getProductAttributes().forEach(pa -> Hibernate.initialize(pa.getProductVariations()));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public List<Stock> getAllStocksByProductCategoryId(int id) {
        List<Stock> stocks = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            stocks = session.createQuery("SELECT s FROM Stock s JOIN FETCH s.product p JOIN FETCH p.productCategory pc WHERE pc.id = :id", Stock.class)
                    .setParameter("id", id)
                    .getResultList();

            stocks.forEach(s -> {
                Hibernate.initialize(s.getProduct());
                Hibernate.initialize(s.getProduct().getProductCategory());
                Hibernate.initialize(s.getProduct().getProductAttributes());
                s.getProduct().getProductAttributes().forEach(pa -> Hibernate.initialize(pa.getProductVariations()));
            });
        } catch (Exception e) {
            throw e;
        }
        return stocks;
    }

    public List<Stock> getAllValidStocksByRange(LocalDate start, LocalDate end){
        List<Stock> stocks = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            stocks = session.createQuery("SELECT s FROM Stock s WHERE s.date >= :start AND s.date <= :end", Stock.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        }catch (Exception e){
            throw e;
        }
        return stocks;
    }
}
