package org.POS.backend.department;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.ResourceClosedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class DepartmentDAO {

    private SessionFactory sessionFactory;

    public DepartmentDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Department department){
        try(Session session = sessionFactory.openSession()){
            // Begin transaction
            session.beginTransaction();
            // Save the user object
            session.persist(department);
            // Commit transaction
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Error saving department: " + e.getMessage());
        }
    }

    public void update(Department department){
        try(Session session = sessionFactory.openSession()){
            // Begin transaction
            session.beginTransaction();
            // Save the user object
            session.merge(department);
            // Commit transaction
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Error updating department: " + e.getMessage());
        }
    }

    public void delete(int departmentId){
        try(Session session = sessionFactory.openSession()){
            // Begin transaction
            session.beginTransaction();

            Department department = session.createQuery("SELECT d FROM Department d WHERE d.id =: departmentId", Department.class)
                    .setParameter("departmentId", departmentId)
                            .getSingleResult();
            department.setDeletedAt(LocalDate.now());
            department.setDeleted(true);
            session.merge(department);
            // Commit transaction
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Error deleting department: " + e.getMessage());
        }
    }

    public Department getDepartmentById(int departmentId){
        try(Session session = sessionFactory.openSession()){
            // Begin transaction
            session.beginTransaction();

            Department department = session.createQuery("SELECT d FROM Department d WHERE d.id =: departmentId", Department.class)
                    .setParameter("departmentId", departmentId)
                    .getSingleResult();
            Hibernate.initialize(department.getUsers());
            // Commit transaction
            session.getTransaction().commit();
            return department;
        }catch (Exception e){
            System.out.println("Error fetching department: " + e.getMessage());
            throw new ResourceClosedException("Department not found");
        }
    }

    public List<Department> getAllValidDepartment(){
        try(Session session = sessionFactory.openSession()){
            // Begin transaction
            session.beginTransaction();

            List<Department> departments = session.createQuery("SELECT d FROM Department d WHERE d.isDeleted = FALSE", Department.class)
                    .getResultList();
            // Commit transaction
            session.getTransaction().commit();
            return departments;
        }catch (Exception e){
            System.out.println("Error fetching department: " + e.getMessage());
            throw new ResourceClosedException("Department not found");
        }
    }
}
