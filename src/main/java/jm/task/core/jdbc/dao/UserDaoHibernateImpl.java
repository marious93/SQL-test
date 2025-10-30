package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
       try (Session session = sFactory.openSession()) {
           session.beginTransaction();
           session.createNativeQuery(DROP_TABLE).executeUpdate();
           session.getTransaction().commit();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try(Session session = sFactory.openSession()) {
            User user = new User(name, lastName, age);
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = sFactory.openSession()){
            tx=session.beginTransaction();
            User userToDelete = session.get(User.class, id);
            if (userToDelete != null) {
            session.remove(userToDelete);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        try(Session session = sFactory.openSession())  {
            tx=session.beginTransaction();
            List<User> users = session.createQuery(SELECT, User.class).getResultList();
            tx.commit();
            return users;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = sFactory.openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(DELETE).executeUpdate();
            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }
    }
}
