package org.mktab.cinama.repository;

import org.mktab.cinama.model.Ticket;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {

    public static void saveTicket(Connection connection, Ticket ticket) throws SQLException {
        String sql = "insert into ticket(name,price,count,date,approved,cinema_id) values" +
                "(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, ticket.getName());
        preparedStatement.setInt(2, ticket.getPrice());
        preparedStatement.setInt(3, ticket.getCinemaId());
        preparedStatement.setDate(4, ticket.getDate());
        preparedStatement.setBoolean(5, ticket.isApproved());
        preparedStatement.setInt(6, ticket.getCinemaId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void changeTicket(Connection connection, int id, boolean approved) throws SQLException {
        String update = "update ticket set approved = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setBoolean(1, approved);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static List<Ticket> getAll(Connection connection) throws SQLException {
        String find = "select * from ticket where approved = true";
        PreparedStatement preparedStatement = connection.prepareStatement(find);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Ticket> ticket=null ;
        if(resultSet!=null)
            ticket = createTicket(resultSet);
        preparedStatement.close();
        return ticket;
    }

    public static int getPriceById(Connection connection, int id) throws SQLException {
        String find = "select * from ticket where ticket.id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(find);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("price");
    }

    public static List<Ticket> findByNameAndDate(Connection connection, String name, Date date) throws SQLException {
        String find = "select * from ticket where name like ? and ticket.date > ?";
        PreparedStatement preparedStatement = connection.prepareStatement(find);
        preparedStatement.setString(1, name);
        preparedStatement.setDate(2, date);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Ticket> ticket=null;
        if(resultSet != null)
            ticket = createTicket(resultSet);
        preparedStatement.close();
        return ticket;
    }

    private static List<Ticket> createTicket(ResultSet resultSet) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        while (resultSet.next()) {
            Ticket ticket = Ticket.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getInt("price"))
                    .count(resultSet.getInt("count"))
                    .date(resultSet.getDate("date"))
                    .approved(resultSet.getBoolean("approved"))
                    .cinemaId(resultSet.getInt("cinema_id"))
                    .build();
            tickets.add(ticket);
        }
        return tickets;
    }

    public static void createTable(Connection connection) throws SQLException {
        String create = "create table if not exists ticket(\n" +
                "    id serial primary key ,\n" +
                "    name varchar(200),\n" +
                "    price int,\n" +
                "    count int,\n" +
                "    date date,\n" +
                "    approved boolean,\n" +
                "    cinema_id integer,\n" +
                "    constraint fk_ci foreign key (cinema_id) references cinema(id)\n" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(create);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static List<Ticket> findTicketByCinemaId(Connection connection,int cinemaId) throws SQLException {
        String sql ="select * from ticket where cinema_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,cinemaId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet != null)
            return createTicket(resultSet);
        return null;
    }

    public static Ticket findByTicketId(Connection connection,int ticketId) throws SQLException {
        String sql="select * from ticket where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,ticketId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet == null)
            return null;
        List<Ticket> ticket = createTicket(resultSet);
        if(ticket.size()>0)
            return ticket.get(0);
        return null;
    }

    public static boolean checkDateByCinemaId(Connection connection, int cinemaId) throws SQLException {
        String sql = "select * from ticket where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,cinemaId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next())
            return true;
        Date date = resultSet.getDate("date");
        LocalDate now = LocalDate.now();
        if(date.after(Date.valueOf(now))){
            return false;
        }
        return true;
    }
}
