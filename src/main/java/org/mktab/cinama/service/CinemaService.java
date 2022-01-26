package org.mktab.cinama.service;

import org.mktab.cinama.Main;
import org.mktab.cinama.customexception.NotFoundUserException;
import org.mktab.cinama.model.*;
import org.mktab.cinama.repository.CinemaRepository;
import org.mktab.cinama.repository.ReserveRepository;
import org.mktab.cinama.repository.TicketRepository;
import org.mktab.cinama.repository.UserRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CinemaService implements CinemaServiceImpl {

    private final Connection connection;

    public CinemaService() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","root");
        UserRepository.createTable(connection);
        CinemaRepository.createTable(connection);
        TicketRepository.createTable(connection);
        ReserveRepository.createTable(connection);
    }

    @Override
    public void saveUser(String username, String password, Role role) throws SQLException {
        UserRepository.save(connection,username,password,role);
    }

    @Override
    public void showCinema() throws SQLException {
        List<Cinema> cinemas = CinemaRepository.showCinema(connection);
        for(Cinema cinema:cinemas){
            System.out.println(cinema.toString());
        }
    }

    @Override
    public void showTicket(int cinemaId) throws SQLException {
       List<Ticket> tickets= TicketRepository.findTicketByCinemaId(connection,cinemaId);
        for(Ticket ticket:tickets){
            System.out.println(ticket);
        }
    }

    @Override
    public void approvedCinema(int id,boolean approved) throws SQLException {
        CinemaRepository.changeApproved(connection,id,approved);
        System.out.println("OK~");
    }

    @Override
    public void saveTicket(Ticket ticket) throws SQLException {
        TicketRepository.saveTicket(connection,ticket);
        System.out.println("ok~");
    }

    @Override
    public void showTicket() throws SQLException {
        List<Ticket> all = TicketRepository.getAll(connection);
        for (Ticket ticket :all) {
            System.out.println(ticket);
        }
    }

    @Override
    public void search(String name,Date date) throws SQLException {
        List<Ticket> byNameAndDate = TicketRepository.findByNameAndDate(connection, name, date);
        for (Ticket ticket :byNameAndDate) {
            System.out.println(ticket);
        }
    }

    @Override
    public User login(String username, String password) throws SQLException {
        User login = UserRepository.login(connection, username, password);
        if(login == null){
            throw new NotFoundUserException("not found exception");
        }
        return login;
    }

    @Override
    public Cinema findCinemaIdByUser(User user) throws SQLException {
        return CinemaRepository.findCinemaIdByUserId(connection,user.getId());
    }

    @Override
    public void saveCinema(String name,int userId) throws SQLException {
        CinemaRepository.save(connection,name,userId);
    }

    @Override
    public void saveReserve(Reserve reserve) throws SQLException {
        Ticket ticket=TicketRepository.findByTicketId(connection,reserve.getTicketId());
        if(ticket==null){
            System.out.println("ticket is not exist");
            return;
        }
        int countBy = ReserveRepository.getCountByTicketId(connection,reserve.getTicketId());
        if(ticket.getCount() < reserve.getCount() + countBy){
            System.out.println("not count enough");
            return;
        }
        ReserveRepository.save(connection,reserve);
        System.out.println("OK~");
    }

    @Override
    public void ticketApproved(int cinemaId, boolean approved) throws SQLException {
        if(TicketRepository.checkDateByCinemaId(connection,cinemaId)){
            System.out.println("date is finish");
            return;
        }
        TicketRepository.changeTicket(connection,cinemaId,approved);
        System.out.println("Ok~");
    }

    @Override
    public void showReserveByUser(User user) throws SQLException {
       List<Reserve> reserves=ReserveRepository.getTicketByUserId(connection,user.getId());
        for(Reserve reserve:reserves){
            System.out.println(reserve);
        }
    }

    @Override
    public void showFactorByUser(User user) throws SQLException {
        int cinemaId=CinemaRepository.findByUserId(connection,user.getId());
        List<Ticket> ticketByCinemaId = TicketRepository.findTicketByCinemaId(connection, cinemaId);
        for(Ticket ticket:ticketByCinemaId) {
            Map<Integer, Integer> factor = ReserveRepository.getFactorByCinemaId(connection, ticket.getId());
            System.out.println("ticket name: "+ticket.getName()+" count: "+ factor.keySet() + "price: "+factor.values());
        }
    }
}
