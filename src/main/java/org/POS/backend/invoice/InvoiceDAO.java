package org.POS.backend.invoice;

import jakarta.persistence.NoResultException;
import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    private SessionFactory sessionFactory;

    public InvoiceDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Invoice> getAllValidInvoices(int number){
        List<Invoice> invoices = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            invoices = session.createQuery("SELECT i FROM Invoice i", Invoice.class)
                    .setMaxResults(number)
                    .getResultList();

            for(var invoice : invoices){
                Hibernate.initialize(invoice.getSale());
                Hibernate.initialize(invoice.getSale().getSaleItems());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return invoices;
    }

    public Invoice getValidInvoiceById(int id){
        Invoice invoice = null;
        try (Session session = sessionFactory.openSession()){

            invoice = session.createQuery("SELECT i FROM Invoice i WHERE i.id =: id", Invoice.class)
                    .setParameter("id", id)
                    .getSingleResult();

        }catch (Exception e){
            throw new NoResultException("No invoice found");
        }
        return invoice;
    }
}
