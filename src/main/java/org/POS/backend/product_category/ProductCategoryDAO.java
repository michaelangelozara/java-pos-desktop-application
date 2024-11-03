package org.POS.backend.product_category;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class ProductCategoryDAO {

    private SessionFactory sessionFactory;

    public ProductCategoryDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(ProductCategory productCategory){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(productCategory);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public void update(ProductCategory productCategory){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(productCategory);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public void delete(int categoryId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            ProductCategory productCategory = session.createQuery("SELECT c FROM ProductCategory c WHERE c.id = :categoryId AND c.isDeleted = FALSE", ProductCategory.class)
                            .setParameter("id", categoryId)
                                    .getSingleResult();
            productCategory.setDeleted(true);
            productCategory.setDeletedAt(LocalDate.now());
            session.merge(productCategory);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public List<ProductCategory> getAllValidCategories(){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<ProductCategory> categories = session.createQuery("SELECT c FROM ProductCategory c WHERE c.isDeleted = FALSE", ProductCategory.class)
                    .getResultList();

            session.getTransaction().commit();
            return categories;
        } catch (Exception e){
            System.out.println("Error getting categories: " + e.getMessage());
        }
        return null;
    }

    public ProductCategory getValidCategory(int categoryId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            ProductCategory productCategory = session.createQuery("SELECT c FROM ProductCategory c WHERE c.id =: categoryId AND c.isDeleted = FALSE", ProductCategory.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();

            Hibernate.initialize(productCategory.getSubcategories());

            session.getTransaction().commit();
            return productCategory;
        } catch (Exception e){
            System.out.println("Error getting category: " + e.getMessage());
        }
        return null;
    }
}
