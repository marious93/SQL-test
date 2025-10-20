package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД

    public static Connection getNewConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/user_db";
        String user = "root";
        String password = "manchester93";
        return DriverManager.getConnection(url, user, password);
    }



}
