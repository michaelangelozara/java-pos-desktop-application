package org.POS.backend.product;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
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
            // First, fetch the products without the associations
            List<Product> products = session.createQuery(
                            "SELECT p FROM Product p WHERE p.id IN :productIds AND p.isDeleted = FALSE",
                            Product.class)
                    .setParameter("productIds", productIds)
                    .getResultList();

            // Initialize the associations separately
            for (Product product : products) {
                Hibernate.initialize(product.getSaleItems());
                Hibernate.initialize(product.getPurchaseItems());
                Hibernate.initialize(product.getStocks());
                productsSet.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception logging as needed
        }
        return productsSet;
    }

    public List<Product> getAllValidProductsBelowAlertQuantity() {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createQuery(
                    "SELECT p FROM Product p WHERE p.stock <= p.alertQuantity AND p.isDeleted = FALSE",
                    Product.class
            ).getResultList();
        } catch (Exception e) {
            // Consider logging the exception
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllValidProductsByProductSubcategoryId(int productSubcategoryId) {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createQuery(
                            "SELECT p FROM Product p WHERE p.brand.productSubcategory.id = :productSubcategoryId AND p.isDeleted = FALSE",
                            Product.class
                    ).setParameter("productSubcategoryId", productSubcategoryId)
                    .getResultList();
        } catch (Exception e) {
            // Consider logging the exception
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllValidProductsWithStocksByProductSubcategoryId(int productSubcategoryId) {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createQuery(
                            "SELECT p FROM Product p WHERE p.brand.productSubcategory.id = :productSubcategoryId AND p.isDeleted = FALSE",
                            Product.class
                    ).setParameter("productSubcategoryId", productSubcategoryId)
                    .getResultList();
            for(var product : products){
                if(!product.getStocks().isEmpty()){
                    Hibernate.initialize(product.getStocks());
                }
            }
        } catch (Exception e) {
            // Consider logging the exception
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllValidProductByProductCode(List<String> codes) {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createQuery(
                            "SELECT p FROM Product p WHERE p.code IN: codes  AND p.isDeleted = FALSE",
                            Product.class
                    )
                    .setParameter("codes", codes).getResultList();
        } catch (Exception e) {
            // Consider logging the exception
            e.printStackTrace();
        }
        return products;
    }

}
