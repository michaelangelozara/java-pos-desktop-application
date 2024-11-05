package org.POS.backend.product_subcategory;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class ProductSubcategoryDAO {

    private SessionFactory sessionFactory;

    public ProductSubcategoryDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(ProductSubcategory productSubcategory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(productSubcategory);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error adding subcategory : " + e.getMessage());
        }
    }

    public void update(ProductSubcategory productSubcategory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.merge(productSubcategory);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error updating subcategory : " + e.getMessage());
        }
    }

    public boolean delete(int subcategoryId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            ProductSubcategory productSubcategory = session.createQuery("SELECT s FROM ProductSubcategory s WHERE s.id = :subcategoryId AND s.isDeleted = FALSE", ProductSubcategory.class)
                    .setParameter("subcategoryId", subcategoryId)
                    .getSingleResult();
            productSubcategory.setDeleted(true);
            productSubcategory.setDeletedAt(LocalDate.now());
            session.merge(productSubcategory);

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting subcategory : " + e.getMessage());
        }
        return false;
    }

    public List<ProductSubcategory> getAllValidSubcategories() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<ProductSubcategory> subcategories = session.createQuery("SELECT s FROM ProductSubcategory s WHERE s.isDeleted = FALSE", ProductSubcategory.class)
                    .getResultList();

            session.getTransaction().commit();
            return subcategories;
        } catch (Exception e) {
            System.out.println("Error deleting subcategory : " + e.getMessage());
        }

        return null;
    }

    public ProductSubcategory getValidSubcategoryById(int subcategoryId) {
        ProductSubcategory productSubcategory = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            productSubcategory = session.createQuery("SELECT s FROM ProductSubcategory s WHERE s.id = :subcategoryId AND s.isDeleted = FALSE", ProductSubcategory.class)
                    .setParameter("subcategoryId", subcategoryId)
                    .getSingleResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error getting subcategory : " + e.getMessage());
        }

        return productSubcategory;
    }

    public List<ProductSubcategory> getAllValidSubcategoriesByCategoryId(int categoryId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<ProductSubcategory> subcategories = session.createQuery("SELECT s FROM ProductSubcategory s WHERE s.category.id = :categoryId AND s.isDeleted = FALSE", ProductSubcategory.class)
                    .setParameter("productCategoryId", categoryId)
                    .getResultList();

            session.getTransaction().commit();
            return subcategories;
        } catch (Exception e) {
            System.out.println("Error getting subcategories : " + e.getMessage());
        }

        return null;
    }
}
