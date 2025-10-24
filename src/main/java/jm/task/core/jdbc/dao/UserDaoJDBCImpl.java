package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = new Util().getNewConnection();
    static final String dropTable = "DROP TABLE IF EXISTS user";
    static final String createTable = "CREATE TABLE IF NOT EXISTS user " +
            "(id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15)," +
            " lastName VARCHAR(15), age TINYINT)";
    static final String add = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
    static final String deleteUser = "DELETE FROM user WHERE id = ?";
    static final String getInfo = "SELECT * FROM user";
    static final String cleanTable = "DELETE FROM user";

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement request = connection.prepareStatement(add)) {
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
        try (PreparedStatement statement = connection.prepareStatement(deleteUser)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public List<User> getAllUsers() {
        List<User> dataList = new ArrayList<>();
        try (ResultSet resultSet = connection.createStatement().executeQuery(getInfo)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(id, name, lastName, age);
                dataList.add(user);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return dataList;
    }

    public void cleanUsersTable() {
        try(Statement request = connection.createStatement()) {
            request.executeUpdate(cleanTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    }
