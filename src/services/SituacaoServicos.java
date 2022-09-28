package services;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DAOFactory;
import dao.SituacaoDAO;
import model.Situacao;

public class SituacaoServicos {
	
	public void cadastrarPSituacao(Situacao psm) throws SQLException{
		SituacaoDAO psDAO = DAOFactory.getPsituacao();
		psDAO.cadastrarPSituacao(psm);
	}//fecha cadastrarPSituacao
	
	public ArrayList<Situacao> buscarPSituacao() throws SQLException{
		
		SituacaoDAO psDAO = DAOFactory.getPsituacao();
		return psDAO.buscarPSituacao();
		
	}//fecha buscarSituacao
	
	public void deletarPSituacao(char codSituacao) throws SQLException{
		
		SituacaoDAO psDAO = DAOFactory.getPsituacao();
		psDAO.deletarPSituacao(codSituacao);
		
	}//fecha deletarPSituacao
	
	public void alterarPSituacao(Situacao psm) throws SQLException{
		SituacaoDAO psDAO = DAOFactory.getPsituacao();
		psDAO.alterarPSituacao(psm);
		
	}//fecha alterarPSituacao
}//fecha classe
