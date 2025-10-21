package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    public  Connection getNewConnection()  {
        final String url = "jdbc:mysql://localhost:3306/user_db";
        final String user = "root";
        final String password = "manchester93";
       try {
           return DriverManager.getConnection(url, user, password);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
       return null;
    }
    public Statement getStatement(Connection con) {
        try {
            return con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
