/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.DAOFactory;
import dao.TicketDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Ticket;

/**
 *
 * @author guilherme.oliveira
 */
public class TicketServicos {    
    public int buscarMaiorTicket() throws SQLException{
        TicketDAO tDAO = DAOFactory.getTICKETDAO();
        return tDAO.maiorTicket();
    }
}//fecha classe
