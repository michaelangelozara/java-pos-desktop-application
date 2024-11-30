package org.POS.backend.product;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.user_log.UserLog;
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

    public void add(Product product, UserLog userLog) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(product);

            session.persist(userLog);

            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public void update(Product product, UserLog userLog) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.merge(product);
            session.persist(userLog);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Product product, UserLog userLog) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.merge(product);
            session.persist(userLog);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product getValidProductById(int productId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Product product = session.createQuery("SELECT p FROM Product p WHERE p.id = :productId AND p.isDeleted = FALSE", Product.class)
                    .setParameter("productId", productId)
                    .getSingleResult();

            Hibernate.initialize(product.getInventoryAdjustments());
            Hibernate.initialize(product.getProductAttributes());
            product.getProductAttributes().forEach(p -> Hibernate.initialize(p.getProductVariations()));
            Hibernate.initialize(product.getStocks());
            session.getTransaction().commit();
            return product;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Product> getAllValidProductsWithoutLimit() {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            products = session.createQuery("SELECT p FROM Product p WHERE p.isDeleted = FALSE", Product.class)
                    .getResultList();

            for(var product : products){
                Hibernate.initialize(product.getStocks());
                Hibernate.initialize(product.getProductAttributes());
                product.getProductAttributes().forEach(p -> Hibernate.initialize(p.getProductVariations()));
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return products;
    }

    public List<Product> getAllValidProducts(){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            products = session.createQuery("SELECT p FROM Product p WHERE p.isDeleted = FALSE", Product.class)
                    .getResultList();

            products.forEach(p -> {
                Hibernate.initialize(p.getStocks());
                Hibernate.initialize(p.getProductAttributes());
                p.getProductAttributes().forEach(pa -> Hibernate.initialize(pa.getProductVariations()));
            });
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
                Hibernate.initialize(product.getSaleProducts());
                Hibernate.initialize(product.getStocks());
                Hibernate.initialize(product.getQuotedItems());
                productsSet.add(product);
                product.getProductAttributes().forEach(p -> {
                    Hibernate.initialize(p.getProductVariations());
                    p.getProductVariations().forEach(v -> Hibernate.initialize(v.getSaleProducts()));
                });
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

            products.forEach(p -> {
                Hibernate.initialize(p.getProductAttributes());
                p.getProductAttributes().forEach(a -> Hibernate.initialize(a.getProductVariations()));
            });
        } catch (Exception e) {
            // Consider logging the exception
            e.printStackTrace();
        }
        return products;
    }


    public List<Product> getAllValidProductsWithStocksByProductSubcategoryId(int productCategoryId) {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createQuery(
                            "SELECT p FROM Product p WHERE p.productCategory.id = :productCategoryId AND p.isDeleted = FALSE",
                            Product.class
                    ).setParameter("productCategoryId", productCategoryId)
                    .getResultList();
            for(var product : products){
                if(!product.getStocks().isEmpty()){
                    Hibernate.initialize(product.getStocks());
                    Hibernate.initialize(product.getProductAttributes());
                    product.getProductAttributes().forEach(p -> Hibernate.initialize(p.getProductVariations()));
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
//        try (Session session = sessionFactory.openSession()) {
//            products = session.createQuery(
//                            "SELECT p FROM Product p WHERE p.code IN: codes  AND p.isDeleted = FALSE",
//                            Product.class
//                    )
//                    .setParameter("codes", codes).getResultList();
//
//            for(var product : products){
//                Hibernate.initialize(product.getPurchaseItems());
//                Hibernate.initialize(product.getQuotedItems());
//            }
//        } catch (Exception e) {
//            // Consider logging the exception
//            e.printStackTrace();
//        }
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

    public List<Product> getALlValidProductsByRangeAndCategoryId(LocalDate start, LocalDate end, int categoryId){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            products = session.createQuery("SELECT p FROM Product p WHERE p.productCategory.id =: categoryId AND (p.date >= :start AND p.date <= :end) AND p.isDeleted = FALSE", Product.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .setParameter("categoryId", categoryId)
                    .getResultList();

            for(var product : products){
                Hibernate.initialize(product.getStocks());
                Hibernate.initialize(product.getProductAttributes());
                product.getProductAttributes().forEach(p -> Hibernate.initialize(p.getProductVariations()));
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

    public List<Product> getAllValidProductWithoutLimit(){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            products = session.createQuery("SELECT p FROM Product p WHERE p.isDeleted = FALSE", Product.class)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllValidProductByCategoryId(int id, ProductType type){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            products = session.createQuery("SELECT p FROM Product p WHERE (p.productCategory.id =: id AND p.isDeleted = FALSE) AND p.productType =: type", Product.class)
                    .setParameter("id", id)
                    .setParameter("type", type)
                    .getResultList();

            products.forEach(p -> {
                Hibernate.initialize(p.getStocks());
                Hibernate.initialize(p.getProductAttributes());
                p.getProductAttributes().forEach(pa -> Hibernate.initialize(pa.getProductVariations()));
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllValidProductByCategoryId(int id){
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            products = session.createQuery("SELECT p FROM Product p WHERE p.productCategory.id =: id AND p.isDeleted = FALSE", Product.class)
                    .setParameter("id", id)
                    .getResultList();

            products.forEach(p -> {
                Hibernate.initialize(p.getStocks());
                Hibernate.initialize(p.getProductAttributes());
                p.getProductAttributes().forEach(pa -> Hibernate.initialize(pa.getProductVariations()));
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    public int getProductTotalQuantity(){
        int[] totalQuantity = new int[1];
        try(Session session = sessionFactory.openSession()){
            List<Product> products = session.createQuery("SELECT p FROM Product p", Product.class)
                    .getResultList();
            products.forEach(p -> {
                if(p.getProductType().equals(ProductType.SIMPLE)){
                    totalQuantity[0] += p.getStock();
                }else{
                    for(var attribute : p.getProductAttributes()){
                        for(var variation : attribute.getProductVariations()){
                            totalQuantity[0] += variation.getQuantity();
                        }
                    }
                }
            });
        }catch (Exception e){
            throw e;
        }
        return totalQuantity[0];
    }
}
