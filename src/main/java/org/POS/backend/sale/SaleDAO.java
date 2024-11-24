package org.POS.backend.sale;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.product.Product;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SaleDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void add(
            Sale sale,
            UserLog userLog,
            Set<Product> products
    ) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            products.forEach(session::merge);
            session.persist(sale);
            session.persist(userLog);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }finally {
            session.close();
        }
    }

    public List<Sale> getAllValidSales(int number) {
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            sales = session.createQuery("SELECT s FROM Sale s", Sale.class)
                    .setMaxResults(number)
                    .getResultList();

            sales.forEach(s -> Hibernate.initialize(s.getSaleProducts()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sales;
    }

    public List<Sale> getAllValidSalesByRangeWithoutDto(LocalDate start, LocalDate end) {
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            sales = session.createQuery("SELECT s FROM Sale s WHERE s.date >= :start AND s.date <= :end", Sale.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

            sales.forEach(s -> Hibernate.initialize(s.getSaleProducts()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sales;
    }

    public List<Sale> getAllValidSalesByRange(LocalDate start, LocalDate end) {
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            sales = session.createQuery("SELECT s FROM Sale s WHERE s.date >= :start AND s.date <= :end", Sale.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sales;
    }

    public List<Sale> getAllCashSales() {
        List<Sale> sales = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            sales = session.createQuery("SELECT s FROM Sale s WHERE s.payment.transactionType = 'CASH' ", Sale.class)
                    .setMaxResults(50)
                    .getResultList();

            sales.forEach(s -> Hibernate.initialize(s.getSaleProducts()));

        }catch (Exception e){
            e.printStackTrace();
        }
        return sales;
    }

    public List<Sale> getAllValidPOSales(int number, SaleTransactionMethod method) {
        List<Sale> sales = new ArrayList<>();
//        try (Session session = sessionFactory.openSession()){
//
//            sales = session.createQuery("SELECT s FROM Sale s WHERE s.transactionMethod =: method", Sale.class)
//                    .setParameter("method", method)
//                    .setMaxResults(number)
//                    .getResultList();
//
//            sales.forEach(s -> Hibernate.initialize(s.getInvoice()));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return sales;
    }

    public List<Sale> getAllValidPOSales(LocalDate start, LocalDate end, SaleTransactionMethod method) {
        List<Sale> sales = new ArrayList<>();
//        try (Session session = sessionFactory.openSession()){
//
//            sales = session.createQuery("SELECT s FROM Sale s WHERE (s.date >= :start AND s.date <= : end) AND s.transactionMethod =: method", Sale.class)
//                    .setParameter("method", method)
//                    .setParameter("start", start)
//                    .setParameter("end", end)
//                    .getResultList();
//
//            sales.forEach(s -> Hibernate.initialize(s.getInvoice()));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return sales;
    }

    public BigDecimal getTotalSales() {
        BigDecimal totalSales = BigDecimal.ZERO;
//        try (Session session = sessionFactory.openSession()){
//           totalSales = session.createQuery("SELECT SUM(s.amount) FROM Sale s", BigDecimal.class)
//                    .getSingleResult();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return totalSales;
    }
}
