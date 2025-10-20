package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getNewConnection();
    Statement statement = connection.createStatement();

    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() {
        String customerTableQuery = "CREATE TABLE IF NOT EXISTS Users " +
                "(id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15)," +
                " lastName VARCHAR(15), age TINYINT)";
        try {
            statement.executeUpdate(customerTableQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String customerTableQuery = "DROP TABLE IF EXISTS Users";
        try {
            statement.executeUpdate(customerTableQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String customerTableQuery = "INSERT Users(name, lastName, age) \n" +
                "VALUES ('"+name +"', '"+lastName+"', "+age+");";
        try {
            statement.executeUpdate(customerTableQuery);
            System.out.println("User с именем — "+name+ " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String customerTableQuery = "DELETE FROM users\n" +
                "WHERE id='"+id+"'";
        try {
            statement.executeUpdate(customerTableQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> dataList = new ArrayList<>();
        String sql = "SELECT * FROM users"; // ваш SQL-запрос
        try (ResultSet resultSet = statement.executeQuery(sql)) {
            // Обработка строк результата
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                // Преобразование данных в объект вашего класса
                User user = new User(name, lastName, age);

                // Добавление объекта в список
                dataList.add(user);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return dataList;
    }

    public void cleanUsersTable() {
        String customerTableQuery = "TRUNCATE TABLE users";
        try {
            statement.executeUpdate(customerTableQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
