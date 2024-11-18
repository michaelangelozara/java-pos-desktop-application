package org.POS.backend.sale;

import org.POS.backend.cash_transaction.CashTransaction;
import org.POS.backend.cash_transaction.TransactionPaymentMethod;
import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.invoice.Invoice;
import org.POS.backend.order.Order;
import org.POS.backend.product.Product;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.stock.Stock;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Sale add(
            Sale sale,
            List<SaleItem> saleItems,
            List<Product> products,
            List<Stock> stocks,
            Order order,
            Invoice invoice,
            UserLog userLog
    ) {
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

            session.persist(order);
            session.persist(invoice);
            session.persist(userLog);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sale;
    }

    public Sale add(
            Sale sale,
            List<SaleItem> saleItems,
            List<Product> products,
            List<Stock> stocks,
            CashTransaction cashTransaction,
            Order order,
            Invoice invoice,
            UserLog userLog
    ) {
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

            session.persist(order);
            session.persist(invoice);
            session.persist(userLog);
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

            sales.forEach(s -> Hibernate.initialize(s.getSaleItems()));

        }catch (Exception e){
            e.printStackTrace();
        }
        return sales;
    }

    public List<Sale> getAllValidSalesByRangeWithoutDto(LocalDate start, LocalDate end){
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            sales = session.createQuery("SELECT s FROM Sale s WHERE s.date >= :start AND s.date <= :end", Sale.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

            sales.forEach(s -> Hibernate.initialize(s.getSaleItems()));

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

    public List<Sale> getAllSalesByCashTransactionType(TransactionPaymentMethod type){
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            sales = session.createQuery("SELECT s FROM Sale s JOIN FETCH s.cashTransaction ct WHERE ct.transactionPaymentMethod =: type", Sale.class)
                    .setParameter("type", type)
                    .setMaxResults(50)
                    .getResultList();

            sales.forEach(s -> Hibernate.initialize(s.getSaleItems()));

        }catch (Exception e){
            e.printStackTrace();
        }
        return sales;
    }

    public List<Sale> getAllValidPOSales(int number, SaleTransactionMethod method){
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            sales = session.createQuery("SELECT s FROM Sale s WHERE s.transactionMethod =: method", Sale.class)
                    .setParameter("method", method)
                    .setMaxResults(number)
                    .getResultList();

            sales.forEach(s -> Hibernate.initialize(s.getInvoice()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return sales;
    }

    public List<Sale> getAllValidPOSales(LocalDate start,LocalDate end, SaleTransactionMethod method){
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            sales = session.createQuery("SELECT s FROM Sale s WHERE (s.date >= :start AND s.date <= : end) AND s.transactionMethod =: method", Sale.class)
                    .setParameter("method", method)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

            sales.forEach(s -> Hibernate.initialize(s.getInvoice()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return sales;
    }

    public BigDecimal getTotalSales(){
        BigDecimal totalSales = BigDecimal.ZERO;
        try (Session session = sessionFactory.openSession()){
            totalSales = session.createQuery("SELECT SUM(s.amount) FROM Sale s", BigDecimal.class)
                    .getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        return totalSales;
    }
}
