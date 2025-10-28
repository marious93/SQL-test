package jm.task.core.jdbc.dao;


import jakarta.transaction.Transactional;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sFactory = new Util().getSessionFactory();

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS User " +
            "(id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15)," +
            " lastName VARCHAR(15), age TINYINT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS User";
    private static final String SELECT = "FROM User";
    private static final String DELETE = "DELETE FROM User";


    @Override
    public void createUsersTable() {
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void dropUsersTable() {
       try (Session session = sFactory.openSession()) {
           session.beginTransaction();
           session.createNativeQuery(DROP_TABLE).executeUpdate();
           session.getTransaction().commit();
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }


     @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction tx = null;
        try {
            User user = new User(name, lastName, age);
            session = sFactory.openSession();
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            try {
                if (tx != null) {
                    tx.rollback();
                }
                System.out.println(e.getMessage());
            } finally {
                session.close();
            }
        }
    }


    @Override
    public void removeUserById(long id) {
        Session session=null;
        Transaction tx=null;
        try  {
            session=sFactory.openSession();
            tx=session.beginTransaction();
            User userToDelete = session.get(User.class, id);
            session.remove(userToDelete);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e.getMessage());
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session=null;
        Transaction tx=null;
        try  {
            session=sFactory.openSession();
            session.beginTransaction();
            List<User> users = session.createQuery(SELECT, User.class).getResultList();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e.getMessage());
        }
        finally {
            if (session != null) {
                session.close();
            }

        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(DELETE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
