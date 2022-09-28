/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.ClienteComentarioDAO;
import dao.DAOFactory;
import java.sql.SQLException;
import java.util.List;
import model.ClienteComentario;

/**
 *
 * @author ti
 */
public class ClienteComentarioController {
    
    private ClienteComentarioDAO dao;

    public ClienteComentarioController() {
        this.dao = DAOFactory.getCLIENTECOMENTARIODAO();
    }
    
    public List<ClienteComentario> buscarComentariosDoCliente(int codColigada, String codCliente) throws SQLException{
        try {
            return dao.buscarComentariosDoCliente(codColigada, codCliente);
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    } 

    public void inserir(ClienteComentario comentario, int codColigada, String codCliente) throws SQLException {
        try {
            dao.inserir(comentario, codColigada, codCliente);
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }
}
