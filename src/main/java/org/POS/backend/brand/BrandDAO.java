package org.POS.backend.brand;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
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
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<Brand> brands = session.createQuery("SELECT b FROM Brand b WHERE b.isDeleted = FALSE", Brand.class)
                    .getResultList();

            session.getTransaction().commit();
            return brands;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Brand getValidBrand(int brandId){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Brand brand = session.createQuery("SELECT b FROM Brand b WHERE b.id = :brandId AND b.isDeleted = FALSE", Brand.class)
                    .setParameter("brandId", brandId)
                    .getSingleResult();

            session.getTransaction().commit();
            return brand;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
