package org.POS.backend.return_product;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnProductDAO {

    private SessionFactory sessionFactory;

    public ReturnProductDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<ReturnProduct> getAllValidReturnedProducts(int number){
        List<ReturnProduct> returnProducts = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){

            returnProducts = session.createQuery("SELECT rp FROM ReturnProduct rp", ReturnProduct.class)
                    .setMaxResults(number)
                    .getResultList();
            for(var returnProduct : returnProducts){
                Hibernate.initialize(returnProduct.getReturnedSaleItems());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return returnProducts;
    }

    public ReturnProduct getValidReturnProductById(int id){
        ReturnProduct returnProduct = null;
        try (Session session = sessionFactory.openSession()){

            returnProduct = session.createQuery("SELECT rp FROM ReturnProduct rp WHERE rp.id =: id", ReturnProduct.class)
                    .setParameter("id", id)
                    .getSingleResult();

            Hibernate.initialize(returnProduct.getReturnedSaleItems());
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnProduct;
    }

    public List<ReturnProduct> getAllValidReturnProductsByCode(String query){
        List<ReturnProduct> returnProducts = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            returnProducts = session.createQuery("SELECT rp FROM ReturnProduct rp WHERE rp.code LIKE : query OR rp.user.name LIKE : query", ReturnProduct.class)
                    .setParameter("query", "%" + query + "%")
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnProducts;
    }

    public List<ReturnProduct> getAllValidReturnProductsByRange(LocalDate start, LocalDate end){
        List<ReturnProduct> returnProducts = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            returnProducts = session.createQuery("SELECT rp FROM ReturnProduct rp WHERE rp.returnedAt >= : start AND rp.returnedAt <= : end", ReturnProduct.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

            returnProducts.forEach(rp -> Hibernate.initialize(rp.getReturnedSaleItems()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnProducts;
    }
}
