package org.POS.backend.expense_subcategory;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.expense_category.ExpenseCategory;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseSubcategoryDAO {

    private SessionFactory sessionFactory;

    public ExpenseSubcategoryDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(ExpenseSubcategory expenseSubcategory, UserLog userLog){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(expenseSubcategory);

            session.persist(userLog);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void update(ExpenseSubcategory expenseSubcategory, UserLog userLog){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(expenseSubcategory);

            session.persist(userLog);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(int expenseSubcategoryId, UserLog userLog){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            ExpenseSubcategory expenseCategory = session.createQuery("SELECT es FROM ExpenseSubcategory es WHERE es.id = :expenseSubcategoryId AND es.isDeleted = FALSE ", ExpenseSubcategory.class)
                            .setParameter("expenseSubcategoryId", expenseSubcategoryId)
                                    .getSingleResult();
            expenseCategory.setDeleted(true);
            expenseCategory.setDeletedAt(LocalDate.now());
            session.merge(expenseCategory);

            userLog.setCode(expenseCategory.getCode());
            session.persist(userLog);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ExpenseSubcategory getValidExpenseSubcategoryById(int expenseSubcategoryId){
        ExpenseSubcategory expenseSubcategory = null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            expenseSubcategory = session.createQuery("SELECT es FROM ExpenseSubcategory es WHERE es.id = :expenseSubcategoryId AND es.isDeleted = FALSE ", ExpenseSubcategory.class)
                    .setParameter("expenseSubcategoryId", expenseSubcategoryId)
                    .getSingleResult();

            session.getTransaction().commit();
            return expenseSubcategory;
        }catch (Exception e){
            e.printStackTrace();
        }

        return expenseSubcategory;
    }

    public List<ExpenseSubcategory> getAllValidExpenseSubcategories(){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String hqlQuery = "SELECT es FROM ExpenseSubcategory es LEFT JOIN FETCH es.expenseCategory WHERE es.isDeleted = FALSE ";
            List<ExpenseSubcategory> expenseSubcategories = session.createQuery(hqlQuery, ExpenseSubcategory.class)
                            .getResultList();

            session.getTransaction().commit();
            return expenseSubcategories;
        }
    }

    public List<ExpenseSubcategory> getAllValidExpenseSubcategoriesByExpenseCategoryId(
            int expenseCategoryId
    ){
        List<ExpenseSubcategory> expenseSubcategories = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String hqlQuery = "SELECT es FROM ExpenseSubcategory es WHERE es.expenseCategory.id = :expenseCategoryId AND es.isDeleted = FALSE ";
            expenseSubcategories = session.createQuery(hqlQuery, ExpenseSubcategory.class)
                    .setParameter("expenseCategoryId", expenseCategoryId)
                    .getResultList();

            session.getTransaction().commit();
            return expenseSubcategories;
        }catch (Exception e){
            e.printStackTrace();
        }
        return expenseSubcategories;
    }

    public List<ExpenseSubcategory> getAllValidExpenseSubcategoryByExpenseCategoryId(int expenseCategoryId){
        List<ExpenseSubcategory> expenseSubcategories = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            expenseSubcategories = session.createQuery("SELECT es FROM ExpenseSubcategory es WHERE es.expenseCategory.id =: expenseCategoryId AND es.isDeleted = FALSE ", ExpenseSubcategory.class)
                    .setParameter("expenseCategoryId",expenseCategoryId)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return expenseSubcategories;
    }

    public List<ExpenseSubcategory> getAllValidExpenseSubcategoryByName(String name) {
        List<ExpenseSubcategory> expenseSubategories = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            // HQL query to find expense categories where name contains the specified string
            String hql = "SELECT es FROM ExpenseSubcategory es WHERE es.name LIKE :name AND es.isDeleted = FALSE ";
            expenseSubategories = session.createQuery(hql, ExpenseSubcategory.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseSubategories;
    }
}
