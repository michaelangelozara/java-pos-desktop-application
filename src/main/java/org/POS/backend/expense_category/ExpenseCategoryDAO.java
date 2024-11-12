package org.POS.backend.expense_category;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.expense_subcategory.ExpenseSubcategory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCategoryDAO {

    private SessionFactory sessionFactory;

    public ExpenseCategoryDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(ExpenseCategory expenseCategory){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(expenseCategory);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void update(ExpenseCategory expenseCategory){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(expenseCategory);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(int expenseCategoryId){
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            var dateNow = LocalDate.now();

            ExpenseCategory expenseCategory = session.createQuery("SELECT ec FROM ExpenseCategory ec WHERE ec.id = :expenseCategoryId AND ec.isDeleted = FALSE ", ExpenseCategory.class)
                            .setParameter("expenseCategoryId", expenseCategoryId)
                                    .getSingleResult();
            expenseCategory.setDeleted(true);
            expenseCategory.setDeletedAt(dateNow);

            if(expenseCategory.getExpenseSubcategories() != null){
                Hibernate.initialize(expenseCategory.getExpenseSubcategories());

                List<ExpenseSubcategory> subcategoryList = expenseCategory.getExpenseSubcategories();
                for(ExpenseSubcategory expenseSubcategory : subcategoryList){
                    expenseSubcategory.setDeleted(true);
                    expenseSubcategory.setDeletedAt(dateNow);
                    session.merge(expenseSubcategory);
                }
            }

            session.merge(expenseCategory);

            session.getTransaction().commit();
        }catch (Exception e){
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        }
    }

    public ExpenseCategory getValidExpenseCategoryById(int expenseCategoryId){
        ExpenseCategory expenseCategory = null;
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            expenseCategory = session.createQuery("SELECT ec FROM ExpenseCategory ec WHERE ec.id = :expenseCategoryId AND ec.isDeleted = FALSE ", ExpenseCategory.class)
                            .setParameter("expenseCategoryId", expenseCategoryId)
                                    .getSingleResult();

            session.getTransaction().commit();
            return expenseCategory;
        }catch (Exception e){
            e.printStackTrace();
        }
        return expenseCategory;
    }

    public List<ExpenseCategory> getAllValidExpenseCategories(){
        List<ExpenseCategory> expenseCategories = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            expenseCategories = session.createQuery("SELECT ec FROM ExpenseCategory ec WHERE ec.isDeleted = FALSE ", ExpenseCategory.class)
                    .getResultList();

            session.getTransaction().commit();
            return expenseCategories;
        }catch (Exception e){
            e.printStackTrace();
        }
        return expenseCategories;
    }

    public List<ExpenseCategory> getAllValidExpenseCategoryByName(String name) {
        List<ExpenseCategory> expenseCategories = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            // HQL query to find expense categories where name contains the specified string
            String hql = "SELECT ec FROM ExpenseCategory ec WHERE ec.name LIKE :name AND ec.isDeleted = FALSE";
            expenseCategories = session.createQuery(hql, ExpenseCategory.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseCategories;
    }
}
