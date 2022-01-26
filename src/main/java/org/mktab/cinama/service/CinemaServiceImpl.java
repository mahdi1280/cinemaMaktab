package org.mktab.cinama.service;

import org.mktab.cinama.model.*;

import java.sql.Date;
import java.sql.SQLException;

public interface CinemaServiceImpl {

    void saveUser(String username, String password, Role role) throws SQLException;

    void showCinema() throws SQLException;

    void showTicket(int cinemaId) throws SQLException;

    void approvedCinema(int id, boolean approved) throws SQLException;

    void saveTicket(Ticket ticket) throws SQLException;

    void showTicket() throws SQLException;

    void search(String name, Date date) throws SQLException;

    User login(String username, String password) throws SQLException;

    Cinema findCinemaIdByUser(User user) throws SQLException;

    void saveCinema(String name, int userId) throws SQLException;

    void saveReserve(Reserve reserve) throws SQLException;

    void ticketApproved(int cinemaId, boolean approved) throws SQLException;

    void showReserveByUser(User user) throws SQLException;

    void showFactorByUser(User user) throws SQLException;
}
