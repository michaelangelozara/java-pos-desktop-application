package org.POS.backend.expense;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.global_variable.GlobalVariable;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class ExpenseDAO {

    private SessionFactory sessionFactory;

    public ExpenseDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Expense expense){
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(expense);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(Expense expense){
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(expense);

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String delete(int expenseId){
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            Expense expenseToBeDeleted = session.createQuery("SELECT e FROM Expense e WHERE e.id = :expenseId AND e.isDeleted = FALSE ", Expense.class)
                    .setParameter("expenseId", expenseId)
                    .getSingleResult();

            expenseToBeDeleted.setDeleted(true);
            expenseToBeDeleted.setDeletedAt(LocalDate.now());
            session.merge(expenseToBeDeleted);

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
                if(expense.getSubcategory() != null)
                    Hibernate.initialize(expense.getSubcategory());

                assert expense.getSubcategory() != null;
                if(expense.getSubcategory().getCategory() != null)
                    Hibernate.initialize(expense.getSubcategory().getCategory());
            }

            session.getTransaction().commit();
            return expense;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Expense> getAllValidExpenses(){
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            List<Expense> expenses = session.createQuery("SELECT e FROM Expense e WHERE e.isDeleted = FALSE ", Expense.class)
                    .getResultList();

            session.getTransaction().commit();
            return expenses;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
