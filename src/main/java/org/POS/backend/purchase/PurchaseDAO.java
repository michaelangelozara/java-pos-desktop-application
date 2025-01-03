package org.POS.backend.purchase;

import jakarta.persistence.NoResultException;
import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.purchased_item.PurchaseItem;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {

    private SessionFactory sessionFactory;

    public PurchaseDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Purchase purchase,UserLog userLog) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(purchase);
            session.persist(userLog);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void update(Purchase purchase, UserLog userLog, List<PurchaseItem> purchaseItemList) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.merge(purchase);
            session.persist(userLog);

            purchaseItemList.forEach(s -> session.merge(s));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(int purchaseId, UserLog userLog) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Purchase purchase = session.createQuery("SELECT p FROM Purchase p WHERE p.id = :purchaseId AND p.isDeleted = FALSE", Purchase.class)
                    .setParameter("purchaseId", purchaseId)
                    .getSingleResult();
            purchase.setDeleted(true);
            purchase.setDeletedAt(LocalDate.now());
            session.merge(purchase);

            userLog.setCode(purchase.getCode());
            session.persist(userLog);

            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public Purchase getValidPurchaseById(int purchaseId) {
        Purchase purchase = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            purchase = session.createQuery("SELECT p FROM Purchase p WHERE p.id = : purchaseId AND p.isDeleted = FALSE", Purchase.class)
                    .setParameter("purchaseId", purchaseId)
                    .getSingleResult();

            Hibernate.initialize(purchase.getPurchaseItems());
            session.getTransaction().commit();
        } catch (NoResultException e) {
            throw new NoResultException("This purchase is empty, please delete it and create a new");
        }
        return purchase;
    }

    public List<Purchase> getAllValidPurchases() {
        List<Purchase> purchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            purchases = session.createQuery("SELECT p FROM Purchase p WHERE p.isDeleted = FALSE ", Purchase.class)
                    .setMaxResults(50)
                    .getResultList();

            purchases.forEach(e -> Hibernate.initialize(e.getPurchaseItems()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }

    public List<Purchase> getAllValidPurchasesWithoutLimit() {
        List<Purchase> purchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            purchases = session.createQuery("SELECT p FROM Purchase p WHERE p.isDeleted = FALSE ", Purchase.class)
                    .getResultList();

            purchases.forEach(e -> Hibernate.initialize(e.getPurchaseItems()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }

    public List<Purchase> getAllValidPurchaseBySupplierId(int supplierId) {
        List<Purchase> purchases = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hqlQuery = "SELECT p FROM Purchase p WHERE p.person.id = :supplierId AND p.isDeleted = FALSE ";
            purchases = session.createQuery(hqlQuery, Purchase.class)
                    .setParameter("supplierId", supplierId)
                    .getResultList();

            purchases.forEach(p -> Hibernate.initialize(p.getPurchaseItems()));

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return purchases;
    }

    public List<Purchase> getAllValidPurchasesByCodeAndSupplier(String query) {
        List<Purchase> purchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            purchases = session.createQuery("SELECT p FROM Purchase p WHERE (p.code LIKE :query OR p.person.name LIKE :query) AND p.isDeleted = FALSE", Purchase.class)
                    .setParameter("query", "%" + query + "%")
                    .getResultList();

            for (var purchase : purchases) {
                Hibernate.initialize(purchase.getPurchaseItems());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }

    public List<Purchase> getAllValidPurchaseByRange(LocalDate start, LocalDate end) {
        List<Purchase> purchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            purchases = session.createQuery("SELECT p FROM Purchase p WHERE p.createdDate >= :start AND p.createdDate <= :end AND p.isDeleted = FALSE", Purchase.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

            for (var purchase : purchases) {
                Hibernate.initialize(purchase.getPurchaseItems());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }

    public List<Purchase> getAllValidPurchaseByRangeAndSupplierId(LocalDate start, LocalDate end, int id) {
        List<Purchase> purchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            purchases = session.createQuery("SELECT p FROM Purchase p WHERE p.person.id =: id AND (p.createdDate >= :start AND p.createdDate <= :end) AND p.isDeleted = FALSE", Purchase.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .setParameter("id", id)
                    .getResultList();

            for (var purchase : purchases) {
                Hibernate.initialize(purchase.getPurchaseItems());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }

    public List<Purchase> getAllValidPurchasesByCodeAndSupplierId(String query, int id) {
        List<Purchase> purchases = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            purchases = session.createQuery("SELECT p FROM Purchase p WHERE (p.code LIKE : query AND p.person.id =: id) AND p.isDeleted = FALSE", Purchase.class)
                    .setParameter("query", "%" + query + "%")
                    .setParameter("id", id)
                    .getResultList();

            for (var purchase : purchases) {
                Hibernate.initialize(purchase.getPurchaseItems());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }

}
