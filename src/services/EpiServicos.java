package services;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DAOFactory;
import dao.EpiDAO;
import model.Epi;

public class EpiServicos {

    public ArrayList<Epi> buscarEpi() throws SQLException {
        EpiDAO epiDAO = DAOFactory.getEpidao();
        return epiDAO.buscarEpi();
    }//fecha buscarEpi
    
    public ArrayList<Epi> filtrarEpi(String query) throws SQLException {
        EpiDAO dao = DAOFactory.getEpidao();
        return dao.filtrarEpi(query);
    }//fecha filtrarEpi
    
    public void alterar(Epi epi) throws SQLException {
        EpiDAO dao = DAOFactory.getEpidao();
        dao.alterar(epi);
    }//alterar

}//fecha classe
