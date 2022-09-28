/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.ClassificacaoDAO;
import dao.DAOFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import model.os.Classificacao;

/**
 *
 * @author ti
 */
public class ClassificacaoController {
    
    private ClassificacaoDAO dao;

    public ClassificacaoController() {
        this.dao = DAOFactory.getCLASSIFICACAODAO();
    }
    
    public ArrayList<Classificacao> buscar() throws SQLException {
        try {
            return dao.buscar();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }
}
