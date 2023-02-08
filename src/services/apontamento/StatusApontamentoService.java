/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.apontamento;

import dao.apontamento.StatusApontamentoDAO;
import java.sql.SQLException;
import java.util.List;
import model.apontamento.StatusApont;

/**
 *
 * @author ti
 */
public class StatusApontamentoService {
    
    private final StatusApontamentoDAO statusApontamentoDAO;

    public StatusApontamentoService() {
        this.statusApontamentoDAO = new StatusApontamentoDAO();
    }
    
    public List<StatusApont> buscarStatusApontamento() throws SQLException {
        return statusApontamentoDAO.filtrarStatusApontamento("");
    }
    
    public List<StatusApont> buscarStatusApontamentoSemFolga() throws SQLException {
        String query = " WHERE CODSTATUSAPONT <> 'FO'";
        
        return statusApontamentoDAO.filtrarStatusApontamento(query);
    }
}
