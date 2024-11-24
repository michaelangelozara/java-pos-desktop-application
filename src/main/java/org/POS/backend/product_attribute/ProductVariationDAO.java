package org.POS.backend.product_attribute;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductVariationDAO {

    private SessionFactory sessionFactory;

    public ProductVariationDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<ProductVariation> getAllValidProductVariationByIds(Set<Integer> ids) {
        List<ProductVariation> productVariations = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            productVariations = session.createQuery("SELECT v FROM ProductVariation v WHERE v.id IN :ids", ProductVariation.class)
                    .setParameter("ids", ids)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return productVariations;
    }
}
