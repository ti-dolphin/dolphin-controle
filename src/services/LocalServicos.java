/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.DAOFactory;
import dao.LocalDAO;
import java.sql.SQLException;
import model.Local;

/**
 *
 * @author guilherme.oliveira
 */
public class LocalServicos {

    public Local buscarLocal() throws SQLException {
        LocalDAO lDAO = DAOFactory.getLOCALDAO();
        return lDAO.buscarLocal();
    }//fecha buscarLocal
}
