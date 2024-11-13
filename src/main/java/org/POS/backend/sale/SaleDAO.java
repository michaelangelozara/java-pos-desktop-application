package org.POS.backend.sale;

import jakarta.transaction.Transactional;
import org.POS.backend.cash_transaction.CashTransaction;
import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.product.Product;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.stock.Stock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Sale add(Sale sale, List<SaleItem> saleItems, List<Product> products, List<Stock> stocks) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(sale);
            for (var saleItem : saleItems) {
                session.persist(saleItem);
            }
            for (var stock : stocks) {
                session.persist(stock);
            }
            for (var product : products) {
                session.merge(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sale;
    }

    public Sale add(Sale sale, List<SaleItem> saleItems, List<Product> products, List<Stock> stocks, CashTransaction cashTransaction) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            sale.setCashTransaction(cashTransaction);
            session.persist(sale);
            for (var saleItem : saleItems) {
                session.persist(saleItem);
            }
            for (var stock : stocks) {
                session.persist(stock);
            }

            session.persist(cashTransaction);

            for (var product : products) {
                session.merge(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sale;
    }

    public List<Sale> getAllValidSales(int number){
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            sales = session.createQuery("SELECT s FROM Sale s", Sale.class)
                    .setMaxResults(number)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return sales;
    }

    public List<Sale> getAllValidSalesByRange(LocalDate start, LocalDate end){
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            sales = session.createQuery("SELECT s FROM Sale s WHERE s.date >= :start AND s.date <= :end", Sale.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return sales;
    }

}
