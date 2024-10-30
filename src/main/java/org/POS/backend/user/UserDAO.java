package org.POS.backend.user;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.exception.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class UserDAO {

    private SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void add(User user){
       try(Session session = sessionFactory.openSession()){
           session.beginTransaction();

           session.persist(user);

           session.getTransaction().commit();
       } catch (Exception e){
           System.out.println("Error saving user: " + e.getMessage());
       }
    }

    public void update(User user){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.merge(user);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    public void delete(int userId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            User userToBeDeleted = session.createQuery("SELECT u FROM User u WHERE u.id =: userId", User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();

            userToBeDeleted.setDeletedAt(LocalDate.now());
            userToBeDeleted.setDeleted(true);
            session.merge(userToBeDeleted);

            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }



    public List<User> getAllValidUsers() throws ResourceNotFoundException {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<User> users = session.createQuery("SELECT u FROM User u WHERE u.isDeleted = FALSE", User.class)
                            .getResultList();

            session.getTransaction().commit();
            return users;
        } catch (Exception e){
            System.out.println("Error deleting user: " + e.getMessage());
        }

        return null;
    }

    public User getUserById(int userId){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            User user = session.createQuery("SELECT u FROM User u WHERE u.id = :userId AND u.isDeleted = FALSE", User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();

            session.getTransaction().commit();
            return user;
        } catch (Exception e){
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return null;
    }

    public User authenticateUserByUsernameAndPassword(String username, String password){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User loggedInUser = session.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND u.isDeleted = FALSE", User.class)
                            .setParameter("username", username)
                                    .setParameter("password", password)
                                            .getSingleResult();
            session.getTransaction().commit();
            return loggedInUser;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

}
