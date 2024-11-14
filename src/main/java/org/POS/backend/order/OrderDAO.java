package org.POS.backend.order;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
