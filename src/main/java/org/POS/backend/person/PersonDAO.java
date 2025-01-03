package org.POS.backend.person;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.user_log.UserLog;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private SessionFactory sessionFactory;

    public PersonDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(Person person, UserLog userLog){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(person);

            session.persist(userLog);

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(Person person, UserLog userLog){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.merge(person);

            session.persist(userLog);

            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean delete(int personId, UserLog userLog){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Person person = session.createQuery("SELECT p FROM Person p WHERE p.id = :personId AND p.isDeleted = FALSE", Person.class)
                            .setParameter("personId", personId)
                                    .getSingleResult();
            person.setDeleted(true);
            person.setDeletedAt(LocalDate.now());
            session.merge(person);

            userLog.setCode(person.getPersonCode());
            userLog.setAction(person.getType().equals(PersonType.CLIENT) ? UserActionPrefixes.CLIENTS_REMOVE_ACTION_LOG_PREFIX : UserActionPrefixes.SUPPLIERS_REMOVE_ACTION_LOG_PREFIX);
            session.persist(userLog);

            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Person getValidPersonById(int personId){
        Person person = null;
        try (Session session = sessionFactory.openSession()){


            person = session.createQuery("SELECT p FROM Person p WHERE p.id = :personId AND p.isDeleted = FALSE", Person.class)
                    .setParameter("personId", personId)
                    .getSingleResult();

            if(person.getType().equals(PersonType.CLIENT)){
                Hibernate.initialize(person.getSales());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return person;
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

    public Person getValidPersonByTypeAndId(int personId, PersonType type){
        Person person = null;
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            person = session.createQuery("SELECT p FROM Person p WHERE p.id = :personId AND p.isDeleted = FALSE AND p.type = :type ", Person.class)
                    .setParameter("personId", personId)
                    .setParameter("type", type)
                    .getSingleResult();

            Hibernate.initialize(person.getPurchases());
            Hibernate.initialize(person.getQuotations());
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return person;
    }

    public List<Person> getAllValidPeopleByType(PersonType type){
        List<Person> people = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            people = session.createQuery("SELECT p FROM Person p WHERE p.type = : type AND p.isDeleted = FALSE", Person.class)
                    .setParameter("type", type)
                    .getResultList();

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return people;
    }

    public List<Person> getAllValidPersonByName(String name, PersonType type){
        List<Person> people = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            people = session.createQuery("SELECT p FROM Person p WHERE (p.name LIKE :name AND p.type = :type) AND p.isDeleted = FALSE", Person.class)
                    .setParameter("name", "%" + name + "%")
                    .setParameter("type", type)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return people;
    }
}
