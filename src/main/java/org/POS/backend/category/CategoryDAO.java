package org.POS.backend.category;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class CategoryDAO {

    private SessionFactory sessionFactory;

    public CategoryDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Category category){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(category);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public void update(Category category){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(category);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public void delete(int categoryId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Category category = session.createQuery("SELECT c FROM Category c WHERE c.id = :categoryId AND c.isDeleted = FALSE", Category.class)
                            .setParameter("categoryId", categoryId)
                                    .getSingleResult();
            category.setDeleted(true);
            category.setDeletedAt(LocalDate.now());
            session.merge(category);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public List<Category> getAllValidCategories(){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<Category> categories = session.createQuery("SELECT c FROM Category c WHERE c.isDeleted = FALSE", Category.class)
                    .getResultList();

            session.getTransaction().commit();
            return categories;
        } catch (Exception e){
            System.out.println("Error saving category: " + e.getMessage());
        }
        return null;
    }

    public Category getValidCategory(int categoryId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Category category = session.createQuery("SELECT c FROM Category c WHERE c.id =: categoryId AND c.isDeleted = FALSE", Category.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();

            session.getTransaction().commit();
            return category;
        } catch (Exception e){
            System.out.println("Error saving category: " + e.getMessage());
        }
        return null;
    }
}
