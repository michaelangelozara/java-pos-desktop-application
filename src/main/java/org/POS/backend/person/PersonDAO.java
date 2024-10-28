package org.POS.backend.person;

import org.POS.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class PersonDAO {

    private SessionFactory sessionFactory;

    public PersonDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Person person){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(person);

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(Person person){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.merge(person);

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean delete(int personId){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Person person = session.createQuery("SELECT p FROM Person p WHERE p.id = :personId AND p.isDeleted = FALSE", Person.class)
                            .setParameter("personId", personId)
                                    .getSingleResult();
            person.setDeleted(true);
            person.setDeletedAt(LocalDate.now());
            session.merge(person);

            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Person getValidPersonById(int personId){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Person person = session.createQuery("SELECT p FROM Person p WHERE p.id = :personId AND p.isDeleted = FALSE", Person.class)
                    .setParameter("personId", personId)
                    .getSingleResult();

            session.getTransaction().commit();

            return person;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Person> getAllValidPeople(){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<Person> people = session.createQuery("SELECT p FROM Person p Where p.isDeleted = FALSE ", Person.class)
                            .getResultList();

            session.getTransaction().commit();
            return people;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
