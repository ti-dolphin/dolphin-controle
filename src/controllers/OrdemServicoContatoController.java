/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.OrdemServicoContatoDAO;
import exceptions.CampoEmBrancoException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Contato;

/**
 *
 * @author guilh
 */
public class OrdemServicoContatoController {
    
    private OrdemServicoContatoDAO dao;

    public OrdemServicoContatoController() {
        this.dao = new OrdemServicoContatoDAO();
    }
    
    public void inserir(int ordemServicoId, int contatoId) throws SQLException {
        dao.inserir(ordemServicoId, contatoId);
    }
    
    public ArrayList<Contato> buscarPorOrdemServico(int ordemServicoId) throws SQLException, CampoEmBrancoException {
        return dao.buscarPorOrdemServicoId(ordemServicoId);
    } 

    public void excluir(int ordemServicoId, int contatoId) throws SQLException {
        dao.deletar(ordemServicoId, contatoId);
    }
        
}
