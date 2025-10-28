package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        if (sFactory==null) {
            sFactory = new Util().getSessionFactory();
        }
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        }
    }



    @Override
    public void dropUsersTable() {
        if (sFactory==null) {
            sFactory = new Util().getSessionFactory();
        }
       try (Session session = sFactory.openSession()) {
           session.beginTransaction();
           session.createNativeQuery(DROP_TABLE).executeUpdate();
           session.getTransaction().commit();
       }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        if (sFactory==null) {
            sFactory = new Util().getSessionFactory();
        }
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            User user = new User(null, name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        if (sFactory==null) {
            sFactory = new Util().getSessionFactory();
        }
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            User userToDelete = session.get(User.class, id);
            if (userToDelete != null) {
                session.remove(userToDelete);
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
        if (sFactory==null) {
            sFactory = new Util().getSessionFactory();
        }
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery(SELECT, User.class).getResultList();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        if (sFactory==null) {
            sFactory = new Util().getSessionFactory();
        }
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(DELETE).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
