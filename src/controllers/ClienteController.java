/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.DAOFactory;
import dao.os.ClienteDAO;
import java.sql.SQLException;
import model.os.Cliente;

/**
 *
 * @author ti
 */
public class ClienteController {
    
    private ClienteDAO dao;

    public ClienteController() {
        this.dao = DAOFactory.getCLIENTEDAO();
    }
    
    public void editar(Cliente cliente) throws SQLException {
        dao.editar(cliente);
    }
}
