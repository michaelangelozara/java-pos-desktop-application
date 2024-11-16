package org.POS.backend.code_generator;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.global_variable.GlobalVariable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.text.DecimalFormat;

public class CodeGeneratorService {
    private final DecimalFormat formatter = new DecimalFormat("000"); // Pads numbers to 3 digits

    private SessionFactory sessionFactory;

    public CodeGeneratorService(){
        this.sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();

            Query<Long> query = session.createQuery("SELECT COUNT(c) FROM CodeGenerator c", Long.class);
            Long count = query.uniqueResult();

            if(count == 0){
                // create an entry for code generator
                CodeGenerator codeGenerator = new CodeGenerator();
                codeGenerator.setLastCount(1);
                session.persist(codeGenerator);
            }
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String generateProductCode(String prefix) {
        Integer COUNT;
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();
            CodeGenerator counter = session.createQuery("SELECT c FROM CodeGenerator c", CodeGenerator.class)
                    .getSingleResult();
            COUNT = counter.getLastCount();

            // update the count
            counter.setLastCount(counter.getLastCount() + 1);
            session.merge(counter);

            session.getTransaction().commit();
            return prefix + formatter.format(COUNT);
        }catch (Exception e){
            e.printStackTrace();
        }

        return GlobalVariable.GENERATING_CODE_ERROR;
    }

    public String generateProductCode() {
        Integer COUNT;
        try (Session session = this.sessionFactory.openSession()){
            session.beginTransaction();
            CodeGenerator counter = session.createQuery("SELECT c FROM CodeGenerator c", CodeGenerator.class)
                    .getSingleResult();
            COUNT = counter.getLastCount();

            // update the count
            counter.setLastCount(counter.getLastCount() + 1);
            session.merge(counter);

            session.getTransaction().commit();
            return formatter.format(COUNT);
        }catch (Exception e){
            e.printStackTrace();
        }

        return GlobalVariable.GENERATING_CODE_ERROR;
    }
}