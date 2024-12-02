package org.POS.backend.order;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.payment.Payment;
import org.POS.backend.product.Product;
import org.POS.backend.product_attribute.ProductVariation;
import org.POS.backend.return_product.ReturnOrder;
import org.POS.backend.sale.Sale;
import org.POS.backend.sale_product.SaleProduct;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private SessionFactory sessionFactory;

    public OrderDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }


    public List<Order> getAllValidOrders(int number) {
        List<Order> orders = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            orders = session.createQuery("SELECT o FROM Order o", Order.class)
                    .setMaxResults(number)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order getValidOrderById(int id) {
        Order order = null;
        try (Session session = sessionFactory.openSession()) {

            order = session.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class)
                    .setParameter("id", id)
                    .getSingleResult();

            Hibernate.initialize(order.getSale());
            Hibernate.initialize(order.getSale().getSaleProducts());
            Hibernate.initialize(order.getSale().getPayment());
            Hibernate.initialize(order.getSale().getInvoice());
            Hibernate.initialize(order.getSale().getPayment().getPoLogs());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public void update(
            List<Product> products,
            ReturnOrder returnOrder,
            UserLog userLog,
            Sale sale
    ) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.merge(sale);
            products.forEach(session::merge);
            session.persist(returnOrder);
            session.persist(userLog);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void updateOrderAmountDue(Order order, Sale sale) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.merge(order);
            session.merge(sale);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Order> getAllValidOrdersByCode(String code) {
        List<Order> orders;
        try (Session session = sessionFactory.openSession()){
            String query = "SELECT o FROM Order o WHERE (o.orderNumber LIKE : code) OR (o.sale.person.name LIKE : code) OR (STR(o.status) LIKE : code) OR (STR(o.sale.payment.transactionType) LIKE : code)";
            orders = session.createQuery(query, Order.class)
                    .setParameter("code", "%" + code + "%")
                    .getResultList();
            return orders;
        }catch (Exception e){
            throw e;
        }
    }

    public List<Order> getAllValidOrdersByRange(LocalDate start, LocalDate end) {
        List<Order> orders = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            String query = "SELECT o FROM Order o WHERE o.sale.date >= :start AND o.sale.date <= : end";
            orders = session.createQuery(query, Order.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }
}
