package org.POS.backend.subcategory;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class SubcategoryDAO {

    private SessionFactory sessionFactory;

    public SubcategoryDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Subcategory subcategory){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(subcategory);

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Error adding subcategory : " + e.getMessage());
        }
    }

    public void update(Subcategory subcategory){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(subcategory);

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Error updating subcategory : " + e.getMessage());
        }
    }

    public boolean delete(int subcategoryId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Subcategory subcategory = session.createQuery("SELECT s FROM Subcategory s WHERE s.id = :subcategoryId AND s.isDeleted = FALSE", Subcategory.class)
                            .setParameter("subcategoryId", subcategoryId)
                                    .getSingleResult();
            subcategory.setDeleted(true);
            subcategory.setDeletedAt(LocalDate.now());
            session.merge(subcategory);

            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            System.out.println("Error deleting subcategory : " + e.getMessage());
        }
        return false;
    }

    public List<Subcategory> getAllValidSubcategories(){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<Subcategory> subcategories = session.createQuery("SELECT s FROM Subcategory s WHERE s.isDeleted = FALSE", Subcategory.class)
                    .getResultList();

            session.getTransaction().commit();
            return subcategories;
        }catch (Exception e){
            System.out.println("Error deleting subcategory : " + e.getMessage());
        }

        return null;
    }

    public Subcategory getValidSubcategoryById(int subcategoryId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Subcategory subcategory = session.createQuery("SELECT s FROM Subcategory s WHERE s.id = :subcategoryId AND s.isDeleted = FALSE", Subcategory.class)
                            .setParameter("subcategoryId", subcategoryId)
                                    .getSingleResult();

            session.getTransaction().commit();
            return subcategory;
        }catch (Exception e){
            System.out.println("Error deleting subcategory : " + e.getMessage());
        }

        return null;
    }
}
