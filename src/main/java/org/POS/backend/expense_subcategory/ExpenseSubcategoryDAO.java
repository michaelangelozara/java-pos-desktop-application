package org.POS.backend.expense_subcategory;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class ExpenseSubcategoryDAO {

    private SessionFactory sessionFactory;

    public ExpenseSubcategoryDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(ExpenseSubcategory expenseSubcategory){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(expenseSubcategory);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void update(ExpenseSubcategory expenseSubcategory){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(expenseSubcategory);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(int expenseSubcategoryId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            ExpenseSubcategory expenseCategory = session.createQuery("SELECT es FROM ExpenseSubcategory es WHERE es.id = :expenseSubcategoryId AND es.isDeleted = FALSE ", ExpenseSubcategory.class)
                            .setParameter("expenseSubcategoryId", expenseSubcategoryId)
                                    .getSingleResult();
            expenseCategory.setDeleted(true);
            expenseCategory.setDeletedAt(LocalDate.now());
            session.merge(expenseCategory);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ExpenseSubcategory getValidExpenseSubcategoryById(int expenseSubcategoryId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            ExpenseSubcategory expenseSubcategory = session.createQuery("SELECT es FROM ExpenseSubcategory es WHERE es.id = :expenseSubcategoryId AND es.isDeleted = FALSE ", ExpenseSubcategory.class)
                    .setParameter("expenseSubcategoryId", expenseSubcategoryId)
                    .getSingleResult();

            session.getTransaction().commit();
            return expenseSubcategory;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<ExpenseSubcategory> getAllValidExpenseSubcategories(){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String hqlQuery = "SELECT es FROM ExpenseSubcategory es WHERE es.isDeleted = FALSE ";
            List<ExpenseSubcategory> expenseSubcategories = session.createQuery(hqlQuery, ExpenseSubcategory.class)
                            .getResultList();

            session.getTransaction().commit();
            return expenseSubcategories;
        }
    }

    public List<ExpenseSubcategory> getAllValidExpenseSubcategoriesByExpenseCategoryId(
            int expenseCategoryId
    ){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String hqlQuery = "SELECT es FROM ExpenseSubcategory es WHERE es.expenseCategory.id = :expenseCategoryId AND es.isDeleted = FALSE ";
            List<ExpenseSubcategory> expenseSubcategories = session.createQuery(hqlQuery, ExpenseSubcategory.class)
                    .setParameter("expenseCategoryId", expenseCategoryId)
                    .getResultList();

            session.getTransaction().commit();
            return expenseSubcategories;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
