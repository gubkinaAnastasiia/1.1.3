package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoJDBCImpl implements UserDao{

    private Connection CONNECTION;

    public UserDaoJDBCImpl() {
        Util util = new Util();
        CONNECTION = util.getConnection();
    }

    public void setConnection(Connection connection) {
        CONNECTION = connection;
    }

    public void createUsersTable() {
        String CREATE_TABLE = "create table if not exists users" +
                "(id int AUTO_INCREMENT PRIMARY KEY NOT NULL ," +
                "name varchar(15) null," +
                "lastName varchar(20) null," +
                "age int null)";
        try {
            Statement statement = CONNECTION.createStatement();

            statement.executeUpdate(CREATE_TABLE);

            statement.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка в создании таблицы.");
            throwables.printStackTrace();
        }
    };

    public void dropUsersTable() {
        String DROP_TABLE = "drop table if exists users";
        try {
            Statement statement = CONNECTION.createStatement();

            statement.executeUpdate(DROP_TABLE);

            statement.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка в удалении таблицы");
            throwables.printStackTrace();
        }
    };

    public void saveUser(String name, String lastName, byte age) {
        String SAVE_USER = "insert into users(name, lastName, age) values (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(SAVE_USER);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();

            System.out.println("User с именем " + name + " добавлен в базу данных.");
            preparedStatement.close();
        } catch (SQLException throwables) {
            System.err.println("Ошибка в добавлении пользователя.");
            throwables.printStackTrace();
        }
    };

    public void removeUserById(long id) {
        String REMOVE_USER = "delete from users where id=(?)";

        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(REMOVE_USER);

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException throwables) {
            System.err.println("Проблема с удалением пользователя.");
            throwables.printStackTrace();
        }
    };

    public List<User> getAllUsers() {
        String GET_ALL_USER = "select * from users";

        try {
            Statement statement = CONNECTION.createStatement();

            List<User> result = new ArrayList<>();
            ResultSet set = statement.executeQuery(GET_ALL_USER);

            while (set.next()) {
                User user = new User(set.getString("name"), set.getString("lastName"), set.getByte("age"));
                user.setId(set.getLong("id"));
                result.add(user);
            }

            statement.close();
            return result;
        } catch (SQLException throwables) {
            System.err.println("Проблема с выдачей списка.");
            throwables.printStackTrace();
            return null;
        }
    };

    public void cleanUsersTable() {
        String CLEAN_USERS = "delete from users";

        try {
            Statement statement = CONNECTION.createStatement();

            statement.executeUpdate(CLEAN_USERS);

            statement.close();
        } catch (SQLException throwables) {
            System.err.println("Проблема с очисткой таблицы.");
            throwables.printStackTrace();
        }
    };
}

