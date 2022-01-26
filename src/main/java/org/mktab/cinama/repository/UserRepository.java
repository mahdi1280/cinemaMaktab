package org.mktab.cinama.repository;

import org.mktab.cinama.model.Role;
import org.mktab.cinama.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public static void save(Connection connection, String userName, String password, Role role) throws SQLException {
        String insertUser="insert into users(user_name,password,role)values(?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(insertUser);
        preparedStatement.setString(1,userName);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,role.toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void createTable(Connection connection) throws SQLException {
        String create="create table if not exists users(\n" +
                "    id serial primary key ,\n" +
                "    user_name varchar(200),\n" +
                "    password varchar(200),\n" +
                "    role varchar(200)\n" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(create);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static User login(Connection connection, String username, String password) throws SQLException {
        String sql="select * from users where users.user_name=? and users.password=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next())
            return null;
        return new User(resultSet.getInt("id")
                ,resultSet.getString("user_name")
                ,resultSet.getString("password")
                ,resultSet.getString("role"));
    }

}
