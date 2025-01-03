package org.POS.backend.expense;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.expense_category.ExpenseCategory;
import org.POS.backend.global_variable.GlobalVariable;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    private SessionFactory sessionFactory;

    public ExpenseDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Expense expense, UserLog userLog){
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(expense);

            session.persist(userLog);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Expense expense, UserLog userLog){
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(expense);

            session.persist(userLog);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String delete(int expenseId, UserLog userLog){
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            Expense expenseToBeDeleted = session.createQuery("SELECT e FROM Expense e WHERE e.id = :expenseId AND e.isDeleted = FALSE ", Expense.class)
                    .setParameter("expenseId", expenseId)
                    .getSingleResult();

            expenseToBeDeleted.setDeleted(true);
            expenseToBeDeleted.setDeletedAt(LocalDate.now());
            session.merge(expenseToBeDeleted);

            userLog.setCode(expenseToBeDeleted.getCode());
            session.persist(userLog);

            session.getTransaction().commit();
            return GlobalVariable.EXPENSE_DELETED;
        }catch (Exception e){
            e.printStackTrace();
        }
        return GlobalVariable.EXPENSE_NOT_FOUND;
    }

    public Expense getValidExpenseById(int expenseId){
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            Expense expense = session.createQuery("SELECT e FROM Expense e WHERE e.id = :expenseId AND e.isDeleted = FALSE ", Expense.class)
                            .setParameter("expenseId", expenseId)
                                    .getSingleResult();

            if(expense != null){
                if(expense.getExpenseSubcategory() != null)
                    Hibernate.initialize(expense.getExpenseSubcategory());

                assert expense.getExpenseSubcategory() != null;
                if(expense.getExpenseSubcategory().getExpenseCategory() != null)
                    Hibernate.initialize(expense.getExpenseSubcategory().getExpenseCategory());
            }

            session.getTransaction().commit();
            return expense;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Expense> getAllValidExpenses(){
        List<Expense> expenses = new ArrayList<>();
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            expenses = session.createQuery("SELECT e FROM Expense e WHERE e.isDeleted = FALSE ", Expense.class)
                    .getResultList();

            session.getTransaction().commit();
            return expenses;
        }catch (Exception e){
            e.printStackTrace();
        }
        return expenses;
    }

    public List<Expense> getAllValidExpenseByExpenseSubcategoryId(int number, int expenseSubcategoryId){
        List<Expense> expenses = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            expenses = session.createQuery("SELECT e FROM Expense e JOIN FETCH e.expenseSubcategory ex WHERE ex.id = :expenseSubcategoryId AND e.isDeleted = FALSE ", Expense.class)
                    .setParameter("expenseSubcategoryId", expenseSubcategoryId)
                    .setMaxResults(number)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return expenses;
    }

    public BigDecimal getTheSumOfExpenses(){
        BigDecimal sum = BigDecimal.ZERO;
        try (Session session = sessionFactory.openSession()){
            sum = session.createQuery("SELECT SUM(e.amount) FROM Expense e", BigDecimal.class)
                    .getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sum;
    }

    public List<Expense> getAllValidExpenseByReason(String reason) {
        List<Expense> expenseCategories = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            // HQL query to find expense categories where reason contains the specified string
            String hql = "SELECT e FROM Expense e WHERE e.expenseReason LIKE :reason AND e.isDeleted = FALSE";
            expenseCategories = session.createQuery(hql, Expense.class)
                    .setParameter("reason", "%" + reason + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseCategories;
    }

    public List<Expense> getAllValidExpenseByRangeAndSubcategoryId(
            LocalDate start,
            LocalDate end,
            int subcategoryId
    ){
        List<Expense> expenses = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            expenses = session.createQuery("SELECT e FROM Expense e JOIN FETCH e.expenseSubcategory ex WHERE (e.createdAt >= :start AND e.createdAt <= :end) AND ex.id = :subcategoryId AND e.isDeleted = FALSE ", Expense.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .setParameter("subcategoryId", subcategoryId)
                    .getResultList();

        }catch (Exception e){
            throw e;
        }
        return expenses;
    }

    public List<Expense> getAllValidExpensesWithoutLimit(){
        List<Expense> expenses = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){

            expenses = session.createQuery("SELECT e FROM Expense e", Expense.class)
                    .getResultList();

        }catch (Exception e){
            throw e;
        }

        return expenses;
    }

}
