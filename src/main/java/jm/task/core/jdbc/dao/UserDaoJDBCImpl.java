package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();
    private final Connection connection = util.getNewConnection();


    public void createUsersTable(){
        final String createTable = "CREATE TABLE IF NOT EXISTS Users " +
                "(id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15)," +
                " lastName VARCHAR(15), age TINYINT)";
        executeRequest(createTable);
    }

    public void dropUsersTable() {
        final String dropTable = "DROP TABLE IF EXISTS Users";
        executeRequest(dropTable);
    }

    public void saveUser(String name, String lastName, byte age) {
        final String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try {
            PreparedStatement request = connection.prepareStatement(sql);
            request.setString(1, name);
            request.setString(2, lastName);
            request.setInt(3, age);
            request.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        final String deleteUser = "DELETE FROM Users\n" +
                "WHERE id='"+id+"'";
        executeRequest(deleteUser);
    }

    public List<User> getAllUsers() {
        List<User> dataList = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                dataList.add(user);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return dataList;
    }

    public void cleanUsersTable() {
        final String cleanTable = "TRUNCATE TABLE Users";
        executeRequest(cleanTable);
    }
    public void executeRequest(String sql) {
        try {
            util.getStatement(connection).executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
