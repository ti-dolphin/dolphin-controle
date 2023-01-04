package services.epi;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.epi.EpiDAO;
import model.epi.Epi;

public class EpiService {
    
    private final EpiDAO epiDAO;

    public EpiService() {
        this.epiDAO = new EpiDAO();
    }

    public ArrayList<Epi> buscarEpi() throws SQLException {
        return epiDAO.buscarEpi();
    }
    
    public ArrayList<Epi> filtrarEpi(String query) throws SQLException {
        return epiDAO.filtrarEpi(query);
    }
    
    public void alterar(Epi epi) throws SQLException {
        epiDAO.alterar(epi);
    }

}
