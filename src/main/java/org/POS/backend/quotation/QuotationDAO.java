package org.POS.backend.quotation;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.quoted_item.QuotedItem;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuotationDAO {

    private SessionFactory sessionFactory;

    public QuotationDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Quotation quotation, UserLog userLog) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(quotation);
            session.persist(userLog);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Quotation quotation = session.createQuery("SELECT q FROM Quotation q WHERE q.id = :id", Quotation.class)
                    .setParameter("id", id)
                    .getSingleResult();

            quotation.setDeleted(true);
            quotation.setDeletedAt(LocalDate.now());
            session.merge(quotation);

            transaction.commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Quotation> getAllValidQuotation(int number) {
        List<Quotation> quotations = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            quotations = session.createQuery("SELECT q FROM Quotation q WHERE q.isDeleted = FALSE ", Quotation.class)
                    .setMaxResults(number)
                    .getResultList();
            quotations.forEach(q -> Hibernate.initialize(q.getQuotedItems()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotations;
    }

    public void update(Quotation quotation, List<QuotedItem> quotedItems, UserLog userLog) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.merge(quotation);

            for (var quotedItem : quotedItems) {
                session.merge(quotedItem);
            }

            session.persist(userLog);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Quotation getValidQuotationById(int id) {
        Quotation quotation = null;
        try (Session session = sessionFactory.openSession()) {
            quotation = session.createQuery("SELECT q FROM Quotation q WHERE q.id =: id AND q.isDeleted = FALSE", Quotation.class)
                    .setParameter("id", id)
                    .getSingleResult();

            Hibernate.initialize(quotation.getQuotedItems());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotation;
    }

    public List<Quotation> getAllValidQuotationByCustomerName(String name) {
        List<Quotation> quotations = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            quotations = session.createQuery("SELECT q FROM Quotation q JOIN FETCH q.person p WHERE p.name LIKE :name AND p.isDeleted = FALSE", Quotation.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quotations;
    }
}
