package org.POS.backend.department;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.ResourceClosedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
            session.remove(department);
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

            // Commit transaction
            session.getTransaction().commit();
            return department;
        }catch (Exception e){
            System.out.println("Error fetching department: " + e.getMessage());
            throw new ResourceClosedException("Department not found");
        }
    }
}
