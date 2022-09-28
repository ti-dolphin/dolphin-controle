/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.ContatoDAO;
import exceptions.CampoEmBrancoException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Contato;

/**
 *
 * @author guilh
 */
public class ContatoController {
    
    private ContatoDAO dao;

    public ContatoController() {
        this.dao = new ContatoDAO();
    }
        
    public void cadastrar(Contato contato) throws SQLException {
        dao.inserir(contato);
    }
    
    public ArrayList<Contato> listar() throws SQLException, CampoEmBrancoException {
        return dao.listar();
    }
    
    public Contato buscarPorId(int id) throws SQLException, CampoEmBrancoException {
        return dao.buscarPorId(id);
    }

    public void editar(Contato contato) throws SQLException {
        dao.editar(contato);
    }
}
