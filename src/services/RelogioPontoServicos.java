/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.DAOFactory;
import dao.ponto.RelogioPontoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ponto.RelogioPonto;

/**
 *
 * @author guilherme.oliveira
 */
public class RelogioPontoServicos {
    
    RelogioPontoDAO dao = DAOFactory.getRELOGIOPONTODAO();
    
    public ArrayList<RelogioPonto> buscar(String query) throws SQLException {
        return dao.buscar(query);
    }//fecha buscarEpi

    public void inserir(RelogioPonto relogio) throws SQLException {
        dao.inserir(relogio);
    }//alterar
    
    public void alterar(RelogioPonto relogio) throws SQLException {
        dao.alterar(relogio);
    }//alterar
}
