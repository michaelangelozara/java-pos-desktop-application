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
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            products = session.createQuery("SELECT p FROM Product p WHERE p.isDeleted = FALSE", Product.class)
                    .setMaxResults(50)
                    .getResultList();

            for(var product : products){
                if(!product.getStocks().isEmpty()){
                    Hibernate.initialize(product.getStocks());
                }
            }
            session.getTransaction().commit();
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
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

    public List<Product> getAllValidProductsByProductSubcategoryId(int productSubcategoryId, boolean isGreaterThanZero) {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {

            String query = "";
            if(isGreaterThanZero){
                query = "SELECT p FROM Product p WHERE p.brand.productSubcategory.id = :productSubcategoryId AND p.isDeleted = FALSE AND p.stock > 0";
            }else {
                query = "SELECT p FROM Product p WHERE p.brand.productSubcategory.id = :productSubcategoryId AND p.isDeleted = FALSE";
            }
            products = session.createQuery(
                            query,
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

    public List<Product> getAllValidProductByProductCode(Set<String> codes) {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createQuery(
                            "SELECT p FROM Product p WHERE p.code IN: codes  AND p.isDeleted = FALSE",
                            Product.class
                    )
                    .setParameter("codes", codes).getResultList();

            for(var product : products){
                Hibernate.initialize(product.getPurchaseItems());
            }
        } catch (Exception e) {
            // Consider logging the exception
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllValidProductByName(String name){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            products = session.createQuery("SELECT p FROM Product p WHERE p.name LIKE :name AND p.isDeleted = FALSE", Product.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllValidProductByNameQuantityGreaterThanZero(String name){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            products = session.createQuery("SELECT p FROM Product p WHERE (p.name LIKE :name AND p.isDeleted = FALSE) AND p.stock > 0", Product.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getALlValidProductByRangeAndSubcategoryId(LocalDate start, LocalDate end, int subcategoryId){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            products = session.createQuery("SELECT p FROM Product p JOIN FETCH p.brand b JOIN FETCH b.productSubcategory ps WHERE ps.id =:subcategoryId AND (p.date >= :start AND p.date <= :end) AND p.isDeleted = FALSE", Product.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .setParameter("subcategoryId", subcategoryId)
                    .getResultList();

            for(var product : products){
                Hibernate.initialize(product.getStocks());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getALlValidProductByRange(LocalDate start, LocalDate end){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            products = session.createQuery("SELECT p FROM Product p WHERE (p.date >= :start AND p.date <= :end) AND p.isDeleted = FALSE", Product.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

            for(var product : products){
                Hibernate.initialize(product.getStocks());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }
}
