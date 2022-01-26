package org.mktab.cinama.repository;

import org.mktab.cinama.model.Reserve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveRepository {

    public static void save(Connection connection, Reserve reserve) throws SQLException {
        String insert = "insert into reserve(count,ticket_id,user_id,price) values (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1,reserve.getCount());
        preparedStatement.setInt(2,reserve.getTicketId());
        preparedStatement.setInt(3,reserve.getUserId());
        preparedStatement.setInt(4,
                reserve.getCount() * TicketRepository.getPriceById(connection,reserve.getTicketId()));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void createTable(Connection connection) throws SQLException {
        String create="create table if not exists reserve(\n" +
                "    id serial primary key ,\n" +
                "    count integer,\n" +
                "    price integer,\n" +
                "    ticket_id integer,\n" +
                "    user_id integer,\n" +
                "    constraint fk_u foreign key (user_id) references users(id),\n" +
                "    constraint fk_ti foreign key (ticket_id) references ticket(id)\n" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(create);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static int getCountByTicketId(Connection connection, int ticketId) throws SQLException {
        String sql="select sum(count) as count from reserve where ticket_id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,ticketId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet == null)
            return 0 ;
        resultSet.next();
        return resultSet.getInt("count");
    }

    public static List<Reserve> getTicketByUserId(Connection connection, int id) throws SQLException {
        String sql="select * from reserve where user_id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return createReserve(resultSet);
    }

    private static List<Reserve> createReserve(ResultSet resultSet) throws SQLException {
        List<Reserve> reserves=new ArrayList<>();
        while (resultSet.next()){
           reserves.add( Reserve.builder()
                    .setCount(resultSet.getInt("count"))
                    .setTicketId(resultSet.getInt("ticket_id"))
                    .setPrice(resultSet.getInt("price"))
                    .setUserId(resultSet.getInt("user_id"))
                    .setId(resultSet.getInt("id"))
                    .build());
        }
        return reserves;
    }

    public static Map<Integer, Integer> getFactorByCinemaId(Connection connection, int ticketId) throws SQLException {
        HashMap<Integer,Integer> map=new HashMap<>();
        String sql="select sum(count) as count , sum(price) as price from reserve " +
                "where ticket_id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,ticketId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next())
        {
           map.put(1,1);
        }
        map.put(resultSet.getInt("count"),resultSet.getInt("price"));
        return map;
    }
}
