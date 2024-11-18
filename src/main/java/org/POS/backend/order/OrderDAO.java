package org.POS.backend.order;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.payment.Payment;
import org.POS.backend.product.Product;
import org.POS.backend.return_product.ReturnProduct;
import org.POS.backend.sale.Sale;
import org.POS.backend.sale_item.SaleItem;
import org.POS.backend.user.User;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private SessionFactory sessionFactory;

    public OrderDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }


    public List<Order> getAllValidOrders(int number){
        List<Order> orders = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            orders = session.createQuery("SELECT o FROM Order o", Order.class)
                    .setMaxResults(number)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return orders;
    }

    public Order getValidOrderById(int id){
        Order order = null;
        try (Session session = sessionFactory.openSession()){

            order = session.createQuery("SELECT o FROM Order o WHERE o.id = :id", Order.class)
                    .setParameter("id", id)
                    .getSingleResult();

            Hibernate.initialize(order.getSale());
            Hibernate.initialize(order.getSale().getSaleItems());
            Hibernate.initialize(order.getPerson().getStocks());
            Hibernate.initialize(order.getPerson().getPayments());
            Hibernate.initialize(order.getSale().getInvoice().getPayments());

        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }

    public void update(
            Order order,
            ReturnProduct returnProduct,
            Sale sale,
            List<Product> products,
            List<SaleItem> saleItems,
            UserLog userLog
    ) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(order);
            // Update sale
            session.merge(sale);
            // Update products
            for (Product product : products) {
                session.merge(product);
            }
            // Update saleItems
            for (SaleItem saleItem : saleItems) {
                session.merge(saleItem);
            }
            // Save returnProduct
            session.merge(returnProduct);
            // Commit transaction

            session.persist(userLog);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateOrderAmountDue(Order order, Sale sale, Payment payment){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            session.merge(order);
            session.merge(sale);
            session.persist(payment);

            transaction.commit();
        }catch (Exception e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw new RuntimeException(e.getMessage());
        }
    }
}
