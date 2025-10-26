package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;


import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
       /* UserDaoJDBCImpl a = new UserDaoJDBCImpl();
        a.createUsersTable();
        a.saveUser("Name1","lastName1", (byte) 29);
        a.saveUser("Name2","lastName2", (byte) 15);
        a.saveUser("Name3","lastName3", (byte) 47);
        a.saveUser("Name4","lastName4", (byte) 24);

        List<User> users = a.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        a.cleanUsersTable();
        a.dropUsersTable();

        */

        UserDaoHibernateImpl dao = new UserDaoHibernateImpl();
      //  dao.createUsersTable();
        dao.saveUser("Name1","lastName1", (byte) 29);
        dao.saveUser("Name2","lastName2", (byte) 15);
        dao.saveUser("Name3","lastName3", (byte) 47);
        dao.saveUser("Name4","lastName4", (byte) 24);
        List<User> users = dao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }


      //  dao.dropUsersTable();
       // dao.cleanUsersTable();

    }
}
