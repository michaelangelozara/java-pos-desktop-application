package org.POS.backend.brand;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO {


    private SessionFactory sessionFactory;

    public BrandDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Brand brand){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(brand);

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(Brand brand){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(brand);

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean delete(int brandId){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Brand brand = session.createQuery("SELECT b FROM Brand b WHERE b.id = :brandId AND b.isDeleted = FALSE", Brand.class)
                            .setParameter("brandId", brandId)
                                    .getSingleResult();
            brand.setDeleted(true);
            brand.setDeletedAt(LocalDate.now());
            session.merge(brand);

            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Brand> getAllValidBrands(){
        List<Brand> brands = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            brands = session.createQuery("SELECT b FROM Brand b WHERE b.isDeleted = FALSE", Brand.class)
                    .getResultList();

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return brands;
    }

    public List<Brand> getAllValidBrandsByProductSubcategoryId(int productSubcategoryId){
        List<Brand> brands = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String hqlQuery = "SELECT b FROM Brand b LEFT JOIN FETCH b.productSubcategory WHERE b.productSubcategory.id =: productSubcategoryId AND b.isDeleted = FALSE";
            brands = session.createQuery(hqlQuery, Brand.class)
                    .setParameter("productSubcategoryId", productSubcategoryId)
                    .getResultList();

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return brands;
    }

    public Brand getValidBrandById(int brandId){
        Brand brand = null;
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            brand = session.createQuery("SELECT b FROM Brand b LEFT JOIN FETCH b.productSubcategory WHERE b.id = :brandId AND b.isDeleted = FALSE", Brand.class)
                    .setParameter("brandId", brandId)
                    .getSingleResult();

            Hibernate.initialize(brand.getProductSubcategory());
            Hibernate.initialize(brand.getProductSubcategory().getProductCategory());
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return brand;
    }
}
