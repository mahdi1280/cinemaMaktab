package org.mktab.cinama.repository;

import org.mktab.cinama.model.Cinema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaRepository {
    

    public static void changeApproved(Connection connection, int id, boolean approved) throws SQLException {
        String update = "update cinema set approved = ? where id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setBoolean(1, approved);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    public static void createTable(Connection connection) throws SQLException {
        String create = "create table if not exists cinema(\n" +
                "            id serial primary key ,\n" +
                "            name varchar(200),\n" +
                "            approved boolean,\n" +
                "            user_id integer,\n" +
                "            constraint fk_user_id foreign key (user_id) references users(id)\n" +
                "                );";
        PreparedStatement preparedStatement = connection.prepareStatement(create);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static List<Cinema> showCinema(Connection connection) throws SQLException {
        String sql = "select * from cinema";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getCinema(resultSet);
    }

    public static Cinema findCinemaIdByUserId(Connection connection,int id) throws SQLException {
        String sql = "select * from cinema where user_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Cinema.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .approved(resultSet.getBoolean("approved"))
                .userId(resultSet.getInt("user_id"))
                .build();
    }

    public static void save(Connection connection, String name,int userId) throws SQLException {
        String sql = "insert into cinema(name,approved,user_id) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setBoolean(2,false);
        preparedStatement.setInt(3,userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    private static List<Cinema> getCinema(ResultSet resultSet) throws SQLException {
        List<Cinema> cinemas = new ArrayList<>();
        while (resultSet.next()) {
            Cinema build = Cinema.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .approved(resultSet.getBoolean("approved"))
                    .userId(resultSet.getInt("user_id"))
                    .build();
            cinemas.add(build);
        }
        return cinemas;
    }

    public static int findByUserId(Connection connection,int id) throws SQLException {
        String sql="select * from cinema where user_id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next())
            return 0;
        return resultSet.getInt("id");
    }
}
