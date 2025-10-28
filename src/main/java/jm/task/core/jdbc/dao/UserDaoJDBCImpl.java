package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = new Util().getNewConnection();
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS User";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS User " +
            "(id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, name VARCHAR(15)," +
            " lastName VARCHAR(15), age TINYINT)";
    private static final String ADD = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM User WHERE id = ?";
    private static final String GET_INFO = "SELECT * FROM User";
    private static final String CLEAN_TABLE = "DELETE FROM User";

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement request = connection.prepareStatement(ADD)) {
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public List<User> getAllUsers() {
        List<User> dataList = new ArrayList<>();
        try (ResultSet resultSet = connection.createStatement().executeQuery(GET_INFO)) {
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
            request.executeUpdate(CLEAN_TABLE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    }
