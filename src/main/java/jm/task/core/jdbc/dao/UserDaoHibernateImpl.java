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
        try (Session currentSession = sFactory.openSession()) {
            currentSession.beginTransaction();
            currentSession.createNativeQuery(CREATE_TABLE).executeUpdate();
            currentSession.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session currentSession = sFactory.openSession()) {
            currentSession.beginTransaction();
            currentSession.createNativeQuery(DROP_TABLE).executeUpdate();
            currentSession.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction currentTransaction = null;
        try (Session currentSession = sFactory.openSession()) {
            User user = new User(name, lastName, age);
            currentTransaction = currentSession.beginTransaction();
            currentSession.save(user);
            currentTransaction.commit();
        } catch (Exception e) {
            if (currentTransaction != null) {
                currentTransaction.rollback();
            }
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {
        Transaction currentTransaction = null;
        try (Session currentSession = sFactory.openSession()) {
            currentTransaction = currentSession.beginTransaction();
            User userToDelete = currentSession.get(User.class, id);
            if (userToDelete != null) {
                currentSession.remove(userToDelete);
            }
            currentTransaction.commit();
        } catch (Exception e) {
            if (currentTransaction != null) {
                currentTransaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction currentTransaction = null;
        try (Session currentSession = sFactory.openSession()) {
            currentTransaction = currentSession.beginTransaction();
            List<User> users = currentSession.createQuery(SELECT, User.class).getResultList();
            currentTransaction.commit();
            return users;
        } catch (Exception e) {
            if (currentTransaction != null) {
                currentTransaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction currentTransaction = null;
        try (Session currentSession = sFactory.openSession()) {
            currentTransaction = currentSession.beginTransaction();
            currentSession.createNativeQuery(DELETE).executeUpdate();
            currentTransaction.commit();
        } catch (Exception e) {
            if (currentTransaction != null) {
                currentTransaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
