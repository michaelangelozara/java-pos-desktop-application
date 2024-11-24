package org.POS.backend.return_product;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnOrderDAO {

    private SessionFactory sessionFactory;

    public ReturnOrderDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<ReturnOrder> getAllValidReturnedProducts(int number) {
        List<ReturnOrder> returnOrders = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            returnOrders = session.createQuery("SELECT ro FROM ReturnOrder ro", ReturnOrder.class)
                    .setMaxResults(number)
                    .getResultList();

            returnOrders.forEach(r -> {
                Hibernate.initialize(r.getOrder().getSale());
                Hibernate.initialize(r.getOrder().getSale().getPerson());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnOrders;
    }

    public ReturnOrder getValidReturnProductById(int id) {
        ReturnOrder returnOrder = null;
        try (Session session = sessionFactory.openSession()) {

            returnOrder = session.createQuery("SELECT rp FROM ReturnOrder rp WHERE rp.id =: id", ReturnOrder.class)
                    .setParameter("id", id)
                    .getSingleResult();

            Hibernate.initialize(returnOrder.getOrder());
            Hibernate.initialize(returnOrder.getOrder().getSale());
            Hibernate.initialize(returnOrder.getOrder().getSale().getPerson());
            Hibernate.initialize(returnOrder.getReturnItems());
            returnOrder.getReturnItems().forEach(r -> Hibernate.initialize(r.getSaleProduct()));
            returnOrder.getReturnItems().forEach(r -> Hibernate.initialize(r.getSaleProduct().getProduct()));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnOrder;
    }

    public List<ReturnOrder> getAllValidReturnProductsByCode(String query) {
        List<ReturnOrder> returnOrders = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            returnOrders = session.createQuery("SELECT rp FROM ReturnOrder rp WHERE rp.code LIKE : query OR rp.user.name LIKE : query", ReturnOrder.class)
                    .setParameter("query", "%" + query + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnOrders;
    }

    public List<ReturnOrder> getAllValidReturnProductsByRange(LocalDate start, LocalDate end) {
        List<ReturnOrder> returnOrders = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            returnOrders = session.createQuery("SELECT rp FROM ReturnOrder rp WHERE rp.returnedAt >= : start AND rp.returnedAt <= : end", ReturnOrder.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnOrders;
    }
}
