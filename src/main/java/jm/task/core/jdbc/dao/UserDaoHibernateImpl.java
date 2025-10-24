package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sFactory = new Util().getSessionFactory();
    static final String create = "CREATE TABLE IF NOT EXISTS User " +
            "(id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15)," +
            " lastName VARCHAR(15), age TINYINT)";
    static final String drop = "DROP TABLE IF EXISTS User";
    static final String select = "FROM User";


    @Override
    public void createUsersTable() {
        Session session = sFactory.openSession();
        session.createNativeQuery(create).executeUpdate();
    }

    @Override
    public void dropUsersTable() {
        Session session = sFactory.openSession();
        session.createNativeQuery(drop).executeUpdate();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            User user = new User(null, name, lastName, age);
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            User userToDelete = session.get(User.class, id);
            if (userToDelete != null) {
                session.delete(userToDelete);
            } else {
                System.out.println("User not found");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sFactory.openSession()) {
            Query<User> query = session.createQuery(select, User.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sFactory.openSession()) {
            session.createNativeQuery("TRUNCATE TABLE User").executeUpdate();
        }
    }
}
