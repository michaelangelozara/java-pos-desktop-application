package org.POS.backend.user;

import org.POS.backend.configuration.HibernateUtil;
import org.POS.backend.exception.ResourceNotFoundException;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
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
           throw new IllegalArgumentException("Username is already existing.");
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



    public List<User> getAllValidUsers() {
        List<User> users = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            UserRole role = UserRole.SUPER_ADMIN;
            users = session.createQuery("SELECT u FROM User u WHERE u.isDeleted = FALSE AND u.role != :role", User.class)
                    .setParameter("role", role)
                    .getResultList();

            session.getTransaction().commit();;
        } catch (Exception e){
            System.out.println("Error deleting user: " + e.getMessage());
        }

        return users;
    }

    public User getUserById(int userId){
        User user = null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            user = session.createQuery("SELECT u FROM User u WHERE u.id = :userId AND u.isDeleted = FALSE", User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            if(!user.getSales().isEmpty()){
                Hibernate.initialize(user.getSales());
            }

            if(!user.getSales().isEmpty()){
                Hibernate.initialize(user.getSales());
            }

            if(!user.getStocks().isEmpty()){
                Hibernate.initialize(user.getStocks());
            }

            if(!user.getExpenses().isEmpty()){
                Hibernate.initialize(user.getExpenses());
            }

            if(!user.getOpenCashes().isEmpty()){
                Hibernate.initialize(user.getOpenCashes());
            }

            if(!user.getCashTransactions().isEmpty()){
                Hibernate.initialize(user.getCashTransactions());
            }

            if(!user.getPurchases().isEmpty()){
                Hibernate.initialize(user.getPurchases());
            }

            if(!user.getInventoryAdjustments().isEmpty()){
                Hibernate.initialize(user.getInventoryAdjustments());
            }
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public User authenticateUserByUsernameAndPassword(String username){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            User loggedInUser = session.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.isDeleted = FALSE", User.class)
                            .setParameter("username", username)
                                            .getSingleResult();
            session.getTransaction().commit();
            return loggedInUser;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<User> getAllValidUserByName(String name){
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()){
            UserRole role = UserRole.SUPER_ADMIN;
            users = session.createQuery("SELECT u FROM User u WHERE (u.name LIKE :name AND u.isDeleted = FALSE) AND u.role !=: role", User.class)
                    .setParameter("name", "%" + name + "%")
                    .setParameter("role", role)
                    .getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

}
