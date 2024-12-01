package org.POS.backend.invoice;

import jakarta.persistence.NoResultException;
import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    private SessionFactory sessionFactory;

    public InvoiceDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Invoice> getAllValidInvoices(int number) {
        List<Invoice> invoices = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            invoices = session.createQuery("SELECT i FROM Invoice i", Invoice.class)
                    .setMaxResults(number)
                    .getResultList();

            for (var invoice : invoices) {
                Hibernate.initialize(invoice.getSale());
                Hibernate.initialize(invoice.getSale().getSaleProducts());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public Invoice getValidInvoiceById(int id) {
        Invoice invoice = null;
        try (Session session = sessionFactory.openSession()) {

            invoice = session.createQuery("SELECT i FROM Invoice i WHERE i.id =: id", Invoice.class)
                    .setParameter("id", id)
                    .getSingleResult();

            Hibernate.initialize(invoice.getSale().getSaleProducts());
            Hibernate.initialize(invoice.getSale().getPayment().getPoLogs());
        } catch (Exception e) {
            throw new NoResultException("No invoice found");
        }
        return invoice;
    }

    public List<Invoice> getAllInvoicesByPersonId(int id) {
        List<Invoice> invoices = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            invoices = session.createQuery("SELECT i FROM Invoice i WHERE i.sale.person.id = : id", Invoice.class)
                    .setParameter("id", id)
                    .getResultList();

            invoices.forEach(i -> {
                Hibernate.initialize(i.getSale());
            });
        } catch (NoResultException e) {
            throw new RuntimeException("No invoice found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public List<Invoice> getAllValidInvoiceByRangeAndId(LocalDate start, LocalDate end, int id) {
        List<Invoice> invoices = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            invoices = session.createQuery("SELECT i FROM Invoice i WHERE i.sale.person.id = : id AND (i.sale.date >= : start AND i.sale.date <= :end)", Invoice.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .setParameter("id", id)
                    .getResultList();

            invoices.forEach(i -> {
                Hibernate.initialize(i.getSale());
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return invoices;
    }

    public List<Invoice> getAllValidInvoiceByRange(LocalDate start, LocalDate end) {
        List<Invoice> invoices = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            invoices = session.createQuery("SELECT i FROM Invoice i WHERE i.sale.date >= : start AND i.sale.date <= :end", Invoice.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

            invoices.forEach(i -> {
                Hibernate.initialize(i.getSale());
                Hibernate.initialize(i.getSale().getSaleProducts());
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return invoices;
    }

    public List<Invoice> getAllValidInvoiceByCodeAndPersonId(String code, int id) {
        List<Invoice> invoices = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            invoices = session.createQuery("SELECT i FROM Invoice i WHERE i.sale.person.id = : id AND i.invoiceNumber LIKE : code", Invoice.class)
                    .setParameter("code", "%" + code + "%")
                    .setParameter("id", id)
                    .getResultList();

            invoices.forEach(i -> {
                Hibernate.initialize(i.getSale());
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return invoices;
    }

    public List<Invoice> getAllValidInvoicesByCode(String code) {
        List<Invoice> invoices = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            invoices = session.createQuery("SELECT i FROM Invoice i WHERE i.invoiceNumber LIKE : code OR i.sale.person.name LIKE : code", Invoice.class)
                    .setParameter("code", "%" + code + "%")
                    .getResultList();

            invoices.forEach(i -> {
                Hibernate.initialize(i.getSale());
                Hibernate.initialize(i.getSale().getSaleProducts());
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return invoices;
    }

    // invoice numebr/ client
}
