package org.POS.backend.product_category;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDAO {

    private SessionFactory sessionFactory;

    public ProductCategoryDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(ProductCategory productCategory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(productCategory);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public void update(ProductCategory productCategory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.merge(productCategory);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public void delete(int categoryId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            ProductCategory productCategory = session.createQuery("SELECT c FROM ProductCategory c WHERE c.id = :categoryId AND c.isDeleted = FALSE", ProductCategory.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
            productCategory.setDeleted(true);
            productCategory.setDeletedAt(LocalDate.now());
            session.merge(productCategory);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error saving category: " + e.getMessage());
        }
    }

    public List<ProductCategory> getAllValidCategories() {
        List<ProductCategory> productCategories = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            productCategories = session.createQuery("SELECT c FROM ProductCategory c WHERE c.isDeleted = FALSE", ProductCategory.class)
                    .getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error getting categories: " + e.getMessage());
        }
        return productCategories;
    }

    public ProductCategory getValidCategory(int categoryId) {
        ProductCategory productCategory = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            productCategory = session.createQuery("SELECT c FROM ProductCategory c LEFT JOIN FETCH c.subcategories WHERE c.id =: categoryId AND c.isDeleted = FALSE", ProductCategory.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error getting category: " + e.getMessage());
        }
        return productCategory;
    }

    public List<ProductCategory> getAllValidProductCategoryByName(String name) {
        List<ProductCategory> productCategories = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            productCategories = session.createQuery("SELECT pc FROM ProductCategory pc WHERE pc.name LIKE :name AND pc.isDeleted = FALSE", ProductCategory.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productCategories;
    }
}
