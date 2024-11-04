package org.POS.backend.product;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDAO {

    private SessionFactory sessionFactory;

    public ProductDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(product);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.merge(product);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean delete(int productId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Product product = session.createQuery("SELECT p FROM Product p WHERE p.id = :productId AND p.isDeleted = FALSE", Product.class)
                    .setParameter("productId", productId)
                    .getSingleResult();
            product.setDeletedAt(LocalDate.now());
            product.setDeleted(true);
            session.merge(product);

            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Product getValidProduct(int productId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Product product = session.createQuery("SELECT p FROM Product p WHERE p.id = :productId AND p.isDeleted = FALSE", Product.class)
                    .setParameter("productId", productId)
                    .getSingleResult();

            session.getTransaction().commit();
            return product;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Product> getAllValidProducts() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Product> products = session.createQuery("SELECT p FROM Product p WHERE p.isDeleted = FALSE", Product.class)
                    .getResultList();

            session.getTransaction().commit();
            return products;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Set<Product> getAllValidProductsByProductIds(Set<Integer> productIds) {
        Set<Product> productsSet = new HashSet<>();
        try (Session session = sessionFactory.openSession()) {
            List<Product> products = session.createQuery("SELECT p FROM Product p WHERE p.id IN :productIds AND p.isDeleted = FALSE", Product.class)
                    .setParameter("productIds", productIds)
                    .getResultList();

            // Add all fetched products to the productsSet
            productsSet.addAll(products);
        } catch (Exception e) {
            e.printStackTrace();
            // You might want to handle logging or specific exception handling here
        }
        // Return the set of products, which may be empty if no products were found
        return productsSet;
    }


}
