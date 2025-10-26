package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sFactory = new Util().getSessionFactory();
    static final String create = "CREATE TABLE IF NOT EXISTS User " +
            "(id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15)," +
            " lastName VARCHAR(15), age TINYINT)";
    static final String drop = "DROP TABLE IF EXISTS User";
    static final String select = "FROM User";
    static final String delete = "DELETE FROM User";


    @Override
    public void createUsersTable() {
        try(Session session = sFactory.openSession()){
            session.beginTransaction();
            session.createNativeQuery(create).executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public void dropUsersTable() {
       try (Session session = sFactory.openSession()) {
           session.beginTransaction();
           session.createNativeQuery(drop).executeUpdate();
           session.getTransaction().commit();
       }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
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
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery(select, User.class).getResultList();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(delete).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
