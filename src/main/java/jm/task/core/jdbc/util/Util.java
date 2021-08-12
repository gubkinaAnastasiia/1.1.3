package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String URL = "jdbc:mysql://localhost:3306/users_shem?autoReconnect=true&useSSL=false";
    private static String USER = "root";
    private static String PASSWORD = "root";
    private static Connection CONNECTION;

    public Util() {
        try {
            CONNECTION = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            System.err.println("Ошибка в подключении");
            throwables.printStackTrace();
        }
    }
    public Util(String url, String user, String password) {
        try {
            CONNECTION = DriverManager.getConnection(url, user, password);
            URL = url;
            USER = user;
            PASSWORD = password;
        } catch (SQLException throwables) {
            System.err.println("Ошибка в подключении");
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return CONNECTION;
    }
}

