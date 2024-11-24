package org.POS.backend.quoted_item;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuotedItemDAO {

    private SessionFactory sessionFactory;

    public QuotedItemDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

}
